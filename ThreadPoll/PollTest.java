import sun.awt.windows.WPrinterJob;

public class PollTest {
    public static void main(String[] args) {
        IPoll poll = new DefaultThreadPoll(10);

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        for (int i =0;i<30;i++){
            final int finalI = i;
            poll.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println("打印数字：" + finalI);
                }
            });
        }
        System.out.println("poll size: "+ poll.poolSize());
        // 验证是否会等正在工作的线程工作完成后才关掉线程池
//        System.out.println("停止线程池");
//        poll.shutDown();

//        poll.addWorker(5);
//        System.out.println("poll size:" + poll.poolSize());
//
//        poll.removedWorker(3);
//        System.out.println("poll size:" + poll.poolSize());

    }
}
