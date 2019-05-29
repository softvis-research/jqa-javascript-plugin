/*
    Function which invokes a second function right above. Finally we determine the degree celsius out of 500 degree Fahrenheit.
*/

function answer(x){
    return "\"The temperature is " + x + " Celsius";
}

function toCelsius(fahrenheit) {
    var x = (5/9) * (fahrenheit-32);
    return answer(x);

}

var y = toCelsius(500);