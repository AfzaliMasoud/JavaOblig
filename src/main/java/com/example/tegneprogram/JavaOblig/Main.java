package com.example.tegneprogram.JavaOblig;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.input.MouseButton;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.input.KeyEvent;
import javax.swing.JOptionPane;
import java.util.ArrayList;


public class Main extends Application {
    Label velgFigur = new Label("Velg Figur");
    Label velgFarge = new Label("Velg Linje Farge");
    Label VelgFill = new Label("Velg Fill farge");
    Label velgStrokeWidth = new Label("Skriv Stroke width: ");
    TextField Strokwidthtext = new TextField("1");

    double strokebredde;
    Figur nyFigur = null;

    Label whichshape = new Label("Figur Du har klikket på: ");
    TextField figurklikket = new TextField();

    Label FigurPos = new Label("Figur Positionen din (midten): ");
    TextField FigurPos2 = new TextField();

    Label FigurFarge = new Label("Figur Linje farge: ");
    TextField FigurFarge2 = new TextField();

    Label FigurFillFarge = new Label("Figur Fill farge: ");
    TextField FigurFillFargetext = new TextField();

    Label velgnystroke = new Label("Velg en ny linje/stroke farge: ");
    ComboBox<String> velgnystroketext = new ComboBox<>();

    Label velgnyFiller = new Label("Velg en ny Fill farge: ");
    ComboBox<String> velgnyFillertext = new ComboBox<>();

    Button Slett = new Button("SLETT FIGUR");
    Label gamleStrokewidth = new Label("Figur Stroke/linje Bredde:");
    TextField gamleStrokewidthtext = new TextField();

    Label byttStrokewidth = new Label("Bytt Stroke/linje Bredde: ");
    TextField byttStrokewidthText = new TextField();

    Button SaveFile = new Button("Lagre Fil");


    VBox infBox;
    ArrayList<Figur> figurer = new ArrayList<>();
    String valgtFigur = "Rektangel";
    Color valgtFarge = Color.BLUE;
    Color valgtFillFarge = Color.RED;

    private double startX;
    private double startY;
    public Figur selectedFigur;


    @Override
    public void start(Stage primaryStage) {
        figurklikket.setEditable(false);
        FigurPos2.setEditable(false);
        FigurFarge2.setEditable(false);
        FigurFillFargetext.setEditable(false);
        gamleStrokewidthtext.setEditable(false);


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

    }

    private void oppsettMusTrykk(Pane tegnePane) {
        tegnePane.setOnMousePressed(e -> {
            if (e.getButton() == MouseButton.PRIMARY) {
                startX = e.getX();
                startY = e.getY();
                strokebredde = Double.parseDouble(Strokwidthtext.getText());
                switch (valgtFigur) {
                    case "Rektangel":
                        nyFigur = new Rektangel(startX, startY, 0, 0, valgtFarge, valgtFillFarge, strokebredde);
                        break;
                    case "Sirkel":
                        nyFigur = new Sirkel(startX, startY, 0, valgtFarge, valgtFillFarge, strokebredde);
                        break;
                    case "Linje":
                        nyFigur = new Linje(startX, startY, startX, startY, valgtFarge, strokebredde);
                        break;
                }
                if (nyFigur != null) {
                    figurer.add(nyFigur);
                    tegnePane.getChildren().add(nyFigur.getShape());
                    selectedFigur = nyFigur;
                    selectedFigur.handleMousePressed(startX, startY);
                }
            }
            if (strokebredde <= 0 || strokebredde > 20) {
                if (nyFigur != null) {
                    tegnePane.getChildren().remove(nyFigur.getShape());
                    figurer.remove(nyFigur);
                } else if (nyFigur != null) {
                    JOptionPane.showMessageDialog(null, "Stroke bredde kan ikke være mindre enn 1 og større enn 20", "FEIL!!!", JOptionPane.ERROR_MESSAGE);
                } else if (nyFigur == null) {
                    JOptionPane.showMessageDialog(null, "Du må lage en figur først!", "Tom pane", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        Strokwidthtext.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (newValue.isEmpty()) {
                    return;
                }
                if (!isValidDouble(newValue)) {
                    JOptionPane.showMessageDialog(null, "Stroke bredde må være et tall!", "FEIL!!!", JOptionPane.ERROR_MESSAGE);
                    Strokwidthtext.setText(oldValue);
                } else {
                    try {
                        double strokeWidth = Double.parseDouble(newValue);
                        if (strokeWidth < 1 || strokeWidth > 20) {
                            JOptionPane.showMessageDialog(null, "Stroke bredde kan ikke være mindre enn 1 og større enn 20", "FEIL!!!", JOptionPane.ERROR_MESSAGE);
                            Strokwidthtext.setText(oldValue);
                        }
                    } catch (NumberFormatException e) {
                        JOptionPane.showMessageDialog(null, "Stroke bredde må være et tall!", "FEIL!!!", JOptionPane.ERROR_MESSAGE);
                        Strokwidthtext.setText(oldValue);
                    }
                }
            }
        });

        Strokwidthtext.addEventFilter(KeyEvent.KEY_TYPED, e -> {
            if (!e.getCharacter().matches("[0-9.]")) {
                e.consume();
            }
        });
    }

    private boolean isValidDouble(String text) {
        if (text == null || text.isEmpty()) {
            return true;
        }
        try {
            Double.parseDouble(text);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public VBox lagValgPane() {
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        ComboBox<String> listeFigurer = new ComboBox<>();
        ComboBox<String> listeFill = new ComboBox<>();
        listeFill.getItems().addAll("Rød", "Blå", "Grønn", "Gul", "Svart");
        listeFill.setValue("Rød");
        listeFigurer.getItems().addAll("Linje", "Sirkel", "Rektangel");
        listeFigurer.setValue("Rektangel");
        listeFigurer.setOnAction(e -> valgtFigur = listeFigurer.getValue());
        ComboBox<String> listeFarger = new ComboBox<>();
        listeFarger.getItems().addAll("Blå", "Rød", "Grønn", "Gul", "Svart");
        listeFarger.setValue("Blå");
        velgnystroketext.getItems().addAll("Blå", "Rød", "Grønn", "Gul", "Svart");
        velgnyFillertext.getItems().addAll("Blå", "Rød", "Grønn", "Gul", "Svart");
        listeFarger.setOnAction(e -> valgtFarge = fargeValg(listeFarger.getValue()));
        listeFill.setOnAction(e -> valgtFillFarge = fargeValg(listeFill.getValue()));
        vBox.getChildren().addAll(velgFigur, listeFigurer, velgFarge, listeFarger);
        vBox.getChildren().addAll(VelgFill, listeFill);
        vBox.getChildren().addAll(velgnystroke,velgnystroketext);
        vBox.getChildren().addAll(velgStrokeWidth,Strokwidthtext);
        stilValg(vBox);
        return vBox;
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
            if (e.getButton() == MouseButton.SECONDARY && !e.isPrimaryButtonDown()) {
                double mouseX = e.getX();
                double mouseY = e.getY();
                selectedFigur = null;

                for (int i = figurer.size() - 1; i >= 0; i--) {
                    Figur figur = figurer.get(i);

                    if (figur.getShape().contains(mouseX, mouseY)) {
                        selectedFigur = figur;
                        figurklikket.setText(selectedFigur.getDetails());
                        FigurPos2.setText("X: " + selectedFigur.fåX() + ", Y: " + selectedFigur.fåY());
                        FigurFarge2.setText(fargeTilString(selectedFigur.getStrokeColor()));
                        FigurFillFargetext.setText(fargeTilString(selectedFigur.getFillColor()));
                        String strokeColorString = fargeTilString(selectedFigur.getStrokeColor());
                        velgnystroketext.setValue(strokeColorString);
                        gamleStrokewidthtext.setText(String.valueOf(selectedFigur.getStrokeWidth()));
                        String fillColorString = fargeTilString(selectedFigur.getFillColor());
                        velgnyFillertext.setValue(fillColorString);
                        infBox.setVisible(true);
                        break;
                    }
                }

                if (selectedFigur == null) {
                    infBox.setVisible(false);
                }
            }
        });

        velgnystroketext.setOnAction(e -> {
            if (selectedFigur != null) {

                String valgavastrokfarge = velgnystroketext.getValue();
                Color nyStrokeFarge = nyfargeValg(valgavastrokfarge);
                selectedFigur.setnyStrokeColor(nyStrokeFarge);
                FigurFarge2.setText("" + nyStrokeFarge);

            }
        });

        velgnyFillertext.setOnAction(e -> {
            if (selectedFigur != null){
                String valgavfill = velgnyFillertext.getValue();
                Color nyfillFarge = nyfargeValg(valgavfill);
                selectedFigur.SetNyFill(nyfillFarge);
                FigurFillFargetext.setText(""+nyfillFarge);
            }
        });

        byttStrokewidthText.setOnAction(e -> {
            if (selectedFigur != null) {
                String newStrokeWidthText = byttStrokewidthText.getText();
                if (!isvalid(newStrokeWidthText)) {
                    JOptionPane.showMessageDialog(null, "Stroke bredde må være et gyldig tall! (mellom 0 OG 20, bruk komma(.)for å skrive tall med desimaltall", "FEIL!!!", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                double breddeny = Double.parseDouble(newStrokeWidthText);
                if (breddeny <= 0 || breddeny > 20) {
                    JOptionPane.showMessageDialog(null, "Stroke bredde kan ikke være mindre enn 1 og større enn 20", "FEIL!!!", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                selectedFigur.setNyStrokewidth(breddeny);
            }
        });

        Slett.setOnAction(e -> {
            if (selectedFigur != null) {
                tegnePane.getChildren().remove(selectedFigur.getShape());
                figurer.remove(selectedFigur);
                selectedFigur = null;
                infBox.setVisible(false);
            }
        });
    }

    private boolean isvalid(String text) {
        if (text == null || text.isEmpty()) {
            return false;
        }
        try {
            Double.parseDouble(text);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public VBox lagInfPane() {
        VBox boxinf = new VBox();
        boxinf.setAlignment(Pos.CENTER);
        boxinf.getChildren().addAll(whichshape, figurklikket, FigurPos, FigurPos2, FigurFarge, FigurFarge2,FigurFillFarge,
                FigurFillFargetext,gamleStrokewidth,gamleStrokewidthtext,velgnystroke,velgnystroketext,velgnyFiller,velgnyFillertext,byttStrokewidth,byttStrokewidthText,Slett);
        slettStyle(Slett);
        stilValg(boxinf);
        return boxinf;
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

    public Color nyfargeValg(String fargenavn){
        if (fargenavn.equals("Rød")) {
            return Color.RED;
        } else if (fargenavn.equals("Grønn")) {
            return Color.GREEN;
        } else if (fargenavn.equals("Gul")) {
            return Color.YELLOW;
        } else if (fargenavn.equals("Svart")) {
            return Color.BLACK;
        } else if (fargenavn.equals("Blå")) {
            return Color.BLUE;
        }
        else {
            return Color.BLUE;
        }
    }
    public String fargeTilString(Color farge) {
        if (farge == null) {
            return "Ukjent";
        }
        if (farge.equals(Color.RED)) {
            return "Rød";
        } else if (farge.equals(Color.GREEN)) {
            return "Grønn";
        } else if (farge.equals(Color.YELLOW)) {
            return "Gul";
        } else if (farge.equals(Color.BLACK)) {
            return "Svart";
        } else if (farge.equals(Color.BLUE)) {
            return "Blå";
        } else {
            return "Ukjent";
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
    public void slettStyle(Node n){
        n.setStyle(
                "-fx-background-color: #ff4d4d;" +
                        "-fx-text-fill: white;" +
                        "-fx-font-size: 14px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-border-color: #cc0000;" +
                        "-fx-border-width: 2px;" +
                        "-fx-background-radius: 5px;" +
                        "-fx-border-radius: 5px;" +
                        "-fx-padding: 10 20;"
        );
    }



    public static void main(String[] args) {
        launch(args);
    }
}
