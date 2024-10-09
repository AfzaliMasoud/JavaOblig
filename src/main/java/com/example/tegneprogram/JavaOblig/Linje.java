package com.example.tegneprogram.JavaOblig;

import javafx.scene.paint.Color;

public class Linje extends Figur {
    private javafx.scene.shape.Line linje;
    private double startX;
    private double startY;

    public Linje(double startX, double startY, double endX, double endY, Color strokeColor, double strokebredde) {
        linje = new javafx.scene.shape.Line(startX, startY, endX, endY);
        linje.setStroke(strokeColor);
        linje.setStrokeWidth(strokebredde);
    }

    public void setEndPoints(double startX, double startY, double endX, double endY) {
        linje.setStartX(startX);
        linje.setStartY(startY);
        linje.setEndX(endX);
        linje.setEndY(endY);
    }


    public Color getStrokeColor() {
        return (Color) linje.getStroke();
    }
    @Override
    public void setnyStrokeColor(Color nyFarge){
        linje.setStroke(nyFarge);
    }

    public void setNyStrokewidth(double nystrokebredde){
        linje.setStrokeWidth(nystrokebredde);
    }


    @Override
    public javafx.scene.shape.Shape getShape() {
        return linje;
    }

    @Override
    public String getDetails() {
        double startX = linje.getStartX();
        double startY = linje.getStartY();
        double endX = linje.getEndX();
        double endY = linje.getEndY();

        double length = Math.sqrt(Math.pow(endX - startX, 2) + Math.pow(endY - startY, 2));

        return "Lengde: " + String.format("%.2f", length);
    }

    @Override
    public void handleMousePressed(double x, double y) {
        startX = x;
        startY = y;
        setEndPoints(startX, startY, startX, startY);
    }
    @Override
    public double fåX(){
        return (linje.getStartX() + linje.getEndX()) / 2;
    }
    @Override
    public double fåY(){
        return (linje.getStartY() + linje.getEndY()) / 2;
    }

    @Override
    public void handleMouseDragged(double x, double y) {
        setEndPoints(startX, startY, x, y);
    }

    @Override
    public void handleMouseReleased() {
        if (Math.abs(linje.getEndX() - linje.getStartX()) < 1 && Math.abs(linje.getEndY() - linje.getStartY()) < 1) {
            removeShape();
        }
    }

    @Override
    public void removeShape() {
        linje.setVisible(false);
    }

}