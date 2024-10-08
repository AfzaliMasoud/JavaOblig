package com.example.tegneprogram.JavaOblig;

import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

public abstract class Figur {
    protected Color strokeColor;
    protected Color fillColor;

    public Figur(Color strokeColor, Color fillColor) {
        this.strokeColor = strokeColor;
        this.fillColor = fillColor;
    }

    public Figur() {
    }

    public abstract Color getStrokeColor();

    public void setStrokeColor(Color strokeColor) {
        this.strokeColor = strokeColor;
    }

    public abstract void setFillColor(Color fillColor);
    public abstract void setnyStrokeColor(Color nystrokeColor);

    public abstract Color getFillColor();

    public abstract Shape getShape();

    public abstract void removeShape();

    public abstract String getDetails();

    public abstract double fåX();

    public abstract double fåY();

    public abstract void handleMousePressed(double x, double y);

    public abstract void handleMouseDragged(double x, double y);

    public abstract void handleMouseReleased();
}
