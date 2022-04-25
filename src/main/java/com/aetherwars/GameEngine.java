package com.aetherwars;

import com.aetherwars.event.*;
import com.aetherwars.model.Phase;
import com.aetherwars.model.Player;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class GameEngine implements Publisher, Subscriber {
    private GameChannel eventChannel;
    private Player[] players;
    private int currentPlayer;

//    private int currentRound;

    private IntegerProperty currentRound;

    public GameEngine(Player p1, Player p2, GameChannel eventChannel) {
        this.players = new Player[2];
        this.players[0] = p1;
        this.players[1] = p2;
        this.eventChannel = eventChannel;
        this.currentPlayer = 0;
        this.currentRound = new SimpleIntegerProperty(1);
    }

    public int getCurrentRound() {
        return currentRound.get();
    }

    public IntegerProperty currentRoundProperty() {
        return currentRound;
    }

    public Player[] getPlayers() {
        return players;
    }

    public void setupGame() {
        this.drawBoth();
        publish(new ChangePlayerEvent(this.players[this.currentPlayer]));
    }

    public void drawBoth() {
        // di sini draw-nya gak dikembaliin
        this.players[1].drawOnly();
        this.players[0].drawOnly();
    }


    public void stageController(Phase phase) {
        if (phase == Phase.DRAW) {
            // ini draw yang dikembaliin
//            this.players[this.currentPlayer].draw();
        } else if (phase == Phase.END) {
//            Jalanin prosedur END phase disini, semua kartu currentplayer statusnya dijadiin belum dipakai dan sebagainya

            if (this.currentPlayer == 1) {
                this.currentRound.set(this.getCurrentRound() + 1);
            }

            this.currentPlayer = (this.currentPlayer == 0) ? 1 : 0;

            this.players[this.currentPlayer].increaseManaLimit();
            this.players[this.currentPlayer].resetMana();
            publish(new ChangePlayerEvent(this.players[this.currentPlayer]));
        }
    }

    @Override
    public void publish(Event event) {
        this.eventChannel.sendEvent(this, event);
    }

    @Override
    public void onEvent(Event event) {

    }
}
