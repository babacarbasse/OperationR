package application;
	
import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


/**
 * 
 * @author Cheikh
 * @date 25/05/2017
 * Classe principale qui se charge de demarrer l'application
 * Ecrit en javaFx
 */
public class Main extends Application {
	@Override
	public void start(Stage primaryStage) throws Exception{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MainScene.fxml"));
            Controller controleur= new Controller();
            loader.setController(controleur);
            Parent root = loader.load();
            primaryStage.setTitle("Application de resolution �quation 2nd degr� dans R");
            primaryStage.setScene(new Scene(root,840,600));
            primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
