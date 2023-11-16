package br.com.erpsystem.pessoa.services;

import br.com.erpsystem.pessoa.dtos.cargoDTO.CargoRequestDTO;
import br.com.erpsystem.pessoa.dtos.cargoDTO.CargoResponseDTO;
import br.com.erpsystem.pessoa.models.Cargo;
import br.com.erpsystem.pessoa.repositories.CargoRepository;
import br.com.erpsystem.pessoa.repositories.FuncionarioRepository;
import br.com.erpsystem.sistema.exception.ExcecaoSolicitacaoInvalida;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CargoService {

    private final CargoRepository cargoRepository;
    private final FuncionarioRepository funcionarioRepository;

    private final ModelMapper mapper;

    public CargoService(CargoRepository cargoRepository, FuncionarioRepository funcionarioRepository, ModelMapper mapper) {
        this.cargoRepository = cargoRepository;
        this.funcionarioRepository = funcionarioRepository;
        this.mapper = mapper;
    }

    public List<CargoResponseDTO> listarTodosCargos(){
        return cargoRepository.findAll()
                .stream()
                .map(cargo -> mapper.map(cargo, CargoResponseDTO.class))
                .collect(Collectors.toList());
    }

    public CargoResponseDTO listarCargoPorId(Long id){
        Cargo cargo = retornaCargoSeExistente(id);

        return mapper.map(cargo, CargoResponseDTO.class);
    }

    public CargoResponseDTO criarCargo(CargoRequestDTO cargoRequest){

        checarSeCargoExistentePorNome(cargoRequest.getNome());

        Cargo cargoSalvo = cargoRepository.save(mapper.map(cargoRequest, Cargo.class));

        return mapper.map(cargoSalvo, CargoResponseDTO.class);
    }

    public CargoResponseDTO alterarCargoPorId(Long id, CargoRequestDTO cargoRequest){

        Cargo cargo = retornaCargoSeExistente(id);

        if(cargo.getNome().equals(cargoRequest.getNome())){
            return mapper.map(cargo, CargoResponseDTO.class);
        }

        checarSeCargoExistentePorNome(cargoRequest.getNome());

        cargo.setNome(cargoRequest.getNome());

        return mapper.map(cargoRepository.save(cargo), CargoResponseDTO.class);

    }

    public void deletarCargoPorId(Long id){

        Cargo cargo = retornaCargoSeExistente(id);

        if(!funcionarioRepository.findAllByCargo(cargo).isEmpty()){
            throw new ExcecaoSolicitacaoInvalida("Este cargo esta sendo utilizado. Operação não pode ser concluída");
        }

        cargoRepository.delete(cargo);
    }

    private Cargo retornaCargoSeExistente(Long id) {
        return cargoRepository.findById(id)
                .orElseThrow(() -> new ExcecaoSolicitacaoInvalida("Registro de cargo não encontrado"));
    }

    private void checarSeCargoExistentePorNome(String nome){
        if(cargoRepository.findByNome(nome).isPresent()){
            throw new ExcecaoSolicitacaoInvalida("Já existe cargo com este nome");
        }
    }
}
