package endava.internship.traininglicensessharing.domain.enums;

public enum TimePeriod {
    SECOND,
    SECONDS,
    MINUTE,
    MINUTES,
    HOUR,
    HOURS,
    DAY,
    DAYS,
    WEEK,
    WEEKS,
    MONTH,
    MONTHS,
    YEAR,
    YEARS;

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }
}
