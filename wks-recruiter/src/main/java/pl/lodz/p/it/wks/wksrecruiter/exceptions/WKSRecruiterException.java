package pl.lodz.p.it.wks.wksrecruiter.exceptions;

import javax.validation.ConstraintViolationException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class WKSRecruiterException extends Exception implements Serializable {
    public static class Error {

        private String code;
        private String description;

        public Error() {

        }

        public Error(final String code, final String description) {
            this.code = code;
            this.description = description;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public static Error of(final String code, final String description) {
            return new Error(code, description);
        }

        public static Error createQuestionTypeNotFoundError(String questionType) {
            return new Error(
                    "QUESTION_TYPE_NOT_FOUND",
                    "Question type with name " + questionType + " doesn't exist.");
        }

        public static Error createQuestionPhraseError() {
            return new Error(
                    "QUESTION_PHRASE_EMPTY",
                    "Question phrase cannot be empty.");
        }

        public static Error createQuestionMaxPointsError() {
            return new Error(
                    "INVALID_MAX_POINTS",
                    "Question max points value has to be greater than 0."
            );
        }

        public static Error createQuestionSelectionOptionsError() {
            return new Error(
                    "EMPTY_SELECTION_LIST",
                    "Selection list cannot be empty.");
        }

        public static Error createQuestionNumberParamsError() {
            return new Error(
                    "INVALID_VALUES",
                    "Maximum value has to be greater than minimum value.");
        }

        public static Error createQuestionScaleStepError() {
            return new Error(
                    "INVALID_STEP",
                    "Step has to be lower or equal difference between maximum and minimum value");
        }

        public static Error createQuestionScaleMinusStepError() {
            return new Error(
                    "INVALID_STEP",
                    "Step has to be positive number greater than 0."
            );
        }
    }

    private List<Error> errors = new ArrayList<>();

    public WKSRecruiterException() {

    }

    public WKSRecruiterException(final List<Error> errors) {
        this.errors.addAll(errors);
    }

    public WKSRecruiterException(Error... errors) {
        this.errors.addAll(Arrays.asList(errors));
    }

    public List<Error> getErrors() {
        return Collections.unmodifiableList(errors);
    }

    public void setErrors(final List<Error> errors) {
        this.errors.clear();
        this.errors.addAll(errors);
    }

    public WKSRecruiterException add(Error error) {
        errors.add(error);
        return this;
    }

    public static WKSRecruiterException of(Throwable ex) {
        while (ex.getCause() != null) {
            ex = ex.getCause();
        }
        System.err.println("*** ROOT EXCEPTION: " + ex.toString());
        if (ex instanceof ConstraintViolationException) {
            return new WKSRecruiterException(((ConstraintViolationException) ex).getConstraintViolations().stream().map(v -> new Error("VALIDATION_ERROR", v.getMessage())).collect(Collectors.toList()));
        } else {
            return createException("ERR_001", ex.getMessage());
        }
    }

    public static WKSRecruiterException createException(String code, String description) {
        return new WKSRecruiterException(new WKSRecruiterException.Error(code, description));
    }

    public static WKSRecruiterException createPositionNotFoundException() {
        return createException("POSITION_NOT_FOUND", "Position with such name does not exist.");
    }

    public static WKSRecruiterException createAccountNotFoundException() {
        return createException("ACCOUNT_NOT_FOUND", "Account with such login does not exist.");
    }

    public static WKSRecruiterException createTestNotFoundException() {
        return createException("TEST_NOT_FOUND", "Test with such name does not exist.");
    }

    @Override
    public String toString() {
        StringBuilder message = new StringBuilder();
        for (int i = 0; i < getErrors().size(); i++) {
            message.append(getErrors().get(i).getDescription()).append("\n");
        }
        return message.toString();
    }
}
