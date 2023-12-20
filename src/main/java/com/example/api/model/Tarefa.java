package com.example.api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity

public class Tarefa {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id;
	String tarefa;
	String status;
	String fotoS3;
	
	public String getFotoS3() {
		return fotoS3;
	}
	public void setFotoS3(String fotoS3) {
		this.fotoS3 = fotoS3;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTarefa() {
		return tarefa;
	}
	public void setTarefa(String tarefa) {
		this.tarefa = tarefa;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	

}
