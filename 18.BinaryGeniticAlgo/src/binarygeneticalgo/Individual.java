
package binarygeneticalgo;
import java.util.Random;

    //Individual class
class Individual {
    char[] genes;
    int geneLength,fitness = 0;
    char[] target;
    
    public Individual(char[] target) {
        this.target = target;
        geneLength = target.length;
        genes = new char[geneLength];
        
        //Set genes randomly for each individual
        for (int i = 0; i < genes.length; i++) {
            genes[i] = getRandomChar();
        }
        fitness = 0;
    }
    
    //Calculate fitness
    public void calcFitness() {
        fitness = 0;
        for (int i = 0; i < geneLength; i++) {
            if (genes[i] == target[i]) {
                fitness++;
            }
        }
    }
    
    public String toString(){
        return new String(genes);
    }
    
    //returns a random character
    public char getRandomChar(){
        Random rn = new Random();
        return (char)(rn.nextInt(126 - 32) + 32);
    }
}