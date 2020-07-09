package br.com.caelum.livraria.bean;

import java.io.Serializable;

import java.util.List;

import javax.faces.view.ViewScoped;

import javax.inject.Named;

import br.com.caelum.livraria.dao.DAO;
import br.com.caelum.livraria.modelo.Autor;
import br.com.caelum.livraria.util.RedirectView;

@Named
@ViewScoped
public class AutorBean implements Serializable {

	/**
	 *  Serialização gerada automaticamente pelo eclipse
	 */
	private static final long serialVersionUID = 3611840478350763974L;

	private Autor autor = new Autor();
	
	private Integer autorId;
	
	public Autor getAutor() {
		return autor;
	}
	
	public void setAutor(Autor autor) {
		this.autor = autor;
	}
	
	public Integer getAutorId() {
		return autorId;
	}

	public void setAutorId(Integer autorId) {
		this.autorId = autorId;
	}
	
	public void carregarAutorPeloId() {
		if (autorId != null)
			this.autor = new DAO<Autor>(Autor.class).buscaPorId(autorId);
	}
	
	public List<Autor> getAutores() {
		return new DAO<Autor>(Autor.class).listaTodos();
	}

	public RedirectView gravar() {
		System.out.println("Gravando autor " + this.autor.getNome());

		if (autor.getId() == null) {
			new DAO<Autor>(Autor.class).adiciona(this.autor);
		}
		else {
			new DAO<Autor>(Autor.class).atualiza(this.autor);
		}
			
		
		
		this.autor = new Autor();
		
		return new RedirectView("livro");
	}
	
	public void remover(Autor autor) {
		new DAO<Autor>(Autor.class).remove(autor);
	}
}
