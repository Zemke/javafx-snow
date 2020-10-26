package io.zemke.snow;

import javafx.animation.AnimationTimer;
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
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class App extends Application {

    public static final int PADDING = 90;
    public static final int WIDTH = 1000;
    public static final int HEIGHT = 728;

    private static final int INTERVAL = 20;

    private double xOffset = 0;
    private double yOffset = 0;

    private final List<Flake> snow = new ArrayList<>();
    private final Pane container = new Pane();

    private List<Flake> createSnow(int amount) {
        var snow = new ArrayList<Flake>();
        for (var i = 0; i < amount; i++) {
            snow.add(new Flake());
        }
        return snow;
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("Snow");
        System.out.println("javaVersion " + System.getProperty("java.version"));
        System.out.println("javafxVersion " + System.getProperty("javafx.version"));
        addFlakes(createSnow(10));
        var backgroundImage = new BackgroundImage(
                new Image(App.class.getResourceAsStream("/pic.png"), WIDTH, HEIGHT, false, true),
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
        container.setPadding(new Insets(PADDING));
        var scene = new Scene(container, WIDTH, HEIGHT);
        scene.getRoot().setEffect(new DropShadow(BlurType.GAUSSIAN, Color.BLACK, 45, 0, 0, 10));
        scene.setFill(Color.TRANSPARENT);
        stage.setScene(scene);
        stage.setWidth(WIDTH + PADDING);
        stage.setHeight(HEIGHT + PADDING);
        stage.setResizable(false);
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.show();
        new AnimationTimer() {
            @Override
            public void handle(long currentNanoTime) {
                try {
                    Thread.sleep(INTERVAL);
                    loop();
                } catch (InterruptedException ignored) {
                }
            }
        }.start();
    }

    private void loop() {
        removeFlakes();
        addFlakes(createSnow(random(4)));
    }

    private void addFlakes(List<Flake> flakes) {
        snow.addAll(flakes);
        container.getChildren().addAll(
                flakes.stream().map(Flake::getCircle).toArray(Circle[]::new));
    }

    private void removeFlakes() {
        snow.removeIf(flake -> flake.fall() > (HEIGHT + PADDING / 2 - 10));
        container.getChildren().removeIf(child -> ((Circle) child).getCenterY() > (HEIGHT + PADDING / 2 - 10));
    }

    public static int random(int max) {
        return new Random().nextInt(max);
    }

    public static void main(String[] args) {
        launch();
    }
}
