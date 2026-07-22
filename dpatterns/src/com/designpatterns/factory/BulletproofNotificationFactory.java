package com.designpatterns.factory;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

// 4. BULLETPROOF REGISTRY-BASED FACTORY (Adheres to Open-Closed Principle)
public class BulletproofNotificationFactory {
    //A map storing lamda functions (Suppliers) to instantiate objects dymanically
    private static final Map<NotificationChannel, Supplier<Notification>> registry = new HashMap<>();

    //static initializer block to register existing products
    static {
        registry.put(NotificationChannel.SMS, SMSNotification::new);
        registry.put(NotificationChannel.MAIL, EmailNotification::new);
        registry.put(NotificationChannel.WHATSAPP, WhatsAppNotification::new);
    }

    //Inerviwe flex: method to dynamically register new products without changing this class
    public static void registerProduct(NotificationChannel channel, Supplier<Notification> supplier){
        registry.put(channel, supplier);
    }

    public Notification createNotification(NotificationChannel channel){
        if(channel == null){
            throw new IllegalArgumentException("Channel cannot be null !");
        }

        Supplier<Notification> supplier = registry.get(channel);
        if(supplier == null){
            throw new IllegalArgumentException("Unregistered channel type: " + channel);
        }

        //creates the object dynamically
        return supplier.get();
    }
}
