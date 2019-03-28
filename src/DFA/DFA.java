package DFA;

import NFA.FinalExpressionKind;
import NFA.NFA;
import NFA.Node;
import NFA.transition.Transition;

import java.util.ArrayList;

public class DFA {

    private NFA nfa;
    private Node start;
    private Node end;
    private Node trap;

    public DFA(NFA nfa) {
        this.nfa = nfa;
        convert(nfa);
    }

    private void convert(NFA nfa) {
        ArrayList<Node> nfaNodes = new ArrayList<>();
        getNodes(nfa.getStartNode(), nfaNodes);
        //each node should have to transition
        //with lambda nodes are equal
        //when we have no transition with a|b go to trap
        ArrayList<DFANode> dfaNodes = equalNodes(nfaNodes);
//        for (Node node : nfaNodes) {
//            ArrayList<Transition> transitions = node.getTransitions();
//            for (Transition transition : transitions) {
//                switch (transition.getExpression().getFinalExpressionKind()) {
//                    case a:
//                        break;
//                    case b:
//                        break;
//                    case lambda:
//                        break;
//                    case none:
//                        break;
//                }
//
//            }
//        }
        showEqualNode(dfaNodes);
    }

    private ArrayList<DFANode> equalNodes(ArrayList<Node> nodes) {
        ArrayList<DFANode> dfaNodes = new ArrayList<>();
        ArrayList<Node> seen = new ArrayList<>();
        for (int i = 0; i < nodes.size(); i++) {
            Node node = nodes.get(i);
            DFANode dfaNode = getEquals(node, seen);
            if (dfaNode != null) {
                dfaNodes.add(dfaNode);
            }
        }
        return dfaNodes;
    }

    private DFANode getEquals(Node start, ArrayList<Node> seen) {
        if (seen.contains(start)) {
            return null;
        }else{
            seen.add(start);
        }
        ArrayList<Node> equals = new ArrayList<>();
        ArrayList<Transition> equalsNodeTransition = new ArrayList<>();
        for (Transition transition : start.getTransitions()) {
            if (transition.getExpression().getFinalExpressionKind() == FinalExpressionKind.lambda) {
                equals.add(transition.getEnd());
                equalsNodeTransition.add(transition);
                DFANode dfaNode = getEquals(transition.getEnd(), seen);
                if (dfaNode != null) {
                    equals.addAll(dfaNode.getNodes());
                    equalsNodeTransition.addAll(dfaNode.getTransitions());
                }
            }

        }
        return new DFANode(equals, equalsNodeTransition);
    }

    private void getNodes(Node start, ArrayList<Node> nodes) {
        if (nodes.contains(start)) {
            return;
        }
        nodes.add(start);
        for (Transition transition : start.getTransitions()) {
            Node end = transition.getEnd();
            getNodes(end, nodes);
        }
    }

    private void showEqualNode(ArrayList<DFANode> nodes) {
        for (DFANode node : nodes) {
            ArrayList<Node> nfaNodes = node.getNodes();
            System.out.print("these node are equal : ");
            for (Node temp : nfaNodes) {
                System.out.print(temp.getName());
                System.out.print(" ");
            }
            System.out.println();
        }
    }

    public NFA getNfa() {
        return nfa;
    }

    public Node getStart() {
        return start;
    }

    public Node getEnd() {
        return end;
    }
}
