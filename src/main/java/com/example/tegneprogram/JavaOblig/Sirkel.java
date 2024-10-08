package com.example.tegneprogram.JavaOblig;


import javafx.scene.paint.Color;


public class Sirkel extends Figur {
    private javafx.scene.shape.Circle sirkel;
    private double initialX;
    private double initialY;
    private  int strokebredde;

    public Sirkel(double x, double y, double radius, Color strokeColor,Color fillColor, double strokebredde) {
        sirkel = new javafx.scene.shape.Circle(x, y, radius);
        sirkel.setStroke(strokeColor);
        sirkel.setFill(fillColor);
        sirkel.setStrokeWidth(strokebredde);

    }


    public void setCenter(double x, double y) {
        sirkel.setCenterX(x);
        sirkel.setCenterY(y);
    }

    public void setRadius(double radius) {
        sirkel.setRadius(radius);
    }

    public double getRadius() {
        return sirkel.getRadius();
    }
    @Override
    public Color getStrokeColor() {
        return (Color) sirkel.getStroke();
    }


    @Override
    public void setnyStrokeColor(Color nyFarge){
        sirkel.setStroke(nyFarge);
    }


    @Override
    public double getStrokeWidth() {
        return (double) sirkel.getStrokeWidth();
    }

    public void setNyStrokewidth(double nystrokebredde){
        sirkel.setStrokeWidth(nystrokebredde);
    }

    public Color getFillColor() {
        return (Color) sirkel.getFill();
    }

    public void SetNyFill(Color nyFarge){
        sirkel.setFill(nyFarge);
    }

    @Override
    public javafx.scene.shape.Shape getShape() {
        return sirkel;
    }

    @Override
    public String getDetails() {
        return "Sirkel: Radius = " + getRadius();
    }

    @Override
    public void handleMousePressed(double x, double y) {
        initialX = x;
        initialY = y;
        setCenter(x, y);
    }
    @Override
    public double fåX(){
        return sirkel.getCenterX();
    }
    @Override
    public double fåY(){
        return sirkel.getCenterY();
    }

    @Override
    public void handleMouseDragged(double x, double y) {
        double radius = Math.hypot(x - initialX, y - initialY);
        setRadius(radius);
    }

    @Override
    public void handleMouseReleased() {
        double radius = getRadius();
        if (radius < 1) {
            removeShape();
        }
    }

    @Override
    public void removeShape() {
        sirkel.setVisible(false);
    }

}