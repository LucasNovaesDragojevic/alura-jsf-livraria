package br.com.caelum.livraria.dao;

import java.io.Serializable;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import br.com.caelum.livraria.modelo.Usuario;

public class UsuarioDao implements Serializable {

	private static final long serialVersionUID = 1324743295664097809L;

	@Inject
	EntityManager em;
	
	public Boolean existe(Usuario usuario) {
		TypedQuery<Usuario> query = em.createQuery("SELECT u FROM Usuario u WHERE u.email = :pEmail AND u.senha = :pSenha", Usuario.class)
				.setParameter("pEmail", usuario.getEmail())
				.setParameter("pSenha", usuario.getSenha());
		try {
			Usuario resultado = query.getSingleResult();
			return true;
		} catch (NoResultException noResultException) {
			return false;
		}
	}
}
