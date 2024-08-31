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

import com.fernandocanabarro.desafio_bossabox_tools.dtos.ToolDTO;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/tools")
@RequiredArgsConstructor
public class ToolController {

    @GetMapping
    public ResponseEntity<Page<ToolDTO>> findByTag(Pageable pageable,@RequestParam(name = "tag",defaultValue = "") String tag){
        return null;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ToolDTO> findById(@PathVariable String id){
        return null;
    }

    @PostMapping
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ToolDTO> insert(@RequestBody @Valid ToolDTO dto){
        return null;
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<ToolDTO> update(@PathVariable String id, @RequestBody @Valid ToolDTO dto){
        return null;
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable String id){
        return null;
    }
}
