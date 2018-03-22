package pl.lodz.p.it.wks.wksrecruiter.collections;

public enum PositionEnum {
    JUNIOR_DEV("Junior Developer"),
    SENIOR_DEV("Senior Developer"),
    REPOMAN("Repository Master");
    private final String value;
    PositionEnum(final String value) { this.value = value; }
    @Override
    public String toString() { return value; }
}
