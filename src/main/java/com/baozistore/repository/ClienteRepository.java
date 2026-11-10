package com.baozistore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.baozistore.model.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
