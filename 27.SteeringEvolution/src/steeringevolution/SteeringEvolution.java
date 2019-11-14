
package steeringevolution;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class SteeringEvolution extends Application {
    int width = 600, height = 400;
    Pane root;
    Timeline draw;
    
    @Override
    public void start(Stage primaryStage) {
        root = new Pane();
        
        Scene scene = new Scene(root, width, height);
        primaryStage.setTitle("Steering Evolution!");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        Vehicule test = new Vehicule(100,100);
        root.getChildren().add(test);
        test.display();
        
        Circle target = new Circle(400,200,20,Color.AQUA);
        PVector targetVector = new PVector(400,-200);
        root.getChildren().add(target);
        draw = new Timeline(new KeyFrame(Duration.millis(20),f->{
            test.seek(targetVector);
            test.update();
            test.display();
        }));
        draw.setCycleCount(Integer.MAX_VALUE);
        draw.play();
        
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
}
