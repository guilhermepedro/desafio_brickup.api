package com.example.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.api.config.AmazonClient;
import com.example.api.model.Tarefa;
import com.example.api.service.TarefaService;

@RestController
@RequestMapping("/tarefas")
public class TarefaController {
	
	@Autowired
	TarefaService tarefaService;
	
	private AmazonClient amazonClient;
	
	@Autowired
	public TarefaController(AmazonClient amazonClient) {
		this.amazonClient = amazonClient;
		
	}

	@GetMapping
	public List<Tarefa> listar() {
		return tarefaService.listarTarefa();
	}
	
	@PostMapping
	public ResponseEntity<Tarefa> cadastrar(@RequestBody Tarefa tarefa) {
		return new ResponseEntity<>(tarefaService.cadastrarTarefa(tarefa), HttpStatus.CREATED);
	}
	
	@DeleteMapping
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deletar(@RequestParam(name="tarefa_ID") Long id){
		tarefaService.deletarTarefa(id);
	}
	
	@PostMapping ("upload")
	public String upload (@RequestPart(value="file") MultipartFile file) {
		return amazonClient.processaArquivo(file, null);
		
	}
	

}
