package com.xk.thread;

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
        TwoPhaseTerminationVolatile thread = new TwoPhaseTerminationVolatile();
        thread.start();
        Thread.sleep(3500);
        log.debug("停止监控");
        thread.stop();
    }

}
@Slf4j(topic = "c.TwoPhaseTerminationVolatile")
class TwoPhaseTerminationVolatile {
    //监控线程
    private Thread monitorThread;

    //停止标记
    private boolean stop = false;

    //启动监控线程
    public void start() {
        monitorThread = new Thread(() -> {
            while (true) {
                if (stop) {
                    log.debug("料理后事");
                    break;
                }

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
