package br.com.erpsystem.almoxarifado.controllers;

import br.com.erpsystem.almoxarifado.dtos.estoqueProdutoDTO.EstoqueProdutoAddQuantidadeRequestDTO;
import br.com.erpsystem.almoxarifado.dtos.estoqueProdutoDTO.EstoqueProdutoAtualizarRequestDTO;
import br.com.erpsystem.almoxarifado.dtos.estoqueProdutoDTO.EstoqueProdutoRequestDTO;
import br.com.erpsystem.almoxarifado.dtos.estoqueProdutoDTO.EstoqueProdutoResponseDTO;
import br.com.erpsystem.almoxarifado.services.EstoqueProdutoService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin()
@RestController
@RequestMapping(value = "/api/v1/almoxarifado/estoque-produto")
public class EstoqueProdutoController {

    private final EstoqueProdutoService estoqueProdutoService;


    public EstoqueProdutoController(EstoqueProdutoService estoqueProdutoService) {
        this.estoqueProdutoService = estoqueProdutoService;
    }

    @Operation(summary = "Lista todos os produtos em estoque por almoxarifado ou produto", tags = "Modulo: Almoxarifado -> Estoque Produto")
    @GetMapping()
    public ResponseEntity<List<EstoqueProdutoResponseDTO>> listarTodosProdutosEstoquePorAlmoxarifado(@RequestParam String parametro,
                                                                                                     @RequestParam Long fkParametro){
        return new ResponseEntity<>(estoqueProdutoService.listarTodosEstoquesPorParametro(parametro, fkParametro), HttpStatus.OK);
    }

    @Operation(summary = "Busca estoque produto por id", tags = "Modulo: Almoxarifado -> Estoque Produto")
    @GetMapping(value = "{id}")
    public ResponseEntity<EstoqueProdutoResponseDTO> buscarEstoqueProdutoPorId(@PathVariable Long id){
        return new ResponseEntity<>(estoqueProdutoService.buscarEstoqueProdutoPorId(id), HttpStatus.OK);
    }

    @Operation(summary = "Salva novo estoque produto", tags = "Modulo: Almoxarifado -> Estoque Produto")
    @PostMapping()
    public ResponseEntity<EstoqueProdutoResponseDTO> criarNovoEstoqueProduto(@RequestBody
                                                                @Valid EstoqueProdutoRequestDTO estoqueProdutoRequest){
        return new ResponseEntity<>(estoqueProdutoService.criarNovoEstoqueProduto(estoqueProdutoRequest), HttpStatus.CREATED);
    }

    @Operation(summary = "Altera estoque produto por id", tags = "Modulo: Almoxarifado -> Estoque Produto")
    @PutMapping(value = "/alterar/{id}")
    public ResponseEntity<EstoqueProdutoResponseDTO> alterarEstoqueProdutoPorId(@PathVariable Long id,
                                                    @RequestBody @Valid EstoqueProdutoAtualizarRequestDTO estoqueProdutoRequest){
        return new ResponseEntity<>(estoqueProdutoService.atualizarEstoqueProdutoPorId(id, estoqueProdutoRequest), HttpStatus.OK);
    }

    @Operation(summary = "Adiciona quantidade ao estoque produto por id", tags = "Modulo: Almoxarifado -> Estoque Produto")
    @PutMapping(value = "/add-qtd/{id}")
    public ResponseEntity<EstoqueProdutoResponseDTO> adicionaQuantidadeEstoqueProdutoPorId(@PathVariable Long id,
                                                                                @RequestBody @Valid EstoqueProdutoAddQuantidadeRequestDTO estoqueProdutoRequest){
        return new ResponseEntity<>(estoqueProdutoService.adicionarQuantidadeEstoque(id, estoqueProdutoRequest), HttpStatus.OK);
    }

    @Operation(summary = "Deleção fisica de um estoque produto usando id", tags = "Modulo: Almoxarifado -> Estoque Produto")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deletarAlmoxarifadoPorId(@PathVariable Long id){
        estoqueProdutoService.deletarEstoqueProdutoPorId(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
