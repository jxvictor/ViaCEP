package com.example.ViaCEP.controller;

import com.example.ViaCEP.model.Endereco;
import com.example.ViaCEP.service.EnderecoService;
import com.example.ViaCEP.service.PageEnderecoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/endereco", produces = "application/json")
public class EnderecoController {

    @Autowired
    private EnderecoService cepService;

    @Autowired
    private PageEnderecoService pageEnderecoService;

    @Operation(summary = "Retorna o cep especificado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Sucesso"),
            @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
            @ApiResponse(responseCode = "404", description = "Endpoint não encontrado"),
            @ApiResponse(responseCode = "500", description = "Erro no servidor")
    })
    @GetMapping("/{cep}")
    public ResponseEntity<Endereco> getCep(@PathVariable String cep) {
        Endereco endereco = cepService.buscarEnderecoPor(cep);
        try
        {
            return new ResponseEntity<>(endereco, HttpStatus.OK);
        }
        catch(Exception e)
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping()
    public ResponseEntity<Page<Endereco>> obterTodos(Pageable pageable){
        try
        {
            return new ResponseEntity<>(pageEnderecoService.findAll(pageable), HttpStatus.OK);
        }
        catch(Exception e)
        {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}