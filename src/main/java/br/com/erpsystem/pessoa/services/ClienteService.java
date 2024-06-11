package br.com.erpsystem.pessoa.services;

import br.com.erpsystem.pessoa.dtos.clienteDTO.ClienteRequestDTO;
import br.com.erpsystem.pessoa.dtos.clienteDTO.ClienteResponseDTO;
import br.com.erpsystem.pessoa.models.Cliente;
import br.com.erpsystem.pessoa.models.Endereco;
import br.com.erpsystem.pessoa.repositories.ClienteRepository;
import br.com.erpsystem.sistema.exception.ExcecaoSolicitacaoInvalida;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final EnderecoService enderecoService;

    private final ModelMapper mapper;

    public ClienteService(ClienteRepository clienteRepository, EnderecoService enderecoService, ModelMapper mapper) {
        this.clienteRepository = clienteRepository;
        this.enderecoService = enderecoService;
        this.mapper = mapper;
    }

    public List<ClienteResponseDTO> listarTodosClientes(){
        return clienteRepository.findAll()
                .stream()
                .map(cliente -> mapper.map(cliente, ClienteResponseDTO.class))
                .collect(Collectors.toList());
    }

    public ClienteResponseDTO listarClientePorId(Long id){
        Cliente cliente = retornaClienteSeExistente(id);

        return mapper.map(cliente, ClienteResponseDTO.class);
    }

    public ClienteResponseDTO cadastrarCliente(ClienteRequestDTO clienteRequest){

        checaExistenciaClienteCamposUnicos(clienteRequest.getRazaoSocial(), clienteRequest.getCnpj(),
                clienteRequest.getInscricaoEstadual(), clienteRequest.getInscricaoMunicipal());

        Endereco endereco = enderecoService.gerarEndereco(clienteRequest.getEnderecoRequest());

        Cliente cliente = Cliente.builder()
                .telefone(clienteRequest.getTelefone())
                .celular(clienteRequest.getCelular())
                .email(clienteRequest.getEmail())
                .endereco(endereco)
                .nomeFantasia(clienteRequest.getNomeFantasia())
                .razaoSocial(clienteRequest.getRazaoSocial())
                .cnpj(clienteRequest.getCnpj())
                .inscricaoEstadual(clienteRequest.getInscricaoEstadual())
                .inscricaoMunicipal(clienteRequest.getInscricaoMunicipal())
                .isAtivo(Boolean.TRUE)
                .build();

        Cliente clienteSalvo = clienteRepository.save(cliente);

        return mapper.map(clienteSalvo, ClienteResponseDTO.class);
    }

    public ClienteResponseDTO alterarClientePorId(Long id, ClienteRequestDTO clienteRequest){
        Cliente cliente = retornaClienteSeExistente(id);

        if (!cliente.getCnpj().equals(clienteRequest.getCnpj())) {
            checaExistenciaClienteCnpj(clienteRequest.getCnpj());
        }

        if (!cliente.getRazaoSocial().equals(clienteRequest.getRazaoSocial())) {
            checaExistenciaClienteRazaoSocial(clienteRequest.getRazaoSocial());
        }

        if (!cliente.getInscricaoEstadual().equals(clienteRequest.getInscricaoEstadual())) {
            checaExistenciaClienteInscricaoEstadual(clienteRequest.getInscricaoEstadual());
        }

        if (!cliente.getInscricaoMunicipal().equals(clienteRequest.getInscricaoMunicipal())) {
            checaExistenciaClienteInscricaoMunicipal(clienteRequest.getInscricaoMunicipal());
        }

        if (!cliente.getEndereco().getCep().equals(clienteRequest.getEnderecoRequest().getCep())) {
            Endereco novoEndereco = enderecoService.gerarEndereco(clienteRequest.getEnderecoRequest());

            cliente.getEndereco().setCep(novoEndereco.getCep());
            cliente.getEndereco().setLogradouro(novoEndereco.getLogradouro());
            cliente.getEndereco().setBairro(novoEndereco.getBairro());
            cliente.getEndereco().setCidade(novoEndereco.getCidade());
            cliente.getEndereco().setUf(novoEndereco.getUf());
            cliente.getEndereco().setNumero(novoEndereco.getNumero());
            cliente.getEndereco().setComplemento(novoEndereco.getComplemento());
        }

        cliente.setTelefone(clienteRequest.getTelefone());
        cliente.setCelular(clienteRequest.getCelular());
        cliente.setEmail(clienteRequest.getEmail());
        cliente.setNomeFantasia(clienteRequest.getNomeFantasia());
        cliente.setRazaoSocial(clienteRequest.getRazaoSocial());
        cliente.setCnpj(clienteRequest.getCnpj());
        cliente.setInscricaoEstadual(clienteRequest.getInscricaoEstadual());
        cliente.setInscricaoMunicipal(clienteRequest.getInscricaoMunicipal());

        cliente.getEndereco().setNumero(clienteRequest.getEnderecoRequest().getNumero());
        cliente.getEndereco().setComplemento(clienteRequest.getEnderecoRequest().getComplemento());

        Boolean isAtivo = Optional.ofNullable(clienteRequest.getIsAtivo()).orElse(true);
        cliente.setIsAtivo(isAtivo);

        Cliente clienteSalvo = clienteRepository.save(cliente);

        return mapper.map(clienteSalvo, ClienteResponseDTO.class);
    }

    public void inativarClientePorId(Long id){
        Cliente cliente = retornaClienteSeExistente(id);

        cliente.setIsAtivo(Boolean.FALSE);

        clienteRepository.save(cliente);
    }

    protected Cliente retornaClienteSeExistente(Long id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new ExcecaoSolicitacaoInvalida("Registro de cliente não encontrado"));
    }

    private void checaExistenciaClienteCnpj(String cnpj){
        if(clienteRepository.findByCnpj(cnpj).isPresent()){
            throw new ExcecaoSolicitacaoInvalida("Já existe cliente cadastrado com este cnpj");
        }
    }

    private void checaExistenciaClienteRazaoSocial(String razaoSocial){
        if(clienteRepository.findByRazaoSocial(razaoSocial).isPresent()){
            throw new ExcecaoSolicitacaoInvalida("Já existe cliente cadastrado com esta razao social");
        }
    }

    private void checaExistenciaClienteInscricaoEstadual(String inscricaoEstadual){
        if(clienteRepository.findByInscricaoEstadual(inscricaoEstadual).isPresent()){
            throw new ExcecaoSolicitacaoInvalida("Já existe cliente cadastrado com esta inscrição estadual");
        }
    }

    private void checaExistenciaClienteInscricaoMunicipal(String inscricaoMunicipal){
        if(clienteRepository.findByInscricaoMunicipal(inscricaoMunicipal).isPresent()){
            throw new ExcecaoSolicitacaoInvalida("Já existe cliente cadastrado com esta inscrição municipal");
        }
    }

    private void checaExistenciaClienteCamposUnicos(String razaoSocial, String cnpj, String inscricaoEstadual, String inscricaoMunicipal) {
        if(clienteRepository.findByRazaoSocialAndCnpjAndInscricaoEstadualAndInscricaoMunicipal(razaoSocial, cnpj, inscricaoEstadual, inscricaoMunicipal).isPresent()){
            throw new ExcecaoSolicitacaoInvalida("Já existe cliente cadastrado com estas informações");
        }
    }
}
