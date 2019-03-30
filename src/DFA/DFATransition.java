
package DFA;

import NFA.FinalExpressionKind;

public class DFATransition {

    private DFANode start;
    private DFANode end;
    private FinalExpressionKind expression;

    public DFATransition(DFANode start, DFANode end, FinalExpressionKind expression) {
        this.start = start;
        this.end = end;
        this.expression = expression;
    }


    public DFANode getStart() {
        return start;
    }

    public DFANode getEnd() {
        return end;
    }

    public void setStart(DFANode start) {
        this.start = start;
    }

    public void setEnd(DFANode end) {
        this.end = end;
    }

    public FinalExpressionKind getExpression() {
        return expression;
    }

    public String toString() {
        return "transition ( " + start.getName() + " , " + expression + " ) = " + end.getName();
    }

}
