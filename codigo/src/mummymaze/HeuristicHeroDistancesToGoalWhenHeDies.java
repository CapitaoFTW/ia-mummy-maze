package mummymaze;

import agent.Heuristic;

public class HeuristicHeroDistancesToGoalWhenHeDies extends Heuristic<MummyMazeProblem, MummyMazeState>{

    @Override
    public double compute(MummyMazeState state){
        return state.computeHeroDistancesToGoalWhenHeDies();
    }
    
    @Override
    public String toString(){
        return "Distance to goal when hero dies";
    }
}