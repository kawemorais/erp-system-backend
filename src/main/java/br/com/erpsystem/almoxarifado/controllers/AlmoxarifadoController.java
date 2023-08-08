package br.com.erpsystem.almoxarifado.controllers;

import br.com.erpsystem.almoxarifado.dtos.almoxarifadoDTO.AlmoxarifadoRequestDTO;
import br.com.erpsystem.almoxarifado.dtos.almoxarifadoDTO.AlmoxarifadoResponseDTO;
import br.com.erpsystem.almoxarifado.services.AlmoxarifadoService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/almoxarifado/almoxarifado-estoque")
public class AlmoxarifadoController {

    private final AlmoxarifadoService almoxarifadoService;

    public AlmoxarifadoController(AlmoxarifadoService almoxarifadoService) {
        this.almoxarifadoService = almoxarifadoService;
    }

    @Operation(summary = "Lista todas os almoxarifados cadastrados", tags = "Modulo: Almoxarifado -> Almoxarifado Estoque")
    @GetMapping
    public ResponseEntity<List<AlmoxarifadoResponseDTO>> listarTodasCategoriasProduto(){
        return new ResponseEntity<>(almoxarifadoService.listarTodosAlmoxarifados(), HttpStatus.OK);
    }

    @Operation(summary = "Lista todas os almoxarifados cadastrados por status(ativo/inativo)",
            tags = "Modulo: Almoxarifado -> Almoxarifado Estoque")
    @GetMapping(value = "/busca")
    public ResponseEntity<List<AlmoxarifadoResponseDTO>> listarTodasCategoriasProdutoPorStatus(@RequestParam String status){
        return new ResponseEntity<>(almoxarifadoService.listarTodosAlmoxarifadosPorStatus(status), HttpStatus.OK);
    }

    @Operation(summary = "Lista um almoxarifado usando id", tags = "Modulo: Almoxarifado -> Almoxarifado Estoque")
    @GetMapping(value = "/{id:[\\d]+}")
    public ResponseEntity<AlmoxarifadoResponseDTO> listarAlmoxarifadoPorId(@PathVariable Long id){
        return new ResponseEntity<>(almoxarifadoService.listarAlmoxarifadoPorId(id), HttpStatus.OK);
    }

    @Operation(summary = "Cria um almoxarifado", tags = "Modulo: Almoxarifado -> Almoxarifado Estoque")
    @PostMapping
    public ResponseEntity<AlmoxarifadoResponseDTO> criarAlmoxarifado(@RequestBody @Valid AlmoxarifadoRequestDTO almoxarifadoRequest){
        return new ResponseEntity<>(almoxarifadoService.criarAlmoxarifado(almoxarifadoRequest), HttpStatus.CREATED);
    }

    @Operation(summary = "Altera um almoxarifado usando id", tags = "Modulo: Almoxarifado -> Almoxarifado Estoque")
    @PutMapping(value = "/{id:[\\d]+}")
    public ResponseEntity<AlmoxarifadoResponseDTO> alterarCategoriaProdutoPorId(@PathVariable Long id,
                                                                  @RequestBody @Valid AlmoxarifadoRequestDTO almoxarifadoRequest){
        return new ResponseEntity<>(almoxarifadoService.alterarAlmoxarifadoPorId(id, almoxarifadoRequest), HttpStatus.OK);
    }

    @Operation(summary = "Deleção fisica de um almoxarifado usando id", tags = "Modulo: Almoxarifado -> Almoxarifado Estoque")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deletarCategoriaProdutoPorId(@PathVariable Long id){
        almoxarifadoService.deletarAlmoxarifadoPorId(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
