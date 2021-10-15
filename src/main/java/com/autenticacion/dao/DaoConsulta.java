package com.autenticacion.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.autenticacion.dto.SolicitudValidacionToken;

@Service
@Transactional
public class DaoConsulta {

	String consulta = "Select 1\r\n" + "from Usuario u\r\n" + "join usuario_rol ur on (ur.usuario_id = u.id)\r\n"
			+ "join Rol r on (r.idRol = ur.rol_id)\r\n"
			+ "left join rol_permiso_endpoint rpe on (rpe.idRol=r.idRol)\r\n"
			+ "left join permiso_endpoint pe on (pe.idPermiso=rpe.idPermiso)\r\n"
			+ "where u.nombreUsuario ='wilmer'\r\n" + "and pe.nombre_backend ='api'\r\n"
			+ "and pe.endpoint = 'api/v1/tasklist'\r\n" + "and pe.metodo_http='POST' ";

	@PersistenceContext
	private EntityManager em;

	public boolean getUsuario(SolicitudValidacionToken solicitud) throws Exception {

		if (solicitud == null || solicitud.getNombreUsuario() == null || solicitud.getMetodoHTTP() == null
				|| solicitud.getEndpoint() == null || solicitud.getNombreBackend() == null) {

			return false;
		}
		try {
			String quericito = "select 1 from Usuario u ";
			quericito+=" join u.roles r";
			quericito+=" join r.rolesEndpoint re";
			quericito+=" join re.EndPoint e";
			quericito += " where e.metodoHttp = '" + solicitud.getMetodoHTTP() + "'";
			quericito += " and e.endpoint = '" + solicitud.getEndpoint() + "'";
			quericito += " and e.nombreBackend = '" + solicitud.getNombreBackend() + "'";
			quericito += " and u.nombreUsuario = '" + solicitud.getNombreUsuario() + "'";
			Query query = this.em.createQuery(quericito);

			List<Object>resultados = query.getResultList();
			
			if(resultados!=null  && !resultados.isEmpty()) {
				
				//Nos devolvemos al controller
				return true;
			}
		} catch (Exception e) {
			// Si llega aqui es por que hubo error en la consulta
			e.printStackTrace();
			throw e;
			
		}

		// Si llega hasta aqui, es por que el usuario no tiene permisos
		return false;
	}

}
