package NFA.transition;

import NFA.Expression;
import NFA.Node;
import NFA.NodeKind;

public class AndTransition extends Transition {

    public AndTransition(Node start, Node end, String firstSequence, String secondSequence) {
        Node left = new Node(NodeKind.normal);
        Node right = new Node(NodeKind.normal);
        Node midle = new Node(NodeKind.normal);

        Expression expression = new Expression("y");
        Transition temp = new Transition(start, left, expression);
        start.addTransition(temp);

        expression = new Expression(firstSequence);
        temp = new Transition(left, midle, expression);
        left.addTransition(temp);

        expression = new Expression(secondSequence);
        temp = new Transition(midle, right, expression);
        midle.addTransition(temp);

        expression = new Expression("y");
        temp = new Transition(right, end, expression);
        right.addTransition(temp);


    }


}
