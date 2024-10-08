package com.example.tegneprogram.JavaOblig;

import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

public abstract class Figur {
    protected Color fillColor;

    public abstract Color getStrokeColor();

    public abstract void setnyStrokeColor(Color nystrokeColor);

    public abstract Color getFillColor();

    public abstract void SetNyFill(Color nystrokeColor);

    public abstract Shape getShape();

    public abstract void removeShape();

    public abstract String getDetails();

    public abstract double fåX();

    public abstract double fåY();

    public abstract void handleMousePressed(double x, double y);

    public abstract void handleMouseDragged(double x, double y);

    public abstract void handleMouseReleased();
}
