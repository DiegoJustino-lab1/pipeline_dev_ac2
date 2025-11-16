package com.example.DQAP.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.DQAP.ValueObjects.Email;
import com.example.DQAP.ValueObjects.Senha;
import com.example.DQAP.dto.ProgressoDTO;
import com.example.DQAP.entity.Aluno;
import com.example.DQAP.entity.Conclusao;
import com.example.DQAP.entity.Curso;
import com.example.DQAP.exception.NotFoundException;
import com.example.DQAP.repository.AlunoRepository;
import com.example.DQAP.repository.ConclusaoRepository;
import com.example.DQAP.repository.CursoRepository;
import com.example.DQAP.service.ProgressoService;

@ExtendWith(MockitoExtension.class)
public class ProgressoServiceTest {
	 @Mock private AlunoRepository alunoRepository;
	    @Mock private CursoRepository cursoRepository;
	    @Mock private ConclusaoRepository conclusaoRepository;

	    @InjectMocks private ProgressoService progressoService;

	    private Aluno aluno;

	    @BeforeEach
	    void setup() {
	        aluno = new Aluno();
	        aluno.setId(1L);
	        aluno.setNome("Rafael");
	        aluno.setMediaGeral(8.5);
	        aluno.setConclusoes(new ArrayList());
	    }

	    @Test
	    void deveCalcularProgressoQuandoNenhumCursoConcluido() {
	        when(alunoRepository.findById(1L)).thenReturn(Optional.of(aluno));

	        ProgressoDTO dto = progressoService.calcularProgresso(1L);

	        assertEquals("Nenhum curso concluído. Faltam 12 cursos para atingir o bônus.", dto.getMensagem());
	        assertEquals(0, dto.getCursosConcluidos());
	    }

	    @Test
	    void deveCalcularProgressoMensagemGenerica() {
	        aluno.getConclusoes().add(new Conclusao(null, aluno, null, true));
	        aluno.getConclusoes().add(new Conclusao(null, aluno, null, true));

	        when(alunoRepository.findById(1L)).thenReturn(Optional.of(aluno));

	        ProgressoDTO dto = progressoService.calcularProgresso(1L);

	        assertTrue(dto.getMensagem().contains("Cursos concluídos: 2"));
	    }

	    @Test
	    void deveLancarExcecaoAlunoNaoEncontrado() {
	        when(alunoRepository.findById(1L)).thenReturn(Optional.empty());

	        assertThrows(NotFoundException.class,
	            () -> progressoService.calcularProgresso(1L));
	    }

	    @Test
	    void deveRegistrarConclusaoComSucesso() {
	        Curso curso = new Curso(null, "Java", 9.0);
	        curso.setId(2L);

	        when(alunoRepository.findById(1L)).thenReturn(Optional.of(aluno));
	        when(cursoRepository.findById(2L)).thenReturn(Optional.of(curso));
	        when(conclusaoRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

	        Conclusao c = progressoService.registrarConclusao(1L, 2L, true);

	        assertNotNull(c);
	        assertEquals(aluno, c.getAluno());
	        assertEquals(curso, c.getCurso());
	        verify(conclusaoRepository).save(any(Conclusao.class));
	    }

	    @Test
	    void deveLancarExcecaoQuandoAlunoNaoExisteAoRegistrar() {
	        when(alunoRepository.findById(1L)).thenReturn(Optional.empty());
	        assertThrows(NotFoundException.class,
	            () -> progressoService.registrarConclusao(1L, 2L, true));
	    }

	    @Test
	    void deveLancarExcecaoQuandoCursoNaoExisteAoRegistrar() {
	        when(alunoRepository.findById(1L)).thenReturn(Optional.of(aluno));
	        when(cursoRepository.findById(2L)).thenReturn(Optional.empty());
	        assertThrows(NotFoundException.class,
	            () -> progressoService.registrarConclusao(1L, 2L, true));
	    }

}
