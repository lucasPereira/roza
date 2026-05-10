package br.ufsc.saas.teste.selenium.mec.graficos;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.ufsc.saas.entidade.Instancia;
import br.ufsc.saas.entidade.saas.Usuario;
import br.ufsc.saas.fachada.EmBanco;
import br.ufsc.saas.fachada.EmSaas;
import br.ufsc.saas.teste.selenium.EmTestePagina;
import br.ufsc.saas.teste.selenium.SeleniumSaas;

public class TesteTelaGraficos {

	private SeleniumSaas selenium;
	private Usuario beatriz;
	private Usuario juliana;

	@Before
	public void setup() throws Exception {
		Instancia teste = EmBanco.novaInstancia();
		EmBanco.get(teste).cadastrarAdmin(beatriz);
		EmSaas.get(beatriz).cadastrarOUsuarioMec(juliana);

		selenium = EmTestePagina.selenium();
		EmTestePagina.get(selenium).trocarInstancia(teste);
		selenium.acessar();
		selenium.digitar("loge:usuario", juliana.getLogin());
		selenium.digitar("loge:senha", juliana.getSenha());
		selenium.clicar("loge:logar");
	}

	@Test
	public void fic() {
		selenium.selecionarItemDeMenu("menu:graficos", "menu:graficosFic");
		selenium.assertHtmlTitleEquals("FIC · Metabase");
	}

	@Test
	public void estatistica() {
		selenium.selecionarItemDeMenu("menu:graficos", "menu:graficosEstatisticas");
		selenium.assertHtmlTitleEquals("Estatísticas de dados SAAS · Metabase");
	}

	@Test
	public void ofertasCurso() throws Exception {
		selenium.selecionarItemDeMenu("menu:graficos", "menu:graficosOfertasCurso");
		selenium.assertHtmlTitleEquals("Ofertas de cursos avaliadas por região ao longo dos períodos · Metabase");
	}

	@Test
	public void emails() throws Exception {
		selenium.selecionarItemDeMenu("menu:graficos", "menu:graficosEmails");
		selenium.assertHtmlTitleEquals("Emails enviados do SAAS · Metabase");
	}

	@After
	public void tearDown() {
		selenium.fechar();
	}

}
