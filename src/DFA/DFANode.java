package DFA;

import NFA.Node;
import NFA.transition.Transition;

import java.util.ArrayList;

public class DFANode {

    private ArrayList<Node> nodes;
    private ArrayList<Transition> transitions;

    public DFANode(ArrayList<Node> nodes) {
        this.nodes = nodes;
    }

    public DFANode(ArrayList<Node> nodes, ArrayList<Transition> transitions) {
        this.nodes = nodes;
        this.transitions = transitions;
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
}
