package MessagePassingActorServer;// Connor Cooke
// CEC383
// 11239140

import akka.actor.ActorRef;
import akka.actor.ActorSystem;

public class Main {
    public static void main(String[] args){
        final ActorSystem system = ActorSystem.create("fib");
        final ActorRef master = system.actorOf(MasterActor.props(1),
                "fib-master");
        final ActorRef writer1 = system.actorOf(
                ExampleClientActor.props("tester1.txt",master));
        final ActorRef writer2 = system.actorOf(
                ExampleClientActor.props("tester2.txt",master));
        writer1.tell(new ServerMessages.write("words words words"),ActorRef.noSender());
        writer2.tell(new ServerMessages.write("words words words"),ActorRef.noSender());
        writer2.tell(new ServerMessages.read(),ActorRef.noSender());
        writer1.tell(new ServerMessages.read(),ActorRef.noSender());
        writer2.tell(new ServerMessages.write("stuff and words"),ActorRef.noSender());
        writer2.tell(new ServerMessages.read(),ActorRef.noSender());
        writer1.tell(new ServerMessages.write("there are no words"),ActorRef.noSender());
        writer2.tell(new ServerMessages.write("words and stuff"),ActorRef.noSender());
        writer2.tell(new ServerMessages.read(),ActorRef.noSender());
        writer1.tell(new ServerMessages.read(),ActorRef.noSender());
        writer2.tell(new ServerMessages.write(" added some words", "append"),ActorRef.noSender());
        writer2.tell(new ServerMessages.read(),ActorRef.noSender());
        writer1.tell(new ServerMessages.closed(),ActorRef.noSender());
        writer2.tell(new ServerMessages.closed(),ActorRef.noSender());
    }
}
