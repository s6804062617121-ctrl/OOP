/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab7;

/**
 *
 * @author kitti
 */
class Fruit {}

class Apple extends Fruit {
    void makeApple() {}
}

class Orange extends Fruit {
    void makeOrangeJuice() {}
}

class GoldenDelicious extends Apple {}

class Macintosh extends Apple {}

public class lab703 {
    public static void main(String[] args) {
        Fruit fruit = new GoldenDelicious();
        Orange orange = new Orange();

        System.out.println("1. " + (fruit instanceof Orange));
        System.out.println("2. " + (fruit instanceof Apple));
        System.out.println("3. " + (fruit instanceof GoldenDelicious));
        System.out.println("4. " + (fruit instanceof Macintosh));
        System.out.println("5. " + (orange instanceof Orange));
        System.out.println("6. " + (orange instanceof Fruit));
        System.out.println("7. " + (((Object) orange) instanceof Apple));

        ((Apple) fruit).makeApple();
        orange.makeOrangeJuice();
        ((Orange) fruit).makeOrangeJuice();
    }
}