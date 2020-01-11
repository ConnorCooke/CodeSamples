package fibonacciNumbers2;

/**
 * Message class for sending the result
 */
public class ResultMessage {

    private final int result;

    /**
     * Creates a result message
     * @param r the value of the result
     */
    public ResultMessage(int r){
        this.result = r;
    }

    /**
     * getter for result
     */
    public int getResult(){
        return result;
    }

}
