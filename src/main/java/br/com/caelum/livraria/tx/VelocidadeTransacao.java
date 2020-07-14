package br.com.caelum.livraria.tx;

import java.io.Serializable;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

@Log
@Interceptor
public class VelocidadeTransacao implements Serializable {
	
	private static final long serialVersionUID = 3232956120611484707L;

	@AroundInvoke
	public Object cronometro(InvocationContext contexto) throws Exception {
		Long inicio = System.currentTimeMillis();
		String metodoNome = contexto.getMethod().getName();
		Object cronometro = contexto.proceed();
		Long fim = System.currentTimeMillis();
		Long resultado = fim - inicio;
		System.out.println("[INFO] Método executado: " + metodoNome);
		System.out.println("[INFO] Inicio: " + inicio);
		System.out.println("[INFO] Fim: " + fim);
		System.out.println("[INFO] Duração: " + resultado);
		return cronometro;
	}
}
