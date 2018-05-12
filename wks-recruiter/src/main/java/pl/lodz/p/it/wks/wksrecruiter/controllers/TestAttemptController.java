package pl.lodz.p.it.wks.wksrecruiter.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pl.lodz.p.it.wks.wksrecruiter.collections.TestAttempt;
import pl.lodz.p.it.wks.wksrecruiter.exceptions.WKSRecruiterException;
import pl.lodz.p.it.wks.wksrecruiter.services.MailService;
import pl.lodz.p.it.wks.wksrecruiter.services.TestAttemptService;

import javax.mail.MessagingException;

@RestController
@RequestMapping(value = "/test-attempts")
public class TestAttemptController {

    private final TestAttemptService testAttemptService;
    private final MailService mailService;

    @Autowired
    public TestAttemptController(TestAttemptService testAttemptService, MailService mailService) {
        this.testAttemptService = testAttemptService;
        this.mailService = mailService;
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

    @RequestMapping(method = RequestMethod.PUT, value = "/{login}")
    public ResponseEntity evaluateTestAttempt(@PathVariable String login, @RequestBody TestAttempt testAttempt, Authentication authentication) {
        try {
            return ResponseEntity.ok(testAttemptService.evaluateTestAttempt(login, testAttempt, authentication));
        } catch (WKSRecruiterException exc) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(exc.toString());
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/mail/{email}")
    public ResponseEntity sendMail(@PathVariable String email, @RequestBody TestAttempt testAttempt) {
        try {
            this.mailService.sendMail(email, testAttempt);
            return ResponseEntity.ok().build();
        } catch (WKSRecruiterException exc) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(exc);
        }
    }
}
