package searchmethods;

import agent.Action;
import agent.Problem;
import agent.Solution;
import agent.State;
import java.util.List;
import utils.NodeLinkedList;

public class DepthFirstSearch extends GraphSearch<NodeLinkedList> {

    public DepthFirstSearch() {
        frontier = new NodeLinkedList();
    }

    /**
     * In this version of Depth First Search we are returning the solution
     * when we generate a goal state (we don't add it to the frontier).
     * This is not a "pure" depth first search but it is completely unnecessary
     * to continue the search if we find a goal state!
     * In this (optimized) version we are assuming that the initial state is never a goal state.
     * If this could happen, we should have an initial condition to verify that.
     * Graph Search without explored list
     */
    @Override
    protected Solution graphSearch(Problem problem) {
        frontier.clear();
        frontier.add(new Node(problem.getInitialState()));

        while (!frontier.isEmpty() && !stopped) {
            Node n = frontier.poll();
            State state = n.getState();
            List<Action> actions = problem.getActions(state);
            for(Action action : actions){
                State successor = problem.getSuccessor(state, action);
                if (problem.isGoal(successor)) {
                    Node successorNode = new Node(successor, n);
                    return new Solution(problem, successorNode);
                }
                addSuccessorToFrontier(successor, n);
            }
            computeStatistics(actions.size());
        }
        return null;
    }

    @Override
    public void addSuccessorToFrontier(State successor, Node parent) {
        if (!frontier.containsState(successor)) {
            //parent is always != null
            if (!parent.isCycle(successor)) {
                frontier.addFirst(new Node(successor, parent));
            }
        }
    }

    @Override
    public String toString() {
        return "Depth first search";
    }
}
