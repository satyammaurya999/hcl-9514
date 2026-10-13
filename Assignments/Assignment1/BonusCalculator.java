public class BonusCalculator implements BonusPolicy {

    @Override
    public double calculateBonus(Employee1 employee) {

        double percentage;

        switch (employee.getRole().toLowerCase()) {

            case "manager":
                percentage = 12;
                break;

            case "admin":
                percentage = 10;
                break;

            case "engineer":
                percentage = 12;
                break;

            case "accountant":
                percentage = 8;
                break;

            default:
                percentage = 0;
        }

        // Only Basic + HRA are considered
        double eligibleSalary = employee.getBasic() + employee.getHra();

        return eligibleSalary * percentage / 100;
    }
}