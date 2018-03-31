package pl.lodz.p.it.wks.wksrecruiter.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.lodz.p.it.wks.wksrecruiter.collections.Position;
import pl.lodz.p.it.wks.wksrecruiter.exceptions.WKSRecruiterException;
import pl.lodz.p.it.wks.wksrecruiter.services.PositionService;

@RestController
@RequestMapping("/positions")
public class PositionController {

    private final PositionService positionService;

    @Autowired
    public PositionController(PositionService positionService) {
        this.positionService = positionService;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity addPosition(@RequestBody Position position) {
        try {
            return ResponseEntity.ok(positionService.addPosition(position));
        } catch (WKSRecruiterException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.toString());
        } catch (Throwable e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(WKSRecruiterException.of(e).toString());
        }
    }

    @RequestMapping(value = "/{name}", method = RequestMethod.PUT)
    public ResponseEntity modifyPosition(@PathVariable String name, @RequestBody Boolean value) {
        try {
            return ResponseEntity.ok(positionService.modifyPosition(name, value));
        } catch (WKSRecruiterException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.toString());
        }
    }
}
