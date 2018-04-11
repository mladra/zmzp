package pl.lodz.p.it.wks.wksrecruiter.collections.questions;

public class ScaleQuestionParams extends AbstractQuestionParams {

    private double minValue;
    private double maxValue;
    private double step;

    public ScaleQuestionParams() {}

    public ScaleQuestionParams(double minValue, double maxValue, double step) {
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.step = step;
    }

    public double getMinValue() {
        return minValue;
    }

    public void setMinValue(double minValue) {
        this.minValue = minValue;
    }

    public double getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(double maxValue) {
        this.maxValue = maxValue;
    }

    public double getStep() {
        return step;
    }

    public void setStep(double step) {
        this.step = step;
    }
}
