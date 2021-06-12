
package ch.hepia.covid_manager;

import java.util.Scanner;

import java.util.Map;
import java.util.Observable;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javafx.scene.control.ComboBox;
import javafx.geometry.NodeOrientation;
import javafx.scene.control.skin.ComboBoxListViewSkin;


import javafx.animation.Timeline;
import javafx.animation.KeyFrame;

import javafx.animation.Animation;
import javafx.animation.Transition;

import javafx.application.Application;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.binding.DoubleBinding;

import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.Group;
import javafx.scene.control.*;

import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.PixelReader;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ListView;

import javafx.scene.paint.Paint;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;

import javafx.scene.control.MenuButton;

import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;

import javafx.scene.Parent;

import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import javafx.scene.text.Font;
import javafx.scene.text.Text;

import javafx.geometry.Rectangle2D;
import javafx.geometry.Point2D;
import javafx.geometry.Insets;
import javafx.geometry.Pos;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.ResultSet;

import javafx.util.Duration;

import javafx.scene.control.Label;
import java.time.LocalDate;

import javafx.stage.StageStyle;

import java.lang.Thread;

import javafx.collections.FXCollections;

public class CasesBar{

    private Region sickBar = new Region();
    private Region vaccinatedBar = new Region();
    private Region deathBar = new Region();
    private Rectangle healthyBar = new Rectangle();
    private Label barName = new Label("World");

    public CasesBar(int newWidth, int newHeight, Countries countries) {
        
        //Barre des gens sains
        this.healthyBar.setWidth(newWidth/3);
        this.healthyBar.setHeight(20.0);
        this.healthyBar.setFill(Color.GREEN);
        this.healthyBar.opacityProperty().set(0.75);
        this.healthyBar.setStroke(Color.WHITE);
        this.healthyBar.setStrokeWidth(3.0);
        this.healthyBar.setTranslateX(newWidth/3);
        this.healthyBar.setTranslateY(newHeight-47);
        this.healthyBar.setArcWidth(30.0);        
        this.healthyBar.setArcHeight(20.0);
        
        //Barre des cas infectés
        this.sickBar.opacityProperty().set(0.75);
        this.sickBar.setPrefSize((newWidth/(3.0 * countries.totalPop() / 100000.0)) * (countries.totalCases() / 100000.0), 16.0);
        this.sickBar.setStyle("-fx-background-color: red; -fx-background-radius: 10 0 0 10");
        this.sickBar.setTranslateX(newWidth/3 + 2);
        this.sickBar.setTranslateY(newHeight-45);
        
        //Barre des cas morts
        this.deathBar.setPrefSize((newWidth/(3.0 * countries.totalPop() / 100000.0)) * (countries.totalDeaths() / 100000.0), 16.0);
        this.deathBar.setStyle("-fx-background-color: black; -fx-background-radius: 10 0 0 10");
        this.deathBar.setTranslateX(newWidth/3 + 2);
        this.deathBar.setTranslateY(newHeight-45);
        
        //Barre des cas vaccinés
        this.vaccinatedBar.opacityProperty().set(0.75);
        this.vaccinatedBar.setPrefSize((newWidth/(3.0 * countries.totalPop() / 100000.0)) * (countries.totalCured() / 100000.0), 16.0); //je mets 0 parce qu'on a pas de données
        this.vaccinatedBar.setStyle("-fx-background-color: blue; -fx-background-radius: 0 10 10 0");
        this.vaccinatedBar.setTranslateX(2*newWidth/3 - 62.0);
        this.vaccinatedBar.setTranslateY(newHeight-45);

        //Nom de la barre des cas
        this.barName.setStyle("-fx-font-size: 1.4em;");
        this.barName.setTextFill(Color.WHITE);
        this.barName.setTranslateX(newWidth/3 + 10);
        this.barName.setTranslateY(newHeight - 73);

    }

    public Region getSickBar(){
        return this.sickBar;
    }

    public void setSickBar(double newWidth, double newHeight){
        this.sickBar.setPrefSize(newWidth, newHeight);
    }

    public Region getDeathBar(){
        return this.deathBar;
    }

    public void setDeathBar(double newWidth, double newHeight){
        this.deathBar.setPrefSize(newWidth, newHeight);        
    }

    public Region getVaccinatedBar(){
        return this.vaccinatedBar;
    }

    public void setVaccinatedBar(double newWidth, double newHeight){
        this.vaccinatedBar.setPrefSize(newWidth, newHeight);        
    }


    public Rectangle getHealthyBar(){
        return this.healthyBar;
    }

    public void setHealthyBar(double newWidth, double newHeight){
        //this.healthyBar.setPrefSize(newWidth, newHeight);
    }

    public Label getBarName(){
        return this.barName;
    }

    public void setBarName(String newName){
        this.barName.setText(newName);
    }

}