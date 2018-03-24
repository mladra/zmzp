package pl.lodz.p.it.wks.wksrecruiter.collections;

public enum RolesEnum {
    CAN("Candidate"),
    ADMIN("Administrator"),
    MOD("Moderator");
    private final String value;
    RolesEnum(final String value) { this.value = value; }
    @Override
    public String toString() { return value; }
}
