package pl.lodz.p.it.wks.wksrecruiter.collections.questions;

public class NumberQuestionParams extends AbstractQuestionParams {

    private double minValue;
    private double maxValue;

    public NumberQuestionParams() {}

    public NumberQuestionParams(double minValue, double maxValue) {
        this.minValue = minValue;
        this.maxValue = maxValue;
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
}
