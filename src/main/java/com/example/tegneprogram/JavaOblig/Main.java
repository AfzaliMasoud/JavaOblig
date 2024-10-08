package com.example.tegneprogram.JavaOblig;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.input.MouseButton;
import java.util.ArrayList;

public class Main extends Application {
    Label velgFigur = new Label("Velg Figur");
    Label velgFarge = new Label("Velg Linje Farge");
    Label VelgFill = new Label("Velg Fill farge");

    Label whichshape = new Label("Figur Du har klikket på: ");
    TextField figurklikket = new TextField();

    Label FigurPos = new Label("Figur Positionen din: ");
    TextField FigurPos2 = new TextField();

    Label FigurFarge = new Label("Figur Linje Farge: ");
    TextField FigurFarge2 = new TextField();

    Label FigurFillFarge = new Label("Figur Fill farge");
    TextField FigurFillFargetext = new TextField();

    VBox infBox;
    ArrayList<Figur> figurer = new ArrayList<>();
    String valgtFigur = "Rektangel";
    Color valgtFarge = Color.BLUE;
    Color valgtFillFarge = Color.BLUE;

    String figurnavn;
    private double startX;
    private double startY;
    public Figur selectedFigur;

    @Override
    public void start(Stage primaryStage) {
        BorderPane hovedPane = new BorderPane();
        VBox velgFigurPane = lagValgPane();
        infBox = lagInfPane();
        Pane tegnePane = new Pane();
        hovedPane.setCenter(tegnePane);
        hovedPane.setLeft(velgFigurPane);
        hovedPane.setRight(infBox);
        infBox.setVisible(false);
        oppsettMusTrykk(tegnePane);
        oppsettMusDra(tegnePane);
        oppsettMusSlipp(tegnePane);
        oppsettMusKlikk(tegnePane);
        Scene scene = new Scene(hovedPane, 1500, 800);
        primaryStage.setTitle("Tegneprogram");
        primaryStage.setScene(scene);
        primaryStage.show();
        tegnePane.requestFocus();
    }

    private void oppsettMusTrykk(Pane tegnePane) {
        tegnePane.setOnMousePressed(e -> {
            if (e.getButton() == MouseButton.PRIMARY) {
                startX = e.getX();
                startY = e.getY();
                Figur nyFigur = null;
                switch (valgtFigur) {
                    case "Rektangel":
                        nyFigur = new Rektangel(startX, startY, 0, 0, valgtFarge, valgtFillFarge);
                        break;
                    case "Sirkel":
                        nyFigur = new Sirkel(startX, startY, 0, valgtFarge, valgtFillFarge);
                        break;
                    case "Linje":
                        nyFigur = new Linje(startX, startY, startX, startY, valgtFarge);
                        break;
                }
                if (nyFigur != null) {
                    figurer.add(nyFigur);
                    tegnePane.getChildren().add(nyFigur.getShape());
                    selectedFigur = nyFigur;
                    selectedFigur.handleMousePressed(startX, startY);
                }
            }
        });
    }

    private void oppsettMusDra(Pane tegnePane) {
        tegnePane.setOnMouseDragged(e -> {
            if (e.getButton() == MouseButton.PRIMARY && selectedFigur != null) {
                double nåværendeX = e.getX();
                double nåværendeY = e.getY();
                selectedFigur.handleMouseDragged(nåværendeX, nåværendeY);
            }
        });
    }

    private void oppsettMusSlipp(Pane tegnePane) {
        tegnePane.setOnMouseReleased(e -> {
            if (e.getButton() == MouseButton.PRIMARY && selectedFigur != null) {
                selectedFigur.handleMouseReleased();
            }
        });
    }


    private void oppsettMusKlikk(Pane tegnePane) {
        tegnePane.setOnMouseClicked(e -> {
            if (e.getButton() == MouseButton.SECONDARY) {
                double mouseX = e.getX();
                double mouseY = e.getY();
                selectedFigur = null;
                for (Figur figur : figurer) {
                    if (figur.getShape().contains(mouseX, mouseY)) {
                        selectedFigur = figur;
                        figurklikket.setText(selectedFigur.getDetails());
                        FigurPos2.setText("X: " + selectedFigur.fåX() + ", Y: " + selectedFigur.fåY());
                        FigurFarge2.setText("Farge Stroke: " + selectedFigur.getStrokeColor());
                        FigurFillFargetext.setText("Farge Fill: " + selectedFigur.getFillColor());
                        infBox.setVisible(true);
                        break;
                    }
                }
                if (selectedFigur == null) {
                    infBox.setVisible(false);
                }
            }
        });
    }

    public VBox lagInfPane() {
        VBox boxinf = new VBox();
        boxinf.setAlignment(Pos.CENTER);
        boxinf.getChildren().addAll(whichshape, figurklikket, FigurPos, FigurPos2, FigurFarge, FigurFarge2,FigurFillFarge,FigurFillFargetext);
        stilValg(boxinf);
        return boxinf;
    }

    public VBox lagValgPane() {
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        ComboBox<String> listeFigurer = new ComboBox<>();
        ComboBox<String> listeFill = new ComboBox<>();
        listeFill.getItems().addAll("Blå", "Rød", "Grønn", "Gul", "Svart");
        listeFill.setValue("Blå");
        listeFigurer.getItems().addAll("Linje", "Sirkel", "Rektangel");
        listeFigurer.setValue("Rektangel");
        listeFigurer.setOnAction(e -> valgtFigur = listeFigurer.getValue());
        ComboBox<String> listeFarger = new ComboBox<>();
        listeFarger.getItems().addAll("Blå", "Rød", "Grønn", "Gul", "Svart");
        listeFarger.setValue("Blå");
        listeFarger.setOnAction(e -> valgtFarge = fargeValg(listeFarger.getValue()));
        listeFill.setOnAction(e -> valgtFillFarge = fargeValg(listeFill.getValue()));
        vBox.getChildren().addAll(velgFigur, listeFigurer, velgFarge, listeFarger);
        vBox.getChildren().addAll(VelgFill, listeFill);
        stilValg(vBox);
        return vBox;
    }

    public Color fargeValg(String fargeNavn) {
        switch (fargeNavn) {
            case "Rød":
                return Color.RED;
            case "Grønn":
                return Color.GREEN;
            case "Gul":
                return Color.YELLOW;
            case "Svart":
                return Color.BLACK;
            default:
                return Color.BLUE;
        }
    }

    public void stilValg(Node n) {
        n.setStyle("-fx-padding: 12; " +
                "-fx-spacing: 12; " +
                "-fx-background-color: #fafafa; " +
                "-fx-border-color: #d3d3d3; " +
                "-fx-border-width: 1.5; " +
                "-fx-border-radius: 8; " +
                "-fx-font-size: 14px; " +
                "-fx-font-family: 'Segoe UI', sans-serif; " +
                "-fx-text-fill: #333; " +
                "-fx-background-radius: 8; " +
                "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.2), 8, 0.5, 0, 0);");
    }

    public static void main(String[] args) {
        launch(args);
    }
}

