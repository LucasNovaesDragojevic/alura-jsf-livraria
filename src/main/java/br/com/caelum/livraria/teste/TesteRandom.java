package br.com.caelum.livraria.teste;

import java.util.Random;

public class TesteRandom {

	public static void main(String[] args) {
		Random gerador = new Random();
		
		Integer resultado = gerador.nextInt(10);
		System.out.println(resultado);
		
		Integer resultado2 = gerador.nextInt(10);
		System.out.println(resultado2);
		
		Long milis = System.currentTimeMillis();
		Random geradorBoolean = new Random(milis);
		
		Boolean valor = geradorBoolean.nextBoolean();
		System.out.println(valor);
		
		Boolean valor2 = geradorBoolean.nextBoolean();
		System.out.println(valor2);
	}
}
