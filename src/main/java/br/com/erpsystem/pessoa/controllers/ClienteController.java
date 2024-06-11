package br.com.erpsystem.pessoa.controllers;

import br.com.erpsystem.pessoa.dtos.clienteDTO.ClienteRequestDTO;
import br.com.erpsystem.pessoa.dtos.clienteDTO.ClienteResponseDTO;
import br.com.erpsystem.pessoa.services.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/pessoa/cliente")
public class ClienteController {

    private final ClienteService clienteService;

    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    @Operation(summary = "Listar todas os clientes cadastrados", tags = "Modulo: Pessoa -> Cliente")
    @GetMapping
    public ResponseEntity<List<ClienteResponseDTO>> listarTodosClientes(){
        return new ResponseEntity<>(clienteService.listarTodosClientes(), HttpStatus.OK);
    }

    @Operation(summary = "Lista um cliente usando id", tags = "Modulo: Pessoa -> Cliente")
    @GetMapping(value = "/{id}")
    public ResponseEntity<ClienteResponseDTO> listarClientePorId(@PathVariable Long id){
        return new ResponseEntity<>(clienteService.listarClientePorId(id), HttpStatus.OK);
    }

    @Operation(summary = "Cadastra um cliente", tags = "Modulo: Pessoa -> Cliente")
    @PostMapping
    public ResponseEntity<ClienteResponseDTO> cadastrarCliente(@RequestBody @Valid ClienteRequestDTO clienteRequest){
        return new ResponseEntity<>(clienteService.cadastrarCliente(clienteRequest), HttpStatus.CREATED);
    }

    @Operation(summary = "Alterar cliente usando id", tags = "Modulo: Pessoa -> Cliente")
    @PutMapping(value = "/{id}")
    public ResponseEntity<ClienteResponseDTO> alterarClientePorId(@PathVariable Long id,
                                                                @RequestBody @Valid ClienteRequestDTO clienteRequest){
        return new ResponseEntity<>(clienteService.alterarClientePorId(id, clienteRequest), HttpStatus.OK);
    }

    @Operation(summary = "Inativar cliente usando id", tags = "Modulo: Pessoa -> Cliente")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> inativarClientePorId(@PathVariable Long id){
        clienteService.inativarClientePorId(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
