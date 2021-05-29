package ch.hepia.my_app;

import javafx.scene.image.ImageView;
import javafx.scene.image.Image;

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
                
        Image imgCure = new Image(this.getClass().getClassLoader().getResourceAsStream("images/icon_cure.png"));        
        ImageView iconCure = new ImageView(imgCure);
        iconCure.setFitWidth(150);
        iconCure.setFitHeight(40);
        
        this.btnCure = ButtonFromBottomBar("");
        this.btnCure.setGraphic(iconCure);
        this.pbCure = ProgressBarFromBottomBar(0, "-fx-accent: blue;");

        Image imgVirus = new Image(this.getClass().getClassLoader().getResourceAsStream("images/icon_virus.png"));        
        ImageView iconVirus = new ImageView(imgVirus);
        iconVirus.setFitWidth(150);
        iconVirus.setFitHeight(40);

        this.btnVirus = ButtonFromBottomBar("");
        this.btnVirus.setGraphic(iconVirus);
        this.pbVirus = ProgressBarFromBottomBar(0, "-fx-accent: green;");
<<<<<<< HEAD
        
        this.date = new Label(LocalDate.of(2020,01,22).toString());
=======

        this.date = new Label(LocalDate.now().toString());
>>>>>>> master
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