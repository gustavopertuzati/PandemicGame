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

    private boolean isAnimating = false;

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
            @Override
            protected void interpolate(double frac){ 
                if(frac < 1 - (hiddenLength / expandedLength) ) {
                    final double curWidth = expandedLength * (1.0 - frac);
                    setPrefWidth(curWidth);
                }
            }
        };

        this.hideSidebar.onFinishedProperty().set(new EventHandler<ActionEvent>(){
            @Override public void handle(ActionEvent actionEvent){
                isAnimating = !isAnimating;
                setVisible(false);
            }
        });

        this.showSidebar = new Transition(){
            { setCycleDuration(Duration.millis(300)); }
            @Override
            protected void interpolate(double frac){
                if(frac > (hiddenLength / expandedLength) ) {
                    final double curWidth = expandedLength * frac;
                    setPrefWidth(curWidth);
                }
            }
        };

        this.showSidebar.onFinishedProperty().set(new EventHandler<ActionEvent>(){
            @Override public void handle(ActionEvent actionEvent){
                isAnimating = !isAnimating;
            }
        });
        
        /*this.controlButton.setOnAction(new EventHandler<ActionEvent>(){
            @Override public void handle(ActionEvent actionEvent){
                if(showSidebar.statusProperty().get() == Animation.Status.STOPPED && hideSidebar.statusProperty().get() == Animation.Status.STOPPED){
                    if(isVisible()){
                        hideSidebar.play();
                    } else if (isOtherVisible) {
                        setVisible(true);
                        showSidebar.play();
                    }
                }
            }
        });*/
    }
    public void animate(boolean isOtherVisible){
        
        
        System.out.println("Cure: " + isOtherVisible);
        if(showSidebar.statusProperty().get() == Animation.Status.STOPPED && hideSidebar.statusProperty().get() == Animation.Status.STOPPED){
            System.out.println("Virus: " + isAnimating);
            if(isAnimating){
                hideSidebar.play();
                //setVisible(false);
            } else if (!isOtherVisible) {
                setVisible(true);
                showSidebar.play();
            }
        }
    }

    public boolean isAnimating(){
        return this.isAnimating;
    }


}