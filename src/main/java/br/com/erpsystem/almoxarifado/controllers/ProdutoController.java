package br.com.erpsystem.almoxarifado.controllers;

import br.com.erpsystem.almoxarifado.dtos.produtoDTO.ProdutoRequestDTO;
import br.com.erpsystem.almoxarifado.dtos.produtoDTO.ProdutoResponseDTO;
import br.com.erpsystem.almoxarifado.services.ImportacaoService;
import br.com.erpsystem.almoxarifado.services.ProdutoService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin()
@RestController
@RequestMapping(value = "/api/v1/almoxarifado/produto")
public class ProdutoController {

    private final ProdutoService produtoService;
    private final ImportacaoService importacaoService;

    public ProdutoController(ProdutoService produtoService, ImportacaoService importacaoService) {
        this.produtoService = produtoService;
        this.importacaoService = importacaoService;
    }

    @Operation(summary = "Lista todos os produtos cadastrados", tags = "Modulo: Almoxarifado -> Produtos")
    @GetMapping
    public ResponseEntity<List<ProdutoResponseDTO>> listarTodosProdutos(){
        return new ResponseEntity<>(produtoService.listarTodosProdutos(), HttpStatus.OK);
    }

    @Operation(summary = "Lista um produto usando id", tags = "Modulo: Almoxarifado -> Produtos")
    @GetMapping(value = "/{id:[\\d]+}")
    public ResponseEntity<ProdutoResponseDTO> listarProdutoPorId(@PathVariable Long id){
        return new ResponseEntity<>(produtoService.listarProdutoPorId(id), HttpStatus.OK);
    }

    @Operation(summary = "Criar um produto", tags = "Modulo: Almoxarifado -> Produtos")
    @PostMapping
    public ResponseEntity<ProdutoResponseDTO> criarProduto(@RequestBody @Valid ProdutoRequestDTO produtoRequest){
        return new ResponseEntity<>(produtoService.criarProduto(produtoRequest), HttpStatus.CREATED);
    }

    @Operation(summary = "Altera um produto usando id", tags = "Modulo: Almoxarifado -> Produtos")
    @PutMapping(value = "/{id}")
    public ResponseEntity<ProdutoResponseDTO> alterarProdutoPorId(@PathVariable Long id,
                                                                  @RequestBody @Valid ProdutoRequestDTO produtoRequest){
        return new ResponseEntity<>(produtoService.alterarProdutoPorId(id, produtoRequest), HttpStatus.OK);
    }

    @Operation(summary = "Deleção logica de um produto usando id", tags = "Modulo: Almoxarifado -> Produtos")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deletarProdutoPorId(@PathVariable Long id){
        produtoService.deletarProdutoPorId(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Importacao de produtos atraves arquivo excel", tags = "Modulo: Almoxarifado -> Produtos")
    @PostMapping("/importar")
    public ResponseEntity<List<ProdutoResponseDTO>> importarProdutosExcel(MultipartFile arquivo){

        List<ProdutoResponseDTO> produtoResponseDTOS = importacaoService.importarProdutosArquivo(arquivo);

        return new ResponseEntity<>(produtoResponseDTOS, HttpStatus.OK);
    }

}
