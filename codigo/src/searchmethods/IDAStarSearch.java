package searchmethods;

import agent.*;

import java.util.List;

public class IDAStarSearch extends IterativeDeepeningSearch {
    /**
     * This algorithm is an hybrid: it is an informed algorithm but it is based
     * on the Iterative Deepening Algorithm (IDA). That's why we make it extend the
     * IterativeDeepeningSearch class instead of the InformedSearch one: it uses
     * a NodeLinkedList instead of a NodePriorityQueue. Despite the fact that it
     * is based on the IDA, we don't reuse any code of it because all methods
     * (search, searchGraph and addSucessorToFrontier) have their particularities.
     *
     * Note that, on each iteration, the search is done in a depth first search way.    
     */

    protected Heuristic heuristic;
    private double newLimit;

    @Override
    public Solution search(Problem problem) {
        statistics.reset();        
        stopped = false;
        this.heuristic = problem.getHeuristic();
        limit = heuristic.compute(problem.getInitialState());
        Solution solution;
        int previousNumGeneratedStates;
        do {
            previousNumGeneratedStates = statistics.numGeneratedSates;
            solution = graphSearch(problem);
        } while (solution == null && statistics.numGeneratedSates != previousNumGeneratedStates);

        return solution;
    }

    @Override
    protected Solution graphSearch(Problem problem) {
        newLimit = Double.POSITIVE_INFINITY;
        frontier.clear();
        frontier.add(new Node(problem.getInitialState()));

        while (!frontier.isEmpty() && !stopped) {
            Node n = frontier.poll();
            State state = n.getState();
            if (problem.isGoal(state) &&  n.getF() <= limit) {
                return new Solution(problem, n);
            }
            List<Action> actions = problem.getActions(state);
            for(Action action : actions){
                State successor = problem.getSuccessor(state, action);
                addSuccessorToFrontier(successor, n);
            }
            computeStatistics(actions.size());
        }
        limit = newLimit;
        return null;
    }

    @Override
    public void addSuccessorToFrontier(State successor, Node parent) {
        if (!frontier.containsState(successor)) {
            double g = parent.getG() + successor.getAction().getCost();
            double f = g + heuristic.compute(successor);
            if (f <= limit) {
                if (!parent.isCycle(successor)) {
                    frontier.addFirst(new Node(successor, parent, g, f));
                }
            } else {
                newLimit = Math.min(newLimit, f);
            }
        }
    }

    @Override
    public String toString() {
        return "IDA* search";
    }
}
