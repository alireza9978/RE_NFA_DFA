package NFA;

public class Expression {

    private final String sequence;
    private final boolean isFinal;
    private final FinalExpressionKind finalExpressionKind;
    private final String firstSequencePart;
    private final String secondSequencePart;
    private final Opration opration;

    public Expression(String sequence) {
        this.sequence = sequence;
        if (sequence.length() == 1){
            isFinal = true;
            finalExpressionKind = FinalExpressionKind.getKind(sequence);
            firstSequencePart = null;
            secondSequencePart = null;
            opration = null;
        }else {
            isFinal = false;
            finalExpressionKind = FinalExpressionKind.none;
            firstSequencePart = sequence;
            secondSequencePart = sequence;
            opration = Opration.concat;
        }
    }

    private void parseSequence(){
        for (int i = 0;i<sequence.length();i++){
            char temp = sequence.charAt(i);
            //todo parse sequence
        }
    }

    public Opration getOpration() {
        return opration;
    }

    public String getFirstSequencePart() {
        return firstSequencePart;
    }

    public String getSecondSequencePart() {
        return secondSequencePart;
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
