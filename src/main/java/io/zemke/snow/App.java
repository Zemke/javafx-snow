package io.zemke.snow;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class App extends Application {

    private double xOffset = 0;
    private double yOffset = 0;

    @Override
    public void start(Stage stage) {
        stage.setTitle("Snow");
        var javaVersion = System.getProperty("java.version");
        var javafxVersion = System.getProperty("javafx.version");
        System.out.println("javaVersion " + javaVersion);
        System.out.println("javafxVersion " + javafxVersion);
        var container = new StackPane();
        var width = 1000;
        var height = 728;
        var backgroundImage = new BackgroundImage(new
                Image(App.class.getResourceAsStream("/pic.png"), width, height, false, true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                BackgroundSize.DEFAULT);
        container.setBackground(new Background(backgroundImage));
        container.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        container.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });
        container.setPadding(new Insets(90, 90, 90, 90));
        var scene = new Scene(container, width, height);
        scene.getRoot().setEffect(new DropShadow(BlurType.GAUSSIAN, Color.BLACK, 45, 0, 0, 10));
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.setWidth(width + 90);
        stage.setHeight(height + 90);
        stage.setResizable(false);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
