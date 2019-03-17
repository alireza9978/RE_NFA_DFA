public class Expression {

    private final String sequence;
    private final boolean isFinal;
    private final FinalExpressionKind finalExpressionKind;

    public Expression(String sequence) {
        this.sequence = sequence;
        if (sequence.length() == 1){
            isFinal = true;
            finalExpressionKind = FinalExpressionKind.getKind(sequence);
        }else {
            isFinal = false;
            finalExpressionKind = FinalExpressionKind.none;
        }
    }

    public String getSequence() {
        return sequence;
    }

    public boolean isFinal() {
        return isFinal;
    }

    public FinalExpressionKind getFinalExpressionKind() {
        return finalExpressionKind;
    }



}
