package com.fernandocanabarro.desafio_bossabox_tools.controllers;

import java.net.URI;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fernandocanabarro.desafio_bossabox_tools.dtos.ToolDTO;
import com.fernandocanabarro.desafio_bossabox_tools.openapi.ToolControllerOpenAPI;
import com.fernandocanabarro.desafio_bossabox_tools.services.ToolService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/tools")
@RequiredArgsConstructor
public class ToolController implements ToolControllerOpenAPI{

    private final ToolService toolService;

    @GetMapping
    public ResponseEntity<Page<ToolDTO>> findByTag(Pageable pageable,@RequestParam(name = "tag",defaultValue = "") String tag){
        Page<ToolDTO> page = toolService.findAll(pageable,tag);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ToolDTO> findById(@PathVariable String id){
        ToolDTO obj = toolService.findById(id);
        return ResponseEntity.ok(obj);
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ToolDTO> insert(@RequestBody @Valid ToolDTO dto){
        dto = toolService.insert(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
            .buildAndExpand(dto.getId()).toUri();
        return ResponseEntity.created(uri).body(dto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ToolDTO> update(@PathVariable String id, @RequestBody @Valid ToolDTO dto){
        dto = toolService.update(id, dto);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable String id){
        toolService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
