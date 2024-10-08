package com.example.tegneprogram.JavaOblig;

import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;


public class Rektangel extends Figur {
    private javafx.scene.shape.Rectangle rektangel;
    private double initialX;
    private double initialY;
    private Color fillColor;

    public Rektangel(double x, double y, double width, double height, Color strokeColor, Color fillColor) {
        rektangel = new javafx.scene.shape.Rectangle(x, y, width, height);
        rektangel.setStroke(strokeColor);
        rektangel.setFill(fillColor);

    }

    public void setPosition(double x, double y) {
        rektangel.setX(x);
        rektangel.setY(y);
    }


    public void setSize(double width, double height) {
        rektangel.setWidth(width);
        rektangel.setHeight(height);
    }

    public double getWidth() {
        return rektangel.getWidth();
    }

    public double getHeight() {
        return rektangel.getHeight();
    }

    @Override
    public void setFillColor(Color fillColor) {
        this.fillColor = fillColor;
    }

    @Override
    public Color getFillColor() {
        return (Color) rektangel.getFill();
    }

    @Override
    public Shape getShape() {
        return rektangel;
    }

    @Override
    public String getDetails() {
        return "Rektangel: Bredde = " + getWidth() + ", Høyde = " + getHeight();
    }
    @Override
    public double fåX(){
        return rektangel.getX() + rektangel.getWidth() / 2;
    }
    @Override
    public double fåY(){
        return rektangel.getY() + rektangel.getHeight() / 2;
    }


    @Override
    public Color getStrokeColor(){
        return (Color) rektangel.getStroke();
    }


    @Override
    public void handleMousePressed(double x, double y) {
        initialX = x;
        initialY = y;
        setPosition(x, y);
    }

    @Override
    public void handleMouseDragged(double x, double y) {
        double width = Math.abs(x - initialX);
        double height = Math.abs(y - initialY);
        setPosition(Math.min(initialX, x), Math.min(initialY, y));
        setSize(width, height);
    }

    @Override
    public void handleMouseReleased() {
        double bredde = getWidth();
        double hoyde = getHeight();
        double areal = bredde * hoyde;
        double forhold = bredde / hoyde;

        if (areal < 25) {
            removeShape();
        } else if (areal > 4900) {
            rektangel.setStrokeWidth(2);
        } else {
            double StrokeWidth = 2 + (areal - 25) / (4900 - 25) * (5 - 2);
            double justering = 1.0;
            if (forhold < 0.5) {
                justering = 0.8;
            } else if (forhold > 2) {
                justering = 0.8;
            }
            StrokeWidth *= justering;
            StrokeWidth = Math.max(2, Math.min(5, StrokeWidth));
            rektangel.setStrokeWidth(StrokeWidth);
        }
    }

    @Override
    public void removeShape() {
        rektangel.setVisible(false);
    }

}