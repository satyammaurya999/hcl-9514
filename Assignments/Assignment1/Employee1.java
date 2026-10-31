public class Employee1 {

    private int empId;
    private String name;
    private String role;

    private double basic;
    private double hra;
    private double ta;
    private double specialAllowance;

    public Employee1(int empId, String name, String role,
                     double basic, double hra,
                     double ta, double specialAllowance) {

        this.empId = empId;
        this.name = name;
        this.role = role;
        this.basic = basic;
        this.hra = hra;
        this.ta = ta;
        this.specialAllowance = specialAllowance;
    }

    public int getEmpId() {
        return empId;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }

    public double getBasic() {
        return basic;
    }

    public double getHra() {
        return hra;
    }

    public double getTa() {
        return ta;
    }

    public double getSpecialAllowance() {
        return specialAllowance;
    }

    public double getBonus() {
        double bonusPercentage = 0;

        switch (role.toLowerCase()) {

            case "manager":
                bonusPercentage = 12;
                break;

            case "admin":
                bonusPercentage = 10;
                break;

            case "engineer":
                bonusPercentage = 12;
                break;

            case "accountant":
                bonusPercentage = 8;
                break;

            default:
                bonusPercentage = 0;
        }

        // Bonus is calculated only on Basic + HRA
        return (basic + hra) * bonusPercentage / 100;
    }
}