package com.aetherwars;

import com.aetherwars.event.Event;
import com.aetherwars.event.EventChannel;
import com.aetherwars.event.Publisher;
import com.aetherwars.event.Subscriber;
import com.aetherwars.model.Player;

public class GameEngine implements Publisher, Subscriber {
    EventChannel eventChannel;
    private Player[] players;

    public GameEngine(Player p1, Player p2, EventChannel eventChannel) {
        this.players[0] = p1;
        this.players[1] = p2;
        this.eventChannel = eventChannel;
    }

    @Override
    public void publish(Event event) {

    }

    @Override
    public void onEvent(Event event) {

    }
}
