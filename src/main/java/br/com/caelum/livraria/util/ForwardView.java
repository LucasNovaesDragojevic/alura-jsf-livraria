package br.com.caelum.livraria.util;

public class ForwardView {

	private String viewName;
	
	public ForwardView (String name) {
		this.viewName = name;
	}
	
	@Override
	public String toString() {
		return viewName;
	}
}
