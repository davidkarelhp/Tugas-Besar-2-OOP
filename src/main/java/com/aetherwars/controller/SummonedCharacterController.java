package com.aetherwars.controller;

import com.aetherwars.event.Event;
import com.aetherwars.event.GameChannel;
import com.aetherwars.event.Publisher;
import com.aetherwars.event.Subscriber;
import com.aetherwars.model.cards.Card;
import com.aetherwars.model.cards.character.SummonedCharacter;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class SummonedCharacterController implements Publisher, Subscriber, Initializable {
    @FXML
    ImageView imageCharacter;

    @FXML
    Label labelExp;

    @FXML
    Label labelHealth;

    @FXML
    Label labelAttack;

    private GameChannel channel;
    private SummonedCharacter character;

    public SummonedCharacterController(GameChannel channel, SummonedCharacter character){
        this.channel = channel;
        this.character = character;

    }


    @Override
    public void publish(Event event) {

    }

    @Override
    public void onEvent(Event event) {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
