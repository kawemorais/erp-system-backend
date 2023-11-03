package br.com.erpsystem.almoxarifado.controllers;

import br.com.erpsystem.almoxarifado.dtos.fichaTecnicaDTO.FichaTecnicaRequestDTO;
import br.com.erpsystem.almoxarifado.dtos.fichaTecnicaDTO.FichaTecnicaResponseDTO;
import br.com.erpsystem.almoxarifado.services.FichaTecnicaService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin()
@RestController
@RequestMapping(value = "/api/v1/almoxarifado/ficha-tecnica")
public class FichaTecnicaController {

    private final FichaTecnicaService fichaTecnicaService;

    public FichaTecnicaController(FichaTecnicaService fichaTecnicaService) {
        this.fichaTecnicaService = fichaTecnicaService;
    }

    @Operation(summary = "Criar uma ficha tecnica", tags = "Modulo: Almoxarifado -> Produtos > Ficha Tecnica")
    @PostMapping
    public ResponseEntity<FichaTecnicaResponseDTO> criarFichaTecnica(@RequestBody @Valid FichaTecnicaRequestDTO fichaTecnicaRequest){
        return new ResponseEntity<>(fichaTecnicaService.criarFichaTecnica(fichaTecnicaRequest), HttpStatus.CREATED);
    }
}
