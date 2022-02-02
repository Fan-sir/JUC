package com.xk.thread.javaMemoryModel;

import lombok.extern.slf4j.Slf4j;

import static java.lang.Thread.sleep;

/**
 * 线程可见性问题 Volatile也可以解决指令重排序，即有序性的问题,加上Volatile在当前属性执行前的指令不会重排序
 *
 * @author 空白
 * @date 2021/12/26
 */
@Slf4j(topic = "c.Volatile")
public class Volatile {
    //当前情况下t线程是不会跳出while循环的
    static boolean run = true;

    public static void main(String[] args) throws InterruptedException {

        Thread t = new Thread(() -> {
            while (run) {

            }
        });

        t.start();
        sleep(1);
        log.debug("停止 t");
        run = false;

    }
}

@Slf4j(topic = "c.Volatile1")
class Volatile1 {

    //run是在主存中存放的，但是在t线程中高速读取run，所以在t线程的工作内存中创建了run，1s后主线程修改了主存中的run值，但是t线程工作内存中的值没有被修改，
    //这样会导致t线程一直运行，这时可以采用volatile修饰run变量，让t线程每次都去读取主存中的run，即解决线程可见性问题
    volatile static boolean run = true;

    public static void main(String[] args) throws InterruptedException {

        Thread t = new Thread(() -> {
            while (run) {
                //如果在while循环中使用了System.out.println();就会停止while循环，原因是System.out.println();中使用了synchronize

            }
        });

        t.start();
        sleep(1);
        log.debug("停止 t");
        run = false;

    }
}

@Slf4j(topic = "c.Volatile2")
class Volatile2 {

    static boolean run = true;

    //当然，线程可见性也可以使用synchronized,但是synchronize会创建重量级锁，效率没有volatile高
    final static Object lock = new Object();

    public static void main(String[] args) throws InterruptedException {

        Thread t = new Thread(() -> {
            while (true) {
                synchronized (lock) {
                    if (!run) {
                        break;
                    }
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
