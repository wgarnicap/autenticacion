package com.autenticacion.entity;



import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="token")
public class Token {

	@Id	
	@Column(name="token")
	private String token;
	@Column(name="username")
	private String username;
	@Column(name="fecha_expiracion")
	private Date fechaExpiracion;
	
	

	public Token(String token, String username, Date fechaExpiracion) {
		super();
		this.token = token;
		this.username = username;
		this.fechaExpiracion = fechaExpiracion;
	}

	public Token() {
		super();
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Date getFechaExpiracion() {
		return fechaExpiracion;
	}

	public void setFechaExpiracion(Date fechaExpiracion) {
		this.fechaExpiracion = fechaExpiracion;
	}
	



	
	
	
}
