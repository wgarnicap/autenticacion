package com.autenticacion.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "permiso_endpoint")
public class PermisoEndPoint {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idPermiso")
	private Long idPermiso;
	@Column(name = "nombre_backend")
	private String nombreBackend;
	@Column(name = "endpoint")
	private String endpoint;
	@Column(name = "metodo_http")
	private String metodoHttp;

	public PermisoEndPoint(Long idPermiso, String nombreBackend, String endpoint, String metodoHttp) {
		super();
		this.idPermiso = idPermiso;
		this.nombreBackend = nombreBackend;
		this.endpoint = endpoint;
		this.metodoHttp = metodoHttp;
	}
	
	

	public PermisoEndPoint() {
		super();
	}



	public Long getIdPermiso() {
		return idPermiso;
	}

	public void setIdPermiso(Long idPermiso) {
		this.idPermiso = idPermiso;
	}

	public String getNombreBackend() {
		return nombreBackend;
	}

	public void setNombreBackend(String nombreBackend) {
		this.nombreBackend = nombreBackend;
	}

	public String getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}

	public String getMetodoHttp() {
		return metodoHttp;
	}

	public void setMetodoHttp(String metodoHttp) {
		this.metodoHttp = metodoHttp;
	}

}
