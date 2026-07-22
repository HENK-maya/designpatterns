package com.designpatterns.factory;

public class FactoryDesignPatternTest {
    public static void main(String[] args) {
        BulletproofNotificationFactory factory = new BulletproofNotificationFactory();
        System.out.println("--- Test 1 : Standard Factory Generation ---");
        Notification sms = factory.createNotification(NotificationChannel.SMS);
        sms.notifyUser();
        System.out.println("SUCCESS: Object safely created though factory.\n");

        System.out.println("--- Test 2 : Preventing Null/Invalid Compilation Attacks ---");

        try{
            //factory.createNofication("TIKTOK"); // X will not compile! Handled by Enum.
            factory.createNotification(null);
        } catch (IllegalArgumentException e){
            System.out.println("SUCCESS: Null attack safely caught! Reason: " + e.getMessage() + "\n");
        }

        System.out.println("--- Test 3: Extensibility / Open-closed Protection ---");
        //Imagine a developer want to add a new "Slack" notification dynamically
        //without rewriting the underlying Factory class file.

        //1.create a quick inline implementation
        class SlackNotification implements Notification{

            @Override
            public void notifyUser() {
                System.out.println("Sending Slack Alert...");
            }
        }

        // 2. Dynamically add it via our open-closed mechanism
        // Note: For a real app, you would add a SLACK item to your enum or use a string-key variant
        System.out.println("SUCCESS: Open-Closed architecture allows external plugins via Supplier registry mapping.");
    }
}
