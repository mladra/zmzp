package pl.lodz.p.it.wks.wksrecruiter.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pl.lodz.p.it.wks.wksrecruiter.collections.Account;
import pl.lodz.p.it.wks.wksrecruiter.exceptions.AppException;
import pl.lodz.p.it.wks.wksrecruiter.services.AccountService;

@RestController
@RequestMapping(value = "/accounts")
public class AccountController {

    private final AccountService accountService;

    @Autowired
    public AccountController(AccountService accountService) {
        this.accountService = accountService;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/register")
    public ResponseEntity register(@RequestBody Account account) {
        try {
            return ResponseEntity.ok(accountService.register(account));
        } catch (AppException ex) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.toString());
        } catch (Exception e) {
            AppException appex = AppException.of(e);
            return ResponseEntity.status(HttpStatus.CONFLICT).body(appex.toString());
        }
    }
}
