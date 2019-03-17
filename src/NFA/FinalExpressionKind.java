package NFA;

public enum FinalExpressionKind {
    //use y instead of lambda
    a, b, lambda, none;

    public static FinalExpressionKind getKind(String name) {
        if (name.length() != 1) {
            return none;
        }
        switch (name.toLowerCase()) {
            case ("a"):
                return a;
            case ("b"):
                return b;
            case ("y"):
                return lambda;

            default:
                return none;
        }
    }
}
