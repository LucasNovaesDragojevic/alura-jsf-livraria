package br.com.caelum.livraria.bean;

import java.io.Serializable;
import java.util.List;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import br.com.caelum.livraria.dao.AutorDao;
import br.com.caelum.livraria.modelo.Autor;
import br.com.caelum.livraria.util.RedirectView;

@Named
@ViewScoped
public class AutorBean implements Serializable {

	private static final long serialVersionUID = 3611840478350763974L;

	private Autor autor = new Autor();
	
	@Inject
	private AutorDao autorDao;
	
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
			this.autor = autorDao.buscaPorId(autorId);
	}
	
	public List<Autor> getAutores() {
		return autorDao.listaTodos();
	}

	public RedirectView gravar() {
		System.out.println("Gravando autor " + this.autor.getNome());

		if (autor.getId() == null) {
			autorDao.adiciona(this.autor);
		}
		else {
			autorDao.atualiza(this.autor);
		}
			
		
		
		this.autor = new Autor();
		
		return new RedirectView("livro");
	}
	
	public void remover(Autor autor) {
		autorDao.remove(autor);
	}
}
