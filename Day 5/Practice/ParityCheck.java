public class ParityCheck {
    public static void main(String[] args) {
        Check c = (a) -> a % 2 == 0;
        System.out.println(c.m1(20));
        System.out.println(c.m1(7));

    }
}

interface Check{
    boolean m1(int a);
}


