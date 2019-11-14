
package steeringevolution;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;

public class Vehicule extends Pane{
    PVector position, velocity, acceleration;
    double r = 10,maxForce, maxSpeed;
    
    Vehicule(){
        acceleration = new PVector(0,0);
        velocity = new PVector(0,0);
        position = new PVector(0,0);
        maxSpeed = 4;
        maxForce = 0.1;
    }
    Vehicule(double x, double y){
        acceleration = new PVector(0,0);
        velocity = new PVector(0,-1);
        position = new PVector(x,y);
        maxSpeed = 3;
        maxForce = 0.1;
    }
    
    void update(){
        velocity.add(acceleration);
        
        velocity.limit(maxSpeed);
        
        position.add(velocity);
        acceleration.mult(0);
    }
    
    void applyForce(PVector force){
        acceleration.add(force);
    }
    
    void seek(PVector target) {
        PVector steer = new PVector(target.getForceX(),target.getForceY());
        
        steer.sub(position);
        steer.setMagnitude(maxSpeed);
        steer.sub(velocity);
        steer.limit(maxForce);
        
        applyForce(steer);
      }
    
    void display(){
        double theta = Math.PI -  velocity.calculateAngleTheta();
        double r = 30;
        this.getChildren().clear();
        Polygon triangle = new Polygon();
        triangle.getPoints().addAll(new Double[]{
            position.getForceX(), position.getForceY(),
            position.getForceX() + r*Math.cos(theta + Math.PI/12), position.getForceY() + r*Math.sin(theta+ Math.PI/12),
            position.getForceX() + r*Math.cos(theta - Math.PI/12), position.getForceY() + r*Math.sin(theta - Math.PI/12)});
        triangle.setFill(Color.color(0, 1, 0));
        this.getChildren().add(triangle);
    }
    
}
