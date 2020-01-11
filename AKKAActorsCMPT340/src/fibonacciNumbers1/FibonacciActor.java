package fibonacciNumbers1;

import akka.actor.*;
import akka.event.*;

class FibonacciActor extends AbstractActor {

    private Integer result;
    private ActorRef requester;

    /**
     * Creates a fibonacci actor
     */
    public FibonacciActor(){
        super();
    }

    /**
     * Used for creating fibonacci actors
     * @return props for a new fibonacci actor
     */
    static public Props props() {
        return Props.create(FibonacciActor.class, () -> {return new FibonacciActor();});
    }

    /**
     * Receives a value n, a desired fibonacci index, and if n is a base case it returns the value to the caller,
     * otherwise it creates two child actors to handle the needed subtaasks
     * @param n index of the desired number from the fibonacci sequence
     */
    public void calculate(int n){
        this.requester = this.getSender();
        if(n == 0){
            this.result = 0;
            this.sendResult();
        }
        else if(n == 1){ // there are two base cases due to how the fibonacci sequence is calculated
            this.result = 1;
            this.sendResult();
        }
        else{
            this.result = -1; // used for checking how many replies have been received
            ActorRef child1 = getContext().getSystem().actorOf(FibonacciActor.props());
            ActorRef child2 = getContext().getSystem().actorOf(FibonacciActor.props()); // creates child actors
            child1.tell( new FibonacciMessage(n-1), this.getSelf());
            child2.tell( new FibonacciMessage(n-2), this.getSelf()); // sends the two subtasks to the children
        }
    }

    /**
     * Sends the result to the parent of the requester
     */
    public void sendResult(){
        requester.tell(new ResultMessage(result), ActorRef.noSender());
    }

    /**
     * If no result has been received yet sets result to r, if it has already received a result it adds the two
     * together and sends to the requester
     * @param r a received value
     */
    public void addResult(int r){
        if(result == -1){
            result = r; // sets result to the first received result
        }
        else{
            result+=r;
            this.sendResult(); // when it receives the second value it adds the two together and sends the result
            // to the parent
        }
    }

    /**
     * Used for receiving and interpreting messages
     * @return a receive builder object
     */
    public Receive createReceive() {
        return this.receiveBuilder().match(FibonacciMessage.class, m -> {this.calculate(m.getN());})
                .match(ResultMessage.class, m -> {this.addResult(m.getResult());}).build();
        // when it receives a fibonacci message it calculates result, when it receives a ResultMessage it calls addResult
    }

}
