package ch.hepia.my_app;

import javax.swing.Action;

import javafx.animation.Animation;
import javafx.animation.Transition;

import javafx.application.Application;

import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.Group;
import javafx.scene.control.*;

import javafx.scene.shape.*;
import javafx.animation.Transition.*;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

import javafx.scene.input.MouseEvent;

import javafx.geometry.Pos;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.util.Duration;


class SideBar extends HBox{
    
    private Animation hideSidebar;
    private Animation showSidebar;

    private Button controlButton;

    // stp gustavo tu peux faire çaaaaaaaaa ?

    SideBar(final double expandedLength, final double hiddenLength, Button btn, int newHeight, Node... nodes){
        
        this.controlButton = btn;

        this.setPrefWidth(expandedLength);
        this.setPrefHeight(newHeight);
        this.setMinWidth(0);
        this.setVisible(false);
        
        setAlignment(Pos.CENTER);
        getChildren().addAll(nodes);
    
        this.hideSidebar = new Transition(){
            { setCycleDuration(Duration.millis(250)); }
            protected void interpolate(double frac){ 
                if(frac < 1 - (hiddenLength / expandedLength)){
                    final double curLength = expandedLength * (1.0 - frac);
                    System.out.println(frac);
                    setPrefWidth(curLength);
                }
            }
        };

        this.hideSidebar.onFinishedProperty().set(new EventHandler<ActionEvent>(){
            @Override public void handle(ActionEvent actionEvent){
                setVisible(false);
            }
        });

        this.showSidebar = new Transition(){
            { setCycleDuration(Duration.millis(250)); }
            protected void interpolate(double frac){
                if(frac > (hiddenLength / expandedLength)){
                    final double curLength = expandedLength * frac;
                    setPrefWidth(curLength);
                }
            }
        };

        this.showSidebar.onFinishedProperty().set(new EventHandler<ActionEvent>(){
            @Override public void handle(ActionEvent actionEvent){}
        });
        
        this.controlButton.setOnAction(new EventHandler<ActionEvent>(){
            @Override public void handle(ActionEvent actionEvent){
                if(showSidebar.statusProperty().get() == Animation.Status.STOPPED && hideSidebar.statusProperty().get() == Animation.Status.STOPPED){
                    if(isVisible()){
                        hideSidebar.play();
                    } else {
                        showSidebar.play();
                    }
                    setVisible(!isVisible());
                }
            }
        });
    }

    /*public void SideBarCure(final double expandedLength, final double hiddenLength, Button btn, int newHeight, Node... nodes){
        
        this.controlButton = btn;

        this.setPrefWidth(expandedLength);
        this.setPrefHeight(newHeight);
        this.setMinWidth(0);
        this.setVisible(false);
        
        setAlignment(Pos.CENTER);
        getChildren().addAll(nodes);
    
        this.hideSidebar = new TranslateTransition(Duration.millis(250), this);

        this.hideSidebar.onFinishedProperty().set(new EventHandler<ActionEvent>(){
            @Override public void handle(ActionEvent actionEvent){
                setVisible(false);
            }
        });

        this.showSidebar = new TranslateTransition(Duration.millis(250), this);

        this.showSidebar.onFinishedProperty().set(new EventHandler<ActionEvent>(){
            @Override public void handle(ActionEvent actionEvent){}
        });
        
        this.controlButton.setOnAction(new EventHandler<ActionEvent>(){
            @Override public void handle(ActionEvent actionEvent){
                if(showSidebar.statusProperty().get() == Animation.Status.STOPPED && hideSidebar.statusProperty().get() == Animation.Status.STOPPED){
                    if(isVisible()){
                        hideSidebar.play();
                    } else {
                        showSidebar.play();
                    }
                    setVisible(!isVisible());
                }
            }
        });
    }*/
}       


/*package ch.hepia.my_app;

import javax.swing.Action;

import javafx.animation.Animation;
import javafx.animation.Transition;
import javafx.animation.Interpolator;

import javafx.application.Application;

import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.Group;
import javafx.scene.control.*;

import javafx.scene.shape.*;
import javafx.animation.Transition.*;
import javafx.animation.TranslateTransition;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.Parent;

import javafx.scene.input.MouseEvent;

import javafx.geometry.Pos;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.util.Duration;

class LeftSideBar extends Parent{

    private double expandedLength;
    private double hiddenLength;

    private TranslateTransition tt = new TranslateTransition(Duration.seconds(1), this);

    private boolean isAnimating = false;

    public LeftSideBar(final double expandedLength, final double hiddenLength, Node... nodes){

        this.expandedLength = expandedLength;
        this.hiddenLength = hiddenLength;

        getChildren().addAll(nodes);

        tt.setOnFinished(e -> isAnimating = false);

        tt.setInterpolator(new Interpolator(){
            @Override
            protected double curve(double t){
                return (t == 1.0 ? 1.0 : 1 - Math.pow(2.0, -10*t));
            }
        });
    }

    public void animate(){

        if(isAnimating) return;
        
        isAnimating = true;

        tt.setFromX(getTranslateX());
        System.out.println(getTranslateX());

        if(getTranslateX() >= 0){ 
            tt.setToX(getTranslateX() - expandedLength);
        }else{
            tt.setToX(getTranslateX() + expandedLength);
        }

        tt.play();
    }
}

///////
class LeftSideBar extends HBox{
    
    private Animation hideSidebar;
    private Animation showSidebar;

    private Button controlButton;

    LeftSideBar(final double expandedLength, final double hiddenLength, Button btn, int newHeight, Node... nodes){
        
        this.controlButton = btn;

        this.setPrefWidth(expandedLength);
        this.setPrefHeight(newHeight);
        this.setMinWidth(0);
        this.setVisible(false);
        
        setAlignment(Pos.CENTER);
        getChildren().addAll(nodes);
    
        this.hideSidebar = new Transition(){
            { setCycleDuration(Duration.millis(250)); }
            protected void interpolate(double frac){ 
                if(frac < 1 - (hiddenLength / expandedLength) ) {
                    final double curWidth = expandedLength * (1.0 - frac);
                    setPrefWidth(curWidth);
                }
            }
        };

        this.hideSidebar.onFinishedProperty().set(new EventHandler<ActionEvent>(){
            @Override public void handle(ActionEvent actionEvent){
                setVisible(false);
            }
        });

        this.showSidebar = new Transition(){
            { setCycleDuration(Duration.millis(250)); }
            protected void interpolate(double frac){
                if(frac > (hiddenLength / expandedLength) ) {
                    final double curWidth = expandedLength * frac;
                    setPrefWidth(curWidth);
                    //setTranslateX(-expandedLength + curWidth);
                }
            }
        };

        this.showSidebar.onFinishedProperty().set(new EventHandler<ActionEvent>(){
            @Override public void handle(ActionEvent actionEvent){}
        });
        
        this.controlButton.setOnAction(new EventHandler<ActionEvent>(){
            @Override public void handle(ActionEvent actionEvent){
                if(showSidebar.statusProperty().get() == Animation.Status.STOPPED && hideSidebar.statusProperty().get() == Animation.Status.STOPPED){
                    if(isVisible()){
                        hideSidebar.play();
                    } else {
                        showSidebar.play();
                    }
                    setVisible(!isVisible());
                }
            }
        });
    }
}*/