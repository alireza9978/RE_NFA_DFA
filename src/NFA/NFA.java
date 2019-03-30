package NFA;

import NFA.transition.Transition;
import guru.nidi.graphviz.attribute.*;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.Graph;
import guru.nidi.graphviz.model.LinkSource;
import guru.nidi.graphviz.model.LinkTarget;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import static guru.nidi.graphviz.attribute.Records.rec;
import static guru.nidi.graphviz.attribute.Records.turn;
import static guru.nidi.graphviz.model.Compass.EAST;
import static guru.nidi.graphviz.model.Compass.WEST;
import static guru.nidi.graphviz.model.Factory.*;

public class NFA {

    private String sequence;
    private Node startNode;
    private Node finalNode;

    public NFA(String sequence) {
        this.sequence = sequence;
        this.startNode = new Node("start", NodeKind.first);
        this.finalNode = new Node("end", NodeKind.terminal);
        Node tempStart = new Node("tempStart", NodeKind.normal);
        Node tempEnd = new Node("tempEnd", NodeKind.normal);

        //initial starting NFA.NFA
        startNode.addTransition(new Transition(startNode, tempStart, new Expression("y")));
        tempStart.addTransition(new Transition(tempStart, tempEnd, new Expression(sequence)));
        tempEnd.addTransition(new Transition(tempEnd, finalNode, new Expression("y")));

        tempStart.simplify(new ArrayList<>());

    }

    public void draw(Node start) {
//        if (start != null && !start.getTransitions().isEmpty()) {
//            for (Transition transition : start.getTransitions()) {
//                System.out.println(transition);
//                Node end = transition.getEnd();
//                draw(end);
//            }
//        }
        if (start != null && !start.getTransitions().isEmpty()) {
            ArrayList<Transition> transitions = getAllTransitions(start, new ArrayList<>());
            for (Transition transition : transitions)
                System.out.println(transition.toString());
        }
    }

    public void drawPNG(Node start, String fileName) {
        try {

            guru.nidi.graphviz.model.Node graphStart = node(start.getName()).with(Records.of(rec("start", startNode.getName()),
                    turn(rec("tag0", start.getTransitions().get(0).getExpression().getSequence()))), Style.FILLED, Color.hsv(.7, .3, 1.0));
//            g.with(getLinksV2(start, "tag0", graphStart));
            Graph g = graph("tempGraph").directed()
                    .graphAttr().with(RankDir.LEFT_TO_RIGHT).with(Objects.requireNonNull(getLinksV2(start, "tag0", graphStart, new ArrayList<>())));

            Graphviz.fromGraph(g).width(5000).render(Format.PNG).toFile(new File("example/" + fileName + ".png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ArrayList<LinkSource> getLinksV2(Node startNode, String inTag, guru.nidi.graphviz.model.Node graphStart, ArrayList<Node> seen) {
        ArrayList<LinkSource> targets = new ArrayList<>();
        if (seen.contains(startNode)) {
            return null;
        }
        try {
            seen.add(startNode);
            int i = 0;
            LinkTarget[] links = new LinkTarget[startNode.getTransitions().size()];
            for (Transition transition : startNode.getTransitions()) {
                Node endNode = transition.getEnd();
                guru.nidi.graphviz.model.Node graphEnd;
                if (endNode.getKind() == NodeKind.terminal) {
                    graphEnd = node(endNode.getName()).with(Records.of(makeNodes(endNode, inTag)), Style.SOLID, Color.rgb("10d020"));
                } else {
                    graphEnd = node(endNode.getName()).with(Records.of(makeNodes(endNode, inTag)));
                }
                ArrayList<LinkSource> arrayList = getLinksV2(endNode, inTag + i, graphEnd, seen);
                if (arrayList == null) {
                    links[i] = (between(port(inTag + i), graphEnd.port(inTag, EAST)));
                } else {
                    targets.addAll(arrayList);
                    links[i] = (between(port(inTag + i), graphEnd.port(inTag, WEST)));
                }
                i++;
            }
            targets.add(graphStart.link(links));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return targets;
    }

    private String[] makeNodes(Node node, String inTag) {
        String[] nodes = new String[node.getTransitions().size() + 1];
        ArrayList<Transition> transitions = node.getTransitions();
        nodes[0] = turn(rec(inTag, node.getName()));
        for (int i = 0; i < transitions.size(); i++) {
            Transition transition = transitions.get(i);
            nodes[i + 1] = rec(inTag + i, transition.getExpression().getSequence());
        }
        return nodes;
    }

    public void drawPngV2(Node start, String fileName) {
        Graph g = graph("tempGraph").directed().graphAttr().with(RankDir.LEFT_TO_RIGHT).with(getLinksV2(start, new ArrayList<>()));
        try {
            Graphviz.fromGraph(g).width(2000).render(Format.PNG).toFile(new File("example/" + fileName + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ArrayList<LinkSource> getLinksV2(Node startNode, ArrayList<Node> seen) {
        ArrayList<LinkSource> linkSources = new ArrayList<>();
        if (seen.contains(startNode)) {
            return linkSources;
        }
        guru.nidi.graphviz.model.Node graphStart = null;
        switch (startNode.getKind()) {
            case first:
                graphStart = node(startNode.getName()).with(Color.GREEN);
                break;
            case terminal:
                graphStart = node(startNode.getName()).with(Color.BLUE);
                break;
            case normal:
                graphStart = node(startNode.getName()).with(Color.BLACK);
                break;
            case trap:
                graphStart = node(startNode.getName()).with(Color.RED);
                break;
        }
        seen.add(startNode);

        ArrayList<Transition> transitions = startNode.getTransitions();
        LinkTarget[] targets = new LinkTarget[transitions.size()];
        for (int i = 0, transitionsSize = transitions.size(); i < transitionsSize; i++) {
            Transition transition = transitions.get(i);
            guru.nidi.graphviz.model.Node target = node(transition.getEnd().getName());
            targets[i] = (to(target).with(Style.SOLID, Label.of(transition.getExpression().getSequence()), Color.BLACK));
        }
        linkSources.add(graphStart.link(targets));
        for (Transition transition : startNode.getTransitions()) {
            linkSources.addAll(getLinksV2(transition.getEnd(), seen));
        }
        return linkSources;
    }

    public ArrayList<Transition> getAllTransitions(Node node, ArrayList<Node> seen) {
        if (seen.contains(node)) {
            return new ArrayList<>();
        }
        seen.add(node);
        ArrayList<Transition> transitionArrayList = new ArrayList<>();
        for (Transition transition : node.getTransitions()) {
            transitionArrayList.add(transition);
            ArrayList<Transition> t = getAllTransitions(transition.getEnd(), seen);
            for (Transition temp : t)
                if (!transitionArrayList.contains(temp))
                    transitionArrayList.add(temp);
        }
        return transitionArrayList;
    }

    public String getSequence() {
        return sequence;
    }

    public Node getStartNode() {
        return startNode;
    }

    public Node getFinalNode() {
        return finalNode;
    }
}
