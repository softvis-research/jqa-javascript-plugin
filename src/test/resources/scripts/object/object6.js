var complex = {
    
    ar: [(x) => x, function(){return 1;}, (y) => y + 1],
    nested: {
        f: function fac(n){
            if(n == 0)
                return n;
            else
                return fac(n-1) * n;
        },
        num: 5,
        float: 3.0,
        string: "s",
        null: null
    }
    
};
