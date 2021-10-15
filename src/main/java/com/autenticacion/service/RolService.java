package com.autenticacion.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autenticacion.entity.Rol;
import com.autenticacion.enums.RolNombre;
import com.autenticacion.repository.IRolRepository;

@Service
@Transactional
public class RolService {
	
	@Autowired
	IRolRepository rolrepo;
	
	public Optional<Rol> getByRolNombre (RolNombre rolNombre){
		return rolrepo.findByRolNombre(rolNombre);
	}
	
	public void save(Rol rol) {
		
		rolrepo.save(rol);
	}

}
