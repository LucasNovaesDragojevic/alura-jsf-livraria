package br.com.caelum.livraria.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import br.com.caelum.livraria.dao.LivroDao;
import br.com.caelum.livraria.modelo.Livro;
import br.com.caelum.livraria.modelo.Venda;

@Named
@ViewScoped
public class VendasBean implements Serializable {
	
	/**
	 * Serializa��o gerada automaticamente pelo eclipse
	 */
	private static final long serialVersionUID = 5819768201146218206L;
	
	@Inject
	LivroDao livroDao;

	private List<Venda> getVendas(Long seed) {
		List<Livro> livros = livroDao.listaTodos();
		List<Venda> vendas = new ArrayList<Venda>();
		Random random = new Random(seed);
		for (Livro livro : livros) {
			Integer quantidade = random.nextInt(500);
			vendas.add(new Venda(livro, quantidade));
		}
		return vendas;
	}
	
	public BarChartModel getVendasModel() {
		BarChartModel model = new BarChartModel();
		ChartSeries vendas2019 = new ChartSeries();
		ChartSeries vendas2020 = new ChartSeries();
		Axis xAxis = model.getAxis(AxisType.X);
		Axis yAxis = model.getAxis(AxisType.Y);
		xAxis.setLabel("T�tulo");
		yAxis.setLabel("Quantidade");
		vendas2019.setLabel("Vendas 2019");
		vendas2020.setLabel("Vendas 2020");
		List<Venda> vendas = getVendas(1234L);
		for (Venda venda : vendas) {
			vendas2019.set(venda.getLivro().getTitulo(), venda.getQuantidade());
		}
		vendas = getVendas(4321L);
		for (Venda venda : vendas) {
			vendas2020.set(venda.getLivro().getTitulo(), venda.getQuantidade());
		}
		model.setTitle("Vendas");
		model.setLegendPosition("ne");
		model.setAnimate(true);
		model.addSeries(vendas2019);
		model.addSeries(vendas2020);
		return model;
	}
}