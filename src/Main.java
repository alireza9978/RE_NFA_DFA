import NFA.NFA;
import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import java.net.URI;
import java.net.URISyntaxException;

public class Main {

    public static void main(String[] args) {

        String input = "a|b*";
        System.out.println("nfa for " + input);
        NFA nfa = new NFA(input);
        nfa.draw(nfa.getStartNode());
        nfa.drawPNG(nfa.getStartNode(),"one");

        input = "(aa|bb)a";
        System.out.println("nfa for " + input);
        nfa = new NFA(input);
        nfa.draw(nfa.getStartNode());
        nfa.drawPNG(nfa.getStartNode(),"two");

        input = "(abba|baab)(a|b)";
        System.out.println("nfa for " + input);
        nfa = new NFA(input);
        nfa.draw(nfa.getStartNode());
        nfa.drawPNG(nfa.getStartNode(),"three");
    }

}
