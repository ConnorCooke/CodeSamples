package fibonacciNumbers2;

import akka.actor.*;
import akka.event.*;

import java.util.Stack;

class FibonacciActor extends AbstractActor {

    private Integer result;
    private ActorRef parent;

    /**
     * Children and totalReceivedVals used for tracking total subtasks have been sent out and how many have returned a
     * result
     */
    private Integer children;
    private Integer totalReceivedVals;


    /**
     * Creates a fibonacci actor
     */
    public FibonacciActor(){
        super();
        this.children = 0;
        this.totalReceivedVals = 0; // sets initial values for children and receivedVals
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
        if(parent == null ) {
            parent = this.getSender();
        } // if this is the first request than it saves the sender for returning final value

        if(n == 0){
            result = 0;
            this.sendResult();
        }
        else if(n == 1){
            result = 1;
            this.sendResult();// if its a base case it returns the value immediately
        }
        else{
            result = -1;

            ActorRef child = getContext().getSystem().actorOf(FibonacciActor.props()); // creates a child

            this.getSelf().tell( new FibonacciMessage(n-1), this.getSelf()); // sends larger sub problem to itself

            child.tell( new FibonacciMessage(n-2), this.getSelf()); // sends smaller subproblem to its child

            this.children += 2;// adds 2 to the children tracker
        }
    }

    /**
     * Sends the result to the parent of the requester
     */
    public void sendResult(){
        int r = result;
        result = -1; // resets result and saves in temp memory its value for sending onwards

        if(this.totalReceivedVals == this.children) { // if it has received all needed values returns to parent
            this.parent.tell(new ResultMessage(r), ActorRef.noSender());
        }
        else{ // if it is still waiting on subproblem responses it sends result to itself
            this.getSelf().tell(new ResultMessage(r), ActorRef.noSender());
        }
    }

    /**
     * If no result has been received yet sets result to r, if it has already received a result it adds the two
     * together and sends to the requester
     * @param r a received value
     */
    public void addResult(int r){
        this.totalReceivedVals++; // adds one to totalReceivedVals
        if(result == -1){
            result = r; // sets result to the first received value
        }
        else{
            result+=r;
            this.sendResult();// when it receives the second value it adds the two together and sends the result
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
