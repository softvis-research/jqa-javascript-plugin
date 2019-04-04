/*
    Classes with polymorphism (extends)
 */

class Tier{
    constructor(name) {
        this.name = name;
    }

    sprich() {
        console.log(this.name + ' macht ein Geräusch.');
    }
}

class Hund extends Tier{
    sprich() {
        console.log(this.name + ' bellt.');
    }
}