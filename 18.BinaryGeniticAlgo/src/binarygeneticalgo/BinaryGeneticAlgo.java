package binarygeneticalgo;

import java.util.Random;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;


public class BinaryGeneticAlgo extends Application{
String targetString = "To be or not to be.";
char[] target = targetString.toCharArray();
Population population;
Individual fittest = new Individual(target); 
Individual secondFittest;
int generationCount = 0, populationSize = 100, targetLength = target.length, mutationPercent = 2;
BorderPane layout;
VBox results, currentPop;
Text best, result,generations, averageFitness, totalPopulation, mutationRate, allPhrase;
Timeline draw;

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) throws Exception {
        layout = new BorderPane();
        
        results = new VBox();
        results.setTranslateY(100);
        currentPop = new VBox();
        currentPop.setTranslateX(-100);
        
        layout.setLeft(results);
        layout.setRight(currentPop);
        
        best = new Text("Best phrase:");
        totalPopulation = new Text("total population : " + populationSize);
        mutationRate = new Text("mutation rate : " + mutationPercent + "%");
        allPhrase = new Text("All phrases: ");
        
        
        primaryStage.setTitle("Generic algorithm");
        Scene scene = new Scene(layout,500,400 );
        primaryStage.setScene(scene);
        primaryStage.show();
        
        BinaryGeneticAlgo demo = new BinaryGeneticAlgo();
        
        //Initialize population
        demo.population = new Population(demo.populationSize,demo.target);
        
        //Calculate fitness of each individual
        demo.population.calculateFitness();
        
        result = new Text(demo.fittest.toString());
        generations = new Text("total generations : " + demo.generationCount);
        averageFitness = new Text("average fitness : " + demo.fittest.fitness);
        results.getChildren().addAll(best,result,generations,averageFitness,totalPopulation,mutationRate);
        
        draw = new Timeline(new KeyFrame(Duration.millis(50),f->{
            demo.generationCount++;
            
            demo.selection();
            demo.crossover();
            demo.mutation();
            
            demo.population.calculateFitness();
            
            result.setText(demo.fittest.toString());
            generations.setText("total generations : " + demo.generationCount);
            averageFitness.setText("average fitness : " + demo.fittest.fitness);
            
            currentPop.getChildren().clear();
            currentPop.getChildren().add(allPhrase);
            for(int i = 0; i< demo.populationSize;i++){
                Text temp = new Text(demo.population.individuals[i].toString());
                currentPop.getChildren().add(temp);
            }
            
            demo.stop(draw);
        }));
        draw.setCycleCount(Integer.MAX_VALUE);
        draw.play();
    }
    
    //Selection
    void selection() {
        fittest = population.getFittest();
        secondFittest = population.getSecondFittest();
    }
    
    //Crossover random
    void crossover(){
        Random rn = new Random();
        Population newPopulation = new Population(populationSize, target);
        for(int i = 0; i<newPopulation.popSize;i++){
            Individual newIndividual= new Individual(target);
            for(int j = 0; j < newIndividual.geneLength; j++){
                if (rn.nextBoolean() == true)
                    newIndividual.genes[j] = fittest.genes[j];
                else
                    newIndividual.genes[j] = secondFittest.genes[j];
            }
            newPopulation.individuals[i] = newIndividual;
        }
        population.individuals = newPopulation.individuals;
    }
    
    //Crossover half-half
    /*
    void crossover(){
        Random rn = new Random();
        Population newPopulation = new Population(populationSize, target);
        for(int i = 0; i<newPopulation.popSize;i++){
            int index = rn.nextInt()%targetLength;
            Individual newIndividual= new Individual(target);
            for(int j = 0; j < newIndividual.geneLength; j++){
                if (index <= i)
                    newIndividual.genes[j] = fittest.genes[j];
                else
                    newIndividual.genes[j] = secondFittest.genes[j];
            }
            newPopulation.individuals[i] = newIndividual;
        }
        population.individuals = newPopulation.individuals;
    }
    */
    
    //Mutation
    void mutation(){
        Random rn = new Random();
        for (int i = 0; i < population.popSize;i++){
            for (int j = 0; j < fittest.geneLength;j++){
                if (Math.abs(rn.nextInt()%100) <= 2)
                    population.individuals[i].genes[j] = fittest.getRandomChar(); 
            }
        }
    }
    
    void stop(Timeline draw){
        if(fittest.fitness == target.length)
            draw.stop();
    }
}

    