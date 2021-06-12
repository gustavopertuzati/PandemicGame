package ch.hepia.covid_manager;

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
import java.lang.Thread;

public class LeftSideBar extends HBox{
    
    private Animation hideSidebar;
    private Animation showSidebar;

    private Button controlButton;

    private boolean isAnimating = false;

    public LeftSideBar(final double expandedLength, final double hiddenLength, Button btn, int newHeight){
        
        this.controlButton = btn;

        this.setPrefWidth(expandedLength);
        this.setPrefHeight(newHeight);
        this.setMinWidth(0);
        this.setVisible(false);
        
        setAlignment(Pos.CENTER);
    
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
    }

    public void addContent(Node... nodes){
        getChildren().addAll(nodes);
    }

    public void animate(boolean isOtherVisible, RightSideBar rsb){   

        System.out.println("Cure: " + isOtherVisible);
        if(showSidebar.statusProperty().get() == Animation.Status.STOPPED && hideSidebar.statusProperty().get() == Animation.Status.STOPPED){
            //Si on ferme
            if(isAnimating){
                try{
                    Thread.sleep(200);
                } catch(InterruptedException e){}
                this.getChildren().clear();
                hideSidebar.play();
                return;
            }else if(isOtherVisible){
            //Si on ouvre et que le menu opposé est ouvert
                rsb.animate(false, this);
            }
            //On ouvre
            setVisible(true);
            showSidebar.play();
        }
    }

    public boolean isAnimating(){
        return this.isAnimating;
    }


}