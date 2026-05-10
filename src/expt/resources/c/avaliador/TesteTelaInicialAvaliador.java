package br.ufsc.saas.teste.selenium.avaliador;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.ufsc.saas.entidade.Instancia;
import br.ufsc.saas.entidade.instituicao.Avaliador;
import br.ufsc.saas.entidade.mantenedora.CoordenadorGeralEtec;
import br.ufsc.saas.entidade.saas.Instituicao;
import br.ufsc.saas.entidade.saas.Mantenedora;
import br.ufsc.saas.entidade.saas.Usuario;
import br.ufsc.saas.fachada.EmBanco;
import br.ufsc.saas.fachada.EmInstituicao;
import br.ufsc.saas.fachada.EmMantenedora;
import br.ufsc.saas.fachada.EmSaas;
import br.ufsc.saas.teste.selenium.EmTestePagina;
import br.ufsc.saas.teste.selenium.SeleniumSaas;

public class TesteTelaInicialAvaliador {

	private SeleniumSaas selenium;
	private Avaliador marina;
	private Usuario beatriz;
	private Instituicao ufsc;
	private Mantenedora ufscMantenedora;
	private Usuario douglas;
	private CoordenadorGeralEtec marinaGeralEtec;

	@Before
	public void setUp() throws Exception {
		Instancia teste = EmBanco.novaInstancia();
		EmBanco.get(teste).cadastrarAdmin(beatriz);
		EmSaas.get(beatriz).cadastrarMantenedora(ufscMantenedora);
		EmSaas.get(beatriz).cadastrarInstituicao(ufsc);
		EmSaas.get(beatriz).cadastrarOGerente(douglas);
		EmInstituicao.get(douglas).cadastrarAvaliador(marina);
		EmMantenedora.get(ufscMantenedora).cadastrarCoordenadorGeralEtec(marinaGeralEtec);
		EmBanco.get(teste).sobreAutenticacao().atualizarSenhaAvaliador("senha", marina);

		selenium = EmTestePagina.selenium();
		EmTestePagina.get(selenium).trocarInstancia(teste);
		selenium.acessar();
		selenium.digitar("loge:usuario", marina.getEmail().toString());
		selenium.digitar("loge:senha", "senha");
		selenium.clicar("loge:logar");
	}

	@Test
	public void sairDaPaginaDoAvaliador() throws Exception {
		selenium.assertUrlEndsWith("avaliador.jsf");
		selenium.clicar("sair");
		selenium.assertUrlEndsWith("/");
	}

	@Test
	public void menuResultado() throws Exception {
		selenium.selecionarItemDeMenu("menu:resultados","menu:avaliacao");
		
		selenium.assertTextEquals("Resultados", "titulo");
	}

	@Test
	public void doResultadoParaPendencias() throws Exception {
		selenium.selecionarItemDeMenu("menu:resultados","menu:avaliacao");
		selenium.selecionarItemDeMenu("menu:questionario","menu:pendentes");
		
		selenium.assertTextEquals("Questionários", "titulo");
		selenium.assertTextEquals("Pendentes", "pendentesTitulo");
	}

	@Test
	public void telaAvaliador() throws Exception {
		selenium.assertUrlEndsWith("avaliador.jsf");
		selenium.assertHtmlTitleEquals("SAAS - Avaliador");
		selenium.assertTextEquals("Questionários", "titulo");
		selenium.assertTextEquals("Pendentes", "pendentesTitulo");
		selenium.assertTextEquals("Foco", "form:pendencias:foco_label");
		selenium.assertTextEquals("Assunto", "form:pendencias:assunto_label");
		selenium.assertTextEquals("Perfil", "form:pendencias:perfil_label");
		selenium.assertTextEquals("Data de encerramento", "form:pendencias:limite_label");
		selenium.assertTextEquals("Deseja responder agora?", "form:pendencias:responder_label");
		selenium.assertTextEquals("Nenhuma avaliação pendente", "form:pendencias_data");
	}
	
	@Test
	public void telaConcluidos() throws Exception {
		selenium.selecionarItemDeMenu("menu:questionario", "menu:concluidos");
		
		selenium.assertUrlEndsWith("avaliador.jsf");
		selenium.assertHtmlTitleEquals("SAAS - Avaliador");
		selenium.assertTextEquals("Avaliações", "titulo");
		selenium.assertTextEquals("Concluídas", "concluidasTitulo");
		selenium.assertTextEquals("Foco", "form:concluidas:foco_label");
		selenium.assertTextEquals("Assunto", "form:concluidas:assunto_label");
		selenium.assertTextEquals("Perfil", "form:concluidas:perfil_label");
		selenium.assertTextEquals("Período", "form:concluidas:periodo_label");
		selenium.assertTextEquals("Data de encerramento", "form:concluidas:limite_label");
		selenium.assertTextEquals("Respondido", "form:concluidas:respondido_label");
		selenium.assertTextEquals("Nenhuma avaliação concluída", "form:concluidas_data");
	}

	@Test
	public void existeLinkMoodle() throws Exception {
		selenium.abrirMenu("menu:gestao");
		
		selenium.assertTextEquals("Fórum", "menu:acessarMoodle");
	}

	@Test
	public void trilhaDeMigalhas() throws Exception {
		selenium.assertElementIsVisible("menuForm:inicio");
		selenium.assertTextEquals("Avaliações", "menuForm:avaliacoes");
	}

	@After
	public void tearDown() {
		selenium.fechar();
	}

}
