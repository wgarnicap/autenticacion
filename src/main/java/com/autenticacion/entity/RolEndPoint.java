package com.autenticacion.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="rol_permiso_endpoint")
public class RolEndPoint {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="id_rol_permiso_endpoint")
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="idRol")	
	private Rol rol;
	
	@ManyToOne
	@JoinColumn(name="idPermiso")	
	private PermisoEndPoint EndPoint;

	public RolEndPoint(Long id, Rol rol, PermisoEndPoint endPoint) {
		super();
		this.id = id;
		this.rol = rol;
		EndPoint = endPoint;
	}

	public RolEndPoint() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Rol getRol() {
		return rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}

	public PermisoEndPoint getEndPoint() {
		return EndPoint;
	}

	public void setEndPoint(PermisoEndPoint endPoint) {
		EndPoint = endPoint;
	}
	
	
	
}
