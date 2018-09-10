package io.anhkhue.jwtauthoritiesbased.controller;

import io.anhkhue.jwtauthoritiesbased.repository.AccountRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.security.auth.login.AccountNotFoundException;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("accounts")
public class AccountController {

    private final AccountRepository accountRepository;

    public AccountController(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @GetMapping
    public ResponseEntity findAll() {
        return ResponseEntity.status(OK)
                             .body(accountRepository.findAll());
    }

    @GetMapping("{id}")
    public ResponseEntity findById(@PathVariable String id) {
        try {
            return ResponseEntity.status(OK)
                                 .body(accountRepository.findById(Integer.parseInt(id))
                                                        .orElseThrow(AccountNotFoundException::new));
        } catch (AccountNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND)
                                 .build();
        }
    }

}
