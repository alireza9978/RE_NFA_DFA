import NFA.NFA;

public class Main {

    public static void main(String[] args){

        String input = "a|b";
        NFA nfa = new NFA(input);
        nfa.draw(nfa.getStartNode());


    }

}
