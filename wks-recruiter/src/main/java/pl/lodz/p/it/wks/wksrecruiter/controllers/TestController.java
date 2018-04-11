package pl.lodz.p.it.wks.wksrecruiter.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.lodz.p.it.wks.wksrecruiter.collections.questions.QuestionInfo;
import pl.lodz.p.it.wks.wksrecruiter.exceptions.WKSRecruiterException;
import pl.lodz.p.it.wks.wksrecruiter.services.TestService;

import java.util.Collection;

@RestController
@RequestMapping(value = "/tests")
public class TestController {

    private final TestService testService;

    @Autowired
    public TestController(TestService testService) {
        this.testService = testService;
    }

    @RequestMapping(value = "/addQuestions/{testId}", method = RequestMethod.PUT)
    public ResponseEntity addQuestions(@PathVariable String testId, @RequestBody Collection<QuestionInfo> questions) {
        try {
            return ResponseEntity.ok(testService.addQuestionsToTest(testId, questions));
        } catch (WKSRecruiterException exception) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(exception.toString());
        } catch (Throwable throwable) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(throwable.toString());
        }
    }

    @RequestMapping(value = "/modifyQuestions/{testId}", method = RequestMethod.PUT)
    public ResponseEntity modifyQuestions(@PathVariable String testId, @RequestBody Collection<QuestionInfo> questions) {
        try {
            return ResponseEntity.ok(testService.modifyQuestionsInTest(testId, questions));
        } catch (WKSRecruiterException exception) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(exception.toString());
        } catch (Throwable throwable) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(throwable.toString());
        }
    }

    @RequestMapping(value = "/removeQuestion/{testId}", method = RequestMethod.PUT)
    public ResponseEntity removeQuestions(@PathVariable String testId, @RequestBody Collection<QuestionInfo> questions) {
        try {
            return ResponseEntity.ok(testService.removeQuestionsFromTest(testId, questions));
        } catch (WKSRecruiterException exception) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(exception.toString());
        } catch (Throwable throwable) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(throwable.toString());
        }
    }

    @RequestMapping(value = "/addPosition/{testId}", method = RequestMethod.PUT)
    public ResponseEntity addPosition(@PathVariable String testId, @RequestBody Collection<String> positionNames) {
        try {
            return ResponseEntity.ok(testService.addPositionsToTest(positionNames, testId));
        } catch (WKSRecruiterException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.toString());
        } catch (Throwable ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(WKSRecruiterException.of(ex).toString());
        }
    }

    @RequestMapping(value = "/removePosition/{testId}", method = RequestMethod.PUT)
    public ResponseEntity removePosition(@PathVariable String testId, @RequestBody Collection<String> positionNames) {
        try {
            return ResponseEntity.ok(testService.removePositionsFromTest(positionNames, testId));
        } catch (WKSRecruiterException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.toString());
        } catch (Throwable ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(WKSRecruiterException.of(ex).toString());
        }
    }

    @RequestMapping(value = "/{testId}", method = RequestMethod.DELETE)
    public ResponseEntity removeTest(@PathVariable String testId) {
        try {
            return ResponseEntity.ok(testService.deleteTest(testId));
        } catch (WKSRecruiterException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.toString());
        } catch (Throwable ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(WKSRecruiterException.of(ex).toString());
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity getTests() {
        return ResponseEntity.ok(testService.getTests());
    }

    @RequestMapping(value = "/{testId}", method = RequestMethod.GET)
    public ResponseEntity getTest(@PathVariable String testId) {
        try {
            return ResponseEntity.ok(testService.getTest(testId));
        } catch (WKSRecruiterException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.toString());
        } catch (Throwable ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(WKSRecruiterException.of(ex).toString());
        }
    }
}
