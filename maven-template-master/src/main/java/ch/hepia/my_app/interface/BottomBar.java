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

import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;

import javafx.scene.text.Font;
import javafx.scene.text.Text;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.stage.Modality;
import javafx.stage.Stage;

import javafx.geometry.Pos;
import javafx.geometry.Insets;


import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;

// pas très propre -> il faut amméliorer le code et revoir les imports dont on a besoin
import java.time.LocalDate;

public class BottomBar extends HBox implements PropertyChangeListener{

    private Button btnVirus;
    private Button btnCure;

    private ProgressBar pbVirus;
    private ProgressBar pbCure;

    private Label date;
    private Label curePoints;
    private Label virusPoints;

    public BottomBar(){
        super();
        
        this.btnCure = buttonFromBottomBar("images/icon_cure.png");
        this.pbCure = progressBarFromBottomBar(0, "-fx-accent: blue;-fx-text-box-border: #000;-fx-control-inner-background: #808080;");
    
        this.btnVirus = buttonFromBottomBar("images/icon_virus.png");
        this.pbVirus = progressBarFromBottomBar(0, "-fx-accent: green;-fx-text-box-border: #000;-fx-control-inner-background: #808080;");
        
        this.date = labelFromBottomBar(LocalDate.of(2020,01,22).toString());
        this.date.setMinWidth(145);
        this.date.setAlignment(Pos.CENTER);
    
        this.virusPoints = labelFromBottomBar("5");

        this.curePoints = labelFromBottomBar("12");
    
        this.getChildren().addAll(this.btnCure, this.curePoints, this.pbCure, this.date, this.pbVirus, this.virusPoints, this.btnVirus);
        this.setBackground(new Background(new BackgroundFill(Color.BLACK,
        CornerRadii.EMPTY,
        Insets.EMPTY)));
    }

    public void updateDate(LocalDate ld){
        this.date.setText(ld.toString());
    }

    public void updateVirusPoints(Integer points){
        this.virusPoints.setText(points.toString());
    }

    public void updateCurePoints(Integer points){
        this.curePoints.setText(points.toString());
    }

    private Button buttonFromBottomBar(String path){
        int width = 60;
        int height = 40;

        Image img = new Image(this.getClass().getClassLoader().getResourceAsStream(path));        
        ImageView icon = new ImageView(img);
        icon.setFitWidth(width);
        icon.setFitHeight(height);

        Button btn = new Button();
        btn.setWrapText(true);
        btn.setMinWidth(width);
        btn.setMinHeight(height);
        btn.setGraphic(icon);
        btn.setStyle("-fx-background-color: #000;");
        return btn;
    }

    private ProgressBar progressBarFromBottomBar(int value, String style){
        ProgressBar pb = new ProgressBar(value*0.01);
        pb.setProgress(value*0.05);
        pb.setPrefSize(400, 60);
        pb.setStyle(style);
        return pb;
    }

    private Label labelFromBottomBar(String value){
        Label l = new Label(value);
        l.setMinHeight(40);
        l.setStyle("-fx-font-size: 1.4em;");
        l.setTextFill(Color.WHITE);
        return l;
    }

    public Button buttonCure(){
        return this.btnCure;
    }

    public Button buttonVirus(){
        return this.btnVirus;
    }


    public void propertyChange(PropertyChangeEvent evt){
        if(evt.getPropertyName().equals("virus")){
            //On a gangé un point!
            Integer inte = (Integer)evt.getNewValue();
            this.pbVirus.setProgress(inte * 0.05);
            this.updateVirusPoints(inte);
        }
    }
}