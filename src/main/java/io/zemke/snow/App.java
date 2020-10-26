package io.zemke.snow;

import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.CacheHint;
import javafx.scene.Scene;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.BoxBlur;
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
import javafx.util.Duration;

import java.util.Random;

import static java.lang.Math.max;

public class App extends Application {

    private static final int PADDING = 90;
    private static final int WIDTH = 1000;
    private static final int HEIGHT = 728;
    private static final int LANDING = 410;
    private static final Random RANDOM = new Random();

    private double xOffset = 0;
    private double yOffset = 0;

    private final Pane container = new Pane();

    @Override
    public void start(Stage stage) {
        stage.setTitle("Snow");
        System.out.println("javaVersion " + System.getProperty("java.version"));
        System.out.println("javafxVersion " + System.getProperty("javafx.version"));
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
        //noinspection IntegerDivisionInFloatingPointContext
        scene.getRoot().setEffect(new DropShadow(BlurType.GAUSSIAN, Color.BLACK, PADDING / 2, 0, 0, 10));
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
                    Thread.sleep(20);
                } catch (InterruptedException ignored) {
                }
                container.getChildren().add(flake());
                container.getChildren().add(flake());
                Platform.runLater(() -> {
                    if (container.getChildren().size() > LANDING) {
                        container.getChildren().remove(0, 2);
                    }
                });
            }
        }.start();
    }

    private Circle flake() {
        var radius = max(3, random(8));
        var circle = new Circle(radius);
        circle.setCache(true);
        circle.setCacheHint(CacheHint.SPEED);
        var bb = new BoxBlur();
        bb.setWidth(3);
        bb.setHeight(3);
        bb.setIterations(3);
        circle.setEffect(bb);
        circle.setFill(Color.WHITE);
        circle.setCenterX(max(PADDING / 2 + 5, random(WIDTH) + PADDING / 2 - 10));
        //noinspection IntegerDivisionInFloatingPointContext
        circle.setCenterY(PADDING / 2 + 10);
        var speed = 10 - circle.getRadius() + ((double) random(10)) / 20;
        var wiggle = new TranslateTransition(Duration.seconds(1), circle);
        wiggle.setByX(random(30));
        wiggle.setAutoReverse(true);
        wiggle.setRate(1);
        wiggle.setCycleCount(Animation.INDEFINITE);
        wiggle.play();
        var fall = new TranslateTransition(Duration.seconds(speed - .5), circle);
        fall.setByY(HEIGHT - 15);
        fall.setCycleCount(1);
        fall.setOnFinished(event -> wiggle.stop());
        fall.play();
        return circle;
    }

    public static int random(int max) {
        return RANDOM.nextInt(max);
    }

    public static void main(String[] args) {
        launch();
    }
}
