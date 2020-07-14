package br.com.caelum.livraria.bean;

import java.io.Serializable;
import java.util.List;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.Axis;
import org.primefaces.model.chart.AxisType;
import org.primefaces.model.chart.BarChartModel;
import br.com.caelum.livraria.modelo.Venda;

@Named
@ViewScoped
public class VendasBean implements Serializable {

	private static final long serialVersionUID = 5819768201146218206L;

	@Inject
	EntityManager em;
	
	private List<Venda> getVendas() {
		TypedQuery<Venda> vendasBanco = em.createQuery("SELECT v FROM Venda v", Venda.class);
		List<Venda> vendas = vendasBanco.getResultList();
		return vendas;
	}
	
	public BarChartModel getVendasModel() {
		BarChartModel model = new BarChartModel();
		ChartSeries vendas = new ChartSeries();
		Axis xAxis = model.getAxis(AxisType.X);
		Axis yAxis = model.getAxis(AxisType.Y);
		xAxis.setLabel("Título");
		yAxis.setLabel("Quantidade");
		vendas.setLabel("Vendas 2020");
		List<Venda> vendasBanco = getVendas();
		for (Venda venda : vendasBanco) {
			vendas.set(venda.getLivro().getTitulo(), venda.getQuantidade());
		}
		model.setTitle("Vendas");
		model.setLegendPosition("ne");
		model.setAnimate(true);
		model.addSeries(vendas);
		return model;
	}
}
