package NFA;

public class Expression {

    private final String sequence;
    private final boolean isFinal;
    private final FinalExpressionKind finalExpressionKind;
    private final String firstSequencePart;
    private final String secondSequencePart;
    private final Opration opration;

    public Expression(String sequence) {
        this.sequence = removeParentheses(sequence);
        Data temp = parseSequence(this.sequence);
        if (this.sequence.length() == 1) {
            isFinal = true;
            finalExpressionKind = FinalExpressionKind.getKind(this.sequence);
        } else {
            isFinal = false;
            finalExpressionKind = FinalExpressionKind.none;
        }
        firstSequencePart = temp.first;
        secondSequencePart = temp.second;
        opration = temp.opration;
    }

    private String removeParentheses(String sequence) {
        if (sequence.length() == 1) {
            return sequence;
        } else {
            if (sequence.charAt(0) == '(') {
                int count = 1;
                for (int i = 1; i < sequence.length() - 1; i++) {
                    if (sequence.charAt(i) == '(')
                        count++;
                    if (sequence.charAt(i) == ')')
                        count--;
                    if (count == 0) {
                        return sequence;
                    }
                }
                if (sequence.charAt(sequence.length() - 1) == ')' && count == 1) {
                    return removeParentheses(sequence.substring(1, sequence.length() - 1));
                }
            }
        }
        return sequence;
    }

    private String preParse(String sequence) {
        StringBuilder temp = new StringBuilder();
        for (int i = 0; i < sequence.length(); i++) {
            if (sequence.charAt(i) == '(') {
                i += getEndOfParantes(sequence, i);
                temp.append("p");
            } else {
                temp.append(sequence.charAt(i));
            }
        }
        return temp.toString();
    }

    private Data parseSequence(String sequence) {
        if (sequence.length() == 1) {
            return new Data(null, null, null);
        } else {
            String tempSequence = preParse(sequence);
            if (tempSequence.length() == 1) {
                String temp = removeParentheses(sequence);
                return parseSequence(temp);
            }
            int i = 0;
            char temp = tempSequence.charAt(i);
            char next = tempSequence.charAt(i + 1);
            if (temp == 'a' || temp == 'b') {
                if (next == 'p') {
                    return new Data("" + temp, sequence.substring(1), Opration.concat);
                } else {
                    if (next == 'a' || next == 'b') {
                        for (int k = i + 2; k < tempSequence.length(); k++) {
                            if (tempSequence.charAt(k) == 'p' || tempSequence.charAt(k) == '|') {
                                if (tempSequence.charAt(k) == 'p') {
                                    String firstPart = sequence.substring(0, k);
                                    String secondPart = sequence.substring(k + 1);
                                    return new Data(firstPart, secondPart, Opration.concat);
                                }
                                if (tempSequence.charAt(k) == '|') {
                                    String firstPart = sequence.substring(0, k);
                                    String secondPart = sequence.substring(k + 1);
                                    return new Data(firstPart, secondPart, Opration.or);
                                }
                                break;
                            }
                        }
                        return new Data("" + temp, sequence.substring(1), Opration.concat);
                    } else {
                        Opration opration = (next == '|') ? Opration.or : Opration.star;
                        return new Data("" + temp, sequence.substring(2), opration);
                    }
                }
            } else {
                if (temp == 'p') {
                    int j = getEndOfParantes(sequence, i);
                    String first = sequence.substring(1, j);
                    char need = sequence.charAt(j + 1);
                    if (need == 'a' || need == 'b' || need == '(') {
                        return new Data(first, sequence.substring(j + 1), Opration.concat);
                    } else {
                        Opration opration = (next == '|') ? Opration.or : Opration.star;
                        return new Data(first, sequence.substring(j + 2), opration);
                    }
                } else {
                    throw new RuntimeException("ridi ke");
                }
            }
        }
    }

    private int getEndOfParantes(String sequence, int i) {
        int j = i + 1;
        int count = 1;
        char tempChar = sequence.charAt(j);
        while (true) {
            if (tempChar == '(') {
                count++;
            }
            if (tempChar == ')') {
                count--;
            }
            if (count == 0) {
                break;
            }
            j++;
            tempChar = sequence.charAt(j);
        }
        return j;
    }

    public Opration getOperation() {
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

    private class Data {
        String first;
        String second;
        Opration opration;

        public Data(String first, String second, Opration opration) {
            this.first = first;
            this.second = second;
            this.opration = opration;
        }
    }

}
