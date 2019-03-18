package NFA;

import NFA.transition.Transition;

import java.util.ArrayList;

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
        startNode.addTransition(new Transition(startNode,tempStart,new Expression("y")));
        tempStart.addTransition(new Transition(tempStart,tempEnd,new Expression(sequence)));
        tempEnd.addTransition(new Transition(tempEnd,finalNode,new Expression("y")));

        for (Transition transition : tempStart.getTransitions()){
            transition.simplify();
        }

    }

    public void draw(Node start){
        if (start == null || start.getTransitions().isEmpty()){
            return;
        } else{
            for (Transition transition : start.getTransitions()){
                System.out.println(transition);
                Node end = transition.getEnd();
                draw(end);
            }
        }
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
