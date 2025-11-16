package com.example.DQAP.service;

import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.stereotype.Service;

import com.example.DQAP.dto.ProgressoDTO;
import com.example.DQAP.entity.Aluno;
import com.example.DQAP.entity.Conclusao;
import com.example.DQAP.entity.Curso;
import com.example.DQAP.repository.AlunoRepository;
import com.example.DQAP.repository.ConclusaoRepository;
import com.example.DQAP.repository.CursoRepository;

@Service
public class ProgressoService {

	private final AlunoRepository alunoRepository;
	private final CursoRepository cursoRepository;
	private final ConclusaoRepository conclusaoRepository;

	public ProgressoService(AlunoRepository alunoRepository, CursoRepository cursoRepository,
			ConclusaoRepository conclusaoRepository) {
		this.alunoRepository = alunoRepository;
		this.cursoRepository = cursoRepository;
		this.conclusaoRepository = conclusaoRepository;
	}

	public ProgressoDTO calcularProgresso(Long idAluno) {
		Aluno aluno = alunoRepository.findById(idAluno)
				.orElseThrow(() -> new com.example.DQAP.exception.NotFoundException("Aluno não encontrado"));

		long concluidos = aluno.getConclusoes().stream().filter(Conclusao::isConcluido).count();

		double media = aluno.getMediaGeral();
		long meta = 12;
		long faltam = meta - concluidos;

		String mensagem = gerarMensagem(concluidos, media, faltam);

		return new ProgressoDTO(mensagem, concluidos, media, faltam);
	}

	private String gerarMensagem(long concluidos, double media, long faltam) {
		if (concluidos == 0) {
			return "Nenhum curso concluído. Faltam 12 cursos para atingir o bônus.";
		}
		if (concluidos >= 5 && media > 7) {
			return "Você concluiu 5 cursos com média acima de 7. Faltam 7 cursos para alcançar o bônus.";
		}
		if (concluidos == 3 && media < 7) {
			return "Você concluiu 3 cursos com média abaixo de 7. Faltam 10 cursos para atingir o bônus.";
		}
		return "Cursos concluídos: " + concluidos + ". Faltam " + faltam + " para o bônus.";
	}

	public Conclusao registrarConclusao(Long idAluno, Long idCurso, boolean concluido) {
		Aluno aluno = alunoRepository.findById(idAluno)
				.orElseThrow(() -> new com.example.DQAP.exception.NotFoundException("Aluno não encontrado"));

		Curso curso = cursoRepository.findById(idCurso)
				.orElseThrow(() -> new com.example.DQAP.exception.NotFoundException("Curso não encontrado"));

		Conclusao conclusao = new Conclusao();
		conclusao.setAluno(aluno);
		conclusao.setCurso(curso);
		conclusao.setConcluido(concluido);

		return conclusaoRepository.save(conclusao);

	}
}
