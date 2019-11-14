package mazegenerator;

import java.util.ArrayList;
import javafx.animation.KeyFrame;
import javafx.application.Application;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;


public class MazeGenerator extends Application{
    int count = 1;
    int cols = 15, rows = 15, width = 25;
    Cell[] grid = new Cell[rows*cols] ;
    Cell currentCell;
    ArrayList<Cell> stack = new ArrayList();
    Timeline draw;
    
    @Override
    public void start(Stage primaryStage) throws Exception{
        
        Pane layout = new Pane();
        for(int i = 0; i< rows; i++)
            for(int j = 0; j<cols;j++)
                grid[j + i*cols] = new Cell(width,i,j); 
        
        layout.getChildren().addAll(grid);
        
        currentCell = grid[0];
        currentCell.setVisited(true);
        stack.add(currentCell);
        
        primaryStage.setTitle("MazeGenerator");
        Scene scene = new Scene(layout,rows*width,cols*width);
        primaryStage.setScene(scene);
        primaryStage.show();
        
        draw = new Timeline(new KeyFrame(Duration.millis(15),f->{
            for(Cell grid1:grid)
                grid1.show();
            
            checkNeighbors();
        }));
        draw.setCycleCount(Integer.MAX_VALUE);
        draw.play();
    }
    
    public void checkNeighbors(){
        Cell[] neighbors = new Cell[4];
        int nNeighbors = 0;
        
        Cell top = grid[index(currentCell.getX(),currentCell.getY() - 1)];
        Cell right = grid[index(currentCell.getX()+1,currentCell.getY())];
        Cell bottom = grid[index(currentCell.getX(),currentCell.getY() +1)];
        Cell left = grid[index(currentCell.getX()-1,currentCell.getY())];
        
        if (!top.getVisited() && top != grid[0]){
            neighbors[nNeighbors] = top;
            nNeighbors++;
        }
        if (!right.getVisited() && right != grid[0]){
            neighbors[nNeighbors] = right;
            nNeighbors++;
        }
        if (!bottom.getVisited() && bottom != grid[0] ){
            neighbors[nNeighbors] = bottom;
            nNeighbors++;
        }
        if (!left.getVisited() && left != grid[0]){
            neighbors[nNeighbors] = left;
            nNeighbors++;
        }
        
        if(nNeighbors > 0){
           int rand = (int)(Math.random()*nNeighbors);
           Cell nextCell = neighbors[rand];
           stack.add(nextCell);
           currentCell.setVisited(true);
           nextCell.setCurrent(true);
           
           removeWalls(currentCell, nextCell);
           currentCell = nextCell;
        }
        else if(stack.size() != 0){
            currentCell.setVisited(true);
            currentCell = stack.get(stack.size() - 1);
            currentCell.setCurrent(true);
            stack.remove(currentCell);
        }
        else if (stack.isEmpty()){
            draw.stop();
        }
    }
    
    public void removeWalls(Cell current, Cell next){
        int x = current.getX() - next.getX();
        int y = current.getY() - next.getY();
        
        if(x == 1){
            current.removeWall(3);
            next.removeWall(1);
        }
        else if (x == -1){
            current.removeWall(1);
            next.removeWall(3);
        }
        
        if(y == 1){
            current.removeWall(0);
            next.removeWall(2);
        }
        else if (y == -1){
            current.removeWall(2);
            next.removeWall(0);
        }
        
    }
    
    public int index(int i, int j){
        if(i < 0 || j < 0 || i > cols-1||j>cols-1)
            return 0;
        return j + i*cols;
    }
    public static void main(String[] args) {
        launch(args);
    }
}