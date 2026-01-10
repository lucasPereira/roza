package br.ufsc.saas.teste.selenium.mec;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;

import br.ufsc.saas.entidade.Instancia;
import br.ufsc.saas.entidade.saas.Usuario;
import br.ufsc.saas.fachada.EmBanco;
import br.ufsc.saas.fachada.EmSaas;
import br.ufsc.saas.teste.selenium.EmTestePagina;
import br.ufsc.saas.teste.selenium.SeleniumSaas;

public class TesteTelaMenuMec {

	private SeleniumSaas selenium;
	private Usuario beatriz;
	private Usuario juliana;

	@Before
	public void setUp() throws Exception {
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
	public void inicial() throws Exception {
		selenium.assertTextEquals("Bem-vindo, Juliana", "uTa:bemvindo");
		selenium.assertTextEquals("SAIR", "sair");
	}

	@Test
	public void noMenuDoUsuarioMecTem() throws Exception {
		selenium.assertTextEquals("Usuários", "menu:usuarios");
		selenium.assertTextEquals("Acompanhamento", "menu:acompanhamento");
		selenium.assertTextEquals("Resultados", "menu:resultados");
		selenium.assertTextEquals("Gestão", "menu:gestao");
		selenium.assertTextEquals("Gráficos", "menu:graficos");
		selenium.assertAmountOfElements(5, By.cssSelector("[id='menu:bar'] > ul > li"));
	}
	
	@Test
	public void oSubMenuUsuarios() throws Exception {
		selenium.abrirMenu("menu:usuarios");
		
		selenium.assertTextEquals("Gerentes", "menu:gerentes");
		selenium.assertAmountOfElements(1, By.cssSelector("[id='menu:usuarios'] > ul > li"));
	}
	
	@Test
	public void oSubMenuAcompanhamento() throws Exception {
		selenium.abrirMenu("menu:acompanhamento");
		
		selenium.assertTextEquals("Participação", "menu:participacao");
		selenium.assertTextEquals("Relatório", "menu:relatorio");
		selenium.assertAmountOfElements(2, By.cssSelector("[id='menu:acompanhamento'] > ul > li"));
	}
	
	@Test
	public void oSubmenuResultados() throws Exception {
		selenium.abrirMenu("menu:resultados");
		
		selenium.assertTextEquals("Avaliação", "menu:avaliacao");
		selenium.assertTextEquals("Egressos", "menu:egressos");
		selenium.assertAmountOfElements(2, By.cssSelector("[id='menu:resultados'] > ul > li"));
	}
	
	@Test
	public void oSubMenuGestao() throws Exception {
		selenium.abrirMenu("menu:gestao");
		
		selenium.assertTextEquals("Resultados", "menu:resultados");
		selenium.assertTextEquals("Fórum", "menu:acessarMoodle");
		selenium.assertAmountOfElements(2, By.cssSelector("[id='menu:gestao'] > ul > li"));
	}
	
	@Test
	public void oSubMenuGraficos() throws Exception {
		selenium.abrirMenu("menu:graficos");
		
		selenium.assertTextEquals("FIC", "menu:graficosFic");
		selenium.assertTextEquals("Estatísticas", "menu:graficosEstatisticas");
		selenium.assertTextEquals("Ofertas de curso", "menu:graficosOfertasCurso");
		selenium.assertTextEquals("Emails", "menu:graficosEmails");
		selenium.assertAmountOfElements(4, By.cssSelector("[id='menu:graficos'] > ul > li"));
	}

	@After
	public void tearDown() {
		selenium.fechar();
	}

}
