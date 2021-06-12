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

import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

import javafx.geometry.Insets;
import javafx.geometry.Pos;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.stage.Modality;
import javafx.stage.Stage;

import javafx.util.Duration;

import javafx.scene.effect.BlurType;

class ContentCureMenu extends Group{

    private Button cases;
    private Button deaths;
    private Button cured;
    private Button recovered;
    private int status;
    private Virus virus;
    private Countries countries;
    private LineChart chart;

    private HBox topSection;
    private VBox bottomSection;
    private VBox currentMenu;


    ContentCureMenu(Countries c, Virus v, int width, int height){       
        this.topSection = this.generateMenuHeader();
        this.currentMenu = new VBox();
        this.bottomSection = this.initializeBottomSection(v);
        
        this.status = 1;
        this.virus = v;
        this.countries = c;
        
        NumberAxis xAxis = new NumberAxis();
        xAxis.setLabel("Date");
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("No of people");
        this.chart = new LineChart(xAxis, yAxis);
        this.chart.lookup(".chart-plot-background").setStyle("-fx-background-color: transparent;");
        this.chart.lookup(".chart-horizontal-grid-lines").setStyle("-fx-stroke: transparent;");
        this.chart.lookup(".chart-vertical-grid-lines").setStyle("-fx-stroke: transparent;");
        
        this.updateChart();
        
        this.updateMenu();
        
        this.currentMenu.getChildren().add(this.topSection);
        this.currentMenu.getChildren().add(new VBox(this.chart));

        this.currentMenu.setMargin(topSection, new Insets(10, 0, 60, 30));

        this.getChildren().add(this.currentMenu);
    }

    
    private Button generateMenuButtons(String label, int pos){
        Button b = new Button(label);
        b.setTextFill(Color.WHITE);
        b.setStyle("-fx-border-color: #fff;-fx-border-width: 3;-fx-background-color: transparent;-fx-font-size: 1.5em;-fx-border-radius: 5px;");
        b.setTranslateX(7 * pos);
        b.hoverProperty().addListener( e -> {
            this.updateMenu();
            // a voir ou il faut mettre ce truc
            this.chart.getData().removeAll();
            this.updateChart();
        });
        return b;
    }

    private HBox generateMenuHeader(){
        this.cases = generateMenuButtons("Cases", 1);
        this.cases.setOnAction(e -> {
            if(this.status != 1){
                this.status = 1;
            }
        });
        
        this.deaths = generateMenuButtons("Deaths", 2);
        this.deaths.setOnAction(e -> {
            if(this.status != 2){
                this.status = 2;
            }
        });
        
        this.cured = generateMenuButtons("Cured", 3);
        this.cured.setOnAction(e -> {
            if(this.status != 3){
                this.status = 3;
            }
        });

        this.recovered = generateMenuButtons("Recovered", 4);
        this.recovered.setOnAction(e -> {
            if(this.status != 4){
                this.status = 4;
            }
        });
        return new HBox(this.cases, this.deaths, this.cured, this.recovered);
    }

    private void updateMenu(){
        if(this.status == 1){
            emphasisButton(this.cases);
            clearButton(this.deaths);
            clearButton(this.cured);
            clearButton(this.recovered);
        } else if(this.status == 2){
            clearButton(this.cases);
            emphasisButton(this.deaths);
            clearButton(this.cured);
            clearButton(this.recovered);
        } else if(this.status == 3){
            clearButton(this.cases);
            clearButton(this.deaths);
            emphasisButton(this.cured);
            clearButton(this.recovered);
        } else if(this.status == 4){
            clearButton(this.cases);
            clearButton(this.deaths);
            clearButton(this.cured);
            emphasisButton(this.recovered);
        } 
    }

    private void updateChart(){     
        if(this.status == 1){
            this.chart.getData().add(dataSet("Evolution of cases"));//, list));
        } else if(this.status == 2){
            this.chart.getData().add(dataSet("Evolution of deaths"));//, list));
        } else if(this.status == 3){
            this.chart.getData().add(dataSet("Evolution of cured"));//, list));
        } else if(this.status == 4){
            this.chart.getData().add(dataSet("Evolution of recovered"));//, list));
        }
    }

    public VBox initializeBottomSection(Virus v){
        Label lblLethality = new Label("lethality: " + v.lethality());
        lblLethality.setWrapText(true);
        lblLethality.setTextFill(Color.WHITE);
        lblLethality.setStyle("-fx-font-size: 2em;");

        Label lblInfectivity = new Label("infectivity: " + v.infectivity());
        lblInfectivity.setWrapText(true);
        lblInfectivity.setTextFill(Color.WHITE);
        lblInfectivity.setStyle("-fx-font-size: 2em;");

        Label lblResistance = new Label("resistance: " + v.resistance());
        lblResistance.setWrapText(true);
        lblResistance.setTextFill(Color.WHITE);
        lblResistance.setStyle("-fx-font-size: 2em;");

        return new VBox(lblInfectivity, lblLethality, lblResistance);
    }

    private void emphasisButton(Button b){
        b.setTextFill(Color.web("0x808080"));
        b.setStyle("-fx-border-color: #808080;-fx-border-width: 3;-fx-background-color: transparent;-fx-font-size: 1.5em;-fx-border-radius: 5px;");
    }

    private void clearButton(Button b){
        b.setTextFill(Color.WHITE);
        b.setStyle("-fx-border-color: #fff;-fx-border-width: 3;-fx-background-color: transparent;-fx-font-size: 1.5em;-fx-border-radius: 5px;");
    }

    public XYChart.Series dataSet(String name){
        XYChart.Series data = new XYChart.Series();
        data.setName(name);
        
        data.getData().add(new XYChart.Data( 1, 567));
        data.getData().add(new XYChart.Data( 5, 612));
        data.getData().add(new XYChart.Data(10, 800));
        data.getData().add(new XYChart.Data(20, 780));

        return data;
    }
}
