package br.com.caelum.livraria.bean;

import java.io.Serializable;
import java.util.List;
import java.util.Locale;
import javax.inject.Inject;
import javax.inject.Named;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import br.com.caelum.livraria.dao.AutorDao;
import br.com.caelum.livraria.dao.LivroDao;
import br.com.caelum.livraria.modelo.Autor;
import br.com.caelum.livraria.modelo.Livro;
import br.com.caelum.livraria.tx.Log;
import br.com.caelum.livraria.tx.Transacional;

@Named
@ViewScoped
public class LivroBean implements Serializable {

	/**
	 * Serialização gerada automaticamente pelo eclipse
	 */
	private static final long serialVersionUID = 1022452390851343337L;

	private Livro livro = new Livro();

	private Integer autorId;
	
	private Integer livroId;

	private List<Livro> livros;
	
	@Inject
	LivroDao livroDao;
	
	@Inject
	AutorDao autorDao;

	public Integer getAutorId() {
		return autorId;
	}

	public void setAutorId(Integer autorId) {
		this.autorId = autorId;
	}
	
	public Integer getLivroId() {
		return livroId;
	}

	public void setLivroId(Integer livroId) {
		this.livroId = livroId;
	}

	public Livro getLivro() {
		return livro;
	}
	
	public void setLivro(Livro livro) {
		this.livro = livro;
	}
	
	public List<Livro> getLivros() {
		if (this.livros == null) {
			this.livros = livroDao.listaTodos();
		}
		System.out.println("Mï¿½todo getLivros: " + livros.toString());
		return livros;
	}

	public List<Autor> getAutores() {
		return autorDao.listaTodos();
	}

	public List<Autor> getAutoresDoLivro() {
		return this.livro.getAutores();
	}

	@Transacional
	@Log
	public void gravarAutor() {
		Autor autor = autorDao.buscaPorId(this.autorId);
		this.livro.adicionaAutor(autor);
	}
	
	@Transacional
	@Log
	public void gravar() {
		System.out.println("Gravando livro " + this.livro.getTitulo());
		if (livro.getAutores().isEmpty()) {
			//throw new RuntimeException("Livro deve ter pelo menos um Autor.");
			FacesContext.getCurrentInstance().addMessage("autor", new FacesMessage("Livro deve ter pelo menos um Autor"));
		}
		if (this.livro.getId() == null) {
			livroDao.adiciona(this.livro);
			this.livros = livroDao.listaTodos();
		}
		else {
			livroDao.atualiza(this.livro);
		}
		this.livro = new Livro();
	}
	
	@Transacional
	@Log
	public void remover(Livro livro) {
		System.out.println("Removendo livro.");
		livroDao.remove(livro);	
	}
	
	public void removerAutorDoLivro(Autor autor) {
		this.livro.removeAutor(autor);
	}
	
	public void carregar(Livro livro) {
		System.out.println("Carregando livro.");
		this.livro = livro;
	}

	public String formAutor() {
		System.out.println("Chamando o formulÃ¡rio do Autor");
		return "autor?faces-redirect=true";
	}
	
	public void comecaComDigitoUm(FacesContext facesContext, UIComponent uiComponent, Object value) throws ValidatorException {
		String valor = value.toString();
		if (!valor.startsWith("1")) {
			throw new ValidatorException(new FacesMessage("ISBN deve comeÃ§ar com 1"));
		}
	}
	
	public void carregarPeloId() {
		if (livroId != null)
			this.livro = livroDao.buscaPorId(livroId);
	}
	
	public Boolean precoEhMenor(Object valorColuna, Object filtroDigitado, Locale locale) {
		String textoDigitado = (filtroDigitado == null) ? null : filtroDigitado.toString().trim();
		System.out.println("Filtrando pelo " + textoDigitado + ", Valor do elemento: " +  valorColuna);
		if (textoDigitado == null || textoDigitado.equals("")) {
			return true;
		}
		if (valorColuna == null) {
			return false;
		}
		try {
			Double precoDigitado = Double.valueOf(textoDigitado);
			Double precoColuna = (Double) valorColuna;
			return precoColuna.compareTo(precoDigitado) < 0;
		} catch (NumberFormatException e) {
			return false;
		}
	}
}