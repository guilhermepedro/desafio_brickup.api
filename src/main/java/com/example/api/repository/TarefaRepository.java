package com.example.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.api.model.Tarefa;

public interface TarefaRepository extends JpaRepository<Tarefa, Long> {

}
