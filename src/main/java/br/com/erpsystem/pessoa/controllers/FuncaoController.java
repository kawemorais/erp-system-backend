package br.com.erpsystem.pessoa.controllers;

import br.com.erpsystem.pessoa.dtos.funcaoDTO.FuncaoRequestDTO;
import br.com.erpsystem.pessoa.dtos.funcaoDTO.FuncaoResponseDTO;
import br.com.erpsystem.pessoa.services.FuncaoService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/v1/pessoa/funcao")
public class FuncaoController {

    private final FuncaoService funcaoService;

    public FuncaoController(FuncaoService funcaoService) {
        this.funcaoService = funcaoService;
    }


    @Operation(summary = "Listar todas as funções cadastradas", tags = "Modulo: Pessoa -> Funcao")
    @GetMapping
    public ResponseEntity<List<FuncaoResponseDTO>> listarTodosCargos(){
        return new ResponseEntity<>(funcaoService.listarTodasFuncoes(), HttpStatus.OK);
    }

    @Operation(summary = "Lista uma função usando id", tags = "Modulo: Pessoa -> Funcao")
    @GetMapping(value = "/{id}")
    public ResponseEntity<FuncaoResponseDTO> listarFuncaoPorId(@PathVariable Long id){
        return new ResponseEntity<>(funcaoService.listarFuncaoPorId(id), HttpStatus.OK);
    }

    @Operation(summary = "Criar uma função", tags = "Modulo: Pessoa -> Funcao")
    @PostMapping
    public ResponseEntity<FuncaoResponseDTO> criarCargo(@RequestBody @Valid FuncaoRequestDTO funcaoRequest){
        return new ResponseEntity<>(funcaoService.criarFuncao(funcaoRequest), HttpStatus.CREATED);
    }

    @Operation(summary = "Alterar uma função usando id", tags = "Modulo: Pessoa -> Funcao")
    @PutMapping(value = "/{id}")
    public ResponseEntity<FuncaoResponseDTO> alterarFuncaoPorId(@PathVariable Long id,
                                                    @RequestBody @Valid FuncaoRequestDTO funcaoRequest){
        return new ResponseEntity<>(funcaoService.alterarFuncaoPorId(id, funcaoRequest), HttpStatus.OK);
    }

    @Operation(summary = "Deletar uma função usando id", tags = "Modulo: Pessoa -> Funcao")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deletarFuncaoPorId(@PathVariable Long id){
        funcaoService.deletarFuncaoPorId(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
