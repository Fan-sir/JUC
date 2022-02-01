package com.xk.thread;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * 创建和运行线程demo
 *
 * @author 空白
 * @date 2021/11/17
 */
@Slf4j(topic = "c.Test1")
public class CreateAndRunThread {
    public static void main(String[] args) {

        Thread t = new Thread() {
            @Override
            public void run() {
                log.debug("running");
            }
        };

        t.setName("t1");
        t.start();

        log.debug("running");
    }
}

@Slf4j(topic = "c.Test2")
class   CreateAndRunThread01 {
    public static void main(String[] args) {
        Runnable r = () -> {
            log.debug("running");
        };

        Thread t = new Thread(r, "t2");

        t.start();

        log.debug("running");

    }
}

@Slf4j(topic = "c.Test3")
class CreateAndRunThread02 {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FutureTask<Integer> task = new FutureTask<Integer>(() -> {
            log.debug("hello");
            Thread.sleep(2000);
            return 100;
        });

        Thread t = new Thread(task, "t3");

        t.start();

        log.debug("{}", task.get());
    }
}
