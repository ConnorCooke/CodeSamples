package fibonacciNumbers1;

import akka.actor.*;

public class MasterActor extends AbstractActor{

    private int nth;

    /**
     * Creates a master actor object
     */
    public MasterActor(){super();}

    /**
     * Props for creating a MasterActor actor
     * @return props object
     */
    static public Props props() {
        return Props.create(MasterActor.class, ()->{return new MasterActor();});
    }

    /**
     * Creates a fibonacci actor and sends it the argument n to get the nth number in the fibonacci sequence
     * @param n the desired index of the number from the fibonacci sequence
     */
    public void findNthFibonacci(int n){
        nth = n;
        ActorRef fibonacciActor = getContext().getSystem().actorOf(FibonacciActor.props());
        fibonacciActor.tell( new FibonacciMessage(n), this.getSelf());
    }

    /**
     * Prints out the number it received from the fibonacci actor
     * @param r value to be printed
     */
    public void printResult(int r){
        System.out.println("The " + nth + "th value in the fibonacci sequence is " + r);
    }

    /**
     * Creates the receiver for receiving and interpreting messages
     * @return the receive builder object
     */
    public AbstractActor.Receive createReceive() {
        return this.receiveBuilder().match(FibonacciMessage.class, m -> {this.findNthFibonacci(m.getN());})
                .match(ResultMessage.class, m -> {this.printResult(m.getResult());}).build();
    }
}
