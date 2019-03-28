package NFA;

import NFA.transition.Transition;
import guru.nidi.graphviz.attribute.RankDir;
import guru.nidi.graphviz.attribute.Records;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.Graph;
import guru.nidi.graphviz.model.Link;
import guru.nidi.graphviz.model.LinkSource;
import guru.nidi.graphviz.model.LinkTarget;

import java.io.File;
import java.util.ArrayList;

import static guru.nidi.graphviz.attribute.Records.rec;
import static guru.nidi.graphviz.attribute.Records.turn;
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

        tempStart.simplify();

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
            ArrayList<Transition> transitions = getAllTransitions(start);
            for (Transition transition : transitions)
                System.out.println(transition.toString());
        }
    }

    public void drawPNG(Node start,String fileName) {
        try {

            guru.nidi.graphviz.model.Node graphStart = node(start.getName()).with(Records.of(rec("start", startNode.getName()),
                    turn(rec("tag0", start.getTransitions().get(0).getExpression().getSequence()))));
//            g.with(getLinks(start, "tag0", graphStart));
            Graph g = graph("tempGraph").directed()
                    .graphAttr().with(RankDir.LEFT_TO_RIGHT).with(getLinks(start, "tag0", graphStart));

            Graphviz.fromGraph(g).width(5000).render(Format.PNG).toFile(new File("example/"+fileName+".png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private ArrayList<LinkSource> getLinks(Node startNode, String inTag, guru.nidi.graphviz.model.Node graphStart) {
        ArrayList<LinkSource> targets = new ArrayList<>();
        try {
            int i = 0;
            LinkTarget[] links = new LinkTarget[startNode.getTransitions().size()];
            for (Transition transition : startNode.getTransitions()) {
                Node endNode = transition.getEnd();

                guru.nidi.graphviz.model.Node graphEnd = node(endNode.getName()).with(Records.of(makeNodes(endNode, inTag)));

                links[i] = (between(port(inTag + i), graphEnd.port(inTag, WEST)));

                targets.addAll(getLinks(endNode, inTag + i, graphEnd));
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

    private ArrayList<Transition> getAllTransitions(Node node) {
        ArrayList<Transition> transitionArrayList = new ArrayList<>();
        for (Transition transition : node.getTransitions()) {
            transitionArrayList.add(transition);
            ArrayList<Transition> t = getAllTransitions(transition.getEnd());
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
