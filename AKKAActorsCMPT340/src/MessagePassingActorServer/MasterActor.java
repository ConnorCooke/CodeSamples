package MessagePassingActorServer;// Connor Cooke
// CEC383
// 11239140

import akka.actor.*;
import akka.japi.Pair;

import java.util.ArrayList;

public class MasterActor extends AbstractActor {

    private ArrayList<ActorRef> busyServers;
    private ArrayList<ActorRef> idleServers;
    private ArrayList<Pair<String,ActorRef>> messagesWaitingForServer;

    public MasterActor(int n){
        super();
        this.busyServers= new ArrayList<>();
        this.idleServers = new ArrayList<>();
        this.messagesWaitingForServer = new ArrayList<>();
        for(int i =0; i< n; i++){
            this.idleServers.add(getContext().getSystem().actorOf(ServerActor.props(this.getSelf())));
        }
    }

    /**
     * Props for creating a MasterActor actor
     * @return props object
     */
    static public Props props(int n) {
        return Props.create(MasterActor.class, ()->{return new MasterActor(n);});
    }

    private void sendOpenToServer(String name, ActorRef sender){
        if(idleServers.isEmpty()){
            this.messagesWaitingForServer.add(new Pair<String,ActorRef>(name, sender));
        }
        else{
            ActorRef idleServer =  this.idleServers.get(0);
            this.idleServers.remove(0);
            this.busyServers.add(idleServer);
            idleServer.tell(new ServerMessages.opened(name),sender);
        }
    }

    private void serverIsClosed(){
        ActorRef server=this.getSender();
        this.idleServers.add(server);
        this.busyServers.remove(server);
        if(!this.messagesWaitingForServer.isEmpty()){
            Pair<String,ActorRef> message = this.messagesWaitingForServer.get(0);
            this.messagesWaitingForServer.remove(0);
            sendOpenToServer(message.first(),message.second());
        }
    }

    public AbstractActor.Receive createReceive() {
        return this.receiveBuilder().match(ServerMessages.opened.class, m -> {sendOpenToServer(m.getFileName(),this.getSender());})
                .match(ServerMessages.closed.class, m -> {serverIsClosed();}).build();
        // if it receives a sort value message it calls sortNumbers, if it receives a returnValueMessage
        // it calls receiveValues
    }
}
