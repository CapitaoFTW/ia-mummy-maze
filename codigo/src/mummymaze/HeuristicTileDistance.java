package mummymaze;

import agent.Heuristic;

public class HeuristicTileDistance extends Heuristic<MummyMazeProblem, MummyMazeState>{

    @Override
    public double compute(MummyMazeState state){
        return state.computeTileDistances();
    }
    
    @Override
    public String toString(){
        return "Tiles distance to final position";
    }
}