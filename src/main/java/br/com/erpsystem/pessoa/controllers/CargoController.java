package br.com.erpsystem.pessoa.controllers;

import br.com.erpsystem.pessoa.dtos.cargoDTO.CargoRequestDTO;
import br.com.erpsystem.pessoa.dtos.cargoDTO.CargoResponseDTO;
import br.com.erpsystem.pessoa.services.CargoService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/v1/pessoa/cargo")
public class CargoController {

    private final CargoService cargoService;

    public CargoController(CargoService cargoService) {
        this.cargoService = cargoService;
    }

    @Operation(summary = "Listar todos os cargos cadastrados", tags = "Modulo: Pessoa -> Cargo")
    @GetMapping
    public ResponseEntity<List<CargoResponseDTO>> listarTodosCargos(){
        return new ResponseEntity<>(cargoService.listarTodosCargos(), HttpStatus.OK);
    }

    @Operation(summary = "Lista um cargo usando id", tags = "Modulo: Pessoa -> Cargo")
    @GetMapping(value = "/{id}")
    public ResponseEntity<CargoResponseDTO> listarCargoPorId(@PathVariable Long id){
        return new ResponseEntity<>(cargoService.listarCargoPorId(id), HttpStatus.OK);
    }

    @Operation(summary = "Criar um cargo", tags = "Modulo: Pessoa -> Cargo")
    @PostMapping
    public ResponseEntity<CargoResponseDTO> criarCargo(@RequestBody @Valid CargoRequestDTO cargoRequest){
        return new ResponseEntity<>(cargoService.criarCargo(cargoRequest), HttpStatus.OK);
    }

    @Operation(summary = "Alterar um cargo usando id", tags = "Modulo: Pessoa -> Cargo")
    @PutMapping(value = "/{id}")
    public ResponseEntity<CargoResponseDTO> alterarCargoPorId(@PathVariable Long id,
                                                              @RequestBody @Valid CargoRequestDTO cargoRequest){
        return new ResponseEntity<>(cargoService.alterarCargoPorId(id, cargoRequest), HttpStatus.OK);
    }

    @Operation(summary = "Deletar um cargo usando id", tags = "Modulo: Pessoa -> Cargo")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deletarCargoPorId(@PathVariable Long id){
        cargoService.deletarCargoPorId(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
