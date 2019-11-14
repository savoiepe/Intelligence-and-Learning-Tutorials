
package steeringevolution;


public class PVector {
    private double forceX;
    private double forceY;
    private double magnitude;
    
    PVector(){
        forceX = 0;
        forceY = 0;
        magnitude = 0;
    }
    
    PVector(double forceX, double forceY){
        this.forceX = forceX;
        this.forceY = forceY;
        calculateMagnitude();
    }
    
    void calculateMagnitude(){
        magnitude = Math.sqrt(forceX*forceX + forceY*forceY);
    }
    
    double getForceX(){
        return forceX;
    }
    double getForceY(){
        return forceY;
    }
    double getMagnitude(){
        return magnitude;
    }
    
    void setForceX(double forceX){
        this.forceX = forceX;
        calculateMagnitude();
    }
    void setForceY(double forceY){
        this.forceY = forceY;
        calculateMagnitude();
    }
    void setMagnitude(double mag){
        forceX = forceX * mag / magnitude;
        forceY = forceY * mag / magnitude;
        calculateMagnitude();
    }
    
    void add(PVector vector){
        this.forceX += vector.forceX;
        this.forceY -= vector.forceY;
        calculateMagnitude();
    }
    void sub(PVector vector){
        this.forceX -= vector.forceX;
        this.forceY += vector.forceY;
        calculateMagnitude();
    }
    void mult(double mult){
        this.forceX *= mult;
        this.forceY *= mult;
        calculateMagnitude();
    }
    
    void limit(double limit){
        if (magnitude > limit){
            forceX = forceX * limit / magnitude;
            forceY = forceY * limit / magnitude;
            calculateMagnitude();
        }   
    }
    
    double calculateAngleTheta(){
        calculateMagnitude();
        if(magnitude == 0)
            return 0;
        if(forceY >= 0)
            return Math.acos(forceX/magnitude);
        return -Math.acos(forceX/magnitude);
    }
}
