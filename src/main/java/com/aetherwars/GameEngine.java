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
    private IntegerProperty currentRound;
    private int currentPhase;
    private static Phase[] phases = {Phase.DRAW, Phase.PLAN, Phase.ATTACK, Phase.END};

    public GameEngine(Player p1, Player p2, GameChannel eventChannel) {
        this.players = new Player[2];
        this.players[0] = p1;
        this.players[1] = p2;
        this.eventChannel = eventChannel;
        this.currentPlayer = 0;
        this.currentRound = new SimpleIntegerProperty(1);
        this.currentPhase = 0;
    }

    public int getCurrentRound() {
        return currentRound.get();
    }

    public int getCurrentPlayer(){
        return this.currentPlayer;
    }

    public IntegerProperty currentRoundProperty() {
        return currentRound;
    }

    public Player[] getPlayers() {
        return players;
    }

    public void setupGame() {
        this.drawBoth();
        publish(new CurrentPhaseEvent(currentPhase()));
        publish(new ChangePlayerEvent(this.players[this.currentPlayer]));
    }

    public void drawBoth() {
        // di sini draw-nya gak dikembaliin
        this.players[1].drawOnly();
        this.players[0].drawOnly();
    }

    public void nextPhase() {
        this.currentPhase = this.currentPhase == 3 ? 0 : this.currentPhase + 1;
        System.out.println(this.currentPhase);
        publish(new CurrentPhaseEvent(currentPhase()));
    }

    public Phase currentPhase() {
        return phases[this.currentPhase];
    }

    public void nextPhaseProcess() {
        this.nextPhase();
        phaseController();
    }

    public void phaseController() {
        if (currentPhase() == Phase.DRAW) {
            // ini draw yang dikembaliin

        } else if (currentPhase() == Phase.PLAN){

        } else if (currentPhase() == Phase.ATTACK) {

        } else if (currentPhase()  == Phase.END) {
//            Jalanin prosedur END phase disini, semua kartu currentplayer statusnya dijadiin belum dipakai dan sebagainya

            if (this.currentPlayer == 1) {
                this.currentRound.set(this.getCurrentRound() + 1);
            }

            this.currentPlayer = (this.currentPlayer == 0) ? 1 : 0;

            this.players[this.currentPlayer].increaseManaLimit();
            this.players[this.currentPlayer].resetMana();

            publish(new ChangePlayerEvent(this.players[this.currentPlayer]));

            // Langsung draw phase lagi setelah end
            this.nextPhaseProcess();
        }
    }

    @Override
    public void publish(Event event) {
        this.eventChannel.sendEvent(this, event);
    }

    @Override
    public void onEvent(Event event) {
        try {

            if (event instanceof NextPhaseEvent) {
                nextPhaseProcess();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
