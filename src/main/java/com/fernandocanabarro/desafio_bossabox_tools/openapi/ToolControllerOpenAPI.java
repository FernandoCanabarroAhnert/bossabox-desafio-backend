package com.fernandocanabarro.desafio_bossabox_tools.openapi;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.fernandocanabarro.desafio_bossabox_tools.dtos.ToolDTO;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;

public interface ToolControllerOpenAPI {

    @Operation(
    description = "Consultar Ferramentas",
    summary = "Endpoint responsável por receber a requisição da Consulta de Ferramentas",
    responses = {
         @ApiResponse(description = "Consulta Realizada", responseCode = "200")
   		}
	)
    ResponseEntity<Page<ToolDTO>> findByTag(Pageable pageable,@RequestParam(name = "tag",defaultValue = "") String tag);

    @Operation(
    description = "Consultar Ferramenta por Id",
    summary = "Endpoint responsável por receber a requisição da Consulta de Ferramenta por Id",
    responses = {
         @ApiResponse(description = "Consulta Realizada", responseCode = "200"),
         @ApiResponse(description = "Ferramenta Não Encontrada", responseCode = "404")
   		}
	)
    ResponseEntity<ToolDTO> findById(@PathVariable String id);

    @Operation(
    description = "Inserir Ferramenta",
    summary = "Endpoint responsável por receber a requisição de Inserir Ferramenta",
    responses = {
         @ApiResponse(description = "Ferramenta Criada", responseCode = "201"),
         @ApiResponse(description = "Quando um Usuário não logado ou um Usuário Comum faz a requisição", responseCode = "403"),
         @ApiResponse(description = "Algum Campo da Ferramenta está Inválido", responseCode = "422")
   		}
	)
    ResponseEntity<ToolDTO> insert(@RequestBody @Valid ToolDTO dto);

    @Operation(
    description = "Atualizar Ferramenta",
    summary = "Endpoint responsável por receber a requisição de Atualizar Ferramenta",
    responses = {
         @ApiResponse(description = "Ferramenta Atualizada", responseCode = "200"),
         @ApiResponse(description = "Quando um Usuário não logado ou um Usuário Comum faz a requisição", responseCode = "403"),
         @ApiResponse(description = "Algum Campo da Ferramenta está Inválido", responseCode = "422")
   		}
	)
    ResponseEntity<ToolDTO> update(@PathVariable String id, @RequestBody @Valid ToolDTO dto);

    @Operation(
    description = "Deletar Ferramenta por Id",
    summary = "Endpoint responsável por receber a requisição de Deletar uma Ferramenta por Id",
    responses = {
         @ApiResponse(description = "Ferramenta Deletada", responseCode = "204"),
         @ApiResponse(description = "Ferramenta Não Encontrada", responseCode = "404")
   		}
	)
    ResponseEntity<Void> delete(@PathVariable String id);
}
