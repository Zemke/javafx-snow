package io.zemke.snow;

import javafx.scene.effect.BoxBlur;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.UUID;

import static io.zemke.snow.App.PADDING;
import static io.zemke.snow.App.WIDTH;
import static io.zemke.snow.App.random;
import static java.lang.Math.max;

public class Flake {

    private final Circle circle;
    private final double speed;
    private int cycles = 0;
    private int xTendency = random(2);
    private final String id = UUID.randomUUID().toString();

    public Flake() {
        this.circle = create();
        this.speed = speed();
    }

    private double speed() {
        var fall = circle.getRadius() - circle.getRadius() / 5;
        var rnd = ((double) random(10)) / 10;
        return fall + rnd;
    }

    public double fall() {
        circle.setCenterY(circle.getCenterY() + speed);
        if (cycles % 20 == 0) {
            xTendency = random(2);
        }
        var wiggle = ((double) random(2)) / 1.5;
        if (xTendency == 1) {
            circle.setCenterX(circle.getCenterX() + wiggle);
        } else {
            circle.setCenterX(circle.getCenterX() - wiggle);
        }
        cycles += 1;
        return circle.getCenterY();
    }

    private Circle create() {
        var radius = max(3, random(8));
        var circle = new Circle(radius);
        var bb = new BoxBlur();
        bb.setWidth(3);
        bb.setHeight(3);
        bb.setIterations(3);
        circle.setEffect(bb);
        circle.setFill(Color.WHITE);
        circle.setCenterX(max(PADDING / 2 + 5, random(WIDTH) + PADDING / 2 - 10));
        circle.setCenterY(PADDING / 2 + 15);
        circle.setId(id);
        return circle;
    }

    public Circle getCircle() {
        return circle;
    }

    public String getId() {
        return id;
    }
}
