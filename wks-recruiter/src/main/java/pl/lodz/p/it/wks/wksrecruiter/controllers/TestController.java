package pl.lodz.p.it.wks.wksrecruiter.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.lodz.p.it.wks.wksrecruiter.collections.Test;
import pl.lodz.p.it.wks.wksrecruiter.exceptions.WKSRecruiterException;
import pl.lodz.p.it.wks.wksrecruiter.services.TestService;
import pl.lodz.p.it.wks.wksrecruiter.utils.XlsGeneratorUtil;

import java.util.Collection;

@RestController
@RequestMapping(value = "/tests")
public class TestController {

    private final TestService testService;
    
    @Autowired
    private XlsGeneratorUtil xlsGeneratorUtil;

    @Autowired
    public TestController(TestService testService) {
        this.testService = testService;
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
    
    @RequestMapping(value = "/{testId}/xls",method = RequestMethod.GET)
    public @ResponseBody ResponseEntity generateXLS(@PathVariable String testId) {
        try {
            Test test = this.testService.getTestById(testId);
            byte[] out = this.xlsGeneratorUtil.generate(test);
            //return file
            HttpHeaders hHeaders = new HttpHeaders();
            hHeaders.add("content-disposition", "attachment; filename=" + test.getName()+".pdf");
            hHeaders.add("Content-Type","application/pdf");
            return new ResponseEntity(out,hHeaders,HttpStatus.OK);
        } catch (WKSRecruiterException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(WKSRecruiterException.of(e));
        }
    }
}
