interface Animal {
    void sound();   // abstract method
    void eat();
}

class Dog implements Animal {

    @Override
    public void sound() {
        System.out.println("Dog barks");
    }

    @Override
    public void eat() {
        System.out.println("Dog eats food");
    }
}

public class Code {
    public static void main(String[] args) {

        Dog d = new Dog();

        d.sound();
        d.eat();
    }
}