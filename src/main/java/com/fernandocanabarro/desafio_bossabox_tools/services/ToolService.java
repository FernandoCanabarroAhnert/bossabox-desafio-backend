package com.fernandocanabarro.desafio_bossabox_tools.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fernandocanabarro.desafio_bossabox_tools.controllers.ToolController;
import com.fernandocanabarro.desafio_bossabox_tools.dtos.ToolDTO;
import com.fernandocanabarro.desafio_bossabox_tools.entities.Tool;
import com.fernandocanabarro.desafio_bossabox_tools.repositories.ToolRepository;
import com.fernandocanabarro.desafio_bossabox_tools.services.exceptions.ResourceNotFoundException;

import lombok.RequiredArgsConstructor;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
@RequiredArgsConstructor
public class ToolService {

    private final ToolRepository toolRepository;

    @Transactional(readOnly = true)
    public Page<ToolDTO> findAll(Pageable pageable, String tag){
        return (tag.isBlank()) ? toolRepository.findAll(pageable).map(x -> new ToolDTO(x)
                    .add(linkTo(methodOn(ToolController.class).findById(x.getId())).withRel("Consultar por Id"))) 
                : toolRepository.findByTag(tag,pageable).map(x -> new ToolDTO(x)
                    .add(linkTo(methodOn(ToolController.class).findById(x.getId())).withRel("Consultar por Id")));
    }

    @Transactional(readOnly = true)
    public ToolDTO findById(String id){
        Tool obj = toolRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
        return new ToolDTO(obj).add(linkTo(methodOn(ToolController.class).findByTag(null, "")).withRel("Consultar todas as Tools"));
    }

    @Transactional
    public ToolDTO insert(ToolDTO dto){
        Tool tool = new Tool();
        toEntity(tool,dto);
        tool = toolRepository.save(tool);
        return new ToolDTO(tool);
    }

    private void toEntity(Tool tool, ToolDTO dto) {
        tool.setTitle(dto.getTitle());
        tool.setLink(dto.getLink());
        tool.setDescription(dto.getDescription());
        tool.setTags(dto.getTags());
    }

    @Transactional
    public ToolDTO update(String id, ToolDTO dto){
        Tool entity = toolRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
        toEntity(entity, dto);
        entity = toolRepository.save(entity);
        return new ToolDTO(entity);
    }

    @Transactional
    public void deleteById(String id){
        if (!toolRepository.existsById(id)) {
            throw new ResourceNotFoundException(id);
        }
        toolRepository.deleteById(id);
    }

}
