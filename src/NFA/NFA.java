package NFA;

import NFA.transition.Transition;

import java.util.ArrayList;

public class NFA {

    private String sequence;
    private Node startNode;
    private Node finalNode;
    private ArrayList<Transition> transitions;

    public NFA(String sequence) {
        this.sequence = sequence;
        this.startNode = new Node("start", NodeKind.first);
        this.finalNode = new Node("end", NodeKind.terminal);
        Node tempStart = new Node("tempStart", NodeKind.normal);
        Node tempEnd = new Node("tempEnd", NodeKind.normal);

        //initial starting NFA.NFA
        transitions = new ArrayList<>();
        transitions.add(new Transition(startNode,tempStart,new Expression("y")));
        transitions.add(new Transition(tempStart,tempEnd,new Expression(sequence)));
        transitions.add(new Transition(tempEnd,finalNode,new Expression("y")));

        for (Transition transition : tempStart.getTransitions()){
            transitions.addAll(transition.simplify());
            transitions.remove(transition);
        }

    }

    public void draw(){

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
