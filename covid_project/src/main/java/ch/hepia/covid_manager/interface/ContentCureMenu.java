package ch.hepia.covid_manager;

import java.util.Map;
import java.util.LinkedHashMap;
import java.time.LocalDate;
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

import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;

class ContentCureMenu extends Group{

    private Button cases;
    private Button deaths;
    private Button cured;
    private Button recovered;
    private int status;
    private LineChart chart;
    private Countries countries;
    private Virus virus;

    private Label legend;
    private Label lblInfectivity;
    private Label lblLethality;
    private Label lblResistance;

    private HBox topSection;
    private VBox bottomSection;
    private VBox currentMenu;

    public ContentCureMenu(Countries c, Virus v, int width, int height){       
        this.topSection = this.generateMenuHeader();
        this.currentMenu = new VBox();
        this.bottomSection = this.initBottomSection();

        this.legend = new Label();
        this.legend.setTextFill(Color.WHITE);
        this.legend.setStyle("-fx-font-size: 1.2em;");
        this.legend.setTranslateX(150);

        this.status = 1;
        this.countries = c;
        this.virus = v;
        
        NumberAxis xAxis = new NumberAxis();
        xAxis.setLabel("Date");
        xAxis.setAnimated(false);
        NumberAxis yAxis = new NumberAxis();        
        yAxis.setLabel("No of people");
        yAxis.setAnimated(false);

        this.chart = new LineChart(xAxis, yAxis);
        this.chart.lookup(".chart-plot-background").setStyle("-fx-background-color: transparent;");
        this.chart.lookup(".chart-horizontal-grid-lines").setStyle("-fx-stroke: transparent;");
        this.chart.lookup(".chart-vertical-grid-lines").setStyle("-fx-stroke: transparent;");    
        this.chart.lookup(".axis-label").setStyle("-fx-text-fill: #ffffff;");
        this.chart.lookup(".axis").setStyle("-fx-font-size: 1em; -fx-tick-label-fill: #ffffff;");
        this.chart.setPrefSize(400, 400);
        this.chart.setLegendVisible(false);

        this.updateChart();        
        this.updateMenu();
        this.updateLabel();
        
        Line line = new Line(20,0, width-20,0);
        line.setStroke(Color.WHITE);
        line.setStrokeWidth(3);  

        this.currentMenu.getChildren().addAll(this.topSection, new VBox(this.chart), this.legend, line, this.bottomSection);
        this.currentMenu.setMargin(this.topSection, new Insets(0, 0, 60, 30));
        this.currentMenu.setMargin(line, new Insets(20, 0, 20, 0));
        this.getChildren().addAll(this.currentMenu);
    }

    private Button generateMenuButtons(String label, int pos){
        Button b = new Button(label);
        b.setTextFill(Color.WHITE);
        b.setStyle("-fx-border-color: #fff;-fx-border-width: 3;-fx-background-color: transparent;-fx-font-size: 1.5em;-fx-border-radius: 5px;");
        b.setTranslateX(7 * pos);
        b.setTranslateY(20);
        b.hoverProperty().addListener( e -> {
            this.updateMenu();
        });
        return b;
    }

    private HBox generateMenuHeader(){
        this.cases = generateMenuButtons("Cases", 1);
        this.cases.setOnAction(e -> {
            if(this.status != 1){
                this.status = 1;
                this.updateMenu();
                this.updateChart();
            }
        });
        
        this.deaths = generateMenuButtons("Deaths", 2);
        this.deaths.setOnAction(e -> {
            if(this.status != 2){
                this.status = 2;
                this.updateMenu();
                this.updateChart();
            }
        });
        
        this.cured = generateMenuButtons("Cured", 3);
        this.cured.setOnAction(e -> {
            if(this.status != 3){
                this.status = 3;
                this.updateMenu();
                this.updateChart();
            }
        });

        this.recovered = generateMenuButtons("Recovered", 4);
        this.recovered.setOnAction(e -> {
            if(this.status != 4){
                this.status = 4;
                this.updateMenu();
                this.updateChart();
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
        this.chart.getData().clear();    
        if(this.status == 1){
            this.legend.setText("Evolution of cases");
            this.chart.getData().add(dataSet(this.countries.listOfCasesByDay()));
        } else if(this.status == 2){
            this.legend.setText("Evolution of deaths");
            this.chart.getData().add(dataSet(this.countries.listOfDeathsByDay()));
        } else if(this.status == 3){
            this.legend.setText("Evolution of cured");
            this.chart.getData().add(dataSet(this.countries.listOfCuredByDay()));
        } else if(this.status == 4){
            this.legend.setText("Evolution of recovered");
            this.chart.getData().add(dataSet(this.countries.listOfRecoveredByDay()));
        }
    }

    private VBox initBottomSection(){
        Label lblTitle = new Label("Virus infos:");
        lblTitle.setTextFill(Color.WHITE);
        lblTitle.setStyle("-fx-font-size: 1.2em;");
        lblTitle.setTranslateX(20);

        this.lblLethality = new Label();
        this.lblLethality.setTextFill(Color.WHITE);
        this.lblLethality.setStyle("-fx-font-size: 1.2em;");
        this.lblLethality.setTranslateX(70);

        this.lblInfectivity = new Label();
        this.lblInfectivity.setTextFill(Color.WHITE);
        this.lblInfectivity.setStyle("-fx-font-size: 1.2em;");
        this.lblInfectivity.setTranslateX(70);

        this.lblResistance = new Label();
        this.lblResistance.setTextFill(Color.WHITE);
        this.lblResistance.setStyle("-fx-font-size: 1.2em;");
        this.lblResistance.setTranslateX(70);

        return new VBox(lblTitle, lblInfectivity, lblLethality, lblResistance);
    }

    private void updateLabel(){
        this.lblLethality.setText("lethality: " + this.virus.lethality());
        this.lblInfectivity.setText("infectivity: " + this.virus.infectivity());
        this.lblResistance.setText("resistance: " + this.virus.resistance());
    }

    private void emphasisButton(Button b){
        b.setTextFill(Color.web("0x808080"));
        b.setStyle("-fx-border-color: #808080;-fx-border-width: 3;-fx-background-color: transparent;-fx-font-size: 1.5em;-fx-border-radius: 5px;");
    }

    private void clearButton(Button b){
        b.setTextFill(Color.WHITE);
        b.setStyle("-fx-border-color: #fff;-fx-border-width: 3;-fx-background-color: transparent;-fx-font-size: 1.5em;-fx-border-radius: 5px;");
    }

    private XYChart.Series dataSet(List<Integer> set){
        XYChart.Series data = new XYChart.Series();
        int i = -1;
        for(int val: set){
            data.getData().add(new XYChart.Data( i+=1, val));
        }
        return data;
    }

    public void refreshDisplay(){
        this.updateChart();
        this.updateLabel();
        this.updateMenu();
    }
}
