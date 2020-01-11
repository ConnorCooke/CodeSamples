// Connor Cooke
// CEC383
// 11239140

package DiningPhilosophers;

public class PhilosopherMonitor {

        private fork[] held;
        private int numberOfMealsEaten;
        private PhilosopherRunner[] philosophers;
        protected boolean end_dining;

        private class fork{
            boolean isHeld;
            private fork(){
                isHeld = false;
            }
        }

        public PhilosopherMonitor(int size){
            this.held=new fork[size];
            philosophers = new PhilosopherRunner[size];
            for(int i = 0; i< size; i++){
                this.held[i] = new fork();
                this.philosophers[i] = new PhilosopherRunner(i,this);
            }
            numberOfMealsEaten = 0;
            end_dining = false;
        }

        public void start(){
            for(PhilosopherRunner ph: philosophers){
                ph.start();
            }
        }

        public synchronized boolean getforks(int id){
            int lid;
            if(id ==0){
                lid = this.size()-1;
            }
            else{
                lid = id-1;
            }
            boolean result = this.held[lid].isHeld || this.held[id].isHeld;
            if(!result){
                this.held[lid].isHeld = true;
                this.held[id].isHeld = true;
            }
            return result;
        }

        public synchronized void relforks(int id){
            int lid;
            if(id ==0){
                lid = this.size()-1;
            }
            else{
                lid = id-1;
            }
            this.held[lid].isHeld = false;
            this.held[id].isHeld = false;
            this.numberOfMealsEaten++;
            if(this.numberOfMealsEaten>20){
                for(PhilosopherRunner ph: philosophers){
                    ph.stop();
                }
            }
        }

        public int size(){
            return this.held.length;
        }

    public static void main(String[] args) {
        PhilosopherMonitor philosForks = new PhilosopherMonitor(5);
        philosForks.start();
    }
}
