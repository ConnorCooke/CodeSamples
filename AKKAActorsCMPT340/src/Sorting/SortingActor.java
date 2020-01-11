package Sorting;

import akka.actor.*;

public class SortingActor extends AbstractActor {

    private int smallest;
    ActorRef child;
    ActorRef parent;

    /**
     * Creates a new sorting actor
     */
    public SortingActor(){
        super();
        this.smallest = -1; // sets initial value
    }

    /**
     * Used for creating sorting actors
     * @return props for a new sorting actor
     */
    static public Props props() {
        return Props.create(SortingActor.class, () -> {return new SortingActor();});
    }

    /**
     * Sends the value on to its child, updates smallest if the value is smaller, and if it is the sentinel value
     * than it sends its smallest to the parent
     * @param val the value for sorting
     */
    public void sendOn(int val){
        if(this.parent == null){
            this.parent = this.getSender(); // if this is the first received message it saves the sender to parent
        }
        int sendon = val; // sendon is the value that will be sent to its child

        if (val == 0) {
            this.returnValue(this.smallest); // if it receives the sentinel value it returns its current smallest value
        }
        else {
            if(val < this.smallest || this.smallest == -1){
                sendon = this.smallest; // if no value has been received before or the value is smaller than the current
                this.smallest = val;    // one set smallest to the value, and sendon to smallest
            }

            if (this.child == null && sendon != -1){ // if it already had a smallest and child does not exists yet
                this.child = getContext().getSystem().actorOf(SortingActor.props()); // then it creates the child
            }
        }

        if(child != null) { // if the child exists than send it the sendon value
            this.child.tell(new valueMessage(sendon), this.getSelf());
        }
    }

    /**
     * Sends the value to its parent
     * @param val value to be sent
     */
    public void returnValue(int val){
        this.parent.tell(new returnValueMessage(val), this.getSelf());
    }

    /**
     * Used for receiving and interpreting messages
     * @return a receive builder object
     */
    public Receive createReceive() {
        return this.receiveBuilder().match(valueMessage.class, m -> {this.sendOn(m.getValue());})
                .match(returnValueMessage.class, m -> {this.returnValue(m.getValue());}).build();
        // if it receives valueMessage, then it calls sendsOn, and if it receives returnValueMessage it calls returnValue
    }

}
