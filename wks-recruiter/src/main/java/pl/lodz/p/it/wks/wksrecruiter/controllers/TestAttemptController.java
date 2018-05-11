package pl.lodz.p.it.wks.wksrecruiter.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pl.lodz.p.it.wks.wksrecruiter.collections.TestAttempt;
import pl.lodz.p.it.wks.wksrecruiter.exceptions.WKSRecruiterException;
import pl.lodz.p.it.wks.wksrecruiter.services.TestAttemptService;

@RestController
@RequestMapping(value = "/test-attempts")
public class TestAttemptController {

    private final TestAttemptService testAttemptService;

    @Autowired
    public TestAttemptController(TestAttemptService testAttemptService) {
        this.testAttemptService = testAttemptService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity getUserAttempts(Authentication authentication) {
        try {
            return ResponseEntity.ok(this.testAttemptService.getTestAttempts(authentication));
        } catch (WKSRecruiterException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.toString());
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/all")
    public ResponseEntity getAllTestsAttempts(Authentication authentication) {
        try {
            return ResponseEntity.ok(this.testAttemptService.getAllTestsAttempts(authentication));
        } catch (WKSRecruiterException exc) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(exc.toString());
        }
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity evaluateTestAttempt(@RequestBody TestAttempt testAttempt, Authentication authentication) {
        try {
            testAttemptService.evaluateTestAttempt(testAttempt, authentication);
            return ResponseEntity.ok().build();
        } catch (WKSRecruiterException exc) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(exc.toString());
        }
    }
}
