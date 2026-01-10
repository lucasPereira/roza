package br.ufsc.saas.teste.selenium.mec.relatorio;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.ufsc.saas.entidade.Instancia;
import br.ufsc.saas.entidade.saas.Usuario;
import br.ufsc.saas.fachada.EmBanco;
import br.ufsc.saas.fachada.EmSaas;
import br.ufsc.saas.teste.selenium.EmTestePagina;
import br.ufsc.saas.teste.selenium.SeleniumSaas;

public class TesteTelaRelatorios {

	private SeleniumSaas selenium;
	private Usuario beatriz;
	private Usuario juliana;
	private Instancia teste;

	@Before
	public void setUp() throws Exception {
		teste = EmBanco.novaInstancia();
		EmBanco.get(teste).cadastrarAdmin(beatriz);
		EmSaas.get(beatriz).cadastrarOUsuarioMec(juliana);
		selenium = EmTestePagina.selenium();
		EmTestePagina.get(selenium).trocarInstancia(teste);

		selenium.acessar();
		selenium.digitar("loge:usuario", juliana.getLogin());
		selenium.digitar("loge:senha", juliana.getSenha());
		selenium.clicar("loge:logar");
		selenium.selecionarItemDeMenu("menu:acompanhamento", "menu:relatorio");

	}

	@Test
	public void aTela() throws Exception {
		selenium.assertTextEquals("Relatório de dados cadastrados", "titulo");
		selenium.assertTextEquals("Instituição", "form:lista:instituicaoTitulo");
		selenium.assertTextEquals("Relatório detalhado", "form:lista:detalhadoTitulo");
		selenium.assertTextEquals("Relatório resumido", "form:lista:resumidoTitulo");
		selenium.assertTextEquals("Nenhuma instituição!", "form:lista_data");
	}

	@After
	public void tearDown() {
		selenium.fechar();
	}

}
