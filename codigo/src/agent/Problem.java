package agent;

import mummymaze.MummyMazeState;

import java.util.List;

public abstract class Problem <S extends State>{

    protected S initialState;
    protected Heuristic heuristic;
    
    public Problem(S initialState) {
        this.initialState = initialState;
    }

    public abstract List<Action<S>> getActions(S state);

    public abstract S getSuccessor(S state, Action action);

    public abstract boolean isGoal(S state);
    
    public double computePathCost(List<Action> path) {
        double cost = 0;
        for (Action a : path) {
            cost += a.getCost();
        }
        return cost;
    }

    public S getInitialState() {
        return initialState;
    }

    public Heuristic getHeuristic() {
        return heuristic;
    }

    public void setHeuristic(Heuristic heuristic) {
        this.heuristic = heuristic;
    }    
}
