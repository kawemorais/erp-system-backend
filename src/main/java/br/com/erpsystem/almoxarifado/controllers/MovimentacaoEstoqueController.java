package br.com.erpsystem.almoxarifado.controllers;

import br.com.erpsystem.almoxarifado.dtos.movimentacaoEstoqueDTO.MovimentacaoEstoqueResponseDTO;
import br.com.erpsystem.almoxarifado.services.MovimentacaoEstoqueService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/almoxarifado/movimentacao-estoque")
public class MovimentacaoEstoqueController {

    private final MovimentacaoEstoqueService movimentacaoEstoqueService;

    public MovimentacaoEstoqueController(MovimentacaoEstoqueService movimentacaoEstoqueService) {
        this.movimentacaoEstoqueService = movimentacaoEstoqueService;
    }

    @Operation(summary = "Lista todas as movimentacoes de todos estoques", tags = "Modulo: Almoxarifado -> Movimentacao Estoque")
    @GetMapping("/")
    public ResponseEntity<List<MovimentacaoEstoqueResponseDTO>> listarTodasMovimentacaoEstoque(){
        return new ResponseEntity<>(movimentacaoEstoqueService.listarTodasMovimentacaoEstoque(), HttpStatus.OK);
    }

    @Operation(summary = "Lista todas as movimentacoes de estoque por produto", tags = "Modulo: Almoxarifado -> Movimentacao Estoque")
    @GetMapping()
    public ResponseEntity<List<MovimentacaoEstoqueResponseDTO>> listarTodasMovimentacaoEstoquePorProduto(@RequestParam Long fkProduto){
        return new ResponseEntity<>(movimentacaoEstoqueService.listarMovimentacaoEstoquePorProduto(fkProduto), HttpStatus.OK);
    }

}
