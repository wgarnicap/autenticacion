package com.autenticacion.dto;

public class SolicitudValidacionToken {

		
		private String token;
		
		private String nombreUsuario;
		
		private String metodoHTTP;
		
		private String endpoint;
		
		private String nombreBackend;

		public SolicitudValidacionToken(String token, String nombreUsuario, String metodoHTTP, String endpoint,
				String nombreBackend) {
			super();
			this.token = token;
			this.nombreUsuario = nombreUsuario;
			this.metodoHTTP = metodoHTTP;
			this.endpoint = endpoint;
			this.nombreBackend = nombreBackend;
		}

		public SolicitudValidacionToken() {
			super();
		}

		public String getToken() {
			return token;
		}

		public void setToken(String token) {
			this.token = token;
		}

		public String getNombreUsuario() {
			return nombreUsuario;
		}

		public void setNombreUsuario(String nombreUsuario) {
			this.nombreUsuario = nombreUsuario;
		}

		public String getMetodoHTTP() {
			return metodoHTTP;
		}

		public void setMetodoHTTP(String metodoHTTP) {
			this.metodoHTTP = metodoHTTP;
		}

		public String getEndpoint() {
			return endpoint;
		}

		public void setEndpoint(String endpoint) {
			this.endpoint = endpoint;
		}

		public String getNombreBackend() {
			return nombreBackend;
		}

		public void setNombreBackend(String nombreBackend) {
			this.nombreBackend = nombreBackend;
		}
		
		
		
}
