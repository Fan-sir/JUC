package com.xk.thread.javaMemoryModel;

import lombok.extern.slf4j.Slf4j;

import static java.lang.Thread.sleep;

/**
 * 线程可见性问题
 *
 * @author 空白
 * @date 2021/12/26
 */
@Slf4j(topic = "c.Volatile")
public class Volatile {

//    static boolean run = true;

    //run是在主存中存放的，但是在t线程中高速读取run，所以在t线程的工作内存中创建了run，1s后主线程修改了主存中的run值，但是t线程工作内存中的值没有被修改，
    //这样会导致t线程一直运行，这时可以采用volatile修饰run变量，让t线程每次都去读取主存中的run，即解决线程可见性问题
    volatile static boolean run = true;

    //当然，线程可见性也可以使用synchronized
//    static boolean run = true;
    final static Object lock = new Object();

    public static void main(String[] args) throws InterruptedException {

        Thread t = new Thread(() -> {
            while (run) {
                synchronized (lock) {

                }
            }
        });

        t.start();
        sleep(1);
        log.debug("停止 t");
        synchronized (lock) {
            run = false;
        }

    }
}
