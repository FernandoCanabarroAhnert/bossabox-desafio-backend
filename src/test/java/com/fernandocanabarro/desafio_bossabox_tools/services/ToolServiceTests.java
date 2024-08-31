package com.fernandocanabarro.desafio_bossabox_tools.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.fernandocanabarro.desafio_bossabox_tools.dtos.ToolDTO;
import com.fernandocanabarro.desafio_bossabox_tools.entities.Tool;
import com.fernandocanabarro.desafio_bossabox_tools.repositories.ToolRepository;
import com.fernandocanabarro.desafio_bossabox_tools.services.exceptions.ResourceNotFoundException;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ToolServiceTests {

    @InjectMocks
    private ToolService toolService;
    @Mock
    private ToolRepository toolRepository;
    
    private String existingId,nonExistingId;
    private Tool tool;
    private ToolDTO dto;

    @BeforeEach
    public void setup() throws Exception{
        existingId = "1";
        nonExistingId = "2";
        String tag = "tags";
        List<String> tags = new ArrayList<>();
        tags.add(tag);
        tool = new Tool("1", "title","link", "description", tags);
        dto = new ToolDTO(tool);
    }

    @Test
    public void findAllShouldReturnPageOfToolDTOWhenTagIsBlank(){
        Pageable pageable = PageRequest.of(0, 10);
        Page<Tool> page = new PageImpl<>(List.of(tool));
        when(toolRepository.findAll(pageable)).thenReturn(page);

        Page<ToolDTO> response = toolService.findAll(pageable, "");

        assertThat(response).isNotEmpty();
        assertThat(response.getSize()).isEqualTo(1L);
        assertThat(response.getContent().get(0).getTitle()).isEqualTo("title");
        assertThat(response.getContent().get(0).getLink()).isEqualTo("link");
        assertThat(response.getContent().get(0).getDescription()).isEqualTo("description");
        assertThat(response.getContent().get(0).getTags().get(0)).isEqualTo("tags");
    }

    @Test
    public void findAllShouldReturnPageOfToolDTOWhenTagExists(){
        Pageable pageable = PageRequest.of(0, 10);
        Page<Tool> page = new PageImpl<>(List.of(tool));
        when(toolRepository.findByTag("tags",pageable)).thenReturn(page);

        Page<ToolDTO> response = toolService.findAll(pageable, "tags");

        assertThat(response).isNotEmpty();
        assertThat(response.getSize()).isEqualTo(1L);
        assertThat(response.getContent().get(0).getTitle()).isEqualTo("title");
        assertThat(response.getContent().get(0).getLink()).isEqualTo("link");
        assertThat(response.getContent().get(0).getDescription()).isEqualTo("description");
        assertThat(response.getContent().get(0).getTags().get(0)).isEqualTo("tags");
    }

    @Test
    public void findByIdShouldReturnToolDTOWhenIdExists(){
        when(toolRepository.findById(existingId)).thenReturn(Optional.of(tool));

        ToolDTO response = toolService.findById(existingId);

        assertThat(response).isNotNull();
        assertThat(response.getTitle()).isEqualTo("title");
        assertThat(response.getLink()).isEqualTo("link");
        assertThat(response.getDescription()).isEqualTo("description");
        assertThat(response.getTags().get(0)).isEqualTo("tags");
    }

    @Test
    public void findByIdShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist(){
        when(toolRepository.findById(nonExistingId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> toolService.findById(nonExistingId)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    public void insertShouldReturnToolDTOWhenDataIsValid(){
        when(toolRepository.save(any(Tool.class))).thenReturn(tool);

        ToolDTO response = toolService.insert(dto);

        assertThat(response).isNotNull();
        assertThat(response.getTitle()).isEqualTo("title");
        assertThat(response.getLink()).isEqualTo("link");
        assertThat(response.getDescription()).isEqualTo("description");
        assertThat(response.getTags().get(0)).isEqualTo("tags");
    }

    @Test
    public void updateShouldReturnToolDTOWhenIdExists(){
        when(toolRepository.findById(existingId)).thenReturn(Optional.of(tool));
        when(toolRepository.save(any(Tool.class))).thenReturn(tool);

        ToolDTO response = toolService.update(existingId,dto);

        assertThat(response).isNotNull();
        assertThat(response.getTitle()).isEqualTo("title");
        assertThat(response.getLink()).isEqualTo("link");
        assertThat(response.getDescription()).isEqualTo("description");
        assertThat(response.getTags().get(0)).isEqualTo("tags");
    }

    @Test
    public void updateShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist(){
        when(toolRepository.findById(nonExistingId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> toolService.update(nonExistingId,dto)).isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    public void deleteShouldDoNothingWhenIdExists(){
        when(toolRepository.existsById(existingId)).thenReturn(true);

        assertThatCode(() -> toolService.deleteById(existingId)).doesNotThrowAnyException();
    }

    @Test
    public void deleteShouldThrowResourceNotFoundExceptionWhenIdDoesNotExist(){
        when(toolRepository.existsById(nonExistingId)).thenReturn(false);

        assertThatThrownBy(() -> toolService.deleteById(nonExistingId)).isInstanceOf(ResourceNotFoundException.class);
    }
}
