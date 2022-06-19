package mummymaze;

import agent.Action;

public class ActionStay extends Action<MummyMazeState>{

    public ActionStay(){
        super(0);
    }

    @Override
    public void execute(MummyMazeState state){
        state.dontMove();
        state.setAction(this);
    }

    @Override
    public boolean isValid(MummyMazeState state){
        return true;
    }
}