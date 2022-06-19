package searchmethods;

public class Statistics {
    public int numExpandedNodes;
    public int numGeneratedSates = 1; //due to the initial node
    public int maxFrontierSize;
    
    public void reset(){
        numExpandedNodes = 0;
        numGeneratedSates = 1;
        maxFrontierSize = 0;
    }
}
