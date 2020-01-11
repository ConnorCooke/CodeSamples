package MessagePassingActorServer;// Connor Cooke
// CEC383
// 11239140

import akka.actor.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

/**
 * Opens, then reads and/or writes to a file, then is closed until it is reopened to repeat prev cycle. Only takes in
 * one command at a a time
 */
public class ServerActor extends AbstractActor {

    private String fileName;
    private ActorRef serverManager;

    /**
     * Constructor for the server
     * @param man the actor ref of the server manager that created it
     */
    public ServerActor(ActorRef man){
        super();
        serverManager = man;
    }

    /**
     * Props for creating a ServerActor actor
     * @return props object
     */
    static public Props props(ActorRef man) {
        return Props.create(ServerActor.class, ()->{return new ServerActor(man);});
    }

    /**
     * Gets the file name for later use, tells the client that it is ready for commands
     * @param name
     */
    private void openFile(String name){
        this.fileName = name;
        this.getSender().tell(new ServerMessages.openReply(),this.getSelf());
        // because we dont know if the file will be read or written to we cannot open until we receive that command
        //from the client
    }

    /**
     * Reads the contents from a file, either to a String or prints it to the console
     * @param commandType a string that states what type of command was sent
     */
    private void readFile(String commandType){
        String filesContents = "";
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String currentline ="";
            while((currentline = reader.readLine())!= null){
                if(commandType.equals("print")){
                    System.out.println(currentline);
                }
                else{
                    filesContents = filesContents+currentline;
                }
            }
            reader.close();
            if(commandType.equals("return")){
                this.getSender().tell(new ServerMessages.read(filesContents), this.getSelf());
            }
        }catch (Exception e){
            System.out.println("Error reading file " + fileName + ", due to " + e);
        }
        getSender().tell(new ServerMessages.completedCommand(),this.getSelf());
    }

    private void writeToFile(String newContents, String commandType){
        try{
            String filesContents ="";
            if(commandType.equals("append")){
                BufferedReader reader = new BufferedReader(new FileReader(fileName));

                String currentline ="";
                while((currentline = reader.readLine())!= null){
                    filesContents = filesContents+currentline;
                }
                reader.close();
            }

            BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
            if(commandType.equals("append")){
                writer.write(filesContents);
                writer.append(newContents);
            }
            else{
                writer.write(newContents);
            }
            writer.close();
        }catch (Exception e){
            System.out.println("Error writing to file " + fileName + ", due to " + e);

        }
        getSender().tell(new ServerMessages.completedCommand(),this.getSelf());
    }

    private void closeThisServer(){
        this.fileName = "";
        this.serverManager.tell(new ServerMessages.closed(), this.getSelf());
    }

    public AbstractActor.Receive createReceive() {
        return this.receiveBuilder().match(ServerMessages.opened.class, m -> {this.openFile(m.getFileName());})
                .match(ServerMessages.closed.class, m -> {closeThisServer();})
                .match(ServerMessages.write.class, m -> {writeToFile(m.getNewContentsForFile(),m.getCommandType());})
                .match(ServerMessages.read.class, m -> {readFile(m.getCommand());}).build();

    }

}
