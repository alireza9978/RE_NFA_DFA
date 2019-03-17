package NFA.transition;

import NFA.Expression;
import NFA.Node;

public class AndTransition extends Transition {

    public AndTransition(Node start, Node end, Expression expression) {
        super(start, end, expression);
    }



}
