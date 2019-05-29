function fac(n = 0){
  if(n == 0){
  	return 1;
  } else {
  	return n * fac(n-1)
  }
}

var myVar = 3.2

console.log(fac(5));

var a = function(){
    var ret = "das ist eine anonyme Funktion die gleich ausgeführt wird";
    return () => 1
}()

var b = (() => function(){return "das ist das gleiche nur mit einer Arrow Function"})()

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

const square = new Rectangle(110, 0);

console.log(square.calcArea()); // 100

console.log((JSON.parse("1")); // 1

console.log((JSON.parse("'123'")); // 123
