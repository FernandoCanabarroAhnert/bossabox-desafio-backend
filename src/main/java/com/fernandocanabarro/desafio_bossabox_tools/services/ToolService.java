package com.fernandocanabarro.desafio_bossabox_tools.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fernandocanabarro.desafio_bossabox_tools.dtos.ToolDTO;
import com.fernandocanabarro.desafio_bossabox_tools.entities.Tool;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ToolService {

    @Transactional(readOnly = true)
    public Page<ToolDTO> findAll(Pageable pageable, String tag){
        return null;
    }

    @Transactional(readOnly = true)
    public ToolDTO findById(String id){
        return null;
    }

    @Transactional
    public ToolDTO insert(ToolDTO dto){
        return null;
    }

    private void toEntity(Tool tool, ToolDTO dto) {
        return;
    }

    @Transactional
    public ToolDTO update(String id, ToolDTO dto){
        return null;
    }

    @Transactional
    public void deleteById(String id){
        return;
    }

}
