package pl.lodz.p.it.wks.wksrecruiter.collections;

import java.util.HashSet;

public enum RolesEnum {
    CAN("Candidate"),
    EDITOR("Editor"),
    MOD("Moderator");
    private final String value;
    RolesEnum(final String value) { this.value = value; }
    @Override
    public String toString() { return value; }

    public static HashSet<String> getEnums() {
        HashSet<String> values = new HashSet<>();
        for (RolesEnum rolesEnum : RolesEnum.values()) {
            values.add(rolesEnum.value);
        }
        return values;
    }
}
