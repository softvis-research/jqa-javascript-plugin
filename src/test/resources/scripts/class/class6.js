/*
    Classes with polymorphism (super)
 */

class Katze{
    constructor(name) {
        this.name = name;
    }

    sprich() {
        console.log(this.name + ' macht ein Geräusch.');
    }
}

class Loewe extends Katze {
    sprich() {
        super.sprich();
        console.log(this.name + ' brüllt.');
    }
}