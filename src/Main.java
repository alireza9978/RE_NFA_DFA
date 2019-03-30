import DFA.DFA;
import NFA.NFA;

public class Main {

    public static void main(String[] args) {

        String input = "aab*";
        NFA nfa = new NFA(input);
        DFA dfa = new DFA(nfa);
        nfa.draw(nfa.getStartNode());
        nfa.drawPngV2(nfa.getStartNode(), "aNFA");
        System.out.println("  ");
        dfa.draw();
        dfa.drawPNG(dfa.getStart(), "aDFA");

//        input = "(aa|bb)";
//        nfa = new NFA(input);
//        dfa = new DFA(nfa);
//        nfa.draw(nfa.getStartNode());
//        nfa.drawPngV2(nfa.getStartNode(), "bNFA");
//        dfa.draw(dfa.getStart(), "bDFA");
//
//        input = "(abba|baab)a*(b)";
//        nfa = new NFA(input);
//        dfa = new DFA(nfa);
//        nfa.draw(nfa.getStartNode());
//        nfa.drawPngV2(nfa.getStartNode(), "cNFA");
//        dfa.draw(dfa.getStart(), "cDFA");
    }

}
