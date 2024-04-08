package br.com.erpsystem.pessoa.controllers;

import br.com.erpsystem.pessoa.dtos.funcionarioDTO.FuncionarioRequestDTO;
import br.com.erpsystem.pessoa.dtos.funcionarioDTO.FuncionarioResponseDTO;
import br.com.erpsystem.pessoa.services.FuncionarioService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/v1/pessoa/funcionario")
public class FuncionarioController {

    private final FuncionarioService funcionarioService;

    public FuncionarioController(FuncionarioService funcionarioService) {
        this.funcionarioService = funcionarioService;
    }

    @Operation(summary = "Listar todos os funcionarios cadastrados", tags = "Modulo: Pessoa -> Funcionario")
    @GetMapping
    public ResponseEntity<List<FuncionarioResponseDTO>> listarTodosFuncionarios(){
        return new ResponseEntity<>(funcionarioService.listarTodosFuncionarios(), HttpStatus.OK);
    }

    @Operation(summary = "Lista um funcionario usando id", tags = "Modulo: Pessoa -> Funcionario")
    @GetMapping(value = "/{id}")
    public ResponseEntity<FuncionarioResponseDTO> listarFuncionarioPorId(@PathVariable Long id){
        return new ResponseEntity<>(funcionarioService.listarFuncionarioPorId(id), HttpStatus.OK);
    }

    @Operation(summary = "Criar um novo registro de funcionario", tags = "Modulo: Pessoa -> Funcionario")
    @PostMapping
    public ResponseEntity<FuncionarioResponseDTO> criarFuncionario(@RequestBody @Valid FuncionarioRequestDTO funcionarioRequest){
        return new ResponseEntity<>(funcionarioService.criarFuncionario(funcionarioRequest), HttpStatus.CREATED);
    }

    @Operation(summary = "Alterar um funcionario usando id", tags = "Modulo: Pessoa -> Funcionario")
    @PutMapping(value = "/{id}")
    public ResponseEntity<FuncionarioResponseDTO> alterarFuncionarioPorId(@PathVariable Long id,
                                                               @RequestBody @Valid FuncionarioRequestDTO funcionarioRequest){
        return new ResponseEntity<>(funcionarioService.alterarFuncionarioPorId(id, funcionarioRequest), HttpStatus.OK);
    }

}
