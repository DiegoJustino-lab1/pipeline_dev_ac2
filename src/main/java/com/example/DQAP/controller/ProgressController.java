package com.example.DQAP.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.DQAP.dto.ProgressoDTO;
import com.example.DQAP.entity.Conclusao;
import com.example.DQAP.service.ProgressoService;

@RestController
@RequestMapping("/progresso")
public class ProgressController {

	private final ProgressoService progressoService;

    public ProgressController(ProgressoService progressoService) {
        this.progressoService = progressoService;
    }

    @GetMapping("/{idAluno}")
    public ProgressoDTO getProgresso(@PathVariable Long idAluno) {
        return progressoService.calcularProgresso(idAluno);
    }

    // ✅ NOVO POST
    @PostMapping("/{idAluno}/{idCurso}")
    public ResponseEntity<Conclusao> registrarConclusao(
            @PathVariable Long idAluno,
            @PathVariable Long idCurso,
            @RequestParam boolean concluido) {

        Conclusao novaConclusao = progressoService.registrarConclusao(idAluno, idCurso, concluido);
        return ResponseEntity.ok(novaConclusao);
    }

}
