package ch.hepia.my_app;

import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;
import javafx.scene.Group;
import java.util.List;

public class Rewards{

  public static void addRewardCirclesToBox(Group box, Countries countries, Virus v, int num){

    List<int[]> coords = countries.getRandomCountryCoordinates(num);

    for(int i = 0; i < num; i+=1){

      int[] crt = coords.get(i);
      int randOffsetX = (int)Math.floor(Math.random()*500);
      int randOffsetY = (int)Math.floor(Math.random()*500);

      
      Circle points = new Circle(crt[0]+randOffsetX, crt[1]+randOffsetY, 80, Color.PINK);
      Circle blackOutline = new Circle(crt[0]+randOffsetX, crt[1]+randOffsetY, 90, Color.BLACK);
      box.getChildren().addAll(blackOutline, points);
      points.setOnMouseClicked(e -> {
        v.addPoint();
        box.getChildren().remove(points);
        box.getChildren().remove(blackOutline);
      });
    }

  }
}