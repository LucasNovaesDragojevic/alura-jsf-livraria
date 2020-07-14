package br.com.caelum.livraria.bean;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.caelum.livraria.dao.UsuarioDao;
import br.com.caelum.livraria.modelo.Usuario;
import br.com.caelum.livraria.util.RedirectView;

@Named
@ViewScoped
public class LoginBean implements Serializable {

	private static final long serialVersionUID = 6103599225396622644L;
	
	private Usuario usuario = new Usuario();
	
	@Inject
	UsuarioDao usuarioDao;
	
	@Inject
	FacesContext context;
	
	public Usuario getUsuario() {
		return this.usuario;
	}
	
	public RedirectView efetuaLogin() {
		if (usuarioDao.existe(this.usuario)) {
			context.getExternalContext().getSessionMap().put("usuarioLogado", this.usuario);
			return new RedirectView("livro");
		}
		context.getExternalContext().getFlash().setKeepMessages(true);
		context.addMessage(null, new FacesMessage("Usuário não encontrado"));
		return new RedirectView("login");
	}
	
	public String deslogar() {
		context.getExternalContext().getSessionMap().remove("usuarioLogado");
		return "login?faces-redirect=true";
	}
}
