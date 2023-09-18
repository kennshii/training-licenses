package endava.internship.traininglicensessharing.domain.enums;

public enum UserRole {
    ADMIN,
    REVIEWER,
    USER;

    @Override
    public String toString() {
        StringBuilder roleName = new StringBuilder(super.toString().toLowerCase());
        roleName.replace(0, 1, String.valueOf(roleName.charAt(0)).toUpperCase());

        return roleName.toString();
    }
}
