package br.com.caelum.livraria.util;

public class RedirectView {

	private String viewName;
	
	public RedirectView (String name) {
		this.viewName = name;
	}
	
	@Override
	public String toString() {
		return viewName + "?faces-redirect=true";
	}
}
