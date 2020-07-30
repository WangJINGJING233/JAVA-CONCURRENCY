package com.demo;

public class deadlock {
    public static void main(String[] args) {
        // 创建资源一、二
        Obj1 obj1 = new Obj1();
        Obj2 obj2 = new Obj2();
        // 创建两个线程
        Thread t1 = new Thread(new SynAddRunable(obj1,obj2,1,2,true),"thread1");
        Thread t2 = new Thread(new SynAddRunable(obj1,obj2,1,2,false),"thread2");
        // 启动，演示死锁
        t1.start();
        t2.start();


    }

    /**
     * 线程死锁演示
     */
    public static class SynAddRunable implements Runnable{
        Obj1 obj1;
        Obj2 obj2;
        int a,b;
        boolean flag;

        public SynAddRunable(Obj1 obj1, Obj2 obj2, int a, int b, boolean flag) {
            this.obj1 = obj1;
            this.obj2 = obj2;
            this.a = a;
            this.b = b;
            this.flag = flag;
        }

        @Override
        public void run() {
            try{
                if (flag) {
                    synchronized (obj1){
                        // 获得资源obj1，运行一段时间，需要获得资源obj2才能继续运行
                        Thread.sleep(100);
                        synchronized (obj2){
                            System.out.println(a+b);
                        }
                    }
                }else{
                    synchronized (obj2){
                        // 获得资源obj2，运行一段时间，需要获得资源obj1才能继续运行
                        Thread.sleep(100);
                        synchronized (obj1){
                            System.out.println(a+b);
                        }
                    }
                }
            }catch (InterruptedException e){
                e.printStackTrace();
            }

        }
    }

    // 资源一
    public static class Obj1{}
    // 资源二
    public static class Obj2{}
}
