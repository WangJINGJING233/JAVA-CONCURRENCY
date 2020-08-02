

public interface IPoll {
    /**
     * 把任务加入队列
     * @param runnable
     */
    void execute(Runnable runnable);

    /**
     * 关掉线程池
     * 等待已经开始工作的线程结束
     */
    void shutDown();

    /**
     * 添加线程
     * @param num
     */
    void addWorker(int num);

    /**
     * 移除线程
     * @param num
     */
    void removedWorker(int num);

    /**
     * 获取当前线程池的大小
     * @return 线程池的大小
     */
    int poolSize();

    /**
     * 马上关掉线程
     */
    void shutDownNow();
}
