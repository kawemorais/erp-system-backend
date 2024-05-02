package br.com.erpsystem.pessoa.controllers;

import br.com.erpsystem.pessoa.dtos.usuarioSistemaDTO.UsuarioSistemaRequestDTO;
import br.com.erpsystem.pessoa.dtos.usuarioSistemaDTO.UsuarioSistemaResponseDTO;
import br.com.erpsystem.pessoa.services.UsuarioSistemaService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/v1/pessoa/usuario-sistema")
public class UsuarioSistemaController {

    private final UsuarioSistemaService usuarioSistemaService;

    public UsuarioSistemaController(UsuarioSistemaService usuarioSistemaService) {
        this.usuarioSistemaService = usuarioSistemaService;
    }

    @Operation(summary = "Listar todos os usuarios cadastrados", tags = "Modulo: Pessoa -> UsuarioSistema")
    @GetMapping
    public ResponseEntity<List<UsuarioSistemaResponseDTO>> listarTodosUsuarios(){
        return new ResponseEntity<>(usuarioSistemaService.listarTodosUsuarios(), HttpStatus.OK);
    }

    @Operation(summary = "Listar um usuario usando id", tags = "Modulo: Pessoa -> UsuarioSistema")
    @GetMapping(value = "/{id}")
    public ResponseEntity<UsuarioSistemaResponseDTO> listarUsuarioPorId(@PathVariable Long id){
        return new ResponseEntity<>(usuarioSistemaService.listarUsuarioPorId(id), HttpStatus.OK);
    }

    @Operation(summary = "Criar um usuario", tags = "Modulo: Pessoa -> UsuarioSistema")
    @PostMapping
    public ResponseEntity<UsuarioSistemaResponseDTO> criarUsuario(@RequestBody @Valid UsuarioSistemaRequestDTO usuarioRequest){
        return new ResponseEntity<>(usuarioSistemaService.criarUsuario(usuarioRequest), HttpStatus.OK);
    }

    @Operation(summary = "Alterar um usuario usando id", tags = "Modulo: Pessoa -> UsuarioSistema")
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioSistemaResponseDTO> alterarUsuario(@PathVariable Long id,
                                                                    @RequestBody @Valid UsuarioSistemaRequestDTO usuarioRequest){
        return new ResponseEntity<>(usuarioSistemaService.alterarUsuarioPorId(id, usuarioRequest), HttpStatus.OK);
    }

    @Operation(summary = "Alterar status do usuario usando id", tags = "Modulo: Pessoa -> UsuarioSistema")
    @PutMapping("/alterarstatus/{id}")
    public ResponseEntity<UsuarioSistemaResponseDTO> ativarInativarUsuario(@PathVariable Long id){
        return new ResponseEntity<>(usuarioSistemaService.ativarOuInativarUsuario(id), HttpStatus.OK);
    }

}
