import java.util.*;
public class Code3 {
    public static void main(String[] args) {
        ArrayList<Integer>arr=new ArrayList<>();
        for(int i=0;i<5;i++){
            arr.add(i);

        }
        arr.set(2,5);
        for(Integer x:arr){
            System.out.print(x);
        }

    }
}
