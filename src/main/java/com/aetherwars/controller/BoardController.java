package com.aetherwars.controller;

import com.aetherwars.event.*;
import com.aetherwars.model.Player;
import com.aetherwars.model.cards.character.SummonedCharacter;
import javafx.beans.binding.Binding;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.Pair;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class BoardController implements Initializable, Publisher, Subscriber {
    @FXML
    StackPane character1;

    @FXML
    StackPane character2;

    @FXML
    StackPane character3;

    @FXML
    StackPane character4;

    @FXML
    StackPane character5;

    private GameChannel channel;
    private Player player;
    private StackPane[] charArr;
    private int handIdx;

    public BoardController(GameChannel channel, Player player) {
        this.channel = channel;
        this.player = player;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.charArr = new StackPane[]{character1, character2, character3, character4, character5};
//        for (int i = 0; i < 5; i++) {
//            FXMLLoader sumCharFXML = new FXMLLoader(getClass().getResource("../SummonedCharacter.fxml"));
//            int idx = i;
//            sumCharFXML.setControllerFactory(c -> new SummonedCharacterController(this.channel, this.player.getBoard().selectedChar(idx)));
//
//            AnchorPane sumCharPane = null;
//
//            try {
//                sumCharPane = sumCharFXML.load();
//                this.charArr[i].getChildren().add(sumCharPane);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//
//        }
//        Label x = new Label("");
//        ListProperty<SummonedCharacter> ch = new SimpleListProperty<>();
//        x.textProperty().bind(Bindings.when(ch.isNull()).then("lol").otherwise("lal"));
    }

    public void prepareToMoveToBoardEventHandler() {
        for (int i = 0; i < 5; i++) {
            int idx = i;
            this.charArr[i].setOnMouseClicked(e -> publish(new MoveToBoardEvent(this.handIdx, idx)));
        }
    }

    public void refreshBoard() {
        for (int i = 0; i < 5; i++) {
            this.charArr[i].getChildren().clear();

            if (this.player.getBoard().getAtSlot(i) != null) {
                FXMLLoader sumCharFXML = new FXMLLoader(getClass().getResource("../SummonedCharacter.fxml"));
                int idx = i;

                sumCharFXML.setControllerFactory(c -> new SummonedCharacterController(this.channel, this.player.getBoard().selectedChar(idx)));
                AnchorPane sumCharPane = null;

                try {
                    sumCharPane = sumCharFXML.load();
                    this.charArr[i].getChildren().add(sumCharPane);
                    sumCharPane.setOnMouseClicked(e -> showCharacterOptions(this.charArr[idx], idx));

                    AnchorPane temp = sumCharPane;
                    sumCharPane.setOnMouseEntered(e -> temp.setBackground(new Background(new BackgroundFill(new Color(0.6, 0.6, 0.6, 0.5), CornerRadii.EMPTY, Insets.EMPTY))));
                    sumCharPane.setOnMouseExited(e -> temp.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY))));
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        }
    }

    public void showCharacterOptions(StackPane characterPane, int idx) {
        StackPane optionPane = new StackPane();
        optionPane.setBackground(new Background(new BackgroundFill(new Color(0.6, 0.6, 0.6, 0.5), CornerRadii.EMPTY, Insets.EMPTY)));
        characterPane.getChildren().add(optionPane);

        Button throwOut = new Button("Throw Out");
        Button cancel = new Button("Cancel");

        StackPane.setMargin(throwOut, new Insets(10, 10, 10, 10));
        StackPane.setMargin(cancel, new Insets(10, 10, 10, 10));

        StackPane.setAlignment(throwOut, Pos.TOP_CENTER);
        StackPane.setAlignment(cancel, Pos.BOTTOM_CENTER);

        optionPane.getChildren().add(throwOut);
        optionPane.getChildren().add(cancel);

        throwOut.setOnAction(e -> publish(new ThrowOutFromBoardEvent(idx)));

        cancel.setOnAction(e -> {
            characterPane.getChildren().remove(optionPane);
            characterPane.setOnMouseClicked(ev -> showCharacterOptions(characterPane, idx));
        });
        characterPane.setOnMouseClicked(null);
    }

    @Override
    public void publish(Event event) {
        this.channel.sendEvent(this, event);
    }

    @Override
    public void onEvent(Event event) {

        if (event instanceof PrepareToMoveToBoardEvent) {

            Pair<Player, Integer> e = (Pair<Player, Integer>) event.getEvent();
            if (this.player.equals(e.getKey())) {
                this.handIdx = e.getValue();
                prepareToMoveToBoardEventHandler();
                System.out.println("here");
            }

        } else if (event instanceof RefreshBoardEvent) {
            refreshBoard();

        }
    }
}
