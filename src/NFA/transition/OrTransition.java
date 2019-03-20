package NFA.transition;

import NFA.Expression;
import NFA.Node;
import NFA.NodeKind;

import java.util.ArrayList;

public class OrTransition extends Transition{

    private Node topLeft = new Node(NodeKind.normal);
    private Node topRight = new Node(NodeKind.normal);
    private Node bottomLeft = new Node(NodeKind.normal);
    private Node bottomRight = new Node(NodeKind.normal);

    private ArrayList<Transition> transitions = new ArrayList<>();
    private ArrayList<Transition> firstTransitions = new ArrayList<>();

    public OrTransition(Node start, Node end, String firstSequence, String secondSequence) {
        super(start,end,null);

        Expression expression = new Expression("y");
        Transition temp = new Transition(start, topLeft, expression);
        start.addTransition(temp);
        transitions.add(temp);
        firstTransitions.add(temp);

        expression = new Expression("y");
        temp = new Transition(start, bottomLeft, expression);
        start.addTransition(temp);
        transitions.add(temp);
        firstTransitions.add(temp);

        expression = new Expression(firstSequence);
        temp = new Transition(topLeft, topRight, expression);
        topLeft.addTransition(temp);
        transitions.add(temp);

        expression = new Expression(secondSequence);
        temp = new Transition(bottomLeft, bottomRight, expression);
        bottomLeft.addTransition(temp);
        transitions.add(temp);


        expression = new Expression("y");
        temp = new Transition(topRight, end, expression);
        topRight.addTransition(temp);
        transitions.add(temp);

        expression = new Expression("y");
        temp = new Transition(bottomRight, end, expression);
        bottomRight.addTransition(temp);
        transitions.add(temp);

    }

    public ArrayList<Transition> getTransitions() {
        return transitions;
    }

    public ArrayList<Transition> getFirstStepTransition(){
        return firstTransitions;
    }


    @Override
    public String toString() {
        StringBuilder temp = new StringBuilder();
        temp.append(transitions.get(0).toString());
        for (int i = 1; i < transitions.size(); i++) {
            Transition transition = transitions.get(i);
            temp.append("\n");
            temp.append(transition.toString());
        }
        return temp.toString();
    }
}
