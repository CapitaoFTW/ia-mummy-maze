package searchmethods;

import agent.Action;
import agent.Problem;
import agent.Solution;
import agent.State;

import java.util.List;

public class IterativeDeepeningSearch extends DepthLimitedSearch {
    /*
     * We do not use the code from DepthLimitedSearch because we can optimize
     * so that the algorithm only verifies if a state is a goal if its depth is
     * equal to the limit. Note that given a limit X we are sure not to
     * encounter a solution below this limit because a (failed) limited depth
     * search has already been done. That's why we do not extend this class from
     * DepthLimitedSearch. We extend from DepthFirstSearch so that we don't need
     * to rewrite method insertSuccessorsInFrontier again.
     * After the class, please see a version of the search algorithm without
     * this optimization.
     */

    @Override
    public Solution search(Problem problem) {
        statistics.reset();
        statistics.numGeneratedSates = 0; //specific to this algorithm
        stopped = false;
        limit = 0;
        Solution solution;
        int previousNumGeneratedStates;
        do {
            previousNumGeneratedStates = statistics.numGeneratedSates;
            solution = graphSearch(problem);
            limit++;
        } while (solution == null && statistics.numGeneratedSates != previousNumGeneratedStates);

        return solution;
    }

    @Override
    protected Solution graphSearch(Problem problem) {
        frontier.clear();
        frontier.add(new Node(problem.getInitialState()));
        statistics.numGeneratedSates++; //specific to this algorithm

        while (!frontier.isEmpty() && !stopped) {
            Node n = (Node) frontier.poll();
            int numSuccessorsSize = 0;
            if (n.getDepth() < limit) {
                State state = n.getState();
                List<Action> actions = problem.getActions(state);
                numSuccessorsSize = actions.size();
                for(Action action : actions){
                    State successor = problem.getSuccessor(state, action);
                    if (n.getDepth() == limit - 1 && problem.isGoal(successor)) {
                        Node successorNode = new Node(successor, n);
                        return new Solution(problem, successorNode);
                    }
                    addSuccessorToFrontier(successor, n);
                }
            }
            computeStatistics(numSuccessorsSize);
        }
        return null;
    }

    @Override
    public String toString() {
        return "Iterative deepening search";
    }
}


/*
 * 
 public class IterativeDeepeningSearch implements SearchMethod {

    @Override
    public Solution search(Problem problem) {
        DepthLimitedSearch dls = new DepthLimitedSearch();
        Solution solution;
        for (int i = 0;; i++) {
            dls.setLimit(i);
            solution = dls.search(problem);
            if (solution != null) {
                return solution;
            }
        }
    }

    @Override
    public String toString() {
        return "Iterative deepening search";
    }
 *
 */