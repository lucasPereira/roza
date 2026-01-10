package br.ufsc.saas.teste.selenium.avaliador;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.ufsc.saas.entidade.Instancia;
import br.ufsc.saas.entidade.instituicao.Avaliador;
import br.ufsc.saas.entidade.saas.Instituicao;
import br.ufsc.saas.entidade.saas.Mantenedora;
import br.ufsc.saas.entidade.saas.Usuario;
import br.ufsc.saas.fachada.EmBanco;
import br.ufsc.saas.fachada.EmInstituicao;
import br.ufsc.saas.fachada.EmSaas;
import br.ufsc.saas.teste.selenium.EmTestePagina;
import br.ufsc.saas.teste.selenium.SeleniumSaas;

public class TesteTelaLoginAvaliadorEmDuasInstituicoes {

	private SeleniumSaas selenium;
	private Avaliador marina;
	private Usuario beatriz;
	private Instituicao ufsc;
	private Mantenedora ufscMantenedora;
	private Usuario douglas;
	private Instancia teste;
	private Instituicao udesc;
	private Usuario lucas;
	private Avaliador marinaClone;
	private Mantenedora udescMantenedora;

	@Before
	public void setUp() throws Exception {
		teste = EmBanco.novaInstancia();
		EmBanco.get(teste).cadastrarAdmin(beatriz);
		EmSaas.get(beatriz).cadastrarMantenedora(ufscMantenedora);
		EmSaas.get(beatriz).cadastrarInstituicao(ufsc);
		EmSaas.get(beatriz).cadastrarMantenedora(udescMantenedora);
		EmSaas.get(beatriz).cadastrarInstituicao(udesc);
		EmSaas.get(beatriz).cadastrarOGerente(douglas);
		EmSaas.get(beatriz).cadastrarOGerente(lucas);
		EmInstituicao.get(douglas).cadastrarAvaliador(marina);
		EmInstituicao.get(lucas).cadastrarAvaliador(marinaClone);
		EmBanco.get(teste).sobreAutenticacao().atualizarSenhaAvaliador("senha", marina);

		selenium = EmTestePagina.selenium();
		EmTestePagina.get(selenium).trocarInstancia(teste);
		selenium.acessar();
		selenium.digitar("loge:usuario", marina.getEmail().toString());
		selenium.digitar("loge:senha", "senha");
		selenium.clicar("loge:logar");
	}

	@Test
	public void fazerLoginUdesc() throws Exception {
		selenium.clicar("selecionar:instituicoes:0:nome");
		selenium.assertTextEquals("Bem-vindo, Marina / UDESC", "uTa:bemvindo");
	}

	@Test
	public void fazerLoginUfsc() throws Exception {
		selenium.clicar("selecionar:instituicoes:1:nome");
		selenium.assertTextEquals("Bem-vindo, Marina / UFSC", "uTa:bemvindo");
	}

	@After
	public void tearDown() {
		selenium.fechar();
	}

}
