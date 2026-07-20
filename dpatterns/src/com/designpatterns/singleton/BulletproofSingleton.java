package com.designpatterns.singleton;

import java.io.Serializable;

//To protect against serialization attacks, the class must implement Serializable
public class BulletproofSingleton implements Serializable, Cloneable {

    //Unique ID for serialization version control
    private static final long serialVersionUID = 1L;

    //volatile prevents instruction reordering and ensures thread visibility
    private static volatile BulletproofSingleton instance;

    //1. Private constructor + rflection protection
    private BulletproofSingleton(){
        //If a instance already exists, throw an exception to block reflection
        if(instance != null){
            throw new IllegalStateException("Instance already exits. Use getInstance().");
        }
    }

    //2. Double-checked locking for thread safety
    public static BulletproofSingleton getInstance(){
        if(instance == null){//First check (no performance penalty)
            synchronized (BulletproofSingleton.class){
                if(instance == null){//Second check (ensures thread-safety)
                    instance = new BulletproofSingleton();
                }
            }
        }
        return instance;
    }

    //3.Serialization protection
    //The JVM automatically calls this during deserialization to replace the new object
    protected Object readResolve(){
        return getInstance();
    }

    //4. Cloning protection
    //Overriding clone() to explicitly block duplicate instances
    @Override
    protected Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException("Cloning a Singleton instance is strictly prohibited.");
    }
}
