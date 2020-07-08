package br.com.caelum.livraria.bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;

import br.com.caelum.livraria.dao.DAO;
import br.com.caelum.livraria.modelo.Livro;
import br.com.caelum.livraria.modelo.Venda;

@ManagedBean
@ViewScoped
public class VendasBean {
	
	private List<Venda> getVendas(Long seed) {
		List<Livro> livros = new DAO<Livro>(Livro.class).listaTodos();
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
		xAxis.setLabel("Título");
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
