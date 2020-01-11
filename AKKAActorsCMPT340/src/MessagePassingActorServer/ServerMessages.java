package MessagePassingActorServer;// Connor Cooke
// CEC383
// 11239140

public class ServerMessages {

    /**
     * used for telling servers to read out the contents of a file
     */
    static public class read{
        /**
         * type of the command
         */
        private final String command;

        /**
         * unless specified the read command will print to console
         */
        public read(){
            this.command = "print";
        }

        public read(String com){
            this.command = com;
        }

        public String getCommand() {
            return command;
        }
    }

    /**
     * Used for telling servers to write values to a file
     */
    static public class write{
        /**
         *  What will be written to the file
         */
        private final String newContentsForFile;
        /**
         * whether the command is to type or append
         */
        private final String commandType;

        /**
         * if no type specified defaults to write
         * @param cont value for newContentsForFile
         */
        public write(String cont){
            this.newContentsForFile = cont;
            this.commandType = "write";
        }

        /**
         * sets values for commandtype and new contents for file
         * @param cont value for newContentsForFile
         * @param comT value for command type
         */
        public write(String cont, String comT){
            this.newContentsForFile = cont;
            this.commandType = comT;
        }

        /**
         * RGetter for type of command
         * @return value held in command type
         */
        public String getCommandType() {
            return this.commandType;
        }

        /**
         * Getter for new contents
         * @return value held in newContentsForFile
         */
        public String getNewContentsForFile(){
            return  this.newContentsForFile;
        }
    }

    static public class openReply{
        public openReply(){}
    }

    /**
     *   Tells a server to open a file
     */
    static public class opened{
        final private String fileName;
        public opened(String name){
            this.fileName = name;
        }

        public String getFileName() {
            return this.fileName;
        }
    }

    /**
     * Used for messaging that a file has been closed or should be closed
     */
    static public class closed{
        public closed(){

        }
    }

    /**
     * tells an client that a command was completed
     */
    static public class completedCommand{
        public completedCommand(){}
    }

}
