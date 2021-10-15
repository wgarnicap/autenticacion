package com.autenticacion.repository;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.autenticacion.entity.Token;

@Service
@Transactional
@Repository
public interface ITokenRepository extends JpaRepository<Token, Long> {

	public List<Token> findByToken(String token);
	
	public List<Token> findByFechaExpiracionBetween (Date desde,Date hasta);
	

	
}
