package com.aetherwars.controller;

import com.aetherwars.event.Event;
import com.aetherwars.event.GameChannel;
import com.aetherwars.event.Publisher;
import com.aetherwars.event.Subscriber;
import com.aetherwars.model.Player;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;

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

    public BoardController(GameChannel channel, Player player) {
        this.channel = channel;
        this.player = player;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.charArr = new StackPane[]{character1, character2, character3, character4, character5};
        for (int i = 0; i < 5; i++) {
            FXMLLoader sumCharFXML = new FXMLLoader(getClass().getResource("../SummonedCharacter.fxml"));
            int idx = i;
            sumCharFXML.setControllerFactory(c -> new SummonedCharacterController(this.channel, this.player.getBoard().selectedChar(idx)));

            AnchorPane sumCharPane = null;

            try {
                sumCharPane = sumCharFXML.load();
//                this.character1.getChildren().add(sumCharPane);
                this.charArr[i].getChildren().add(sumCharPane);
            } catch (IOException e) {
                e.printStackTrace();
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
