package io.zemke.snow;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) {
        var javaVersion = System.getProperty("java.version");
        var javafxVersion = System.getProperty("javafx.version");
        System.out.println("javaVersion " + javaVersion);
        System.out.println("javafxVersion " + javafxVersion);
        var container = new StackPane();
        var width = 1000;
        var height = 728;
        var backgroundImage = new BackgroundImage(new
                Image(App.class.getResourceAsStream("/pic.jpg"), width, height, false, true),
                BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        container.setBackground(new Background(backgroundImage));
        var scene = new Scene(container, 640, 480);
        stage.setScene(scene);
        stage.setWidth(width);
        stage.setHeight(height);
        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
