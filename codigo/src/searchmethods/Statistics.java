package searchmethods;

public class Statistics {
    public int numExpandedNodes;
    public int numGeneratedStates = 1; //due to the initial node
    public int maxFrontierSize;

    public void reset(){
        numExpandedNodes = 0;
        numGeneratedStates = 1;
        maxFrontierSize = 0;
    }
}