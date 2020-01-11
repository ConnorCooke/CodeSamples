package Sorting;

import akka.actor.*;

public class MasterActor extends AbstractActor{

    private Integer[] sortedList;
    int firstEmptyPosition;

    /**
     * Creates a master actor object
     */
    public MasterActor(){
        super();
        this.firstEmptyPosition = 0; // sets first empty to initial value
    }

    /**
     * Props for creating a MasterActor actor
     * @return props object
     */
    static public Props props() {
        return Props.create(MasterActor.class, ()->{return new MasterActor();});
    }

    /**
     * Creates a sort actor and sends it all the values that will be sorted by it.
     * @param values an array of integers that need sorting
     */
    public void sortNumbers(Integer[] values){
        this.sortedList = new Integer[values.length-1];// new list wont include the sentinel value from the argument
        ActorRef sortActor = getContext().getSystem().actorOf(SortingActor.props());

        for(Integer n : values) {
            sortActor.tell(new valueMessage(n), this.getSelf()); // sends the values to sortActor
        }
    }

    /**
     * Prints out the values in the sorted list
     */
    public void printSortedList(){
        String sentence = "";
        for(Integer i = 0; i < (this.sortedList.length-1);i++) {
            sentence = sentence + this.sortedList[i].toString() + ", "; // adds all but the last value to sentence with a comma
        }
        sentence = sentence + this.sortedList[this.sortedList.length-1]; //adds last value without a comma
        System.out.println(sentence);
    }

    /**
     * takes in the values one at a time and adds it to the list
     * @param value
     */
    public void receiveValues(int value) {
        this.sortedList[firstEmptyPosition] = value; // stores the value in the sorted list at first empty position
        firstEmptyPosition++; // increments to the next empty position or after position

        if(firstEmptyPosition == this.sortedList.length){ // if in the after position prints the list out
            this.printSortedList();
        }
    }

    /**
     * Creates the receiver for receiving and interpreting messages
     * @return the receive builder object
     */
    public AbstractActor.Receive createReceive() {
        return this.receiveBuilder().match(SortValuesMessage.class, m -> {this.sortNumbers(m.getValues());})
                .match(returnValueMessage.class, m -> {this.receiveValues(m.getValue());}).build();
        // if it receives a sort value message it calls sortNumbers, if it receives a returnValueMessage
        // it calls receiveValues
    }
}