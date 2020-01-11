// Connor Cooke
// CEC383
// 11239140

package DiningPhilosophers;

/**
 * Thread for a philosopher
 */
public class PhilosopherRunner implements Runnable {

    private PhilosopherMonitor heldForks;
    private Thread t;
    private Integer id;
    private boolean eat;

    public  PhilosopherRunner(int i, PhilosopherMonitor hforks){
        super();
        id = i;
        heldForks = hforks;
    }

    private void randEat(){
        if(Math.random() <0.001){
            this.eat = true;
        }
        else{
            this.eat = false;
        }
    }

    @Override
    public void run() {
        boolean waitingForSpoons = false;
        try {
            randEat();
            while (!heldForks.end_dining) {
                if (this.eat) {
                    while (waitingForSpoons && !heldForks.end_dining) {
                            if (heldForks.getforks(id)) {
                                waitingForSpoons = true;
                            } else {
                                waitingForSpoons = false;
                            }
                    }
                    System.out.println("philosopher " + (id+1) + " is eating");

                    while (this.eat && !heldForks.end_dining) {
                        randEat();
                    }
                    if(heldForks.end_dining) {
                        heldForks.relforks(id);
                    }

                    System.out.println("philosopher " + (id+1) + " has stopped eating");
                }
                while (!this.eat && !heldForks.end_dining) {
                    randEat();
                }
            }
        }catch(Exception e){
            System.out.println("Error for philosopher " + (id+1) + " of type " +e);
        }
    }

    public void stop(){
        t.stop();
    }

    public void start(){
        System.out.println("Starting Philosopher " + (this.id+1));
        if (t == null) {
            t = new Thread (this, id.toString());
            t.start ();
        }
    }
}
