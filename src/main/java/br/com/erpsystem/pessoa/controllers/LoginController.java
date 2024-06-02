package br.com.erpsystem.pessoa.controllers;

import br.com.erpsystem.pessoa.dtos.loginDTO.LoginRequestDTO;
import br.com.erpsystem.pessoa.dtos.loginDTO.LoginResponseDTO;
import br.com.erpsystem.pessoa.services.LoginService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/v1/pessoa/auth/login")
public class LoginController {

    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @Operation(summary = "Fazer login", tags = "Modulo: Pessoa -> Login")
    @PostMapping
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequest){
        return new ResponseEntity<>(loginService.login(loginRequest), HttpStatus.ACCEPTED);
    }
}
