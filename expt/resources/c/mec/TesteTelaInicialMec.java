package br.ufsc.saas.teste.selenium.mec;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.ufsc.saas.entidade.Instancia;
import br.ufsc.saas.entidade.saas.Usuario;
import br.ufsc.saas.fachada.EmBanco;
import br.ufsc.saas.fachada.EmSaas;
import br.ufsc.saas.teste.selenium.EmTestePagina;
import br.ufsc.saas.teste.selenium.SeleniumSaas;

public class TesteTelaInicialMec {

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
	public void cabecalhoDoUsuarioMec() throws Exception {
		selenium.assertTextEquals("Bem-vindo, Juliana", "uTa:bemvindo");
		selenium.assertTextEquals("SAIR", "sair");
	}

	@Test
	public void oTexto() throws Exception {
		selenium.assertTextEquals("MEC", "titulo");
		selenium.assertTextEquals("Prezado(a) Juliana, utilize os menus superiores para obter informações sobre:", "form:texto1");
		selenium.assertTextEquals("Gerentes: bolsistas que gerenciam o SAAS em cada uma das instituições;", "form:texto2");
		selenium.assertTextEquals("Relatório: dados de cursos, polos/unidades de ensino e avaliadores (estudantes, professores mediadores, professores e coordenadores) cadastrados em cada uma das instituições;", "form:texto3");
		selenium.assertTextEquals("Acompanhamento: dados sobre a participação de cada instituição nas diversas coletas.", "form:texto4");
		selenium.assertTextEquals("Avaliação: das avaliações realizadas a partir de 2013/2 nos focos curso, polo/unidade de ensino, disciplina, egressos e socioescolar;", "form:texto5");
		selenium.assertTextEquals("Egressos: acompanhamento das turmas formadas e sua trajetória no mercado de trabalho;", "form:texto6");
		selenium.assertTextEquals("Gestão: apresentação dos resultados da última coleta em um painel de cores;", "form:texto7");
	}

	@After
	public void tearDown() {
		selenium.fechar();
	}

}
