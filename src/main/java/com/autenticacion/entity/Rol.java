package com.autenticacion.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.autenticacion.enums.RolNombre;
import com.sun.istack.NotNull;

@Entity
public class Rol {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long idRol;
	@NotNull
	@Enumerated(EnumType.STRING)
	private RolNombre rolNombre;

	@OneToMany(fetch = FetchType.LAZY,mappedBy = "rol")
	private Set<RolEndPoint> rolesEndpoint = new HashSet<>();

	public Rol() {

	}

	public Rol(RolNombre rolNombre) {

		this.rolNombre = rolNombre;
	}

	public Long getId() {
		return idRol;
	}

	public void setId(Long id) {
		this.idRol = id;
	}

	public RolNombre getRolNombre() {
		return rolNombre;
	}

	public void setRolNombre(RolNombre rolNombre) {
		this.rolNombre = rolNombre;
	}

	public Long getIdRol() {
		return idRol;
	}

	public void setIdRol(Long idRol) {
		this.idRol = idRol;
	}

}
