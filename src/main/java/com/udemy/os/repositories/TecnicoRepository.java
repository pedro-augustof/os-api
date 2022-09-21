package com.udemy.os.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.udemy.os.domain.Tecnico;

@Repository
public interface TecnicoRepository extends JpaRepository<Tecnico, Integer> {

}
