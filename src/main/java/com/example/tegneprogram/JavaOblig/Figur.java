package com.example.tegneprogram.JavaOblig;

import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

public abstract class Figur {

    public abstract Color getStrokeColor();

    public abstract void setnyStrokeColor(Color nystrokeColor);

    public Color getFillColor(){
        return (Color) getShape().getFill();
    };

    public void SetNyFill(Color nyfillColor){

        getShape().setFill(nyfillColor);

    }

    public abstract Shape getShape();

    public abstract void removeShape();

    public double getStrokeWidth(){
        return (double) getShape().getStrokeWidth();
    };

    public abstract void setNyStrokewidth(double nystrokebredde);

    public abstract String getDetails();

    public abstract double fåX();

    public abstract double fåY();

    public abstract void handleMousePressed(double x, double y);

    public abstract void handleMouseDragged(double x, double y);

    public abstract void handleMouseReleased();
}
