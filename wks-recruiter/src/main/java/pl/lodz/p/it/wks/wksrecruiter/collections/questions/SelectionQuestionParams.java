package pl.lodz.p.it.wks.wksrecruiter.collections.questions;

import java.util.Collection;

public class SelectionQuestionParams extends AbstractQuestionParams {

    private Collection<String> options;

    public SelectionQuestionParams() {}

    public SelectionQuestionParams(Collection<String> options) {
        this.options = options;
    }

    public Collection<String> getOptions() {
        return options;
    }

    public void setOptions(Collection<String> options) {
        this.options = options;
    }
}
