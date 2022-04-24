package com.aetherwars;

import com.aetherwars.event.Event;
import com.aetherwars.event.EventChannel;
import com.aetherwars.event.Publisher;
import com.aetherwars.event.Subscriber;
import com.aetherwars.model.Phase;
import com.aetherwars.model.Player;

public class GameEngine implements Publisher, Subscriber {
    private EventChannel eventChannel;
    private Player[] players;
    private int currentPlayer;
    private int currentRound;

    public GameEngine(Player p1, Player p2, EventChannel eventChannel) {
        this.players[0] = p1;
        this.players[1] = p2;
        this.eventChannel = eventChannel;
        this.currentPlayer = 0;
        this.currentRound = 1;
    }

    public void setupGame() {
        this.drawBoth();
    }

    public void drawBoth() {
        // di sini draw-nya gak dikembaliin
//        this.players[1].draw();
//        this.players[0].draw();
    }


    public void stageController(Phase phase) {
        if (phase == Phase.DRAW) {
            // ini draw yang dikembaliin
//            this.players[this.currentPlayer].draw();
        } else if (phase == Phase.END) {
//            Jalanin prosedur END phase disini, semua kartu currentplayer statusnya dijadiin belum dipakai dan sebagainya
//            this.players[this.currentPlayer].

            if (this.currentPlayer == 1) {
                this.currentRound++;
            }

            if (this.currentPlayer == 1) {
                this.currentPlayer = (this.currentPlayer == 0) ? 1 : 0;
            }
        }
    }

    @Override
    public void publish(Event event) {

    }

    @Override
    public void onEvent(Event event) {

    }
}
