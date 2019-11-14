var data = [];

var slope_G = 1;
var intercept_G = 0;

function setup() {
  createCanvas(700, 700);
}

function gradientDescent(){
  var learning_rate = 0.05
  for (var i = 0; i< data.length;i++){
    var x = data[i].x;
    var y = data[i].y;
    
    var guess = slope_G * x + intercept_G;
    var error = y - guess;
    
    slope_G += error * x * learning_rate;
    intercept_G += error * learning_rate;
   
  }
  
}

function drawLine(slope, intercept){
  var x1 = 0;
  var y1 = slope * x1 + intercept;
  var x2 = 1;
  var y2 = slope * x2 + intercept;
  
  x1 = map(x1,0,1,0,width);
  y1 = map(y1,0,1,height,0);
  x2 = map(x2,0,1,0,width);
  y2 = map(y2,0,1,height,0);
  
  
  stroke(255,0,0);
  line(x1,y1,x2,y2);
}


function mousePressed(){
  var x = map(mouseX, 0, width, 0, 1);
  var y = map(mouseY,0,height,1,0);
  var point = createVector(x,y);
  data.push(point);
}

function draw() {
  background(225);
  
  for (var i = 0; i < data.length; i++){
    var x = map(data[i].x,0,1,0,width);
    var y = map(data[i].y,0,1,height,0);
    fill(0);
    stroke(0);
    ellipse(x,y,10,10);
    if(data.length > 1)
      gradientDescent();
    drawLine(slope_G,intercept_G);
  }
}