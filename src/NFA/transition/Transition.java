package NFA.transition;

import NFA.Expression;
import NFA.Node;
import NFA.NodeKind;


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

    public Transition simplify() {
        if (expression.isFinal()) {
            return this;
        } else {
            switch (expression.getOpration()) {
                case concat: {
                    return new AndTransition(start, end, expression.getFirstSequencePart(), expression.getSecondSequencePart());
                }
                case or: {
                    return new OrTransition(start, end, expression.getFirstSequencePart(), expression.getSecondSequencePart());
                }
                case star: {
                    Transition star = new StarTransition(start, end, expression.getFirstSequencePart());
                    Node tempEnd = new Node(NodeKind.normal);
                    Expression tempEx = new Expression(expression.getSecondSequencePart());
                    Transition tempAdd = new Transition(end, tempEnd, tempEx);
                    end.addTransition(tempAdd);
                    return star;
                }
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "Transition(" +
                start.getName() +
                " , " + expression.getSequence() +
                " ) = " + end.getName();
    }
}
