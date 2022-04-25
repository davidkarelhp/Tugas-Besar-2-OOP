package com.aetherwars.controller;

import com.aetherwars.GameEngine;
import com.aetherwars.event.*;
import com.aetherwars.model.Player;
import com.aetherwars.model.cards.Card;
import com.aetherwars.model.cards.character.Character;
import javafx.beans.binding.Bindings;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;


import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MainController implements Initializable, Publisher, Subscriber {
    @FXML
    StackPane backPane;

    @FXML
    Button buttonSkip;

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

    @FXML
    GridPane handGrid;

    private final int MAX_HEALTH = 80;

    private EventChannel channel;
    private GridPane drawPane;

    public MainController(GameChannel channel) {
        this.channel = channel;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.healthPlayer1.setStyle("-fx-accent: green;");
        this.healthPlayer2.setStyle("-fx-accent: green;");

        this.buttonSkip.setOnAction(e -> {
            // Aturan ini buat skip phase, tapi ini  contoh aja
            this.displayDraw();
        });

    }

    public void startGame(GameEngine gameEngine) throws IOException {

        Player[] players = gameEngine.getPlayers();

        this.labelPlayer1.setText(players[0].getPlayerName());
        this.labelPlayer2.setText(players[1].getPlayerName());

        this.labelRound.textProperty().bind(Bindings.concat("Round ", gameEngine.currentRoundProperty()));

        this.healthPlayer1.progressProperty().bind(players[0].healthPointsProperty().divide(this.MAX_HEALTH));
        this.healthPlayer2.progressProperty().bind(players[1].healthPointsProperty().divide(this.MAX_HEALTH));

        this.channel.addSubscriber(this, gameEngine);
        this.channel.addSubscriber(gameEngine, this);
        gameEngine.setupGame();
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

    public void refreshHand(Player player) throws IOException {
        handGrid.getChildren().clear();
        List<Card> hand = player.getHand().getHand();
        int i = 0;
        for (Card card: hand) {
            Character charcard = (Character)card;
            FXMLLoader cardFXML = new FXMLLoader(getClass().getResource("../Card.fxml"));
            cardFXML.setControllerFactory(c -> new CardController(charcard.getName(),charcard.getMana() , charcard.getImagePath(), charcard.getBaseAttack(), charcard.getBaseHealth()));
            StackPane cardPane = cardFXML.load();

            StackPane.setMargin(cardPane, new Insets(10, 10, 10, 10));
            this.handGrid.add(cardPane, i, 0);
            i++;
        }

    }

    @Override
    public void publish(Event event) {

    }

    @Override
    public void onEvent(Event event) {
        try {
            if (event instanceof ChangePlayerEvent) {
                    this.refreshHand((Player)event.getEvent());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
