package br.com.erpsystem.pessoa.controllers;

import br.com.erpsystem.pessoa.dtos.setorDTO.SetorRequestDTO;
import br.com.erpsystem.pessoa.dtos.setorDTO.SetorResponseDTO;
import br.com.erpsystem.pessoa.services.SetorService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/v1/pessoa/setor")
public class SetorController {

    private final SetorService setorService;

    public SetorController(SetorService setorService) {
        this.setorService = setorService;
    }

    @Operation(summary = "Listar todos os setores cadastrados", tags = "Modulo: Pessoa -> Setor")
    @GetMapping
    public ResponseEntity<List<SetorResponseDTO>> listarTodosSetores(){
        return new ResponseEntity<>(setorService.listarTodosSetores(), HttpStatus.OK);
    }

    @Operation(summary = "Lista um setor usando id", tags = "Modulo: Pessoa -> Setor")
    @GetMapping(value = "/{id}")
    public ResponseEntity<SetorResponseDTO> listarSetorPorId(@PathVariable Long id){
        return new ResponseEntity<>(setorService.listarSetorPorId(id), HttpStatus.OK);
    }

    @Operation(summary = "Criar um setor", tags = "Modulo: Pessoa -> Setor")
    @PostMapping
    public ResponseEntity<SetorResponseDTO> criarSetor(@RequestBody @Valid SetorRequestDTO setorRequest){
        return new ResponseEntity<>(setorService.criarSetor(setorRequest), HttpStatus.CREATED);
    }

    @Operation(summary = "Alterar um setor usando id", tags = "Modulo: Pessoa -> Setor")
    @PutMapping(value = "/{id}")
    public ResponseEntity<SetorResponseDTO> alterarSetorPorId(@PathVariable Long id,
                                                               @RequestBody @Valid SetorRequestDTO setorRequest){
        return new ResponseEntity<>(setorService.alterarSetorPorId(id, setorRequest), HttpStatus.OK);
    }

    @Operation(summary = "Deletar uma função usando id", tags = "Modulo: Pessoa -> Setor")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deletarFuncaoPorId(@PathVariable Long id){
        setorService.deletarSetorPorId(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
