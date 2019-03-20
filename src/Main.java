import NFA.NFA;

public class Main {

    public static void main(String[] args){

        String input = "a|b";
        System.out.println("nfa for " + input);
        NFA nfa = new NFA(input);
        nfa.draw(nfa.getStartNode());

        System.out.println();

        input = "(a|b|(ab))a";
        System.out.println("nfa for " + input);
        nfa = new NFA(input);
        nfa.draw(nfa.getStartNode());

        input = "(abba|baab)(a|b)";
        System.out.println("nfa for " + input);
        nfa = new NFA(input);
        nfa.draw(nfa.getStartNode());
    }

}
