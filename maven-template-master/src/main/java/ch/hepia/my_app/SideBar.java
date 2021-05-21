package ch.hepia.my_app;

import javax.swing.Action;

import javafx.animation.Animation;
import javafx.animation.Transition;

import javafx.application.Application;

import javafx.scene.Scene;
import javafx.scene.Node;
import javafx.scene.Group;
import javafx.scene.control.*;

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
    
    private Button controlButton;
    
    public Button getControlButton(){
        return this.controlButton;
    }


    SideBar(final double expandedLength, final double hiddenLength, Button btn, Node... nodes){
        
        this.setPrefWidth(expandedLength);
        this.setMinWidth(0);

        this.controlButton = btn;

        controlButton.setOnAction(new EventHandler<ActionEvent>(){
            @Override public void handle(ActionEvent actionEvent){
                setVisible(false);
            }
        });

        final Animation hideSidebar = new Transition(){
            {
                setCycleDuration(Duration.millis(250));
            }
            protected void interpolate(double frac){
                if(frac < 1 -(hiddenLength / expandedLength)){
                    final double curLength = expandedLength * (1.0 - frac);
                    setPrefWidth(curLength);
                }
            }
        };

        hideSidebar.onFinishedProperty().set(new EventHandler<ActionEvent>(){
            @Override public void handle(ActionEvent actionEvent){
                setVisible(false);
                controlButton.setText("Afficher");
            }
        });

        final Animation showSidebar = new Transition(){
            {
                setCycleDuration(Duration.millis(250));
            }
            protected void interpolate(double frac){
                if(frac > (hiddenLength / expandedLength)){
                    final double curLength = expandedLength * frac;
                    setPrefWidth(curLength);
                }
            }
        };

        showSidebar.onFinishedProperty().set(new EventHandler<ActionEvent>(){
            @Override public void handle(ActionEvent actionEvent){
                controlButton.setText("Retour");
            }
        });

        if(showSidebar.statusProperty().get() == Animation.Status.STOPPED && hideSidebar.statusProperty().get() == Animation.Status.STOPPED){
            if(isVisible()){
                hideSidebar.play();  
            } else {
                setVisible(true);
                showSidebar.play();
            }
        }

    }

}
