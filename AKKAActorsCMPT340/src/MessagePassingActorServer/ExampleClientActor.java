package MessagePassingActorServer;// Connor Cooke
// CEC383
// 11239140

import akka.actor.*;
import akka.japi.Pair;

import java.util.ArrayList;

/**
 * This is an example of a client that could work with the server set up. I have taken the assumption that the client
 * is ensuring that the server is not being sent multiple conflicting read and write values
 */
public class ExampleClientActor extends AbstractActor {

    private String fileName;
    private ActorRef server;
    private ArrayList<Pair<String,String>> contentAndCommands;
    private Boolean serverOpen;
    private ActorRef serverManager;

    /**
     * constructor for client actor
     * @param fname name of file the client actor is working with
     * @param serverMan the server managers reference
     */
    public ExampleClientActor(String fname, ActorRef serverMan){
        super();
        this.fileName =fname;
        this.contentAndCommands = new ArrayList<>();
        this.serverOpen = false;
        this.serverManager = serverMan;
        this.server = null;
    }

    /**
     * Props for creating a ExampleClientActor actor
     * @return props object
     */
    static public Props props(String fname,ActorRef serverMan) {
        return Props.create(ExampleClientActor.class, ()->{return new ExampleClientActor(fname,serverMan);});
    }

    /**
     * Tells the server manager to open a server for it to use
     */
    private void openServer(){
        serverManager.tell(new ServerMessages.opened(this.fileName),this.getSelf());
    }

    /**
     * Tells the client that the server is open, as well as its reference
     */
    private void serverOpened(){
        this.server = this.getSender();
        this.serverOpen = true;
        if(!this.contentAndCommands.isEmpty()){
            sendReadOrWrite();
        }
    }

    /**
     * If there are still commands to execute and if there are then the client will send its request to the server
     */
    private void sendReadOrWrite(){
        if(!contentAndCommands.isEmpty()) {
            Pair command = contentAndCommands.get(0);
            contentAndCommands.remove(0);

            if (command.second() == null) { // if there is no command type then tell the server to close
                this.server.tell(new ServerMessages.closed(), this.getSelf());
            } else if (command.first() == null) { // if there is no content then send a read command
                this.server.tell(new ServerMessages.read((String) command.second()), this.getSelf());
            } else { // otherwise send a write command to the server
                this.server.tell(new ServerMessages.write((String) command.first(),
                        (String) command.second()), this.getSelf());
            }
        }
    }

    /**
     * If there is an open server then attempt to send the command, otherwise add to command queue.
     * @param command the type of command being sent
     */
    private void sendRead(String command){
        this.contentAndCommands.add(new Pair<>(null,command));
        if(this.serverOpen){
            sendReadOrWrite();
        }
        else{
            this.openServer();
        }
    }

    /**
     * If there is an open server then attempt to send the command, otherwise add to command queue.
     * @param command the type of command being sent
     */
    private void sendWrite(String contents, String command){
        this.contentAndCommands.add(new Pair<>(contents,command));
        if(this.serverOpen){
            sendReadOrWrite();
        }
        else{
            this.openServer();
        }
    }

    /**
     * If this is the only command then immediately execute it otherwise wait until all other commands are completed
     */
    private void closeServer(){
        if(this.contentAndCommands.isEmpty()) {
            this.server.tell(new ServerMessages.closed(), this.getSelf());
        }
        else{
            this.contentAndCommands.add(new Pair("close",null));
        }
    }

    /**
     * Builds receiver for messages
     * @return the receivebuilder
     */
    public AbstractActor.Receive createReceive() {
        return this.receiveBuilder().match(ServerMessages.openReply.class, m -> {this.serverOpened();})
                .match(ServerMessages.closed.class, m -> {closeServer();})
                .match(ServerMessages.write.class, m -> {sendWrite(m.getNewContentsForFile(),m.getCommandType());})
                .match(ServerMessages.read.class, m -> {sendRead(m.getCommand());})
                .match(ServerMessages.completedCommand.class, m -> {this.sendReadOrWrite();}).build();

    }
}
