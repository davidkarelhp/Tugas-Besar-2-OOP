package com.aetherwars.controller;

import com.aetherwars.event.*;
import com.aetherwars.model.cards.Card;
import com.aetherwars.model.cards.character.SummonedCharacter;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ResourceBundle;

public class SummonedCharacterController implements Publisher, Subscriber, Initializable {
    @FXML
    AnchorPane characterPane;

    @FXML
    ImageView imageCharacter;

    @FXML
    Label labelExp, labelAttack, labelHealth;;

    private GameChannel channel;
    private SummonedCharacter character;

    public SummonedCharacterController(GameChannel channel, SummonedCharacter character){
        this.channel = channel;
        this.character = character;

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        File file = null;

        try {
            file = new File(getClass().getResource("../" + this.character.getCharacter().getImagePath()).toURI());
        } catch (Exception e) {
            e.printStackTrace();
        }

//        System.out.println(this.character.getCharacter().getName());
//        System.out.println(this.character.getHealth());

        try {
            imageCharacter.setImage(new Image(file.toURI().toString(), 55, 55, true, true));

        } catch (Exception e) {
            System.out.println("no picture");
            imageCharacter.setImage(null);
        }

        labelAttack.textProperty().bind(this.character.bindedAttackProperty().asString());

        labelHealth.textProperty().bind(this.character.bindedHealthProperty().asString());

        labelExp.textProperty().bind(Bindings.concat(this.character.bindedExpProperty(), "/", this.character.bindedLevelProperty().multiply(2).subtract(1), " [", this.character.bindedLevelProperty(), "]"));
    }

    @Override
    public void publish(Event event) {
    }

    @Override
    public void onEvent(Event event) {

    }

}
