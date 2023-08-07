package br.com.erpsystem.almoxarifado.controllers;

import br.com.erpsystem.almoxarifado.dtos.categoriaProdutoDTO.CategoriaProdutoRequestDTO;
import br.com.erpsystem.almoxarifado.dtos.categoriaProdutoDTO.CategoriaProdutoResponseDTO;
import br.com.erpsystem.almoxarifado.services.CategoriaProdutoService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/almoxarifado/categoria-produto")
public class CategoriaProdutoController {

    private final CategoriaProdutoService categoriaProdutoService;

    public CategoriaProdutoController(CategoriaProdutoService categoriaProdutoService) {
        this.categoriaProdutoService = categoriaProdutoService;
    }

    @Operation(summary = "Lista todas as categorias de produto cadastradas", tags = "Modulo: Almoxarifado -> CategoriaProduto")
    @GetMapping
    public ResponseEntity<List<CategoriaProdutoResponseDTO>> listarTodasCategoriasProduto(){
        return new ResponseEntity<>(categoriaProdutoService.listarTodasCategoriasProduto(), HttpStatus.OK);
    }

    @Operation(summary = "Lista uma categoria de produto usando id", tags = "Modulo: Almoxarifado -> CategoriaProduto")
    @GetMapping(value = "/{id:[\\d]+}")
    public ResponseEntity<CategoriaProdutoResponseDTO> listarCategoriaProdutoPorId(@PathVariable Long id){
        return new ResponseEntity<>(categoriaProdutoService.listarCategoriaProdutoPorId(id), HttpStatus.OK);
    }

    @Operation(summary = "Cria uma categoria de produto", tags = "Modulo: Almoxarifado -> CategoriaProduto")
    @PostMapping
    public ResponseEntity<CategoriaProdutoResponseDTO> criarCategoriaProduto(@RequestBody @Valid CategoriaProdutoRequestDTO categoriaProdutoRequest){
        return new ResponseEntity<>(categoriaProdutoService.criarCategoriaProduto(categoriaProdutoRequest), HttpStatus.CREATED);
    }

    @Operation(summary = "Altera uma categoria de produto usando id", tags = "Modulo: Almoxarifado -> CategoriaProduto")
    @PutMapping(value = "/{id:[\\d]+}")
    public ResponseEntity<CategoriaProdutoResponseDTO> alterarCategoriaProdutoPorId(@PathVariable Long id,
                                                                  @RequestBody @Valid CategoriaProdutoRequestDTO categoriaProdutoRequest){
        return new ResponseEntity<>(categoriaProdutoService.alterarCategoriaProdutoPorId(id, categoriaProdutoRequest), HttpStatus.OK);
    }

    @Operation(summary = "Deleção fisica de uma categoria de produto usando id", tags = "Modulo: Almoxarifado -> CategoriaProduto")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deletarCategoriaProdutoPorId(@PathVariable Long id){
        categoriaProdutoService.deletarCategoriaProdutoPorId(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
