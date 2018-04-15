package pl.lodz.p.it.wks.wksrecruiter.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.lodz.p.it.wks.wksrecruiter.collections.questions.QuestionInfo;
import pl.lodz.p.it.wks.wksrecruiter.exceptions.WKSRecruiterException;
import pl.lodz.p.it.wks.wksrecruiter.services.TestService;

import java.util.List;

@RestController
@RequestMapping("/questions")
public class QuestionController {

    private final TestService testService;

    @Autowired
    public QuestionController(TestService testService) {
        this.testService = testService;
    }

    @RequestMapping(value = "/{testId}", method = RequestMethod.PUT)
    private ResponseEntity setTestQuestions(@PathVariable String testId, @RequestBody List<QuestionInfo> questions) {
        try {
            return ResponseEntity.ok().body(testService.setTestQuestions(testId, questions));
        } catch (WKSRecruiterException exc) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(exc.toString());
        }
    }

}
