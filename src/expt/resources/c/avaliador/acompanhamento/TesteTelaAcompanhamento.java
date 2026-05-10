package br.ufsc.saas.teste.selenium.avaliador.acompanhamento;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.ufsc.saas.entidade.Instancia;
import br.ufsc.saas.entidade.instituicao.Avaliador;
import br.ufsc.saas.entidade.instituicao.Curso;
import br.ufsc.saas.entidade.instituicao.OfertaCurso;
import br.ufsc.saas.entidade.saas.Instituicao;
import br.ufsc.saas.entidade.saas.Mantenedora;
import br.ufsc.saas.entidade.saas.Usuario;
import br.ufsc.saas.fachada.EmBanco;
import br.ufsc.saas.fachada.EmInstituicao;
import br.ufsc.saas.fachada.EmSaas;
import br.ufsc.saas.teste.selenium.EmTestePagina;
import br.ufsc.saas.teste.selenium.SeleniumSaas;

public class TesteTelaAcompanhamento {

	private Avaliador jhon;
	private Usuario beatriz;
	private Instituicao ufsc;
	private Mantenedora ufscMantenedora;
	private Usuario douglas;
	private SeleniumSaas selenium;
	private Curso computacao;
	private OfertaCurso computacao2014_1;

	@Before
	public void setUp() throws Exception {
		Instancia teste = EmBanco.novaInstancia();
		EmBanco.get(teste).cadastrarAdmin(beatriz);
		EmSaas.get(beatriz).cadastrarMantenedora(ufscMantenedora);
		EmSaas.get(beatriz).cadastrarInstituicao(ufsc);
		EmSaas.get(beatriz).cadastrarOGerente(douglas);
		EmInstituicao.get(douglas).cadastrarAvaliador(jhon);
		EmBanco.get(teste).sobreAutenticacao().atualizarSenhaAvaliador("senha", jhon);
		EmInstituicao.get(douglas).cadastrarCurso(computacao);
		computacao2014_1.setCoordenador(jhon);
		EmInstituicao.get(douglas).cadastrarOfertaCurso(computacao2014_1);

		selenium = EmTestePagina.selenium();
		EmTestePagina.get(selenium).trocarInstancia(teste);
		selenium.acessar();
		selenium.digitar("loge:usuario", jhon.getEmail().toString());
		selenium.digitar("loge:senha", "senha");
		selenium.clicar("loge:logar");
		selenium.selecionarItemDeMenu("menu:participacao","menu:acompanhamento");
	}

	@Test
	public void menuComoCoordenadorDeCurso() throws Exception {
		selenium.assertTextEquals("Acompanhamento", "titulo");
		selenium.assertTextEquals("Foco", "form:tabela:tituloFoco");
		selenium.assertTextEquals("Criados", "form:tabela:tituloEnviado");
		selenium.assertTextEquals("Respondidos", "form:tabela:tituloRespondidos");
		selenium.assertTextEquals("Participação", "form:tabela:tituloPercentual");
		selenium.assertTextEquals("Não respondidos", "form:tabela:tituloBaixar");
		selenium.assertTextEquals("Não há coletas em andamento", "form:tabela_data");

	}

	@After
	public void tearDown() {
		selenium.fechar();
	}

}
