package br.com.erpsystem.almoxarifado.services;

import br.com.erpsystem.almoxarifado.dtos.almoxarifadoDTO.AlmoxarifadoRequestDTO;
import br.com.erpsystem.almoxarifado.dtos.almoxarifadoDTO.AlmoxarifadoResponseDTO;
import br.com.erpsystem.almoxarifado.models.Almoxarifado;
import br.com.erpsystem.almoxarifado.repositories.AlmoxarifadoRepository;
import br.com.erpsystem.almoxarifado.repositories.EstoqueProdutoRepository;
import br.com.erpsystem.sistema.exception.ExcecaoSolicitacaoInvalida;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AlmoxarifadoService {
    
    private final AlmoxarifadoRepository almoxarifadoRepository;

    private final EstoqueProdutoRepository estoqueProdutoRepository;
    
    private final ModelMapper mapper;

    public AlmoxarifadoService(AlmoxarifadoRepository almoxarifadoRepository, EstoqueProdutoRepository estoqueProdutoRepository, ModelMapper mapper) {
        this.almoxarifadoRepository = almoxarifadoRepository;
        this.estoqueProdutoRepository = estoqueProdutoRepository;
        this.mapper = mapper;
    }

    public List<AlmoxarifadoResponseDTO> listarTodosAlmoxarifados(){
        return almoxarifadoRepository.findAll()
                .stream()
                .map(almoxarifado -> mapper.map(almoxarifado, AlmoxarifadoResponseDTO.class))
                .collect(Collectors.toList());
    }

    public List<AlmoxarifadoResponseDTO> listarTodosAlmoxarifadosPorStatus(String status) {
        if (!status.equalsIgnoreCase("true") && !status.equalsIgnoreCase("false")) {
            throw new ExcecaoSolicitacaoInvalida("Parametro incorreto");
        }

        Boolean isAtivo = Boolean.valueOf(status.toLowerCase());

        return almoxarifadoRepository.findAllByIsAtivo(isAtivo)
                .stream()
                .map(almoxarifado -> mapper.map(almoxarifado, AlmoxarifadoResponseDTO.class))
                .collect(Collectors.toList());
    }

    public AlmoxarifadoResponseDTO listarAlmoxarifadoPorId(Long id){
        Almoxarifado almoxarifado = retornaAlmoxarifadoSeExistente(id);

        return mapper.map(almoxarifado, AlmoxarifadoResponseDTO.class);
    }

    public AlmoxarifadoResponseDTO criarAlmoxarifado(AlmoxarifadoRequestDTO almoxarifadoRequest){

        if (almoxarifadoRepository.findByCodigo(almoxarifadoRequest.getCodigo()).isPresent()){
            throw new ExcecaoSolicitacaoInvalida("Ja existe almoxarifado com este codigo");
        }

        if (almoxarifadoRepository.findByNome(almoxarifadoRequest.getNome()).isPresent()){
            throw new ExcecaoSolicitacaoInvalida("Ja existe almoxarifado com este nome");
        }

        almoxarifadoRequest.setIsAtivo(Boolean.TRUE);

        Almoxarifado almoxarifadoSalvo = almoxarifadoRepository.save(mapper.map(almoxarifadoRequest, Almoxarifado.class));

        return mapper.map(almoxarifadoSalvo, AlmoxarifadoResponseDTO.class);
    }

    public AlmoxarifadoResponseDTO alterarAlmoxarifadoPorId(Long id, AlmoxarifadoRequestDTO almoxarifadoRequest){

        checaExistenciaParametrosDuplicados(almoxarifadoRequest, id);

        Almoxarifado almoxarifado = retornaAlmoxarifadoSeExistente(id);

        almoxarifado.setCodigo(almoxarifadoRequest.getCodigo());
        almoxarifado.setNome(almoxarifadoRequest.getNome());
        almoxarifado.setIsAtivo(almoxarifadoRequest.getIsAtivo());

        return mapper.map(almoxarifadoRepository.save(almoxarifado), AlmoxarifadoResponseDTO.class);
    }

    public void deletarAlmoxarifadoPorId(Long id){

        Almoxarifado almoxarifado = retornaAlmoxarifadoSeExistente(id);

        if(!estoqueProdutoRepository.findAllByAlmoxarifado(almoxarifado).isEmpty()){
            throw new ExcecaoSolicitacaoInvalida("Existem produtos em estoque neste almoxarifado. Operação não pode ser finalizada");
        }

        almoxarifadoRepository.delete(almoxarifado);
    }

    private Almoxarifado retornaAlmoxarifadoSeExistente(Long id){
        return almoxarifadoRepository.findById(id)
                .orElseThrow(() -> new ExcecaoSolicitacaoInvalida("Registro almoxarifado não encontrado"));
    }

    private void checaExistenciaParametrosDuplicados(AlmoxarifadoRequestDTO almoxarifadoRequest, Long id){

        Optional<Almoxarifado> almoxarifadobyCodigo = almoxarifadoRepository.findByCodigo(almoxarifadoRequest.getCodigo());
        if (almoxarifadobyCodigo.isPresent() && !Objects.equals(almoxarifadobyCodigo.get().getId(), id)){
            throw new ExcecaoSolicitacaoInvalida("Ja existe categoria produto com este codigo");
        }

        Optional<Almoxarifado> almoxarifadobyNome = almoxarifadoRepository.findByNome(almoxarifadoRequest.getNome());
        if (almoxarifadobyNome.isPresent() && !Objects.equals(almoxarifadobyNome.get().getId(), id)){
            throw new ExcecaoSolicitacaoInvalida("Ja existe categoria produto com este nome");
        }
    }
    
}
