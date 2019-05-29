/*
    Class with methods (prototype)
 */

class Polygon {
    
    constructor(hoehe, breite) {
        this.hoehe = hoehe;
        this.breite = breite;
    }

    getNMalFlaeche(n){
        return berechneFlaeche() * n;
    }
    
    get flaeche() {
        return this.berechneFlaeche();
    }

    berechneFlaeche() {
        return this.hoehe * this.breite;
    }
}

new Polygon(1,2).flaeche
