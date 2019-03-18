package NFA;

import NFA.transition.Transition;

import java.util.ArrayList;

public class Node {

    private static int count = 0;

    private String name;
    private NodeKind kind;
    private ArrayList<Transition> transitions;


    public Node(String name, NodeKind kind) {
        this.name = name;
        this.kind = kind;
        transitions = new ArrayList<>();
    }

    public Node(NodeKind kind) {
        this.name = "node " + count;
        count++;
        this.kind = kind;
        transitions = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public NodeKind getKind() {
        return kind;
    }

    public void addTransition(Transition transition) {
        transitions.add(transition);
    }

    public void addTransition(Node destenation, Expression expression) {
        Transition transition = new Transition(this, destenation, expression);
        transitions.add(transition);
    }

    public ArrayList<Transition> getTransitions() {
        return transitions;
    }

    @Override
    public String toString() {
        return "Node{" +
                "name='" + name + '\'' +
                ", kind=" + kind +
                '}';
    }
}
