package com.xk.thread.javaMemoryModel.exercises;

import java.io.Serializable;

/**
 * 线程安全单例习题
 *
 * @author 空白
 * @date 2022/02/02
 */

//问题1：为什么加final？
//答：因为防止类被继承，修改了类中的方法，导致破坏了单例模式
//问题2：如果实现了序列化接口，还要做什么来防止反序列化破坏单例？
//答：添加这个方法后，在反序列化后返回的对象就是单例对象，而不是新的对象    public Object readResolve() { return INSTANCE; }
public final class SingletonExercises implements Serializable {
    //问题3：为什么设置为私有？是否能防止反射创建新的实例？
    //答：使别的类不能创建SingletonExercises，只有他自己创建，保证了对象单例。  不能，反射可以获得构造器，然后可以设置构造器的setAccessible(true)来暴力创建对象
    private SingletonExercises() {}

    //问题4：这样初始化是否能保证单例对象创建时的线程安全？
    //答：类加载时静态变量的创建都是由JVM控制的，我们可以相信JVM，因为他是线程安全的。
    private static final SingletonExercises INSTANCE = new SingletonExercises();

    //问题5：为什么提供静态方法而不是直接讲INSTANCE设置为public，讲出你知道的理由。
    //答：方法可以提供其更好的封装性，可以实现其他比如懒汉式的单例，还可以支持泛型
    public static SingletonExercises getInstance() {
        return INSTANCE;
    }

    public Object readResolve() {
        return INSTANCE;
    }
}
