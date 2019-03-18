package NFA.transition;

import NFA.Expression;
import NFA.Node;
import NFA.NodeKind;

public class OrTransition extends Transition{

    public OrTransition(Node start, Node end, String firstSequence, String secondSequence) {
        Node topLeft = new Node(NodeKind.normal);
        Node topRight = new Node(NodeKind.normal);
        Node bottomLeft = new Node(NodeKind.normal);
        Node bottomRight = new Node(NodeKind.normal);

        Expression expression = new Expression("y");
        Transition temp = new Transition(start, topLeft, expression);
        start.addTransition(temp);

        expression = new Expression("y");
        temp = new Transition(start, bottomLeft, expression);
        start.addTransition(temp);

        expression = new Expression(firstSequence);
        temp = new Transition(topLeft, topRight, expression);
        topLeft.addTransition(temp);

        expression = new Expression(secondSequence);
        temp = new Transition(bottomLeft, bottomRight, expression);
        bottomLeft.addTransition(temp);


        expression = new Expression("y");
        temp = new Transition(topRight, end, expression);
        topRight.addTransition(temp);

        expression = new Expression("y");
        temp = new Transition(bottomRight, end, expression);
        bottomRight.addTransition(temp);

    }

}
