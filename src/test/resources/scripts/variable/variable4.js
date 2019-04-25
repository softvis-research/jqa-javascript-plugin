function fac(n){
  if(n == 0){
  	return 1;
  } else {
  	return n * fac(n-1)
  }
}

console.log(fac(5));

var a = function(){
    var ret = "das ist eine anonyme Funktion die gleich ausgeführt wird";
    return ret
}()

var b = (() => "das ist das gleiche nur mit einer Arrow Function")()

JSON.parse("{a : 1}")

var c = a();

var d = b();

class Rectangle {
    constructor(height, width) {
        this.height = height;
        this.width = width;
    }

    // Method
    calcArea() {
        return this.height * this.width;
    }
}

const square = new Rectangle(10, 10);

console.log(square.calcArea()); // 100
