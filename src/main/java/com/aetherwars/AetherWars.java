package com.aetherwars;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import com.aetherwars.controller.MainController;
import com.aetherwars.event.GameChannel;
import com.aetherwars.model.Player;
import com.aetherwars.model.cards.character.CharacterType;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import com.aetherwars.model.cards.character.Character;
import com.aetherwars.util.CSVReader;

public class AetherWars extends Application {
  private static final String CHARACTER_CSV_FILE_PATH = "card/data/character.csv";

  public void loadCards() throws IOException, URISyntaxException {
    File characterCSVFile = new File(getClass().getResource(CHARACTER_CSV_FILE_PATH).toURI());
    CSVReader characterReader = new CSVReader(characterCSVFile, "\t");
    characterReader.setSkipHeader(true);
    List<String[]> characterRows = characterReader.read();
    for (String[] row : characterRows) {
      int id = Integer.parseInt(row[0]);
      String name = row[1];
      CharacterType characterType = CharacterType.valueOf(row[2]);
      String description = row[3];
      String imagePath = row[4];
      int attack = Integer.parseInt(row[5]);
      int health = Integer.parseInt(row[6]);
      int mana = Integer.parseInt(row[7]);
      int attackUp = Integer.parseInt(row[8]);
      int healthUp = Integer.parseInt(row[9]);

      Character c = new Character(id, name, description, mana, imagePath, characterType, attack, health, attackUp, healthUp);
      System.out.println(c);
    }
  }

  @Override
  public void start(Stage stage) throws IOException {
      Text text = new Text();
//    try {

      GameChannel channel = new GameChannel();
      FXMLLoader gameLoader = new FXMLLoader(getClass().getResource("Main.fxml"));
      gameLoader.setControllerFactory(c -> new MainController(channel));

      Parent root = gameLoader.load();
      MainController controller = gameLoader.getController();
      channel.setController(controller);

      Player player1 = new Player();
      Player player2 = new Player();
//      GameEngine gameEngine = new GameEngine(player1, player2);
      GameEngine gameEngine = new GameEngine();

      Scene scene = new Scene(root);

      stage.setTitle("Minecraft: Aether Wars");
      stage.setScene(scene);
      stage.setMaximized(true);
      stage.setResizable(false);
      stage.show();
//    } catch (Exception e) {
//      System.out.println(e);
//    }

//    controller.startGame(gameEngine);
    try {
      this.loadCards();
      text.setText("Minecraft: Aether Wars!");
    } catch (Exception e) {
      text.setText("Failed to load cards: " + e);
    }
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
