package com.example.DQAP.service;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.DQAP.controller.ProgressController;
import com.example.DQAP.dto.ProgressoDTO;
import com.example.DQAP.entity.Conclusao;
import com.example.DQAP.service.ProgressoService;

@WebMvcTest(ProgressController.class)
public class ProgressControllerTest {
	
	@Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProgressoService progressoService;

    @Test
    void deveRetornarProgresso() throws Exception {
        ProgressoDTO dto = new ProgressoDTO("OK", 2, 8.5, 10);

        when(progressoService.calcularProgresso(1L)).thenReturn(dto);

        mockMvc.perform(get("/progresso/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.cursosConcluidos").value(2));
    }

    @Test
    void deveRegistrarConclusao() throws Exception {
        Conclusao conclusao = new Conclusao();
        when(progressoService.registrarConclusao(1L, 2L, true)).thenReturn(conclusao);

        mockMvc.perform(post("/progresso/1/2?concluido=true"))
                .andExpect(status().isOk());
    }

}
