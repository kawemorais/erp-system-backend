package br.com.erpsystem.almoxarifado.controllers;

import br.com.erpsystem.almoxarifado.dtos.unidadeDTO.UnidadeRequestDTO;
import br.com.erpsystem.almoxarifado.dtos.unidadeDTO.UnidadeResponseDTO;
import br.com.erpsystem.almoxarifado.services.UnidadeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/almoxarifado/unidade")
public class UnidadeController {

    private final UnidadeService unidadeService;

    public UnidadeController(UnidadeService unidadeService) {
        this.unidadeService = unidadeService;
    }

    @GetMapping
    public ResponseEntity<List<UnidadeResponseDTO>> listarTodasUnidades(){
        return new ResponseEntity<>(unidadeService.listarTodasUnidades(), HttpStatus.OK);
    }

    @GetMapping(value = "/{id:[\\d]+}")
    public ResponseEntity<UnidadeResponseDTO> listarUnidadePorId(@PathVariable Long id){
        return new ResponseEntity<>(unidadeService.listarUnidadePorId(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UnidadeResponseDTO> criarUnidade(@RequestBody @Valid UnidadeRequestDTO unidadeRequest){
        return new ResponseEntity<>(unidadeService.criarUnidade(unidadeRequest), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<UnidadeResponseDTO> alterarUnidadePorId(@PathVariable Long id,
                                                                  @RequestBody @Valid UnidadeRequestDTO unidadeRequest){
        return new ResponseEntity<>(unidadeService.alterarUnidadePorId(id, unidadeRequest), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<?> deletarUnidadePorId(@PathVariable Long id){
        return new ResponseEntity<>(unidadeService.deletarUnidadePorId(id), HttpStatus.OK);
    }
}
