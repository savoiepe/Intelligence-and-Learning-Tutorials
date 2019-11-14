
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
    int width = 600, height = 800, cityRadius = 6, nCities = 10, populationSize = 100;
    int nRoads = nCities-1, generationCount = 0;
    double bestDistance = Integer.MAX_VALUE, mutationRate = 0.1;
    int[] bestOrder;
    double[] fitness;
    int[][] population;
    Circle[] cities, citiesBottom;
    Line[] roads, bestRoads;
    Timeline draw;
    Text genCounter;
    
    
    @Override
    public void start(Stage primaryStage) {
        Random rn = new Random();
        Pane root = new Pane();
        
        genCounter = new Text("Generation : " + generationCount);
        genCounter.setX(15);
        genCounter.setY(25);
        
        
        cities = new Circle[nCities];
        int[] order = new int[nCities];
        bestOrder = new int[nCities];
        for(int i = 0; i< nCities; i++){
            cities[i] = new Circle(rn.nextInt(width - 2*cityRadius) + cityRadius, rn.nextInt(height/2- 2*cityRadius) + cityRadius, cityRadius, Color.BLACK);
            order[i] = i;
        }
        root.getChildren().addAll(cities);
        
        citiesBottom = new Circle[nCities];
        for(int i = 0; i< nCities; i++)
            citiesBottom[i] = new Circle(cities[i].getCenterX(), cities[i].getCenterY() + height/2, cityRadius, Color.BLACK);
        root.getChildren().addAll(citiesBottom);
        
        population = new int[populationSize][nCities];
        for(int i =0 ; i<populationSize;i++){
            arrayCopy(population[i],order);
            shuffle(population[i],10);
        }
        
        fitness = new double[populationSize];
        calcFitness();
        
        Scene scene = new Scene(root, width, height);
        primaryStage.setTitle("Traveling Salesperson");
        primaryStage.setScene(scene);
        primaryStage.show();
        
        draw = new Timeline(new KeyFrame(Duration.millis(5),f->{
            root.getChildren().clear();
            root.getChildren().addAll(cities);
            root.getChildren().addAll(citiesBottom);
            root.getChildren().add(genCounter);
               
            
            roads = new Line[nRoads];
            for(int i = 0; i< nRoads; i++){
                int index = population[0][i];
                int nextIndex = population[0][i+1];
                roads[i] = new Line(cities[index].getCenterX(),cities[index].getCenterY(),cities[nextIndex].getCenterX(),cities[nextIndex].getCenterY());
            }
            root.getChildren().addAll(roads);
                    
            bestRoads = new Line[nRoads];
            for(int i = 0; i< nRoads; i++){
                int index = bestOrder[i];
                int nextIndex = bestOrder[i+1];
                bestRoads[i] = new Line(citiesBottom[index].getCenterX(),citiesBottom[index].getCenterY(),citiesBottom[nextIndex].getCenterX(),citiesBottom[nextIndex].getCenterY());
                bestRoads[i].setStrokeWidth(3);
                bestRoads[i].setStroke(Color.RED);
            }
            root.getChildren().addAll(bestRoads);
            
            //GA
            calcFitness();
            
            nextGeneration();
            genCounter.setText("Generation : " + ++generationCount);
        }));
        draw.setCycleCount(Integer.MAX_VALUE);
        draw.play();
    }
    
    void shuffle(int[] array, int num){
        Random rn = new Random();
        for(int i = 0; i < num; i++){
            int indexA = rn.nextInt(nCities);
            int indexB = rn.nextInt(nCities);
            swap(array,indexA,indexB);
        }
    }
    
    void swap(int[] array, int i, int j){
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
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
    
    void calcFitness(){
        for(int i = 0 ; i < populationSize; i++){
            fitness[i] = calcDistance(population[i]);
            if(fitness[i] < bestDistance){
                bestDistance = fitness[i];
                bestOrder = population[i];
                System.out.println(bestDistance);
            }
            fitness[i] = 1/(fitness[i]+1);
        }
        normalizeFitness();
    }
    
    void normalizeFitness(){
        double sum = 0;
        for(int i = 0; i<fitness.length;i++)
            sum += fitness[i];
        for(int i = 0; i<fitness.length;i++)
            fitness[i] /= sum;
    }
    
    void nextGeneration(){
        int[][] newPopulation = new int[populationSize][nCities];
        for(int i = 0; i<populationSize;i++){
            int[] orderA = new int[nCities];
            selection(orderA);
            int[] orderB = new int[nCities];
            selection(orderB);
            newPopulation[i] = crossover(orderA, orderB);
            mutate(newPopulation[i]);
        }
        population = newPopulation;
    }
    
    void selection(int[] temp){
        int index = 0;
        Random rn = new Random();
        double r = rn.nextFloat()%1;
        
        while(r >= 0){
            r = r - fitness[index];
            index++;
        }
        index--;
        arrayCopy(temp,population[index]);
    }
    
    int[] crossover(int[] orderA, int[] orderB){
        Random rn = new Random();
        int index = rn.nextInt(nCities-1);
        int[] temp = new int[nCities];
        for(int i = 0; i < index; i++){
            temp[i] = orderA[i];
        }
        for(int i = index; i < nCities;i++){
            boolean contains = true;
            for(int j = 0; j < nCities && contains; j++){
                contains = arrayContain(temp,orderB[j]);
                if(!contains)
                    temp[i] = orderB[j];
            }
        }
        return temp;
    }
    
    void mutate(int[] temp){
        Random rn = new Random();
        if(rn.nextBoolean())
            mutateNeighbor(temp);
        else
            mutateRandom(temp);
    }
    
    void mutateNeighbor(int[] temp){
        for(int i = 0; i< nCities; i++){
            if(Math.random() < mutationRate){
                Random rn = new Random();
                int indexA = rn.nextInt(nCities - 1);
                int indexB = indexA + 1;
                swap(temp,indexA,indexB);
            }
        }
    }
    void mutateRandom(int[] temp){
        for(int i = 0; i< nCities; i++)
            if(Math.random() < mutationRate)
                shuffle(temp,1);
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
    
    boolean arrayContain(int[] array, int contain){
        for(int i = 0; i< array.length;i++)
            if(array[i] == contain)
                return true;
        return false;
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
