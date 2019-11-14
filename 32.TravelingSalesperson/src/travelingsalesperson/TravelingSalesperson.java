
package travelingsalesperson;

import java.util.Random;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;


public class TravelingSalesperson extends Application {
    int count = 1, width = 800, height = 800, cityRadius = 6, nCities = 10, nRoads = nCities-1;
    double bestDistance = Integer.MAX_VALUE;
    int[] order, bestOrder;
    Circle[] cities;
    Line[] roads, bestRoads;
    Timeline draw;
    Text percent;
    
    
    @Override
    public void start(Stage primaryStage) {
        Random rn = new Random();
        Pane root = new Pane();
        
        percent = new Text("100%");
        percent.setScaleX(3);
        percent.setScaleY(3);
        percent.setX(50);
        percent.setY(25);
        
        cities = new Circle[nCities];
        order = new int[nCities];
        bestOrder = new int[nCities];
        for(int i = 0; i< nCities; i++){
            cities[i] = new Circle(rn.nextInt(width - 2*cityRadius) + cityRadius, rn.nextInt(height- 2*cityRadius) + cityRadius, cityRadius, Color.BLACK);
            order[i] = i;
        }
        arrayCopy(bestOrder,order);
        root.getChildren().addAll(cities);
        
        Scene scene = new Scene(root, width, height);
        primaryStage.setTitle("Traveling Salesperson");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        draw = new Timeline(new KeyFrame(Duration.millis(5),f->{
            root.getChildren().clear();
            root.getChildren().addAll(cities);
            root.getChildren().add(percent);

            roads = new Line[nRoads];
            for(int i = 0; i< nRoads; i++){
                int index = order[i];
                int nextIndex = order[i+1];
                roads[i] = new Line(cities[index].getCenterX(),cities[index].getCenterY(),cities[nextIndex].getCenterX(),cities[nextIndex].getCenterY());
            }
            root.getChildren().addAll(roads);
            
            bestRoads = new Line[nRoads];
            for(int i = 0; i< nRoads; i++){
                int index = bestOrder[i];
                int nextIndex = bestOrder[i+1];
                bestRoads[i] = new Line(cities[index].getCenterX(),cities[index].getCenterY(),cities[nextIndex].getCenterX(),cities[nextIndex].getCenterY());
                bestRoads[i].setStrokeWidth(3);
                bestRoads[i].setStroke(Color.RED);
            }
            root.getChildren().addAll(bestRoads);
            
            double d = calcDistance(order);
            if(d < bestDistance){
                bestDistance = d;
                System.out.println(bestDistance);
                arrayCopy(bestOrder,order);
            }
            
            percent.setText((int)(100* (double)count / factorial(nCities))  + "%");
            count++;
            
            changeOrder();
        }));
        draw.setCycleCount(Integer.MAX_VALUE);
        draw.play();
        
    }
    
    boolean changeOrder(){
        //STEP 1
        int largestX = -1;
        for(int i = 0; i < order.length -1; i ++)
            if(order[i] < order[i+1])
                largestX = i;
        if(largestX == -1) {//Viewed all possibilities
            draw.stop();
            return false;
        }
        //STEP 2
        int largestY = -1;
        for(int i = 0; i<order.length;i++)
            if (order[largestX] < order[i])
                largestY = i;
        
        //STEP 3
        swapOrder(largestX,largestY);
        
        //STEP 4
        int[] temp = new int[order.length];
        arrayCopy(temp,order);
        for(int i = largestX + 1, j = order.length -1; i < order.length; i++,j-- )
            order[i] = temp[j];
        return true;
    }
    
    void swapOrder(int i, int j){
        int temp = order[i];
        order[i] = order[j];
        order[j] = temp;
    }
    
    void swapCities(int i, int j){
        Circle temp = cities[i];
        cities[i] = cities[j];
        cities[j] = temp;
    }
    
    double calcDistance(int[] order){
        double distance = 0;
        for(int i = 0; i< order.length-1 ; i++){
            int indexA = order[i];
            int indexB = order[i + 1];
            double x = cities[indexA].getCenterX() - cities[indexB].getCenterX();
            double y = cities[indexA].getCenterY() - cities[indexB].getCenterY();
            distance += Math.sqrt(x*x + y*y);
        }
        return distance;
    }
    
    int factorial(int n){
        if(n == 1)
            return 1;
        else
            return n * factorial(n-1);
    }
    
    void arrayCopy(int[] target, int[] old){
        for(int i = 0; i<old.length;i++)
            target[i] = old[i];
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
