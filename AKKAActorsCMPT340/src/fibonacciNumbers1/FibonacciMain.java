package fibonacciNumbers1;

import akka.actor.*;

import java.util.Scanner;

public class FibonacciMain {
    /**
     * Main function to accept an n and call the actors required to calculate and print out the nth fibonacci value
     * @param args not used
     */
    public static void main(String[] args){
        System.out.println("Enter the index of the desired fibonacci value");
        Scanner input = new Scanner(System.in);
        int n = input.nextInt();

        final ActorSystem system = ActorSystem.create("fib");
        final ActorRef master = system.actorOf(MasterActor.props(),
                "fib-master");
        master.tell(new FibonacciMessage(n),ActorRef.noSender());
    }

}
