package com.designpatterns.factory;

// 3. PACKAGE-PRIVATE CONCRETE PRODUCTS (Blocks direct 'new' instantiation outside package)
class SMSNotification implements Notification{
    @Override
    public void notifyUser() {
        System.out.println("Sending SMS....");
    }
}
