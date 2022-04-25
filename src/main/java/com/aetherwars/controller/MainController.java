package com.aetherwars.controller;

import com.aetherwars.GameEngine;
import com.aetherwars.event.EventChannel;
import com.aetherwars.event.GameChannel;
import com.aetherwars.model.Phase;
import com.aetherwars.event.*;
import com.aetherwars.model.Player;
import javafx.beans.binding.Bindings;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;


import java.net.URL;
import java.util.ResourceBundle;

public class MainController implements Initializable, Publisher, Subscriber {
    @FXML
    StackPane backPane;

    @FXML
    Button buttonSkip;

    @FXML
    Label attack_p, draw_p, end_p, plan_p;

    Phase[] phases = new Phase[] { Phase.DRAW, Phase.PLAN, Phase.ATTACK, Phase.END };

    Label[] phases_bar;

    int phase_id = 0;

    @FXML
    Label labelPlayer1;

    @FXML
    Label labelPlayer2;

    @FXML
    Label labelRound;

    @FXML
    ProgressBar healthPlayer1;

    @FXML
    ProgressBar healthPlayer2;

    private final int MAX_HEALTH = 80;

    private EventChannel channel;
    private GridPane drawPane;

    public MainController(GameChannel channel) {
        this.channel = channel;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        this.phases_bar = new Label[]{draw_p, plan_p, attack_p, end_p};
        this.healthPlayer1.setStyle("-fx-accent: green;");
        this.healthPlayer2.setStyle("-fx-accent: green;");

        this.buttonSkip.setOnAction(e -> {
            // Aturan ini buat skip phase, tapi ini  contoh aja
            switch(this.channel.getPhase())
        });

    }

    public void startGame(GameEngine gameEngine) {
        this.channel.addSubscriber(this, gameEngine);
        this.channel.addSubscriber(gameEngine, this);

        Player[] players = gameEngine.getPlayers();

        this.labelPlayer1.setText(players[0].getPlayerName());
        this.labelPlayer2.setText(players[1].getPlayerName());

        this.labelRound.textProperty().bind(Bindings.concat("Round ", gameEngine.currentRoundProperty()));

        this.healthPlayer1.progressProperty().bind(players[0].healthPointsProperty().divide(this.MAX_HEALTH));
        this.healthPlayer2.progressProperty().bind(players[1].healthPointsProperty().divide(this.MAX_HEALTH));
    }

    public void displayDraw() {
        // Contoh kalau mau nampilin sesuatu pakai button, di sini yang ditampilin tuh 3 kartu yang dipilih pas nge-draw
        drawPane = new GridPane();
//        drawPane.setGridLinesVisible(true);

        RowConstraints constraintsR = new RowConstraints();
        constraintsR.setVgrow(Priority.ALWAYS);
        drawPane.getRowConstraints().add(constraintsR);

        for (int i = 0 ; i < 3; i++) {
            ColumnConstraints constraints = new ColumnConstraints();
            constraints.setHgrow(Priority.ALWAYS);
            drawPane.getColumnConstraints().add(constraints);

            StackPane temp = new StackPane();
            temp.setMaxSize(150, 300);
            GridPane.setHalignment(temp, HPos.CENTER);

            temp.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
            drawPane.add(temp, i, 0);
            temp.setOnMouseClicked(e -> this.onDrawnCardClicked());

        }
        drawPane.setBackground(new Background(new BackgroundFill(new Color(0.6, 0.6, 0.6, 0.5), CornerRadii.EMPTY, Insets.EMPTY)));
        backPane.getChildren().add(drawPane);
    }

    public void onDrawnCardClicked() {
        // Method kalau salah satu kartu yang di-draw diklik, untuk sekarang kembali ke main panel aja
        backPane.getChildren().remove(drawPane);
    }


    @Override
    public void publish(Event event) {

    }

    @Override
    public void onEvent(Event event) {

    }
}
