/*
    Class declaration and instantiation
 */

// unnamed
var Polygon = class {
    constructor(height, width) {
        this.height = height;
        this.width = width;
    }
};

// named
var p1 = class Polygon {
    constructor(height, width) {
        this.height = height;
        this.width = width;
    }
};