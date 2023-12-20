package com.example.api.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.api.exceptions.BadRequestException;
import com.example.api.model.Tarefa;
import com.example.api.repository.TarefaRepository;

@Service

public class TarefaService {
	
	@Autowired
	private TarefaRepository tarefaRepository;
	
	public List<Tarefa> listarTarefa() {
		return tarefaRepository.findAll();
	}
	
	public Tarefa cadastrarTarefa(@RequestBody Tarefa tarefa) {
		return tarefaRepository.save(tarefa);
	}
	
	public void deletarTarefa(Long tarefaId) {
		try {
			Optional<Tarefa> tarefa = tarefaRepository.findById(tarefaId);
			if(tarefa.isPresent()) {
				tarefaRepository.deleteById(tarefaId);
			}
			else {
				throw new Exception();
			}
		} catch (Exception e) {
			throw new BadRequestException("Falha ao deletar tarefa");
			
		}
	}

}
