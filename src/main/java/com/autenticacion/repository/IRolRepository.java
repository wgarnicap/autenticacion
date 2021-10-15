package com.autenticacion.repository;	

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.autenticacion.entity.Rol;
import com.autenticacion.enums.RolNombre;
@Repository
public interface IRolRepository extends JpaRepository<Rol, Long>{
	
	Optional<Rol> findByRolNombre (RolNombre rolNombre);
	
	/*
	@Query(value="select u.nombreUsuario,r.rolNombre\n"
			+ "from Usuario u,Rol r\n"
			+ "where nombreUsuario=:username ")
	public List<Rol> findByUsername (@Param("username")String username);
*/
}
