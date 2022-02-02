package com.xk.thread.javaMemoryModel.exercises;

/**
 * balking模式习题
 * 希望doInit()方法仅被调用一次，下面的实现是否有问题，为什么？
 *
 * @author 空白
 * @date 2022/02/02
 */
public class BalkingExercises {
    volatile boolean initialized = false;

    void init() {
        if (initialized) {
            return;
        }
        doInit();
        initialized = true;
    }

    private void doInit() {

    }
}

//第一个问题是在多线程并发的情况下，initialized变量没有被synchronized修饰，可能造成多个线程同时进入doInit()执行，不能只保证doInit()执行一次，有现成安全问题
//第二个问题就是initialized变量我们只需要第一次线程访问时候进行判断，在修改initialized为true后，就不需要判断initialized了，这样可以提高效率，
// 解决方法时在synchronized外面在加一次initialized判断即可，volatile也保证了其写之前和读之后不会发生指令重排的问题，即JMM有序性问题
class BalkingExercisesAnswer {
    volatile boolean initialized = false;

    void init() {
        synchronized (BalkingExercisesAnswer.class) {
            if (initialized) {
                return;
            }
            initialized = true;
        }
        doInit();
    }

    private void doInit() {

    }
}
