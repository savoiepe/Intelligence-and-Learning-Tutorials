function setup() {
  createCanvas(500, 500);
  setLine(0.3,0.2);
  
  points = new Array(100);
  for (let i =0; i< points.length; i++)
    points[i] = new dataPoint(random(-1,1),random(-1,1));
  
  perceptron = new Perceptron(3,2);
}

//add a new point at mouse location
function mousePressed(){
  points.push(new dataPoint(unmapX(mouseX),unmapY(mouseY)));
}

function draw() {
  
  background(255);
  //actual line 
  x1 = mapX(-1);
  x2 = mapX(1);
  y1 = mapY(lineFunction(-1));
  y2 = mapY(lineFunction(1)); 
  line(x2,y2,x1,y1);
  
  //perceptron's line
  stroke(150);
  x1 = mapX(-1);
  x2 = mapX(1);
  y1 = mapY(perceptron.guessLine(-1));
  y2 = mapY(perceptron.guessLine(1)); 
  line(x2,y2,x1,y1);
  
  
  //train the perceptron on each point in the graph
  for(let i = 0 ; i < points.length; i++){
    let inputs = [points[i].x,points[i].y,1];
    let guess = perceptron.feedforward(inputs);
    
    perceptron.train(inputs, points[i].label);
    points[i].show(guess);
  }
}

//coordinates to pixels
function mapX(x){
  return map(x,-1,1,0,width);
} 
function mapY(y){
  return map(y,-1,1,height,0);
}
//pixels to coordinates
function unmapX(x){
  return map(x,0,width,-1,1);
} 
function unmapY(y){
  return map(y,height,0,-1,1);
}