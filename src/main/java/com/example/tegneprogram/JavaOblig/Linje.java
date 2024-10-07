package com.example.tegneprogram.JavaOblig;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class Linje extends Figur {
    private javafx.scene.shape.Line linje;
    private double startX;
    private double startY;

    public Linje(double startX, double startY, double endX, double endY, Color strokeColor) {
        linje = new javafx.scene.shape.Line(startX, startY, endX, endY);
        linje.setStroke(strokeColor);
    }

    public void setEndPoints(double startX, double startY, double endX, double endY) {
        linje.setStartX(startX);
        linje.setStartY(startY);
        linje.setEndX(endX);
        linje.setEndY(endY);
    }

    @Override
    public Color getStrokeColor() {
        return (Color) linje.getStroke();
    }


    public void setFillColor(Color fillColor) {
        this.fillColor = fillColor;
    }

    @Override
    public Color getFillColor() {
        return null;
    }

    @Override
    public javafx.scene.shape.Shape getShape() {
        return linje;
    }

    @Override
    public String getDetails() {
        return "Linje: Fra (" + linje.getStartX() + ", " + linje.getStartY() + ") til (" + linje.getEndX() + ", " + linje.getEndY() + ")";
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
        if (Math.abs(linje.getEndX() - linje.getStartX()) < 5 && Math.abs(linje.getEndY() - linje.getStartY()) < 5) {
            removeShape();
        }
    }

    private void removeShape() {
        linje.setVisible(false);
    }

    public static class HelloApplication extends Application {
        @Override
        public void start(Stage stage) throws IOException {
            FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 320, 240);
            stage.setTitle("Hello!");
            stage.setScene(scene);
            stage.show();
        }
    
        public static void main(String[] args) {
            launch();
        }
    }
}