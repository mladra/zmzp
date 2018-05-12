package pl.lodz.p.it.wks.wksrecruiter.controllers;

import com.itextpdf.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pl.lodz.p.it.wks.wksrecruiter.collections.Test;
import pl.lodz.p.it.wks.wksrecruiter.collections.TestAttempt;
import pl.lodz.p.it.wks.wksrecruiter.exceptions.WKSRecruiterException;
import pl.lodz.p.it.wks.wksrecruiter.services.TestService;
import pl.lodz.p.it.wks.wksrecruiter.utils.PdfGeneratorUtil;
import pl.lodz.p.it.wks.wksrecruiter.utils.XlsGeneratorUtil;

import java.io.IOException;
import java.util.Collection;

@RestController
@RequestMapping(value = "/tests")
public class TestController {

    private final TestService testService;
    private final XlsGeneratorUtil xlsGeneratorUtil;
    private final PdfGeneratorUtil pdfGeneratorUtil;

    @Autowired
    public TestController(TestService testService, XlsGeneratorUtil xlsGeneratorUtil, PdfGeneratorUtil pdfGeneratorUtil) {
        this.testService = testService;
        this.xlsGeneratorUtil = xlsGeneratorUtil;
        this.pdfGeneratorUtil = pdfGeneratorUtil;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ResponseEntity createTest(@RequestBody Test test, Authentication authentication){
        try {
            return ResponseEntity.ok(testService.createTest(test, authentication));
        } catch (WKSRecruiterException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.toString());
        } catch (Throwable e ) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(WKSRecruiterException.of(e).toString());
        }
    }

    @RequestMapping(value = "/edit", method = RequestMethod.PUT)
    public ResponseEntity editTest(@RequestBody Test test){
        try {
            return ResponseEntity.ok(testService.editTest(test.getId(), test));
        } catch (WKSRecruiterException ex) {
            return  ResponseEntity.status(HttpStatus.CONFLICT).body(ex.toString());
        } catch (Throwable e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(WKSRecruiterException.of(e).toString());
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

    @RequestMapping(value = "/{testId}/pdf", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity generatePdf(@PathVariable String testId) {
        try {
            Test test = this.testService.getTestById(testId);
            byte[] out = this.pdfGeneratorUtil.generate(test);
            //return file
            HttpHeaders hHeaders = new HttpHeaders();
            hHeaders.add("content-disposition", "attachment; filename=" + test.getName() + ".pdf");
            hHeaders.add("Content-Type", "application/pdf");
            return new ResponseEntity(out, hHeaders, HttpStatus.OK);
        } catch (IOException | DocumentException | WKSRecruiterException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(WKSRecruiterException.of(e));
        }
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity getTests(@RequestParam String role, Authentication authentication) {
        try {
            return ResponseEntity.ok(testService.getTests(role, authentication));
        } catch (WKSRecruiterException e) {
            if (e.getErrors().get(0).getCode().equals("FORBIDDEN")) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.toString());
            }
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.toString());
        }
    }

    @RequestMapping(value = "/{testId}", method = RequestMethod.GET)
    public ResponseEntity getTest(@PathVariable String testId) {
        try {
            return ResponseEntity.ok(testService.getTestById(testId));
        } catch (WKSRecruiterException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.toString());
        } catch (Throwable ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(WKSRecruiterException.of(ex).toString());
        }
    }

    @RequestMapping(value = "/{testId}/xls", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity generateXLS(@PathVariable String testId) {
        try {
            Test test = this.testService.getTestById(testId);
            byte[] out = this.xlsGeneratorUtil.generate(test);
            //return file
            HttpHeaders hHeaders = new HttpHeaders();
            hHeaders.add("content-disposition", "attachment; filename=" + test.getName() + ".xls");
            hHeaders.add("Content-Type", "application/vnd.ms-excel");
            return new ResponseEntity(out, hHeaders, HttpStatus.OK);
        } catch (IOException | WKSRecruiterException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(WKSRecruiterException.of(e));
        }
    }

    @RequestMapping(value = "/solve", method = RequestMethod.POST)
    public ResponseEntity solveTest(@RequestBody TestAttempt testAttempt, Authentication authentication) {
        try {
            return ResponseEntity.ok(testService.solve(testAttempt, authentication));
        } catch (WKSRecruiterException e) {
            if (e.getErrors().get(0).getCode().equals("FORBIDDEN")) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.toString());
            }
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.toString());
        }
    }
}
