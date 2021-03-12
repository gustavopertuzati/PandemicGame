package ch.hepia.my_app;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

/*
public class Worldmap extends Application {
    public void start(Stage primaryStage) {

        private static final String imagePath = "images/world.jpg";

        Image worldImage = new Image(imagePath);
        double width = worldImage.getWidth();
        double height = worldImage.getHeight();

        ImageView worldImageView = new ImageView(worldImage);

        ScrollPane scroller = new ScrollPane();
        scroller.setContent(worldImageView);

        // the following line allows detection of clicks on transparent
        // parts of the image:
        worldImageView.setPickOnBounds(true);

        worldImageView.setOnMouseClicked(e -> {
            System.out.println("["+e.getX()+", "+e.getY()+"]");
        });
        Scene scene = new Scene(scroller, 600, 600);
        primaryStage.setScene(scene);
        primaryStage.show();

    }
}*/
