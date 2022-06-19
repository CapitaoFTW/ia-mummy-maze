package mummymaze;

import agent.Heuristic;

public class HeuristicTilesOutOfPlace extends Heuristic<MummyMazeProblem, MummyMazeState> {

    @Override
    public double compute(MummyMazeState state) {
        return state.computeTilesOutOfPlace();
    }
    
    @Override
    public String toString(){
        return "Tiles out of place";
    }    
}