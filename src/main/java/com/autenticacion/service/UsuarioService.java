package com.autenticacion.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.autenticacion.entity.Usuario;
import com.autenticacion.repository.IUsuarioRepository;

@Service
@Transactional
public class UsuarioService {

	@Autowired
	IUsuarioRepository usuariorepo;

	public Optional<Usuario> getByNombreUsuario(String nombreUsuario) {

		return usuariorepo.findByNombreUsuario(nombreUsuario);
	}

	public boolean existsByNombreUsuario(String nombreUsuario) {

		return usuariorepo.existsByNombreUsuario(nombreUsuario);
	}

	public boolean existsByEmail(String email) {

		return usuariorepo.existsByEmail(email);
	}
	public void save (Usuario usuario) {
		
		usuariorepo.save(usuario);
	}

}
