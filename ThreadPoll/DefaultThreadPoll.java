import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

public class DefaultThreadPoll implements IPoll {

    private final BlockingQueue<Runnable> jobs = new LinkedBlockingDeque<>();
    private final BlockingQueue<Worker> workers = new LinkedBlockingDeque<>();

    public DefaultThreadPoll(int num){
        initPool(num);
    }

    public void initPool(int num){
        for (int i=0;i<num;i++){
            Worker worker = new Worker();
            workers.add(worker);
            worker.start();
        }
    }

    @Override
    public void execute(Runnable runnable) {
        if (runnable!=null){
            jobs.add(runnable);
        }

    }

    @Override
    public void shutDown() {
        while (true){
            if(jobs.size()==0){
                for (Worker worker: workers){
                    worker.stopRunning();
                }
            }
            break;
        }
        jobs.clear();
    }

    @Override
    public void addWorker(int num) {
        for (int i=0;i<num;i++){
            Worker worker = new Worker();
            workers.offer(worker);
            worker.start();
        }

    }

    @Override
    public void removedWorker(int num) {
        for (int i=0;i<num;i++){
            try {
                workers.take().stopRunning();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public int poolSize() {
        return workers.size();
    }

    @Override
    public void shutDownNow() {
        jobs.clear();
        shutDown();

    }

    private class Worker extends Thread{
        private volatile boolean isRunning = true;

        public void run(){
            while (isRunning){
                Runnable runnable = null;
                try {
                    runnable = jobs.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                if (runnable!=null){
                    System.out.println(getName()+"-->");
                    runnable.run();
                }

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }

            System.out.println(getName()+"销毁");
        }

        public void stopRunning(){
            isRunning = false;
        }
    }
}
