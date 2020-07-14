package br.com.caelum.livraria.tx;

import java.io.Serializable;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.persistence.EntityManager;

@Transacional
@Interceptor
public class GerenciadorTransacao implements Serializable {

	private static final long serialVersionUID = 728459704605503408L;
	
	@Inject
	EntityManager em;
	
	@AroundInvoke
	public Object executaTX(InvocationContext contexto) throws Exception {
		em.getTransaction().begin();
		System.out.println("[INFO] Transação iniciada");
		Object resultado = contexto.proceed();
		em.getTransaction().commit();
		System.out.println("[INFO] Transação comitada");
		return resultado;
	}
}
