//Separation line
let slope = 0;
let intercept = 0;
function setLine(slope_, intercept_){
  slope = slope_;
  intercept = intercept_;
}
function lineFunction(x) {
  return slope * x + intercept;
}


class dataPoint {

  constructor(x, y) {
    this.x = x;
    this.y = y;
    this.label;

    if (this.y > lineFunction(this.x))
      this.label = -1;
    else
      this.label = 1;
  }

  pixelX() {
    return map(this.x, -1, 1, 0, width);
  }

  pixelY() {
    return map(this.y, -1, 1, height, 0);
  }

  show(guess) {
    stroke(0);
    if (guess == 1)
      fill(0, 255, 0);
    else
      fill(255, 0, 0);

    ellipse(this.pixelX(), this.pixelY(), 10, 10);
  }

}