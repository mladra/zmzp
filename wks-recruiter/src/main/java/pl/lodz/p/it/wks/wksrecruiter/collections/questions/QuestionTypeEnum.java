package pl.lodz.p.it.wks.wksrecruiter.collections.questions;

public enum QuestionTypeEnum {

    STRING("STRING"),
    SINGLE_CHOICE("SINGLE_CHOICE"),
    MULTIPLE_CHOICE("MULTIPLE_CHOICE"),
    SCALE("SCALE"),
    NUMBER("NUMBER");
    private final String type;
    QuestionTypeEnum(String type) { this.type = type; }
    
    @Override
    public String toString() { return this.type;}
}
