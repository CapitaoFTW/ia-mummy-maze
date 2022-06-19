package searchmethods;

import agent.Action;
import agent.Problem;
import agent.Solution;
import agent.State;
import java.util.List;

public class AStarSearch extends InformedSearch {
    /**
     * In this version of the A* algorithm, we are assuming that the heuristic is consistent.
     * When the heuristic is consistent, the first path generated to reach some state is already
     * the optimum path to that state. This allows us to make two optimizations:
     * 1 - When a goal state is generated, we don't need to add it to the frontier; We can just
     * stop the search and return the solution. This optimization implies that we override the
     * graphSearch() method.
     * 2 - There is no need to verify if the node already present in the frontier has a cost g(n)
     * larger than the one of the new node, as we need to do in the uniform cost search and
     * the greedy search.
     *
     * The code of the graphSearch() method is the same as the one of the optimized version
     * of breadth first search. We could organize the code so that it can be reused but we prefer
     * to have the code here so that the code doesn't becomes too scattered.
     *
     * Anyway, at the end of this code we present the version to be used if we don't assume
     * that the heuristic is consistent.
     */

    @Override
    protected Solution graphSearch(Problem problem) {
        frontier.clear();
        explored.clear();
        frontier.add(new Node(problem.getInitialState()));

        while (!frontier.isEmpty() && !stopped) {
            Node n = frontier.poll();
            State state = n.getState();
            explored.add(state);
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

    //f = g + h
    @Override
    public void addSuccessorToFrontier(State successor, Node parent) {
        double g = parent.getG() + successor.getAction().getCost();
        if (!(frontier.containsState(successor) || explored.contains(successor))) {
            frontier.add(new Node(successor, parent, g, g + heuristic.compute(successor)));
        }
    }

    @Override
    public String toString() {
        return "A* search";
    }
}

/**
 * Version to be used if we don't assume that the heuristic is consistent.
 * Notice that in this case we don't override the graphSearch() method
 * (we use the version of the graphSearch() method defined in the GraphSearch
 * class) because it is not guaranteed that the first path generated to reach
 * some state is already the optimum path to that state.
 */
// public class AStarSearch extends InformedSearch {
//    f = g + h
//    @Override
//    public void addSuccessorToFrontier1(State successor, Node parent) {
//        double g = parent.getG() + successor.getAction().getCost();
//        if (!frontier.containsState(successor)) {
//            if (!explored.contains(successor)) {
//                frontier.add(new Node(successor, parent, g, g + heuristic.compute(successor)));
//            }
//        } else if (g < frontier.getNode(successor).getG()) {
//            frontier.removeNode(successor);
//            frontier.add(new Node(successor, parent, g, g + heuristic.compute(successor)));
//        }
//    }
//    @Override
//    public String toString() {
//         return "A* search";
//    }
// }