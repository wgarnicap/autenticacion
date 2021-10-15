package com.autenticacion.controller;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.autenticacion.dao.DaoConsulta;
import com.autenticacion.dto.JwtDto;
import com.autenticacion.dto.LoginUsuario;
import com.autenticacion.dto.NuevoUsuario;
import com.autenticacion.dto.SolicitudValidacionToken;
import com.autenticacion.entity.Rol;
import com.autenticacion.entity.Token;
import com.autenticacion.entity.Usuario;
import com.autenticacion.entity.UsuarioPrincipal;
import com.autenticacion.enums.RolNombre;
import com.autenticacion.jwt.JwtProvider;
import com.autenticacion.repository.IRolRepository;
import com.autenticacion.repository.ITokenRepository;
import com.autenticacion.repository.IUsuarioRepository;
import com.autenticacion.service.RolService;
import com.autenticacion.service.TokenService;
import com.autenticacion.service.UserDetailService;
import com.autenticacion.service.UsuarioService;
import com.autenticacion.utils.Mensaje;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {

	@Autowired
	UserDetailService userdetailservice;

	@Autowired
	ITokenRepository repoToken;

	@Autowired
	DaoConsulta dao;

	@Autowired
	IRolRepository repoRol;

	@Autowired
	IUsuarioRepository repoUsu;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UsuarioService usuarioService;

	@Autowired
	RolService rolService;

	@Autowired
	JwtProvider jwtProvider;
	
	@Autowired
	TokenService tokenservice;

	@Value("${jwt.expiration}")
	private int expiration;
	
	@PostMapping("/nuevo")
	public ResponseEntity<?> nuevo(@Valid @RequestBody NuevoUsuario nuevoUsuario, BindingResult bindingResult) {

				
		if (bindingResult.hasErrors())
			return new ResponseEntity(new Mensaje("Campos Mal Puestos o email invalido"), HttpStatus.BAD_REQUEST);

		if (usuarioService.existsByNombreUsuario(nuevoUsuario.getNombreUsuario()))
			return new ResponseEntity(new Mensaje("Ese nombre ya existe"), HttpStatus.BAD_REQUEST);

		if (usuarioService.existsByEmail(nuevoUsuario.getEmail()))
			return new ResponseEntity(new Mensaje("Ese email ya existe"), HttpStatus.BAD_REQUEST);

		Usuario usuario = new Usuario(nuevoUsuario.getNombre(), nuevoUsuario.getNombreUsuario(),
				nuevoUsuario.getEmail(), passwordEncoder.encode(nuevoUsuario.getPassword()));

		Set<Rol> roles = new HashSet<>();
		roles.add(rolService.getByRolNombre(RolNombre.ROLE_USER).get());
		if (nuevoUsuario.getRoles().contains("admin"))
			roles.add(rolService.getByRolNombre(RolNombre.ROLE_ADMIN).get());

		usuario.setRoles(roles);

		usuarioService.save(usuario);

		return new ResponseEntity(new Mensaje("Usuario Guardado"), HttpStatus.CREATED);

	}

	@PostMapping("/login")
	public ResponseEntity<JwtDto> login(@Valid @RequestBody LoginUsuario loginUsuario, BindingResult bindingResult) {

		if(!loginUsuario.getKey().equals("llave")) {
			
			return new ResponseEntity(new Mensaje("Ingrese key correcta"), HttpStatus.BAD_REQUEST);
		}
		
		if (bindingResult.hasErrors())

			return new ResponseEntity(new Mensaje("Campos Mal Puestos"), HttpStatus.BAD_REQUEST);

		Authentication authentication =

				authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
						loginUsuario.getNombreUsuario(), loginUsuario.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		String jwt = jwtProvider.generatedToken(authentication);

		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		
		JwtDto jwtDto = new JwtDto(jwt, userDetails.getUsername(), userDetails.getAuthorities());

		Token token = new Token();

		token.setToken(jwt);

		token.setUsername(jwtDto.getNombreUsuario());
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date ());
		calendar.add(calendar.MINUTE,30);
		Date expiracion = calendar.getTime();
		token.setFechaExpiracion(expiracion);

		repoToken.save(token);
		tokenservice.borrarTokenVencidos();
		


		return new ResponseEntity(jwtDto, HttpStatus.OK);

	}

	@PostMapping("/validaciontoken")
	public boolean validacionToken(@RequestBody SolicitudValidacionToken solicitud) throws Exception {

		if (solicitud == null) {
			return false;
		}

		String token = solicitud.getToken();

		List<Token> busqueda = repoToken.findByToken(token);

		if (busqueda == null || busqueda.isEmpty()) {
			return false;
		}

		boolean permiso = dao.getUsuario(solicitud);
		
		return permiso;
	}

}
