package edu.up.cs301threadslab;

public class ThreadedAnimationView extends Thread{
    // instance variable for the animation view
    AnimationView view;

    // constructor for the view
    public ThreadedAnimationView(AnimationView v) {
        view = v;
    }

    @Override
    public void run() {
        // call the parent class
        super.run();
        for(int i=0; i<100; i--){
            // call post invalidate on the current view
            view.postInvalidate();

            // sleep for three seconds
            try {
                Thread.sleep(1 / 20); // cp4
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            }
        }

}
