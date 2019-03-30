package DFA;

import NFA.Node;
import NFA.NodeKind;
import NFA.transition.Transition;
import guru.nidi.graphviz.attribute.Color;

import java.util.ArrayList;

import static guru.nidi.graphviz.model.Factory.node;

public class DFANode {

    private static int count = 0;
    private final String name = "DFA Node " + count++;
    private ArrayList<Node> nodes;
    private ArrayList<Transition> transitions;
    private ArrayList<DFATransition> mainTransitions = new ArrayList<>();

    public DFANode(ArrayList<Node> nodes) {
        this.nodes = nodes;
        transitions = new ArrayList<>();
    }

    public NodeKind getKind() {
        if (isTrap())
            return NodeKind.trap;
        if (isFinal())
            return NodeKind.terminal;
        if (isStart())
            return NodeKind.first;
        return NodeKind.normal;
    }

    public boolean isFinal() {
        for (Node node : nodes) {
            if (node.getKind() == NodeKind.terminal) {
                return true;
            }
        }
        return false;
    }

    public boolean isTrap() {
        if (mainTransitions.size() >= 2) {
            for (DFATransition transition : mainTransitions) {
                if (!transition.getStart().equals(transition.getEnd())) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public boolean isStart() {
        for (Node node : nodes) {
            if (node.getKind() == NodeKind.first) {
                return true;
            }
        }
        return false;
    }

    public DFANode(ArrayList<Node> nodes, ArrayList<Transition> transitions) {
        this.nodes = nodes;
        this.transitions = transitions;
    }

    public ArrayList<DFATransition> getMainTransitions() {
        return mainTransitions;
    }

    public ArrayList<Node> getNodes() {
        return nodes;
    }

    public ArrayList<Transition> getTransitions() {
        return transitions;
    }

    public void setNodes(ArrayList<Node> nodes) {
        this.nodes = nodes;
    }

    public void setTransitions(ArrayList<Transition> transitions) {
        this.transitions = transitions;
    }

    public String getName() {
        if (isTrap()) {
            return "trap";
        }
        return name;
    }

    public static guru.nidi.graphviz.model.Node makeGraphNode(DFANode startNode) {
        return getNode(startNode.getKind(), startNode.getName());
    }

    public static guru.nidi.graphviz.model.Node makeGraphNode(Node startNode) {
        return getNode(startNode.getKind(), startNode.getName());
    }

    public static guru.nidi.graphviz.model.Node getNode(NodeKind kind, String name) {
        switch (kind) {
            case first:
                return node(name).with(Color.GREEN);
            case terminal:
                return node(name).with(Color.BLUE);
            case normal:
                return node(name).with(Color.BLACK);
            case trap:
                return node(name).with(Color.RED);
        }
        return null;
    }

}
