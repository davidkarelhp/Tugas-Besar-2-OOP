package com.aetherwars.event;

public interface EventChannel {
    // sends event based on a topic
    void sendEvent(Publisher publisher, Event event);

    // add subscriber to topic
    void addSubscriber(Publisher publisher, Subscriber subscriber);

    void addPublisher(Publisher publisher);

//    Kalau perlu di-uncomment yang bawah
//    void removeSubsriber(Subscriber subscriber);
//
//    void removePublisher(Publisher publisher);
}
