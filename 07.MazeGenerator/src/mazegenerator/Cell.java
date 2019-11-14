package mazegenerator;

import javafx.scene.shape.Line;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Cell extends Pane {
    private boolean[] walls = new boolean[4]; // top,left,bottom,right
    private boolean visited, current;
    private Line top, right, bottom, left;
    private Rectangle backgroundColor;
    private int x,y;
    
    public Cell(int length, int x,int y){
        this.x = x;
        this.y = y;
        top = new Line(x*length,y*length,x*length + length,y*length);
        left = new Line(x*length,y*length,x*length,y*length +length);
        bottom = new Line(x*length,y*length+length,x*length+length,y*length+length);
        right = new Line(x*length+length,y*length+length,x*length+length,y*length);
        top.setStrokeWidth(2);
        left.setStrokeWidth(2);
        bottom.setStrokeWidth(2);
        right.setStrokeWidth(2);
        backgroundColor = new Rectangle(x*length, y*length,length,length);
        backgroundColor.setFill(Color.DARKGREY);
        for(int i = 0; i < walls.length;i++)
            walls[i] = true;
        visited = false;
        current = false;
    }
    public void show(){
        getChildren().clear();
        if(current)
            backgroundColor.setFill(Color.PURPLE);
        else if (visited)
            backgroundColor.setFill(Color.BLUEVIOLET);
        
        getChildren().add(backgroundColor);
        if(walls[0])
            getChildren().add(top);
        if(walls[1])
            getChildren().add(right);
        if(walls[2])
            getChildren().add(bottom);
        if(walls[3])
            getChildren().add(left);
    }
    
    public void removeWall(int i){
        walls[i] = false;
    }
    
    public void setCurrent(boolean visit){
        current = visit;
    }
    public void setVisited(boolean visit){
        current = false;
        visited = visit;
    }
    
    public boolean getVisited(){
        return visited;
    }
    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }
}
