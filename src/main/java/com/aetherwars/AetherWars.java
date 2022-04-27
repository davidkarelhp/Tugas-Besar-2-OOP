package com.aetherwars;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import com.aetherwars.controller.MainController;
import com.aetherwars.event.GameChannel;
import com.aetherwars.model.Dealer;
import com.aetherwars.model.Player;
import com.aetherwars.model.cards.character.CharacterType;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;

import com.aetherwars.model.cards.character.Character;
import com.aetherwars.util.CSVReader;

public class AetherWars extends Application {
  @Override
  public void start(Stage stage) throws IOException, URISyntaxException {
      FileLoader.loadFiles();

      GameChannel channel = new GameChannel();
      FXMLLoader gameLoader = new FXMLLoader(getClass().getResource("Main.fxml"));
      gameLoader.setControllerFactory(c -> new MainController(channel));

      Parent root = gameLoader.load();
      MainController controller = gameLoader.getController();
      channel.setController(controller);

      int deckSize = ThreadLocalRandom.current().nextInt(40, 60 + 1);

      Player player1 = new Player("Steve", Dealer.getRandomDeck(deckSize), channel);
      Player player2 = new Player("Alex", Dealer.getRandomDeck(deckSize), channel);

      GameEngine gameEngine = new GameEngine(player1, player2, channel);
//
      Rectangle2D screenBounds = Screen.getPrimary().getBounds();
      Scene scene = null;
//
      if (screenBounds.getHeight() > 1280 || screenBounds.getHeight() > 720) {
          scene = new Scene(root, 1280, 720);
      } else {
          scene = new Scene(root);
          stage.setMaximized(true);
      }

      stage.setScene(scene);
      stage.setTitle("Minecraft: Aether Wars");
      stage.setResizable(false);
      stage.show();

      controller.startGame(gameEngine);

  }

  public int add(int x, int y) {
    return x + y;
  }

  public int minus(int x, int y) {
    return x - y;
  }

  public static void main(String[] args) {
    launch();
  }
}
