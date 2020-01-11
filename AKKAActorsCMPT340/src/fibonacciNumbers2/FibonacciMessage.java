package fibonacciNumbers2;

/**
 * Message class for sending the value corresponding to the desired number from the fibonacci sequence
 */
public class FibonacciMessage {
    private final int n;

    /**
     * Creates a fibonacci message object
     * @param m the value for sending
     */
    public FibonacciMessage(int m){
        this.n = m;
    }

    /**
     * getter for n
     * @return n
     */
    public int getN() {
        return n;
    }
}
