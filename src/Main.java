import DFA.DFA;
import NFA.NFA;

public class Main {

    public static void main(String[] args) {

        String input = "(a|bb)aa";
        System.out.println("nfa for " + input);
        NFA nfa = new NFA(input);
//        nfa.drawPngV2(nfa.getStartNode(),"testDfa");
        DFA dfa = new DFA(nfa);
        dfa.draw(dfa.getStart(), "testDFAEnd");

//        nfa.draw(nfa.getStartNode());
//        nfa.drawPNG(nfa.getStartNode(),"one");
//        nfa.drawPngV2(nfa.getStartNode(),"oneV");
//
//        input = "(aa|bb)a";
//        System.out.println("nfa for " + input);
//        nfa = new NFA(input);
//        nfa.draw(nfa.getStartNode());
//        nfa.drawPNG(nfa.getStartNode(),"two");
//        nfa.drawPngV2(nfa.getStartNode(),"twoV");
//
//        input = "(abba|baab)(a|b)";
//        System.out.println("nfa for " + input);
//        nfa = new NFA(input);
//        nfa.draw(nfa.getStartNode());
//        nfa.drawPNG(nfa.getStartNode(),"three");
//        nfa.drawPngV2(nfa.getStartNode(),"threeV");
    }

}
