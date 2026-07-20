package com.designpatterns.singleton;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLOutput;

public class SingletonAttackTest {
    public static void main(String[] args) {
        BulletproofSingleton originalInstance = BulletproofSingleton.getInstance();
        System.out.println("Original Instance HashCode: " + originalInstance.hashCode());
        System.out.println("-------------------------------------------\n");

        //---- Test 1: Reflection Attack ----
        System.out.println("[Test 1] Launching Reflection Attack...");
        try{
            Constructor<BulletproofSingleton> constructor = BulletproofSingleton.class.getDeclaredConstructor();
            constructor.setAccessible(true);//Forces private constructor to open

            //This statement will trigger the exception in our constructor
            BulletproofSingleton reflectionInstance = constructor.newInstance();
            System.out.println("Fail : Reflection broke the singleton. Hash: " + reflectionInstance.hashCode());
        } catch (Exception e) {
            System.out.println("SUCCESS: Reflection attack defeated! Reason: " + e.getCause().getMessage());
        }
        System.out.println("\n----------------------------------------------------------");

        //--- Test 2 : Serialization Attack ----
        System.out.println("[Test 2] Launching Serialization Allack...");
        try{
            // Serialize the object to a memory byte array
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(originalInstance);
            oos.close();

            //Deserialize the object back
            ByteArrayInputStream bais =  new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bais);
            BulletproofSingleton deserialozedInstance = (BulletproofSingleton) ois.readObject();
            ois.close();
            System.out.println("Deserialized Instance HashCode: " + deserialozedInstance);
            if(originalInstance == deserialozedInstance){
                System.out.println("SUCCESS: Serialization attack defeated! Both references point to the same object.");
            } else {
                System.out.println("FAIL: Serialization broke the singleton.");
            }
            System.out.println("\n-----------------------------------------------------------");
        } catch (Exception e){
            System.out.println("Error during serialization test : " + e.getMessage());
        }

        //---- TEST 3 : CLoning Attack --------
        System.out.println("[Test 3] Launching Cloning Attack...");
        try{
            // This statement will trigger the overridden clone exception
            BulletproofSingleton clonedInstance = (BulletproofSingleton) originalInstance.clone();
            System.out.println("FAIL: Cloning broke the singleton. Hash: " + clonedInstance);
        } catch (CloneNotSupportedException e){
            System.out.println("SUCCESS : Cloning attack defeated! Reason: " + e.getMessage());
        }
    }
}
