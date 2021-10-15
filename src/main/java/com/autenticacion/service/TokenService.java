package com.autenticacion.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autenticacion.repository.ITokenRepository;
import com.autenticacion.entity.Token;

@Service
@Transactional
public class TokenService {


	@Autowired
	ITokenRepository repotoken;
	

	public void borrarTokenVencidos () {
		
		Date date = new Date (0000-00-00);
		
		List<Token> tokensVencidos =  repotoken.findByFechaExpiracionBetween(date,new Date());
		
		repotoken.deleteAll(tokensVencidos);
	

	}


}
