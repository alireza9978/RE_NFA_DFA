import NFA.NFA;

public class Main {

    public static void main(String[] args){

        String input = "a|b";
        System.out.println("nfa fpr " + input);
        NFA nfa = new NFA(input);
        nfa.draw(nfa.getStartNode());

        System.out.println();

        input = "a|b|(ab)";
        System.out.println("nfa fpr " + input);
        nfa = new NFA(input);
        nfa.draw(nfa.getStartNode());


    }

}
