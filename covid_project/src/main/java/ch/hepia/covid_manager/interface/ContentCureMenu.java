package ch.hepia.covid_manager;

import java.util.Map;
import java.util.LinkedHashMap;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;

import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.Group;
import javafx.scene.control.*;

import javafx.scene.paint.Paint;
import javafx.scene.paint.Color;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Background;
import javafx.scene.layout.Priority;

import javafx.scene.Group;

import javafx.scene.input.MouseEvent;

import javafx.scene.Parent;

import javafx.scene.shape.Line;

import javafx.geometry.Insets;
import javafx.geometry.Pos;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.stage.Modality;
import javafx.stage.Stage;

import javafx.util.Duration;

import javafx.scene.effect.BlurType;

import javafx.scene.shape.Circle;

class ContentCureMenu extends Group{
    private VBox topSection;
    private VBox bottomSection;
    private Circle polarChart;
    private VBox currentMenu;

    public ContentCureMenu(Countries c, Virus v, int width, int height){       
        this.topSection = new VBox();
        this.polarChart = new Circle(100, 100, 50 , Color.RED);
        this.currentMenu = new VBox();
        this.bottomSection = new VBox();

        this.updateTopMenu(c, width, height);
        //this.updateBottomMenu(c, width, height);


        this.currentMenu.getChildren().add(this.topSection);
        this.currentMenu.getChildren().add(this.polarChart);
        this.currentMenu.getChildren().add(this.bottomSection);     
        this.getChildren().add(this.currentMenu);
    }

    public void updateTopMenu(Countries c, int width, int height){
        Label kek = new Label("World infos:\n");
        kek.setMinWidth(200);
        kek.setMinHeight(40);
        kek.setTextFill(Color.WHITE);
        kek.setStyle("-fx-font-size: 2em;");
        kek.setTranslateX(10);
        kek.setTranslateY(10);

        Label kek1 = new Label("Cases: " + c.totalCases());
        kek1.setMinWidth(200);
        kek1.setMinHeight(40);
        kek1.setTextFill(Color.WHITE);
        kek1.setStyle("-fx-font-size: 2em;");
        kek1.setTranslateX(width / 2 - 125);
        kek1.setTranslateY(height - 400);

        Label kek2 = new Label("Actives: " + c.totalActive());
        kek2.setMinWidth(200);
        kek2.setMinHeight(40);
        kek2.setTextFill(Color.WHITE);
        kek2.setStyle("-fx-font-size: 2em;");
        kek2.setTranslateX(width / 2 - 125);
        kek2.setTranslateY(height - 400);

        Label kek3 = new Label("Recovered: " + c.totalRecovered());
        kek3.setMinWidth(200);
        kek3.setMinHeight(40);
        kek3.setTextFill(Color.WHITE);
        kek3.setStyle("-fx-font-size: 2em;");
        kek3.setTranslateX(width / 2 - 125);
        kek3.setTranslateY(height - 400);

        this.topSection.getChildren().addAll(kek,kek1,kek2,kek3);
        
    }
}
