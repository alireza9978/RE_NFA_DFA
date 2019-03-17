package NFA.transition;

import NFA.Expression;
import NFA.Node;

import java.util.ArrayList;


public class Transition {

    private Node start;
    private Node end;
    private Expression expression;

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

    public ArrayList<Transition> simplify() {
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
        return transitions;
    }

}
