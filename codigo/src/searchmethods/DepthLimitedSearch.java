package searchmethods;

import agent.Action;
import agent.Problem;
import agent.Solution;
import agent.State;
import java.util.List;

public class DepthLimitedSearch extends DepthFirstSearch {

    protected double limit;

    public DepthLimitedSearch() {
        this(28);
    }

    public DepthLimitedSearch(int limit) {
        this.limit = limit;
    }

    /**
     * Same comments as for Depth First Search.
     */
    @Override
    protected Solution graphSearch(Problem problem) {
        frontier.clear();
        frontier.add(new Node(problem.getInitialState()));

        while (!frontier.isEmpty() && !stopped) {
            Node n = (Node) frontier.poll();
            int successorsSize = 0;
            if (n.getDepth() < limit) {
                State state = n.getState();
                List<Action> actions = problem.getActions(state);
                successorsSize = actions.size();
                for(Action action : actions){
                    State successor = problem.getSuccessor(state, action);
                    if (problem.isGoal(successor)) {
                        Node successorNode = new Node(successor, n);
                        return new Solution(problem, successorNode);
                    }
                    addSuccessorToFrontier(successor, n);
                }
            }
            computeStatistics(successorsSize);
        }
        return null;
    }

    public double getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    @Override
    public String toString() {
        return "Limited depth first search";
    }
}
