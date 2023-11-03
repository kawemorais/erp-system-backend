package br.com.erpsystem.almoxarifado.services;

import br.com.erpsystem.almoxarifado.dtos.itemFichaTecnicaDTO.ItemFichaTecnicaRequestDTO;
import br.com.erpsystem.almoxarifado.dtos.itemFichaTecnicaDTO.ItemFichaTecnicaResponseDTO;
import br.com.erpsystem.almoxarifado.models.FichaTecnica;
import br.com.erpsystem.almoxarifado.models.ItemFichaTecnica;
import br.com.erpsystem.almoxarifado.models.Produto;
import br.com.erpsystem.almoxarifado.repositories.FichaTecnicaRepository;
import br.com.erpsystem.almoxarifado.repositories.ItemFichaTecnicaRepository;
import br.com.erpsystem.almoxarifado.repositories.ProdutoRepository;
import br.com.erpsystem.sistema.exception.ExcecaoSolicitacaoInvalida;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ItemFichaTecnicaService {

    private final ItemFichaTecnicaRepository itemFichaTecnicaRepository;

    private final ProdutoRepository produtoRepository;

    private final FichaTecnicaRepository fichaTecnicaRepository;

    private final ModelMapper mapper;

    public ItemFichaTecnicaService(ItemFichaTecnicaRepository itemFichaTecnicaRepository, ProdutoRepository produtoRepository, FichaTecnicaRepository fichaTecnicaRepository, ModelMapper mapper) {
        this.itemFichaTecnicaRepository = itemFichaTecnicaRepository;
        this.produtoRepository = produtoRepository;
        this.fichaTecnicaRepository = fichaTecnicaRepository;
        this.mapper = mapper;
    }

    public ItemFichaTecnicaResponseDTO salvarItemFichaTecnica(ItemFichaTecnicaRequestDTO itemFichaTecnicaRequest){

        Produto produto = retornaProdutoSeExistente(itemFichaTecnicaRequest.getFkProduto());

        FichaTecnica fichaTecnica = retornaFichaTecnicaSeExistente(itemFichaTecnicaRequest.getFkFichaTecnica());

        Double quantidade = itemFichaTecnicaRequest.getQuantidade();

        BigDecimal subTotal = calculaSubTotal(produto.getCustoProduto(), quantidade);

        ItemFichaTecnica itemFichaTecnica = ItemFichaTecnica.builder()
                .produto(produto)
                .fichaTecnica(fichaTecnica)
                .quantidade(quantidade)
                .subTotal(subTotal)
                .build();

        ItemFichaTecnica itemFichaTecnicaSalva = itemFichaTecnicaRepository.save(mapper.map(itemFichaTecnica, ItemFichaTecnica.class));

        return mapper.map(itemFichaTecnicaSalva, ItemFichaTecnicaResponseDTO.class);
    }

    public ItemFichaTecnicaResponseDTO alterarItemFichaTecnicaPorId(Long id, ItemFichaTecnicaRequestDTO itemFichaTecnicaRequest){
        ItemFichaTecnica itemFichaTecnica = itemFichaTecnicaRepository.findById(id)
                .orElseThrow(() -> new ExcecaoSolicitacaoInvalida("Item ficha tecnica não encontrado"));

        Produto produto = retornaProdutoSeExistente(itemFichaTecnicaRequest.getFkProduto());
        Double quantidade = itemFichaTecnicaRequest.getQuantidade();

        itemFichaTecnica.setProduto(produto);
        itemFichaTecnica.setQuantidade(quantidade);
        itemFichaTecnica.setSubTotal(calculaSubTotal(produto.getCustoProduto(), quantidade));

        ItemFichaTecnica itemFichaTecnicaSalva = itemFichaTecnicaRepository.save(itemFichaTecnica);

        return mapper.map(itemFichaTecnicaSalva, ItemFichaTecnicaResponseDTO.class);
    }

    private Produto retornaProdutoSeExistente(Long fkProduto){
        return produtoRepository.findById(fkProduto)
                .orElseThrow(() -> new ExcecaoSolicitacaoInvalida("Produto invalido"));
    }

    private FichaTecnica retornaFichaTecnicaSeExistente(Long fkFichaTecnica){
        return fichaTecnicaRepository.findById(fkFichaTecnica)
                .orElseThrow(() -> new ExcecaoSolicitacaoInvalida("Ficha tecnica invalido"));
    }

    private BigDecimal calculaSubTotal(BigDecimal valorProduto, Double quantidade){
        return valorProduto.multiply(BigDecimal.valueOf(quantidade));
    }


}
