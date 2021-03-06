package pl.lodz.p.it.wks.wksrecruiter.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.lodz.p.it.wks.wksrecruiter.collections.Account;
import pl.lodz.p.it.wks.wksrecruiter.exceptions.WKSRecruiterException;
import pl.lodz.p.it.wks.wksrecruiter.services.AccountService;

import java.util.Collection;

@RestController
@RequestMapping(value = "/accounts")
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity getAll() {
        return ResponseEntity.ok(accountService.getAll());
    }

    @CrossOrigin
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity createAccount(@RequestBody Account account) {
        try {
            return ResponseEntity.ok(accountService.createAccount(account));
        } catch (WKSRecruiterException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.toString());
        } catch (Throwable e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(WKSRecruiterException.of(e).toString());
        }
    }

    @RequestMapping(value = "/{login}", method = RequestMethod.PUT)
    public ResponseEntity editRoles(@PathVariable String login, @RequestBody Collection<String> roles) {
        try {
            return ResponseEntity.ok(accountService.editRoles(login, roles));
        } catch (WKSRecruiterException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.toString());
        }
    }

    @RequestMapping(value = "delete/{login}", method = RequestMethod.PUT)
    public ResponseEntity deleteAccount(@PathVariable String login) {
        try {
            return ResponseEntity.ok(accountService.deleteAccount(login));
        } catch (WKSRecruiterException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.toString());
        }
    }

    @CrossOrigin
    @RequestMapping(value = "/edit", method = RequestMethod.PUT)
    public ResponseEntity editAccount(@RequestBody Account account) {
        try {
            return ResponseEntity.ok(accountService.editAccount(account));
        } catch (WKSRecruiterException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.toString());
        } catch (Throwable e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(WKSRecruiterException.of(e).toString());
        }
    }

    @CrossOrigin
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity register(@RequestBody Account account) {
        try {
            return ResponseEntity.ok().body(accountService.register(account));
        } catch (WKSRecruiterException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.toString());
        } catch (Throwable e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(WKSRecruiterException.of(e).toString());
        }
    }
}
