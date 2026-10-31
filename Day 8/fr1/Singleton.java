class Singleton {
    private static Singleton obj;
    private Singleton() {
        System.out.println("object created");
    }
    public static Singleton getInstance() {
        if (obj == null) {
            obj = new Singleton();
        }
        return obj;
    }

}