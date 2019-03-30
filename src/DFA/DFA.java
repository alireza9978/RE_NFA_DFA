package DFA;

import NFA.FinalExpressionKind;
import NFA.NFA;
import NFA.Node;
import NFA.transition.Transition;
import guru.nidi.graphviz.attribute.Color;
import guru.nidi.graphviz.attribute.Label;
import guru.nidi.graphviz.attribute.RankDir;
import guru.nidi.graphviz.attribute.Style;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.Graph;
import guru.nidi.graphviz.model.LinkSource;
import guru.nidi.graphviz.model.LinkTarget;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static guru.nidi.graphviz.model.Factory.*;

public class DFA {

    private NFA nfa;
    private DFANode trap;
    private ArrayList<DFANode> nodes;

    public DFA(NFA nfa) {
        this.nfa = nfa;
        trap = new DFANode(new ArrayList<>(), new ArrayList<>());
        trap.getMainTransitions().add(new DFATransition(trap, trap, FinalExpressionKind.a));
        trap.getMainTransitions().add(new DFATransition(trap, trap, FinalExpressionKind.b));

        convert(nfa);
    }

    public DFANode getStart() {
        for (DFANode node : nodes) {
            if (node.isStart())
                return node;
        }
        return null;
    }

    private void convert(NFA nfa) {
        //each node should have to transition
        //with lambda nodes are equal
        //when we have no transition with a|b go to trap
        nodes = equalNodes(nfa.getAllTransitions(nfa.getStartNode(), new ArrayList<>()));
        connectTrap(nodes);
        nodes.add(trap);
        showEqualNode(nodes);
        showMainTransition(nodes);
    }

    public void draw(DFANode start, String fileName) {
        Graph g = graph("tempGraph").directed().graphAttr().with(RankDir.LEFT_TO_RIGHT).with(getLinks(start, new ArrayList<>()));
        try {
            Graphviz.fromGraph(g).width(2000).render(Format.PNG).toFile(new File("example/" + fileName + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ArrayList<LinkSource> getLinks(DFANode startNode, ArrayList<DFANode> seen) {
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

        ArrayList<DFATransition> transitions = startNode.getMainTransitions();
        LinkTarget[] targets = new LinkTarget[transitions.size()];
        for (int i = 0; i < transitions.size(); i++) {
            DFATransition transition = transitions.get(i);
            guru.nidi.graphviz.model.Node target = node(transition.getEnd().getName());
            targets[i] = (to(target).with(Style.SOLID, Label.of(transition.getExpression().toString()), Color.BLACK));
        }
        linkSources.add(graphStart.link(targets));
        for (DFATransition transition : startNode.getMainTransitions()) {
            linkSources.addAll(getLinks(transition.getEnd(), seen));
        }
        return linkSources;
    }

    private ArrayList<DFANode> equalNodes(ArrayList<Transition> transitions) {
        ArrayList<DFANode> dfaNodes = new ArrayList<>();
        for (Transition transition : transitions) {
            boolean find = false;
            DFANode tempNode = null, secondTepmNode = null;
            boolean needRemove = false;
            if (transition.getExpression().getFinalExpressionKind() == FinalExpressionKind.lambda) {
                for (DFANode node : dfaNodes) {
                    if (node.getNodes().contains(transition.getStart())) {
                        if (!node.getNodes().contains(transition.getEnd())) {
                            if (!find) {
                                node.getNodes().add(transition.getEnd());
                                node.getTransitions().addAll(transition.getEnd().getTransitions());
                                find = true;
                                tempNode = node;
                            } else {
                                needRemove = true;
                                secondTepmNode = node;
                            }
                        }
                    } else {
                        if (node.getNodes().contains(transition.getEnd())) {
                            if (!node.getNodes().contains(transition.getStart())) {
                                if (!find) {
                                    node.getNodes().add(transition.getStart());
                                    node.getTransitions().addAll(transition.getStart().getTransitions());
                                    find = true;
                                    tempNode = node;
                                } else {
                                    secondTepmNode = node;
                                    needRemove = true;
                                }
                            }
                        }
                    }
                }
                if (find) {
                    if (needRemove) {
                        ArrayList<Node> nodes = new ArrayList<>(tempNode.getNodes());
                        for (Node temp : secondTepmNode.getNodes()) {
                            if (!nodes.contains(temp))
                                nodes.add(temp);
                        }
                        ArrayList<Transition> newTransitions = new ArrayList<>();
                        newTransitions.addAll(tempNode.getTransitions());
                        newTransitions.addAll(secondTepmNode.getTransitions());
                        dfaNodes.add(new DFANode(nodes, newTransitions));
                        dfaNodes.remove(tempNode);
                        dfaNodes.remove(secondTepmNode);
                    }
                } else {
                    ArrayList<Node> nodes = new ArrayList<>();
                    nodes.add(transition.getStart());
                    nodes.add(transition.getEnd());
                    ArrayList<Transition> newTransitions = new ArrayList<>();
                    newTransitions.addAll(transition.getStart().getTransitions());
                    newTransitions.addAll(transition.getEnd().getTransitions());
                    dfaNodes.add(new DFANode(nodes, newTransitions));
                }
            }
        }
        for (Transition transition : transitions) {
            if (transition.getExpression().getFinalExpressionKind() != FinalExpressionKind.lambda) {
                DFANode start = null;
                DFANode end = null;
                for (DFANode dfaNode : dfaNodes) {
                    if (dfaNode.getNodes().contains(transition.getStart())) {
                        start = dfaNode;
                    }
                    if (dfaNode.getNodes().contains(transition.getEnd())) {
                        end = dfaNode;
                    }
                }
                if (start == null && end == null) {
                    ArrayList<Node> nodes = new ArrayList<>();
                    nodes.add(transition.getStart());
                    start = new DFANode(nodes, transition.getStart().getTransitions());
                    nodes = new ArrayList<>();
                    nodes.add(transition.getEnd());
                    dfaNodes.add(start);
                    end = new DFANode(nodes, transition.getEnd().getTransitions());
                    dfaNodes.add(end);
                } else {
                    if (start == null || end == null) {
                        if (start != null) {//need to make end
                            ArrayList<Node> nodes = new ArrayList<>();
                            ArrayList<Transition> endTransition = new ArrayList<>(transition.getEnd().getTransitions());
                            nodes.add(transition.getEnd());

                            end = new DFANode(nodes, endTransition);
                            dfaNodes.add(end);
                        } else {
                            ArrayList<Node> nodes = new ArrayList<>();
                            ArrayList<Transition> startTransitions = new ArrayList<>(transition.getStart().getTransitions());
                            nodes.add(transition.getStart());

                            start = new DFANode(nodes, startTransitions);
                            dfaNodes.add(start);
                        }
                    }
                }
                start.getMainTransitions().add(new DFATransition(start, end,
                        transition.getExpression().getFinalExpressionKind()));
            }

        }
        return dfaNodes;
    }

    private void connectTrap(ArrayList<DFANode> nodes) {
        for (DFANode node : nodes) {
            ArrayList<DFATransition> arrayList = node.getMainTransitions();
            if (arrayList.size() != 2) {
                boolean a = false, b = false;
                for (DFATransition transition : arrayList) {
                    if (transition.getExpression() == FinalExpressionKind.a)
                        a = true;
                    if (transition.getExpression() == FinalExpressionKind.b)
                        b = true;
                }
                if (!a) {
                    DFANode target = getTarget(nodes, node, FinalExpressionKind.a);
                    if (target != null) {
                        node.getMainTransitions().add(new DFATransition(node, target, FinalExpressionKind.a));
                    } else {
                        node.getMainTransitions().add(new DFATransition(node, trap, FinalExpressionKind.a));
                    }
                }
                if (!b) {
                    DFANode target = getTarget(nodes, node, FinalExpressionKind.b);
                    if (target != null) {
                        node.getMainTransitions().add(new DFATransition(node, target, FinalExpressionKind.b));
                    } else {
                        node.getMainTransitions().add(new DFATransition(node, trap, FinalExpressionKind.b));
                    }
                }
            }
        }
    }

    private DFANode getTarget(ArrayList<DFANode> nodes, DFANode node, FinalExpressionKind kind) {
        for (Node temp : node.getNodes()) {
            for (Transition transition : temp.getTransitions()) {
                if (transition.getExpression().getFinalExpressionKind() == kind) {
                    for (DFANode tempTarget : nodes) {
                        if (node.getNodes().contains(transition.getEnd())) {
                            return tempTarget;
                        }
                    }
                }
            }
        }
        return null;
    }

    private void getNodes(Node start, ArrayList<Node> nodes) {
        if (nodes.contains(start)) {
            return;
        }
        nodes.add(start);
        for (Transition transition : start.getTransitions()) {
            Node end = transition.getEnd();
            getNodes(end, nodes);
        }
    }

    private void showEqualNode(ArrayList<DFANode> nodes) {
        for (DFANode node : nodes) {
            ArrayList<Node> nfaNodes = node.getNodes();
            System.out.print(node.getName() + " : ");
            for (Node temp : nfaNodes) {
                System.out.print(temp.getName());
                System.out.print(" ");
            }
            System.out.println();
        }
    }

    private void showMainTransition(ArrayList<DFANode> nodes) {
        for (DFANode node : nodes) {
            for (DFATransition temp : node.getMainTransitions()) {
                System.out.print(temp.toString());
                for (Node n : temp.getEnd().getNodes())
                    System.out.print(" " + n.getName());
                System.out.println(" ");
            }
        }
    }

    public NFA getNfa() {
        return nfa;
    }

}
