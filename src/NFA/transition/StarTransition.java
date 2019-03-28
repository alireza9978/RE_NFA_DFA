package NFA.transition;

import NFA.Expression;
import NFA.Node;
import NFA.NodeKind;

import java.util.ArrayList;

public class StarTransition extends Transition {

    private ArrayList<Transition> transitions = new ArrayList<>();
    private ArrayList<Transition> firstTransitions = new ArrayList<>();

    public StarTransition(Node start, Node end, String sequence) {
        Node tempLeft = new Node(NodeKind.normal);
        Node tempRight = new Node(NodeKind.normal);

        Expression expression = new Expression("y");
        Transition temp = new Transition(start, tempLeft, expression);
        start.addTransition(temp);
        transitions.add(temp);
        firstTransitions.add(temp);

        expression = new Expression(sequence);
        temp = new Transition(tempLeft, tempRight, expression);
        tempLeft.addTransition(temp);
        transitions.add(temp);

        expression = new Expression("y");
        temp = new Transition(tempRight, end, expression);
        tempRight.addTransition(temp);
        transitions.add(temp);

        expression = new Expression("y");
        temp = new Transition(start, end, expression);
        start.addTransition(temp);
        transitions.add(temp);
        firstTransitions.add(temp);

        expression = new Expression("y");
        temp = new Transition(end, start, expression);
        end.addTransition(temp);
        transitions.add(temp);

    }

    public ArrayList<Transition> getTransitions() {
        return transitions;
    }

    public ArrayList<Transition> getFirstStepTransition() {
        return firstTransitions;
    }

}
