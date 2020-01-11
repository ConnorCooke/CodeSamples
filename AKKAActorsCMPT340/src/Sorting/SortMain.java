package Sorting;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;

import java.util.Scanner;

public class SortMain {

    /**
     * Main function that takes in n, then n values, then sorts them into ascending order using actors
     * @param args not used
     */
    public static void main(String[] args) {

        Scanner input = new Scanner(System.in);
        System.out.println("Enter the number of positive integers you will be entering");
        int n = input.nextInt(); // intakes the amount of values that will be entered

        Integer[] values = new Integer[n+1]; // creates a list that will hold n values as well as a 0 sentinel value
        values[n] = 0; // 0 is the sentinel value that tells the actors to return results

        for(int x = 0; x < n;x++){
            System.out.println("Enter the " + (x+1) + "th value");
            values[x] = input.nextInt(); // asks for and takes in the desired amount of values
        }

        // creates the total actor system and sends the values to be sorted into it
        final ActorSystem system = ActorSystem.create("fib");
        final ActorRef master = system.actorOf(MasterActor.props(),
                "fib-master");
        master.tell(new SortValuesMessage(values),ActorRef.noSender());
    }
}
