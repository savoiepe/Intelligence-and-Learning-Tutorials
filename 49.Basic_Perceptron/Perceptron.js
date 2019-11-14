class Perceptron{
  
  constructor(numberOfInputs){
    this.weights = new Array(numberOfInputs);
    this.learning_rate = 0.0005;
    
    for (let i = 0 ; i < this.weights.length; i++){
      this.weights[i] = random(-1,1);
    }
    
  }
  
  //train the perceptron to update the weights !!
  train(inputs, target){
    let guess = this.feedforward(inputs);
    let error = target - guess;
    
    for(let i = 0 ; i < this.weights.length; i++){
      this.weights[i] += error * inputs[i] * this.learning_rate;
    }
    
  }
  
  //compute the output of the perceptron
  feedforward(inputs){
    let sum = 0;
    
    for (let i = 0; i < this.weights.length; i++) {
      sum += inputs[i] * this.weights[i];
    }
    
    return this.activate(sum);
  
  }
  
  //activation function
  activate(input){
    if(input >= 0)
      return 1;
    else
      return -1;
  }
  
  guessLine(x){
    let w0 = this.weights[0];
    let w1 = this.weights[1];
    let w2 = this.weights[2];
    return -(w2 / w1) - (w0/w1) * x;
  }
  
}