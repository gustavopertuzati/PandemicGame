package ch.hepia.my_app;

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

//Version + simple du sidebar, qui marche mais j'arrive à faire marcher que à droite :'(
class RightSideBar extends Parent{

    private double expandedLength;
    private double hiddenLength;

    private TranslateTransition tt = new TranslateTransition(Duration.seconds(1), this);

    private boolean isAnimating = false;

    public RightSideBar(final double expandedLength, final double hiddenLength, Node... nodes){

        this.expandedLength = expandedLength;
        this.hiddenLength = hiddenLength;

        getChildren().addAll(nodes);

        tt.setOnFinished(e -> isAnimating = false);

        tt.setInterpolator(new Interpolator(){
            @Override
            protected double curve(double t){
                return (t == 1.0 ? 1.0 : 1-Math.pow(2.0, -10*t));
            }
        });

    }

    public void animate(){

        if(isAnimating) return;
        
        isAnimating = true;

        tt.setFromX(getTranslateX());
        System.out.println(getTranslateX());
        if(getTranslateX() > hiddenLength){
            tt.setToX(getTranslateX() - expandedLength);
        }else{
            tt.setToX(getTranslateX() + expandedLength);
        }

        tt.play();
    }
}