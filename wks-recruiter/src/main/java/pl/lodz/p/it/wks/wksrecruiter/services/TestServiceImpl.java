package pl.lodz.p.it.wks.wksrecruiter.services;

import io.jsonwebtoken.lang.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import pl.lodz.p.it.wks.wksrecruiter.collections.Position;
import pl.lodz.p.it.wks.wksrecruiter.collections.RolesEnum;
import pl.lodz.p.it.wks.wksrecruiter.collections.Test;
import pl.lodz.p.it.wks.wksrecruiter.collections.questions.*;
import pl.lodz.p.it.wks.wksrecruiter.exceptions.WKSRecruiterException;
import pl.lodz.p.it.wks.wksrecruiter.repositories.PositionsRepository;
import pl.lodz.p.it.wks.wksrecruiter.repositories.TestsRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class TestServiceImpl implements TestService {

    @Autowired
    private final TestsRepository testsRepository;

    @Autowired
    private final PositionsRepository positionsRepository;

    @Autowired
    public TestServiceImpl(TestsRepository testsRepository, PositionsRepository positionsRepository) {
        this.testsRepository = testsRepository;
        this.positionsRepository = positionsRepository;
    }

    @Override
    public Test addPositionsToTest(Collection<String> positionNames, String testId) throws WKSRecruiterException {
        Optional<Test> test = testsRepository.findById(testId);
        Collection<Position> positions = positionsRepository.findAllByNameIn(positionNames);
        if (positions.isEmpty()) {
            throw WKSRecruiterException.createPositionNotFoundException();
        }
        if (test.isPresent() && test.get().isActive()) {
            test.get().getPositions().addAll(positions);
            return testsRepository.save(test.get());
        } else {
            throw WKSRecruiterException.createTestNotFoundException();
        }
    }

    @Override
    public Test removePositionsFromTest(Collection<String> positionNames, String testId) throws WKSRecruiterException {
        Optional<Test> test = testsRepository.findById(testId);
        Collection<Position> positions = positionsRepository.findAllByNameIn(positionNames);
        if (positions.isEmpty() || positionNames.size() != positions.size()) {
            throw WKSRecruiterException.createPositionNotFoundException();
        }
        if (test.isPresent() && test.get().isActive()) {
            positions.forEach(position -> test.get().getPositions().removeIf(positionToRemove -> positionToRemove.getName().equals(position.getName())));
            return testsRepository.save(test.get());
        } else {
            throw WKSRecruiterException.createTestNotFoundException();
        }
    }

    @Override
    public Test deleteTest(String testId) throws WKSRecruiterException {
        Optional<Test> test = testsRepository.findById(testId);
        if (test.isPresent() && test.get().isActive()) {
            test.get().setActive(false);
            this.testsRepository.save(test.get());
            return test.get();
        } else {
            throw WKSRecruiterException.createTestNotFoundException();
        }
    }

    @Override
    public Iterable<Test> getTests(String role, Authentication authentication) throws WKSRecruiterException {
        if (role.equals(RolesEnum.CAN.toString())) {
            if (authentication.getAuthorities().stream().noneMatch(o -> o.getAuthority().equals(RolesEnum.CAN.toString()))) {
                throw WKSRecruiterException.createAcessDeniedException();
            }
            return testsRepository.findAllByIsActiveIsTrue();
        } else if (role.equals(RolesEnum.EDITOR.toString())) {
            if (authentication.getAuthorities().stream().noneMatch(o -> o.getAuthority().equals(RolesEnum.EDITOR.toString()))) {
                throw WKSRecruiterException.createAcessDeniedException();
            }
            return testsRepository.findAll();
        } else if (role.equals(RolesEnum.MOD.toString())) {
            throw WKSRecruiterException.createAcessDeniedException();
        } else {
            throw WKSRecruiterException.createException("ROLE_NOT_FOUND", "Such role does not exist.");
        }
    }

    @Override
    public Test getTestById(String testId) throws WKSRecruiterException {
        Optional<Test> test = this.testsRepository.findById(testId);
        if (test.isPresent() && test.get().isActive()) return test.get();
        else throw WKSRecruiterException.createTestNotFoundException();
    }

    @Override
    public Test setTestQuestions(String testId, List<QuestionInfo> questions) throws WKSRecruiterException {
        Optional<Test> test = testsRepository.findById(testId);
        List questionTypes = Collections.arrayToList(QuestionTypeEnum.values());
        if (test.isPresent()) {
            for (int i = 0; i < questions.size(); i++) {
                validateQuestion(questionTypes, questions.get(i));
                questions.get(i).setQuestionNumber(i + 1);
            }

            test.get().setQuestions(questions);
            testsRepository.save(test.get());
            return test.get();
        } else {
            throw WKSRecruiterException.createTestNotFoundException();
        }
    }

    private void validateQuestion(List questionTypes, QuestionInfo question) throws WKSRecruiterException {
        WKSRecruiterException exception = new WKSRecruiterException();

        checkQuestionType(questionTypes, question, exception);
        checkQuestionPhrase(question, exception);
        checkQuestionMaxPoints(question, exception);

        switch (question.getType()) {
            case SINGLE_CHOICE:
            case MULTIPLE_CHOICE:
                checkQuestionSelectionOptions(question, exception);
                break;
            case NUMBER:
                checkQuestionNumberParams(question, exception);
                break;
            case SCALE:
                checkQuestionNumberParams(question, exception);
                checkQuestionScaleParams(question, exception);
        }

        if (!exception.getErrors().isEmpty()) {
            throw exception;
        }
    }

    private void checkQuestionType(List questionTypes, QuestionInfo question, WKSRecruiterException exception) {
        if (!questionTypes.contains(question.getType())) {
            exception.add(WKSRecruiterException.Error.createQuestionTypeNotFoundError(question.getType().name()));
        }
    }

    private void checkQuestionPhrase(QuestionInfo question, WKSRecruiterException exception) {
        if (question.getQuestionPhrase() == null || question.getQuestionPhrase().isEmpty()) {
            exception.add(WKSRecruiterException.Error.createQuestionPhraseError());
        }
    }

    private void checkQuestionMaxPoints(QuestionInfo question, WKSRecruiterException exception) {
        if (question.getMaxPoints() <= 0) {
            exception.add(WKSRecruiterException.Error.createQuestionMaxPointsError());
        }
    }

    private void checkQuestionSelectionOptions(QuestionInfo question, WKSRecruiterException exception) {
        SelectionQuestionParams selectionParams = (SelectionQuestionParams) question.getParams();
        if (selectionParams.getOptions() == null || selectionParams.getOptions().isEmpty()) {
            exception.add(WKSRecruiterException.Error.createQuestionSelectionOptionsError());
        }
    }

    private void checkQuestionNumberParams(QuestionInfo question, WKSRecruiterException exception) {
        NumberQuestionParams numberParams = (NumberQuestionParams) question.getParams();
        if (numberParams.getMinValue() > numberParams.getMaxValue()) {
            exception.add(WKSRecruiterException.Error.createQuestionNumberParamsError());
        }
    }

    private void checkQuestionScaleParams(QuestionInfo question, WKSRecruiterException exception) {
        ScaleQuestionParams scaleParams = (ScaleQuestionParams) question.getParams();

        if (scaleParams.getStep() <= 0) {
            exception.add(WKSRecruiterException.Error.createQuestionScaleMinusStepError());
        }

        if ((scaleParams.getStep() > scaleParams.getMaxValue() - scaleParams.getMinValue()) &&
                scaleParams.getMaxValue() >= scaleParams.getMinValue()) {
            exception.add(WKSRecruiterException.Error.createQuestionScaleStepError());
        }
    }
}
