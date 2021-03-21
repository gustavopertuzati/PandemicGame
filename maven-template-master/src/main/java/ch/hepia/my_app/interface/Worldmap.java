package ch.hepia.my_app;

import java.util.Scanner;
import java.util.Map;
import java.util.HashMap;

import javafx.geometry.Pos;
import javafx.scene.layout.Region;
import javafx.scene.Node;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import javafx.scene.input.MouseEvent;
import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.PixelReader;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Paint;
import javafx.scene.paint.Color;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.canvas.Canvas;
import javafx.scene.Group;
import javafx.animation.*;
//import javafx.animation.Transition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.image.WritableImage;
import javafx.scene.control.*;

class SideBar extends VBox
    {
        /**
         * @return a control button to hide and show the sidebar
         */
        public MouseEvent getControlButton()
        {
            return controlButton;
        }
        private final MouseEvent controlButton;

        /**
         * creates a sidebar containing a vertical alignment of the given nodes
         */
        SideBar(final double expandedWidth, Node... nodes)
        {
            getStyleClass().add("sidebar");
            this.setPrefWidth(expandedWidth);
            this.setMinWidth(0);

// create a bar to hide and show.
            setAlignment(Pos.CENTER);
            getChildren().addAll(nodes);

// create a button to hide and show the sidebar.
            controlButton = new Button("Collapse");
            controlButton.getStyleClass().add("hide-left");

// apply the animations when the button is pressed.
            controlButton.setOnAction(new EventHandler<ActionEvent>()
            {
                @Override
                public void handle(ActionEvent actionEvent)
                {
// create an animation to hide sidebar.
                    final Animation hideSidebar = new Transition()
                    {
                        {
                            setCycleDuration(Duration.millis(250));
                        }

                        @Override
                        protected void interpolate(double frac)
                        {
                            final double curWidth = expandedWidth * (1.0 - frac);
                            setPrefHeight(curWidth);
                            setTranslateY(-expandedWidth + curWidth);
                        }
                    };
                    hideSidebar.onFinishedProperty().set(new EventHandler<ActionEvent>()
                    {
                        @Override
                        public void handle(ActionEvent actionEvent)
                        {
                            setVisible(false);
                            controlButton.setText("Show");
                            controlButton.getStyleClass().remove("hide-left");
                            controlButton.getStyleClass().add("show-right");
                        }
                    });
// create an animation to show a sidebar.
                    final Animation showSidebar = new Transition()
                    {
                        {
                            setCycleDuration(Duration.millis(250));
                        }

                        @Override
                        protected void interpolate(double frac)
                        {
                            final double curWidth = expandedWidth * frac;
                            setPrefHeight(curWidth);
                            setTranslateY(-expandedWidth + curWidth);
                        }
                    };
                    showSidebar.onFinishedProperty().set(new EventHandler<ActionEvent>()
                    {
                        @Override
                        public void handle(ActionEvent actionEvent)
                        {
                            controlButton.setText("Collapse");
                            controlButton.getStyleClass().add("hide-left");
                            controlButton.getStyleClass().remove("show-right");
                        }
                    });
                    if (showSidebar.statusProperty().get() == Animation.Status.STOPPED && hideSidebar.statusProperty().get() == Animation.Status.STOPPED)
                    {
                        if (isVisible())
                        {
                            hideSidebar.play();
                        }
                        else
                        {
                            setVisible(true);
                            showSidebar.play();
                        }
                    }
                }
            });
        }
    }