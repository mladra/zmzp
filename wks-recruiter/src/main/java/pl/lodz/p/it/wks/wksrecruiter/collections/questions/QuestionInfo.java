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
                double minValueScale = (double) params.get("minValue");
                double maxValueScale = (double) params.get("maxValue");
                double stepScale = (double) params.get("step");
                this.params = new ScaleQuestionParams(minValueScale, maxValueScale, stepScale);
                break;
            case NUMBER:
                double minValueNumber = (double) params.get("minValue");
                double maxValueNumber = (double) params.get("maxValue");
                this.params = new NumberQuestionParams(minValueNumber, maxValueNumber);
                break;
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
