package NFA.transition;

import NFA.Expression;
import NFA.Node;

import java.util.ArrayList;


public class Transition {

    private Node start;
    private Node end;
    private Expression expression;

    Transition() {
    }

    public Transition(Node start, Node end, Expression expression) {
        this.start = start;
        this.end = end;
        this.expression = expression;
    }

    public Node getStart() {
        return start;
    }

    public Node getEnd() {
        return end;
    }

    public Expression getExpression() {
        return expression;
    }

    public void simplify() {
        ArrayList<Transition> transitions = new ArrayList<>();
        if (expression.isFinal()) {
            transitions.add(this);
        } else {
            switch (expression.getOpration()) {
                case concat: {
                    //todo complete transition class
                }
                case or: {

                }
                case star: {

                }
            }
        }
        //todo add transition to this class
    }

    @Override
    public String toString() {
        return "Transition(" +
                start.getName() +
                " , " + expression.getSequence() +
                " ) = " + end.getName();
    }
}
