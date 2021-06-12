package ch.hepia.covid_manager;

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
class RightSideBar extends HBox{

    private double expandedLength;
    private double hiddenLength;

    private TranslateTransition tt = new TranslateTransition(Duration.millis(300), this);

    private boolean isAnimating = false;

    private boolean isShowing = false;

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

    public void animate(boolean isOtherVisible, LeftSideBar lsb){

        //if(isAnimating){return;};
        //isAnimating = true;
        /*
        tt.setFromX(getTranslateX());
        System.out.println(getTranslateX());
            if(getTranslateX() <= hiddenLength){
                tt.setToX(getTranslateX() + expandedLength);
                isShowing = false;
            }else if(!isOtherVisible){
                tt.setToX(getTranslateX() - expandedLength);
                lsb.animate(false, this);
                isShowing = true;
            }
        */
        //Si on ferme
        if(getTranslateX() <= hiddenLength ){

            tt.setToX(getTranslateX() + expandedLength);
            isShowing = false;
        }else{
            if(isOtherVisible){
                //Si on ouvre et que le menu opposé est ouvert
                lsb.animate(isShowing, this);
            }
            //On ouvre
            tt.setToX(getTranslateX() - expandedLength);
            isShowing = true;
        }
        tt.play();
    }

    public boolean isAnimating(){
        return this.isShowing;
    }
}