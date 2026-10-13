public class code1 {
    public static void main(String[] args) {
        Box1<String,Integer> b1= new Box1();
        b1.setValue("satyam",23);
        b1.getAge();
        b1.getValue();
    }
}
class Box1<T,L>{
    T name;
    L age;
    void setValue(T name,L age){
        this.name=name;
        this.age=age;
    }
    T getValue(){
        return name;

    }
    L getAge(){
        return age;
    }

}
