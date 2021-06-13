package ch.hepia.covid_manager;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.layout.VBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.effect.DropShadow;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.time.LocalDate;

public class DropdownMenu extends VBox {

    private Button save;
    private Button saveExit;
    private Button exit;
    private VBox items;
    private ImageView menuIcon;
    private boolean status;
    private boolean statusDisplay;

    public DropdownMenu(Virus v, Countries c, int idPlayer, LocalDate ld){
        this.status = true;
        this.statusDisplay = true;
        this.save = generateMenuButtons("Save");
        this.save.setOnAction(e -> {
            //DataBaseCommunicator.save(v, countries, idPlayer, ld);
            this.removeItems();
        });
        this.saveExit = generateMenuButtons("Save & Exit");
        this.saveExit.setOnAction(e -> {
            //DataBaseCommunicator.save(v, countries, idPlayer, ld);
            System.exit(0);
        });
        this.exit = generateMenuButtons("Exit");
        this.exit.setOnAction(e -> {
            System.exit(0);
        });
        this.items = new VBox(this.save, this.saveExit, this.exit);

        Image img = new Image(this.getClass().getClassLoader().getResourceAsStream("images/bars.png"));
        this.menuIcon = new ImageView(img);
        this.menuIcon.setFitWidth(50);
        this.menuIcon.setFitHeight(50);
        this.menuIcon.setOnMouseClicked(event -> {
            if(this.status){
                this.getChildren().add(items);
            } else{
                this.removeItems();
            }
            this.status = !this.status;
        });
        this.getChildren().add(this.menuIcon);
    }

    private Button generateMenuButtons(String label){
        Button b = new Button(label);
        b.setTextFill(Color.WHITE);
        b.setStyle("-fx-border-color: #fff;-fx-border-width: 2;-fx-background-color: transparent;-fx-font-size: 1em;");
        b.setPrefWidth(100);
        b.setPrefHeight(20);
        b.addEventHandler(MouseEvent.MOUSE_ENTERED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                b.setStyle("-fx-border-color: #808080;-fx-border-width: 2;-fx-background-color: transparent;-fx-font-size: 1em;");
            }
        });
        b.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
          @Override
          public void handle(MouseEvent e) {
            b.setStyle("-fx-border-color: #fff;-fx-border-width: 2;-fx-background-color: transparent;-fx-font-size: 1em;");
          }
        });
        return b;
    }

    public void removeItems(){
        this.getChildren().remove(items);
    }

    public void manageDisplay(){
        if(this.statusDisplay){
            this.setVisible(false);
        } else{
            this.setVisible(true);
        }
        this.statusDisplay = !this.statusDisplay;
    }
}