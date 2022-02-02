package com.xk.thread.javaMemoryModel;

import lombok.extern.slf4j.Slf4j;

/**
 * volatile实现两阶段终止
 *
 * @author 空白
 * @date 2021/12/26
 */

@Slf4j(topic = "c.TwoPhaseTermination")
public class TwoPhaseTermination {
    public static void main(String[] args) throws InterruptedException {
        TwoPhaseTerminationVolatile2 thread = new TwoPhaseTerminationVolatile2();
        for (int i = 0; i < 10000; i++) {
            thread.start();
        }
        Thread.sleep(3500);
        log.debug("停止监控");
        thread.stop();
    }

}
@Slf4j(topic = "c.TwoPhaseTerminationVolatile")
class TwoPhaseTerminationVolatile {
    //监控线程
    private Thread monitorThread;

    //启动监控线程
    public void start() {
        monitorThread = new Thread(() -> {
            while (true) {
                Thread current = Thread.currentThread();
                //判断当前线程是否被打断
                if (current.isInterrupted()) {
                    log.debug("料理后事");
                    break;
                }
                //如果在sleep中打断,会清除打断标记,则执行catch语句块,重新设置打断标记,下一个循环进来就可以直接料理后事
                try {
                    Thread.sleep(1000);
                    log.debug("执行监控记录");
                } catch (InterruptedException e) {
                    current.interrupt();
                }
            }
        }, "monitor");
        monitorThread.start();
    }

    //停止监控线程
    public void stop() {
        monitorThread.interrupt();
    }
}

@Slf4j(topic = "c.TwoPhaseTerminationVolatile1")
class TwoPhaseTerminationVolatile1 {
    //监控线程
    private Thread monitorThread;

    private volatile boolean stop = false;

    //启动监控线程
    public void start() {
        monitorThread = new Thread(() -> {
            while (true) {
                if (stop) {
                    log.debug("料理后事");
                    break;
                }
                //如果在sleep中打断,会清除打断标记,则执行catch语句块,重新设置打断标记,下一个循环进来就可以直接料理后事
                //这里有个疑问就是sleep和log.debug都会让stop更新,也就是说可以去掉volatile,是sleep和log中也使用了synchronize么
                try {
                    Thread.sleep(1000);
                    log.debug("执行监控记录");
                } catch (InterruptedException e) {

                }
            }
        }, "monitor");
        monitorThread.start();
    }

    //停止监控线程
    public void stop() {
        stop = true;
        monitorThread.interrupt();
    }
}

@Slf4j(topic = "c.TwoPhaseTerminationVolatile2")
class TwoPhaseTerminationVolatile2 {
    //监控线程
    private Thread monitorThread;

    private volatile boolean stop = false;

    private volatile boolean starting = false;

    //启动监控线程
    public void start() {
        synchronized(this) {
            if (starting) {
                return;
            }
            starting = true;
        }

        monitorThread = new Thread(() -> {
            while (true) {
                if (stop) {
                    log.debug("料理后事");
                    break;
                }
                //如果在sleep中打断,会清除打断标记,则执行catch语句块,重新设置打断标记,下一个循环进来就可以直接料理后事
                //这里有个疑问就是sleep和log.debug都会让stop更新,也就是说可以去掉volatile,是sleep和log中也使用了synchronize么
                try {
                    Thread.sleep(1000);
                    log.debug("执行监控记录");
                } catch (InterruptedException e) {

                }
            }
        }, "monitor");
        monitorThread.start();
    }

    //停止监控线程
    public void stop() {
        stop = true;
        monitorThread.interrupt();
    }
}
