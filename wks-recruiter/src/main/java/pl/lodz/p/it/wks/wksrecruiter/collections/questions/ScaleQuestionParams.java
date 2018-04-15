package pl.lodz.p.it.wks.wksrecruiter.collections.questions;

public class ScaleQuestionParams extends NumberQuestionParams {

    private double step;

    public ScaleQuestionParams() {}

    public ScaleQuestionParams(double minValue, double maxValue, double step) {
        super(minValue, maxValue);
        this.step = step;
    }

    public double getStep() {
        return step;
    }

    public void setStep(double step) {
        this.step = step;
    }
}
