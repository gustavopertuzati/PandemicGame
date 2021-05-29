package ch.hepia.my_app;


import javafx.application.Application;

import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.Group;
import javafx.scene.control.*;

import javafx.scene.paint.Paint;
import javafx.scene.paint.Color;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Region;

import javafx.scene.text.Font;
import javafx.scene.text.Text;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.stage.Modality;
import javafx.stage.Stage;

import java.time.LocalDate;

// pas très propre -> il faut amméliorer le code et revoir les imports dont on a besoin

public class BottomBar extends HBox{

    private Button btnVirus;
    private Button btnCure;

    private ProgressBar pbVirus;
    private ProgressBar pbCure;
    private Label date;

    public BottomBar(){
        super();
    }

    public void fill(){
        this.btnCure = ButtonFromBottomBar("cure");
        this.pbCure = ProgressBarFromBottomBar(0, "-fx-accent: blue;");
        
        this.btnVirus = ButtonFromBottomBar("virus");
        this.pbVirus = ProgressBarFromBottomBar(0, "-fx-accent: green;");
        
        this.date = new Label(LocalDate.of(2020,01,22).toString());
        date.setWrapText(true);
        date.setMinWidth(150);
        date.setMinHeight(40);
        date.setStyle("-fx-font-size: 2em;");

        this.getChildren().addAll(this.btnCure, this.pbCure, this.date, this.pbVirus, this.btnVirus);
    }

    public void updateDate(LocalDate ld){
        this.date.setText(ld.toString());
    }


    private Button ButtonFromBottomBar(String name){
        Button btn = new Button(name);
        btn.setWrapText(true);
        btn.setMinWidth(120);
        btn.setMinHeight(40);
        btn.setStyle("-fx-font-size: 1.0em;");
        return btn;
    }

    private ProgressBar ProgressBarFromBottomBar(int value, String style){
        ProgressBar pb = new ProgressBar(value);
        pb.setProgress(0.1);
        pb.setPrefSize(450, 40);
        pb.setStyle(style);
        return pb;
    }

    public Button buttonCure(){
        return this.btnCure;
    }

    public Button buttonVirus(){
        return this.btnVirus;
    }
}