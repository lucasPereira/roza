package br.ufsc.saas.teste.selenium.avaliador.senha;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

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

public class TesteTelaRecuperarSenhaDoAvaliador {

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
		selenium.acessarComParametro("/novasenha.jsf", "c", chavesAvaliador.get(0).getChave());
	}

	@Test
	public void aTelaEh() throws Exception {
		selenium.assertTextEquals("Nova senha", "panel_header");
		selenium.assertTextEquals("Senha", "form:senha_label");
		selenium.assertTextEquals("Confirme a senha", "form:confirmeSenha_label");
	}

	@Test
	public void aTrocaDeSenha() throws Exception {
		selenium.digitar("form:senha", "novasenha");
		selenium.digitar("form:confirmeSenha", "novasenha");
		selenium.clicar("form:salvar");
		
		selenium.assertTextEquals("Questionários", "titulo");
		
		Avaliador avaliador = EmTesteBanco.get(teste).obterEntidadeDoBanco(marina);
		assertEquals("novasenha", avaliador.getSenha());
		ChaveAvaliador chaveAvaliador = EmTesteBanco.get(teste).obterEntidadeDoBanco(chavesAvaliador.get(0));
		assertFalse(chaveAvaliador.getAtiva());
	}

	@Test
	public void depoisDaTrocaDeSenha() throws Exception {
		selenium.digitar("form:senha", "novasenha");
		selenium.digitar("form:confirmeSenha", "novasenha");
		selenium.clicar("form:salvar");
		
		selenium.assertUrlEndsWith("avaliador.jsf");
	}

	@After
	public void tearDown() {
		selenium.fechar();
	}

}
