package br.ufsc.saas.teste.selenium.mec.gerente.listar;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;

import br.ufsc.saas.entidade.Instancia;
import br.ufsc.saas.entidade.saas.Instituicao;
import br.ufsc.saas.entidade.saas.Mantenedora;
import br.ufsc.saas.entidade.saas.Usuario;
import br.ufsc.saas.fachada.EmBanco;
import br.ufsc.saas.fachada.EmSaas;
import br.ufsc.saas.teste.selenium.EmTestePagina;
import br.ufsc.saas.teste.selenium.SeleniumSaas;

public class TesteTelaListarGerente {

	private SeleniumSaas selenium;
	private Usuario beatriz;
	private Usuario juliana;
	private Instituicao ufsc;
	private Mantenedora ufscMantenedora;
	private Usuario lucasUfsc;
	private Usuario douglas;

	@Before
	public void setUp() throws Exception {
		Instancia teste = EmBanco.novaInstancia();
		EmBanco.get(teste).cadastrarAdmin(beatriz);
		EmSaas.get(beatriz).cadastrarOUsuarioMec(juliana);
		EmSaas.get(beatriz).cadastrarMantenedora(ufscMantenedora);
		EmSaas.get(beatriz).cadastrarInstituicao(ufsc);
		selenium = EmTestePagina.selenium();
		EmTestePagina.get(selenium).trocarInstancia(teste);

		selenium.acessar();
		selenium.digitar("loge:usuario", juliana.getLogin());
		selenium.digitar("loge:senha", juliana.getSenha());
		selenium.clicar("loge:logar");
	}

	@Test
	public void aTelaEh() throws Exception {
		selenium.selecionarItemDeMenu("menu:usuarios", "menu:gerentes");
		
		selenium.assertTextEquals("Gerentes", "titulo");
		selenium.assertTextEquals("Nome", "form:lista:nomeTitulo");
		selenium.assertTextEquals("E-mail", "form:lista:emailTitulo");
		selenium.assertTextEquals("CPF", "form:lista:cpfTitulo");
		selenium.assertTextEquals("Telefone", "form:lista:telefoneTitulo");
		selenium.assertTextEquals("Instituições", "form:lista:instituicoesTitulo");
		selenium.assertTextEquals("Gerentes", "titulo");
		selenium.assertTextEquals("Nenhum gerente!", "form:lista_data");
		selenium.assertAmountOfElements(5, By.cssSelector("[id='form:lista_head'] > tr > th"));
	}

	@Test
	public void umGerente() throws Exception {
		EmSaas.get(beatriz).cadastrarOGerente(douglas);
		
		selenium.selecionarItemDeMenu("menu:usuarios", "menu:gerentes");
		
		selenium.assertTextEquals("Douglas Hiura", "form:lista:0:nome");
		selenium.assertTextEquals("douglashiura@gmail.com", "form:lista:0:email");
		selenium.assertTextEquals("062.193.859-93", "form:lista:0:cpf");
		selenium.assertTextEquals("(49)9903-2569", "form:lista:0:telefone");
		selenium.assertElementIsVisible("form:lista:0:instituicoes");
	}

	@Test
	public void doisGerentes() throws Exception {
		EmSaas.get(beatriz).cadastrarOGerente(douglas);
		EmSaas.get(beatriz).cadastrarOGerente(lucasUfsc);
		
		selenium.selecionarItemDeMenu("menu:usuarios", "menu:gerentes");
		
		selenium.assertTextEquals("Douglas Hiura", "form:lista:0:nome");
		selenium.assertTextEquals("douglashiura@gmail.com", "form:lista:0:email");
		selenium.assertTextEquals("062.193.859-93", "form:lista:0:cpf");
		selenium.assertTextEquals("(49)9903-2569", "form:lista:0:telefone");
		selenium.assertElementIsVisible("form:lista:0:instituicoes");
		selenium.assertTextEquals("Lucas Pereira", "form:lista:1:nome");
		selenium.assertTextEquals("pslucasps@gmail.com", "form:lista:1:email");
		selenium.assertTextEquals("079.674.329-08", "form:lista:1:cpf");
		selenium.assertTextEquals("(49)9903-2569", "form:lista:1:telefone");
		selenium.assertElementIsVisible("form:lista:1:instituicoes");
	}

	@Test
	public void verInstituicoesDoGerenteDouglas() throws Exception {
		EmSaas.get(beatriz).cadastrarOGerente(douglas);
		
		selenium.selecionarItemDeMenu("menu:usuarios", "menu:gerentes");
		selenium.clicar("form:lista:0:instituicoes");
		
		selenium.assertTextEquals("Douglas Hiura", "formInstituicoesDoGerente:nome");
		selenium.assertTextEquals("Instituições de ensino ofertantes", "modalInstituicoesDoGerente_title");
		selenium.assertTextEquals("UFSC", "formInstituicoesDoGerente:instituicoes:0:sigla");
		selenium.assertTextEquals("Universidade Federal de Santa Catarina", "formInstituicoesDoGerente:instituicoes:0:nome");
	}

	@After
	public void tearDown() {
		selenium.fechar();
	}

}
