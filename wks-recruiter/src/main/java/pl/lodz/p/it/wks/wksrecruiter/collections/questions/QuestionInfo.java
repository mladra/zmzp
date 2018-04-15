package pl.lodz.p.it.wks.wksrecruiter.collections.questions;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

public class QuestionInfo {

    private int questionNumber;
    private String questionPhrase;
    private QuestionTypeEnum type;
    private AbstractQuestionParams params;

    public QuestionInfo(){}

    public QuestionInfo(int questionNumber, String questionPhrase, QuestionTypeEnum type, AbstractQuestionParams params) {
        this.questionNumber = questionNumber;
        this.questionPhrase = questionPhrase;
        this.type = type;
        this.params = params;
    }

    @JsonProperty("params")
    private void retrieveQuestionParams(Map<String,Object> params) {
        switch (this.type) {
            case STRING:
                this.params = null;
                break;
            case SINGLE_CHOICE:
            case MULTIPLE_CHOICE:
                List<String> options = (List<String>) params.get("options");
                this.params = new SelectionQuestionParams(options);
                break;
            case SCALE:
                double minValueScale = convertToDouble("minValue", params);
                double maxValueScale = convertToDouble("maxValue", params);
                double stepScale = convertToDouble("step", params);
                this.params = new ScaleQuestionParams(minValueScale, maxValueScale, stepScale);
                break;
            case NUMBER:
                double minValueNumber = convertToDouble("minValue", params);
                double maxValueNumber = convertToDouble("maxValue", params);
                this.params = new NumberQuestionParams(minValueNumber, maxValueNumber);
                break;
        }
    }

    private double convertToDouble(String key, Map<String,Object> params) {
        Object value = params.get(key);
        if (value instanceof Integer) {
            return ((Integer) value).doubleValue();
        } else {
            return (Double) value;
        }
    }

    public int getQuestionNumber() { return questionNumber; }
    public void setQuestionNumber(int questionNumber) { this.questionNumber = questionNumber; }
    public String getQuestionPhrase() { return questionPhrase; }
    public void setQuestionPhrase(String questionPhrase) { this.questionPhrase = questionPhrase; }
    public QuestionTypeEnum getType() { return type; }
    public void setType(QuestionTypeEnum type) { this.type = type; }
    public AbstractQuestionParams getParams() { return params; }
    public void setParams(AbstractQuestionParams params) { this.params = params; }
}
