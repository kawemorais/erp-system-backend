package br.com.erpsystem.almoxarifado.services;

import br.com.erpsystem.almoxarifado.dtos.produtoDTO.ProdutoImportacaoDTO;
import br.com.erpsystem.almoxarifado.dtos.produtoDTO.ProdutoResponseDTO;
import br.com.erpsystem.almoxarifado.models.CategoriaProduto;
import br.com.erpsystem.almoxarifado.models.Produto;
import br.com.erpsystem.almoxarifado.models.Unidade;
import br.com.erpsystem.pessoa.models.Fornecedor;
import br.com.erpsystem.pessoa.services.FornecedorService;
import br.com.erpsystem.sistema.exception.ExcecaoSolicitacaoInvalida;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ImportacaoService {

    private final ProdutoService produtoService;
    private final UnidadeService unidadeService;
    private final CategoriaProdutoService categoriaProdutoService;
    private final FornecedorService fornecedorService;

    public ImportacaoService(ProdutoService produtoService, UnidadeService unidadeService, CategoriaProdutoService categoriaProdutoService, FornecedorService fornecedorService) {
        this.produtoService = produtoService;
        this.unidadeService = unidadeService;
        this.categoriaProdutoService = categoriaProdutoService;
        this.fornecedorService = fornecedorService;
    }

    public List<ProdutoResponseDTO> importarProdutosArquivo(MultipartFile arquivo) {

        List<ProdutoImportacaoDTO> listaProdutoImportacao = new ArrayList<>();

        try {
            Workbook planilhaExcel = new XSSFWorkbook(arquivo.getInputStream());

            Sheet planilha = planilhaExcel.getSheetAt(0);

            int numeroDaLinha = 0;

            for (Row linha : planilha) {

                if(numeroDaLinha == 0){
                    numeroDaLinha += 1;
                    continue;
                }

                List<String> dadosLinha = new ArrayList<>();

                for (Cell celula : linha) {
                    dadosLinha.add(selecionaValorCelula(celula));
                }

                Double peso = null;

                if(!Objects.equals(dadosLinha.get(6), "-")){
                    peso = Double.parseDouble(dadosLinha.get(6));
                }

                ProdutoImportacaoDTO produtoImportacao = ProdutoImportacaoDTO.builder()
                        .codigo(dadosLinha.get(0))
                        .nome(dadosLinha.get(1))
                        .nomeUnidade(dadosLinha.get(2))
                        .nomeCategoriaProduto(dadosLinha.get(3))
                        .cnpjFornecedor(dadosLinha.get(4))
                        .descricao(dadosLinha.get(5))
                        .peso(peso)
                        .build();

                listaProdutoImportacao.add(produtoImportacao);
            }
            List<Produto> produtos = validaInformacoes(listaProdutoImportacao);

            return produtoService.criarProdutosPorImportacao(produtos);

        } catch (IOException e) {
            throw new ExcecaoSolicitacaoInvalida("Houve um erro com o arquivo\n" + e.getMessage());
        }
    }

    private String selecionaValorCelula(Cell celula) {
        return switch (celula.getCellType()) {
            case STRING -> celula.getStringCellValue();
            case NUMERIC -> String.valueOf(celula.getNumericCellValue());
            case BOOLEAN -> String.valueOf(celula.getBooleanCellValue());
            default -> "";
        };
    }

    private List<Produto> validaInformacoes(List<ProdutoImportacaoDTO> lista){

        List<Produto> listaProduto = new ArrayList<>();



        lista.forEach(produtoImportacao -> {
            Fornecedor fornecedor = null;

            produtoService.verificaCodigoProdutoExistente(produtoImportacao.getCodigo());

            Unidade unidade = unidadeService.retornaUnidadeSeExistentePorNome(produtoImportacao.getNomeUnidade());

            CategoriaProduto categoriaProduto = categoriaProdutoService
                    .retornaCategoriaProdutoSeExistentePorNome(produtoImportacao.getNomeCategoriaProduto());

            if(!produtoImportacao.getCnpjFornecedor().equals("-")){
                fornecedor = fornecedorService.retornaFornecedorSeExistentePorCnpj(produtoImportacao.getCnpjFornecedor());
            }

            Produto novoProduto = Produto.builder()
                    .codigo(produtoImportacao.getCodigo())
                    .nome(produtoImportacao.getNome())
                    .unidade(unidade)
                    .categoriaProduto(categoriaProduto)
                    .fornecedor(fornecedor)
                    .descricao(produtoImportacao.getDescricao())
                    .peso(produtoImportacao.getPeso())
                    .dataCriacao(LocalDateTime.now())
                    .dataAlteracao(LocalDateTime.now())
                    .pessoaCriacao(produtoService.retornaFuncionarioSeExistente(1L)) //TODO: Implementar este processo usando session
                    .isAtivo(true)
                    .build();

            listaProduto.add(novoProduto);
        });

        return listaProduto;
    }

}