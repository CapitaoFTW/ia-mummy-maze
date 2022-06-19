package mummymaze;

import agent.Heuristic;

public class HeuristicEnemyDistanceToGoal extends Heuristic<MummyMazeProblem, MummyMazeState> {

    @Override
    public double compute(MummyMazeState state) {
        return state.computeEnemyDistanceToGoal();
    }
    
    @Override
    public String toString(){
        return "Enemy distance to goal";
    }    
}