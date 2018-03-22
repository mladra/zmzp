package pl.lodz.p.it.wks.wksrecruiter.collections;

public enum LanguageEnum {
    RU("Pусский"),
    PL("Polski"),
    EN("English");
    private final String value;
    LanguageEnum(final String value) { this.value = value; }
    @Override
    public String toString() { return value; }
}
