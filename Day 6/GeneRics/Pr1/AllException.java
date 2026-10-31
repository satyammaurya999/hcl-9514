
//public class AllException {
//    public static void main(String[] args) {
//        int a=10;
//        int b=0;
//        try{
//            System.out.println(a/b);
//        }catch (ArithmeticException e){
//            System.out.println(e);
//        }finally {
//            System.out.println("Kya btt hai!!!");
//        }
//    }
//}
import java.io.FileReader;
import java.io.IOException;

public class AllException {
    public static void main(String[] args) {

        try {
            FileReader file = new FileReader("data.txt");

            System.out.println("File opened successfully");

            file.close();

        } catch (IOException e) {
            System.out.println("File not found or cannot be opened");
        }
    }
}
