public class CustomException {

    static void checkAge(int age) throws AgeInvalidException {

        if (age < 18) {
            throw new AgeInvalidException("Age must be 18 or above");
        }

        System.out.println("You are eligible");
    }

    public static void main(String[] args) {

        try {
            checkAge(10);
        }
        catch (AgeInvalidException e) {
            System.out.println(e.getMessage());
        }
    }
}