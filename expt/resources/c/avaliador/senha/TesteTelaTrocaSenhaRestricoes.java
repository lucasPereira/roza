package br.ufsc.saas.teste.selenium.avaliador.senha;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.ufsc.saas.entidade.Instancia;
import br.ufsc.saas.entidade.instituicao.Avaliador;
import br.ufsc.saas.entidade.saas.ChaveAvaliador;
import br.ufsc.saas.entidade.saas.ChaveRecuperarSenha;
import br.ufsc.saas.entidade.saas.Instituicao;
import br.ufsc.saas.entidade.saas.Mantenedora;
import br.ufsc.saas.entidade.saas.Usuario;
import br.ufsc.saas.fachada.EmBanco;
import br.ufsc.saas.fachada.EmInstituicao;
import br.ufsc.saas.fachada.EmSaas;
import br.ufsc.saas.teste.EmTesteBanco;
import br.ufsc.saas.teste.selenium.EmTestePagina;
import br.ufsc.saas.teste.selenium.SeleniumSaas;

public class TesteTelaTrocaSenhaRestricoes {

	private SeleniumSaas selenium;
	private Instituicao ufsc;
	private Mantenedora ufscMantenedora;
	private List<ChaveAvaliador> chavesAvaliador;
	private Usuario beatriz;
	private Instancia teste;
	private Avaliador marina;
	private Usuario douglas;

	@Before
	public void setUp() throws Exception {
		teste = EmBanco.novaInstancia();
		EmBanco.get(teste).cadastrarAdmin(beatriz);
		EmSaas.get(beatriz).cadastrarMantenedora(ufscMantenedora);
		EmSaas.get(beatriz).cadastrarInstituicao(ufsc);
		EmSaas.get(beatriz).cadastrarOGerente(douglas);
		EmInstituicao.get(douglas).cadastrarAvaliador(marina);
		EmBanco.get(teste).sobreAutenticacao().atualizarSenhaAvaliador("senha", marina);
		ChaveRecuperarSenha chave = EmBanco.get(teste).sobreAutenticacao().criarChaveParaUsuario(marina.getEmail().toString());
		EmBanco.get(teste).sobreAutenticacao().insereChaveRecuperarSenha(chave);
		chavesAvaliador = EmTesteBanco.get(teste).listarEntidadesDoSaasDoBanco(ChaveAvaliador.class);
		selenium = EmTestePagina.selenium();
		EmTestePagina.get(selenium).trocarInstancia(teste);
	}

	@Test
	public void chaveInvalida() throws Exception {
		selenium.acessarComParametro("/novasenha.jsf", "c", "chaveInvalida");
		selenium.assertTextEquals("Não é possível autenticar o link", "messages");
	}

	@Test
	public void oLinkEhValidoUmaVez() throws Exception {
		selenium.acessarComParametro("/novasenha.jsf", "c", chavesAvaliador.get(0).getChave());
		selenium.digitar("form:senha", "novasenha");
		selenium.digitar("form:confirmeSenha", "novasenha");
		selenium.clicar("form:salvar");
		selenium.assertTextEquals("Questionários", "titulo");

		selenium.acessarComParametro("/novasenha.jsf", "c", chavesAvaliador.get(0).getChave());
		selenium.assertTextEquals("Não é possível autenticar o link", "messages");
	}

	@Test
	public void senhaEmBranco() throws Exception {
		selenium.acessarComParametro("/novasenha.jsf", "c", chavesAvaliador.get(0).getChave());
		selenium.digitar("form:senha", "");
		selenium.digitar("form:confirmeSenha", "");
		selenium.clicar("form:salvar");

		selenium.assertTextEquals("O usuário não pode ter senha em branco", "form:messages");
	}

	@Test
	public void senhaComMenosDeQuatroCaracteres() throws Exception {
		selenium.acessarComParametro("/novasenha.jsf", "c", chavesAvaliador.get(0).getChave());
		selenium.digitar("form:senha", "aaa");
		selenium.digitar("form:confirmeSenha", "aaa");
		selenium.clicar("form:salvar");

		selenium.assertTextEquals("Senha não pode conter menos que 4 caracteres", "form:messages");
	}

	@Test
	public void senhaComMaisDeVinteCaracteres() throws Exception {
		selenium.acessarComParametro("/novasenha.jsf", "c", chavesAvaliador.get(0).getChave());
		selenium.digitar("form:senha", "aaaaaaaaaaaaaaaaaaaaa");
		selenium.digitar("form:confirmeSenha", "aaaaaaaaaaaaaaaaaaaaa");
		selenium.clicar("form:salvar");

		selenium.assertTextEquals("Senha não pode conter mais que 20 caracteres", "form:messages");
	}

	@After
	public void tearDown() {
		selenium.fechar();
	}

}
