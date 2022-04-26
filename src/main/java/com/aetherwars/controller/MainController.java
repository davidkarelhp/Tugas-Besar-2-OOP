package com.aetherwars.controller;

import com.aetherwars.GameEngine;
import com.aetherwars.event.EventChannel;
import com.aetherwars.event.GameChannel;
import com.aetherwars.model.Phase;
import com.aetherwars.event.*;
import com.aetherwars.model.Player;
import com.aetherwars.model.cards.Card;
import com.aetherwars.model.cards.character.Character;
import javafx.beans.binding.Bindings;
import javafx.event.ActionEvent;
import javafx.event.EventType;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
    Label atack_p, draw_p, end_p, plan_p;

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

    @FXML
    GridPane handGrid;

    private final int MAX_HEALTH = 80;

    private GameChannel channel;
    private GridPane drawPane;

    GameEngine engine;

    public MainController(GameChannel channel) {
        this.channel = channel;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        this.phases_bar = new Label[]{draw_p, plan_p, atack_p, end_p};
        this.healthPlayer1.setStyle("-fx-accent: green;");
        this.healthPlayer2.setStyle("-fx-accent: green;");

        this.buttonSkip.setOnAction(e -> {
            // Aturan ini buat skip phase, tapi ini  contoh aja
            switch(this.channel.getPhase()){
                case SKILLPICK:
                    break;

                default:
                    this.currentPhase(e);
                    break;
            }
        });

    }

    public void startGame(GameEngine gameEngine) throws IOException {

        this.engine = gameEngine;

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

    public void currentPhase(ActionEvent event){
        phases_bar[phase_id].setStyle("-fx-background-color : darkgray;" + "-fx-color: dimgray");
        phase_id++;
        switch(phase_id){
            case 1:
                System.out.println("phase 1");
                break;

            case 2:
                System.out.println("phase 2");
                break;

            case 3:
                System.out.println(phase_id);
                break;

            case 4:
                System.out.println(phase_id);
                break;
//                if (!this.targeting.isEmpty()) {
//                    this.targeting.get(0).toggleSelected();
//                    for (SummonedCharacterController summonedchara_controller : this.player_controllers[this.channel
//                            .getPlayerID() % 2 + 1].getSummonedCharaController()) {
//                        summonedchara_controller.setHinting(false);
//                    }
//                    this.player_controllers[this.channel.getPlayerID() % 2 + 1].setHinting(false);
//                    this.targeting.clear();
//                }
//
//                int cur_player = this.engine.getCurrentPlayer()
//                if (this.game_engine.getPlayer(cur_player).getHand().size() > Player.MAX_HAND) {
//                    this.channel.setPhase(Phase.DISCARD);
//                }
//                break;
//            case 4:
//                int prev_player = game_engine.getCurPlayer() % 2 + 1;
//                if (this.game_engine.getPlayer(prev_player).getHand().size() > Player.MAX_HAND) {
//                    phase_id--;
//                    phase_bar[phase_id].setStyle("-fx-background-color: aquamarine;" + "-fx-color: black");
//                    AlertBox.display(1280 / 1.5, 720 / 1.5, "Hand card limit exceeded", "Discard one card to continue.\nDouble Click to Discard.");
//                    return;
//                }
//
//                for (int i = 1; i <= 2; i++) {
//                    this.player_controllers[i].flipHand();
//                }
//                break;
            default:
                break;

        }

        phase_id %= 4;
        this.engine.stageController(phases[phase_id]);
        if (phase_id == 0) {
            this.channel.setPlayerById(this.engine.getCurrentPlayer());
        }
        if (this.channel.getPhase() != Phase.DISCARD) {
            this.channel.setPhase(phases[phase_id]);
        }

        phases_bar[phase_id].setStyle("-fx-background-color: aquamarine;" + "-fx-color: black");
//        if (phase_id==0) {sleep(500, true);}
    }
}
