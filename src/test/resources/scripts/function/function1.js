/*
    Creates function within a function.
*/

function pow(base, exp) {
    
    var ret = base;
    var iterate = function(){
        for(var i = 1; i < exp; i++){
            ret = ret * ret;
        }
        return ret;
    }
    return iterate();
}

var x = pow(2,2);
