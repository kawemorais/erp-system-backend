package br.com.erpsystem.almoxarifado.controllers;

import br.com.erpsystem.almoxarifado.dtos.unidadeDTO.UnidadeRequestDTO;
import br.com.erpsystem.almoxarifado.dtos.unidadeDTO.UnidadeResponseDTO;
import br.com.erpsystem.almoxarifado.services.UnidadeService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin()
@RestController
@RequestMapping(value = "/api/v1/almoxarifado/unidade")
public class UnidadeController {

    private final UnidadeService unidadeService;

    public UnidadeController(UnidadeService unidadeService) {
        this.unidadeService = unidadeService;
    }

    @Operation(summary = "Lista todas as unidades cadastradas", tags = "Modulo: Almoxarifado -> Unidades")
    @GetMapping
    public ResponseEntity<List<UnidadeResponseDTO>> listarTodasUnidades(){
        return new ResponseEntity<>(unidadeService.listarTodasUnidades(), HttpStatus.OK);
    }

    @Operation(summary = "Lista uma unidade usando id", tags = "Modulo: Almoxarifado -> Unidades")
    @GetMapping(value = "/{id:[\\d]+}")
    public ResponseEntity<UnidadeResponseDTO> listarUnidadePorId(@PathVariable Long id){
        return new ResponseEntity<>(unidadeService.listarUnidadePorId(id), HttpStatus.OK);
    }

    @Operation(summary = "Cria uma unidade", tags = "Modulo: Almoxarifado -> Unidades")
    @PostMapping
    public ResponseEntity<UnidadeResponseDTO> criarUnidade(@RequestBody @Valid UnidadeRequestDTO unidadeRequest){
        return new ResponseEntity<>(unidadeService.criarUnidade(unidadeRequest), HttpStatus.CREATED);
    }

    @Operation(summary = "Altera uma unidade usando id", tags = "Modulo: Almoxarifado -> Unidades")
    @PutMapping(value = "/{id}")
    public ResponseEntity<UnidadeResponseDTO> alterarUnidadePorId(@PathVariable Long id,
                                                                  @RequestBody @Valid UnidadeRequestDTO unidadeRequest){
        return new ResponseEntity<>(unidadeService.alterarUnidadePorId(id, unidadeRequest), HttpStatus.OK);
    }

    @Operation(summary = "Deleção fisica de uma unidade usando id", tags = "Modulo: Almoxarifado -> Unidades")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deletarUnidadePorId(@PathVariable Long id){
        unidadeService.deletarUnidadePorId(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
