/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package lab8;

/**
 *
 * @author kitti
 */
abstract class GeometricObject {
    private String color = "white";
    private boolean filled;

    protected GeometricObject() {
    }

    protected GeometricObject(String color, boolean filled) {
        this.color = color;
        this.filled = filled;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public boolean isFilled() {
        return filled;
    }

    public void setFilled(boolean filled) {
        this.filled = filled;
    }

    public abstract double getArea();

    public abstract double getPerimeter();
}

class Circle extends GeometricObject implements Comparable<Circle> {
    private double radius;

    public Circle() {
    }

    public Circle(double radius) {
        this.radius = radius;
    }

    public Circle(double radius, String color, boolean filled) {
        super(color, filled);
        this.radius = radius;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public double getDiameter() {
        return 2 * radius;
    }

    @Override
    public double getArea() {
        return Math.PI * radius * radius;
    }

    @Override
    public double getPerimeter() {
        return 2 * Math.PI * radius;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Circle)) {
            return false;
        }
        return Double.compare(radius, ((Circle) obj).radius) == 0;
    }

    @Override
    public int hashCode() {
        return Double.hashCode(radius);
    }

    @Override
    public int compareTo(Circle o) {
        return Double.compare(radius, o.radius);
    }

    @Override
    public String toString() {
        return "Circle radius=" + radius;
    }
}

class Rectangle extends GeometricObject implements Comparable<Rectangle> {
    private double width;
    private double height;

    public Rectangle() {
    }

    public Rectangle(double width, double height) {
        this.width = width;
        this.height = height;
    }

    public Rectangle(double width, double height, String color, boolean filled) {
        super(color, filled);
        this.width = width;
        this.height = height;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    @Override
    public double getArea() {
        return width * height;
    }

    @Override
    public double getPerimeter() {
        return 2 * (width + height);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Rectangle)) {
            return false;
        }
        return Double.compare(getArea(), ((Rectangle) obj).getArea()) == 0;
    }

    @Override
    public int hashCode() {
        return Double.hashCode(getArea());
    }

    @Override
    public int compareTo(Rectangle o) {
        return Double.compare(getArea(), o.getArea());
    }

    @Override
    public String toString() {
        return "Rectangle width=" + width + " height=" + height;
    }
}

class Octagon extends GeometricObject implements Comparable<Octagon>, Cloneable {
    private double side;

    public Octagon() {
    }

    public Octagon(double side) {
        this.side = side;
    }

    public double getSide() {
        return side;
    }

    public void setSide(double side) {
        this.side = side;
    }

    @Override
    public double getArea() {
        return (2 + 4 / Math.sqrt(2)) * side * side;
    }

    @Override
    public double getPerimeter() {
        return 8 * side;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Octagon)) {
            return false;
        }
        return Double.compare(getArea(), ((Octagon) obj).getArea()) == 0;
    }

    @Override
    public int hashCode() {
        return Double.hashCode(getArea());
    }

    @Override
    public int compareTo(Octagon o) {
        return Double.compare(getArea(), o.getArea());
    }

    @Override
    public Object clone() {
        try {
            return super.clone();
        } catch (CloneNotSupportedException ex) {
            return null;
        }
    }
}

public class lab805 {
    public static void main(String[] args) {
        Circle c1 = new Circle(5);
        Circle c2 = new Circle(5);
        Circle c3 = new Circle(7);
        System.out.println("Circle c1 equals c2: " + c1.equals(c2));
        System.out.println("Circle c1 equals c3: " + c1.equals(c3));
        System.out.println("Circle c1 compareTo c3: " + c1.compareTo(c3));

        Rectangle r1 = new Rectangle(4, 6);
        Rectangle r2 = new Rectangle(3, 8);
        Rectangle r3 = new Rectangle(2, 12);
        System.out.println("Rectangle r1 equals r2: " + r1.equals(r2));
        System.out.println("Rectangle r1 equals r3: " + r1.equals(r3));
        System.out.println("Rectangle r1 compareTo r2: " + r1.compareTo(r2));
        System.out.println("Rectangle r2 compareTo r3: " + r2.compareTo(r3));

        Octagon o1 = new Octagon(5);
        System.out.println("Octagon area: " + o1.getArea());
        System.out.println("Octagon perimeter: " + o1.getPerimeter());
        Octagon o2 = (Octagon) o1.clone();
        System.out.println("o1 equals o2: " + o1.equals(o2));
        System.out.println("o1 == o2: " + (o1 == o2));
        System.out.println("o1 compareTo o2: " + o1.compareTo(o2));
    }
}