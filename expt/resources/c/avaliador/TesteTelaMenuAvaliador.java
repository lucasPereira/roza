package br.ufsc.saas.teste.selenium.avaliador;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;

import br.ufsc.saas.entidade.Instancia;
import br.ufsc.saas.entidade.instituicao.Avaliador;
import br.ufsc.saas.entidade.instituicao.CoordenadorPolo;
import br.ufsc.saas.entidade.instituicao.Curso;
import br.ufsc.saas.entidade.instituicao.OfertaCurso;
import br.ufsc.saas.entidade.instituicao.Polo;
import br.ufsc.saas.entidade.mantenedora.CoordenadorAdjunto;
import br.ufsc.saas.entidade.mantenedora.CoordenadorAdjuntoEtec;
import br.ufsc.saas.entidade.mantenedora.CoordenadorGeral;
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

public class TesteTelaMenuAvaliador {

	private Avaliador jhon;
	private Usuario beatriz;
	private Instituicao ufsc;
	private Mantenedora ufscMantenedora;
	private Usuario douglas;
	private SeleniumSaas selenium;
	private Curso computacao;
	private OfertaCurso computacao2014_1;
	private CoordenadorAdjunto jhonGestorAdjunto;
	private CoordenadorAdjuntoEtec jhonAdjuntoEtec;
	private CoordenadorGeral jhonGestorGeral;
	private CoordenadorGeralEtec jhonGeralEtec;
	private Polo ufscJaragua;
	private CoordenadorPolo jhonUfscJaragua;

	@Before
	public void setUp() throws Exception {
		Instancia teste = EmBanco.novaInstancia();
		EmBanco.get(teste).cadastrarAdmin(beatriz);
		EmSaas.get(beatriz).cadastrarMantenedora(ufscMantenedora);
		EmSaas.get(beatriz).cadastrarInstituicao(ufsc);
		EmSaas.get(beatriz).cadastrarOGerente(douglas);
		EmInstituicao.get(douglas).cadastrarAvaliador(jhon);

		selenium = EmTestePagina.selenium();
		EmTestePagina.get(selenium).trocarInstancia(teste);
		selenium.acessar();
		selenium.digitar("loge:usuario", jhon.getEmail().toString());
		selenium.digitar("loge:senha", "senha");
	}

	@Test
	public void menuComoNaoCoordenador() throws Exception {
		selenium.clicar("loge:logar");

		selenium.assertTextEquals("Questionários", "menu:questionario");
		selenium.assertTextEquals("Resultados", "menu:resultados");
		selenium.assertAmountOfElements(2, By.cssSelector("[id='menu:menu'] > ul > li"));
		selenium.assertTextEquals(String.format("Bem-vindo, %s / %s", jhon.getNome(), jhon.getInstituicao().getSigla()), "uTa:bemvindo");
		selenium.assertTextEquals("SAIR", "sair");
	}

	@Test
	public void menuComoCoordenadorDeCurso() throws Exception {
		EmInstituicao.get(douglas).cadastrarCurso(computacao);
		computacao2014_1.setCoordenador(jhon);
		EmInstituicao.get(douglas).cadastrarOfertaCurso(computacao2014_1);

		selenium.clicar("loge:logar");

		selenium.assertTextEquals("Questionários", "menu:questionario");
		selenium.assertTextEquals("Participação", "menu:participacao");
		selenium.assertTextEquals("Resultados", "menu:resultados");
		selenium.assertTextEquals("Gestão", "menu:gestao");
		selenium.assertTextEquals("SAIR", "sair");
		selenium.assertAmountOfElements(4, By.cssSelector("[id='menu:menu'] > ul > li"));
		selenium.assertTextEquals(String.format("Bem-vindo, %s / %s", jhon.getNome(), jhon.getInstituicao().getSigla()), "uTa:bemvindo");
	}

	@Test
	public void menuComoCoordenadorAdjunto() throws Exception {
		EmMantenedora.get(ufscMantenedora).cadastrarCoordenadorAdjunto(jhonGestorAdjunto);

		selenium.clicar("loge:logar");

		selenium.assertTextEquals("Questionários", "menu:questionario");
		selenium.assertTextEquals("Resultados", "menu:resultados");
		selenium.assertTextEquals("Gestão", "menu:gestao");
		selenium.assertTextEquals("SAIR", "sair");
		selenium.assertAmountOfElements(3, By.cssSelector("[id='menu:menu'] > ul > li"));
		selenium.assertTextEquals(String.format("Bem-vindo, %s / %s", jhon.getNome(), jhon.getInstituicao().getSigla()), "uTa:bemvindo");
	}

	@Test
	public void menuComoCoordenadorAdjuntoEtec() throws Exception {
		EmMantenedora.get(ufscMantenedora).cadastrarCoordenadorAdjuntoEtec(jhonAdjuntoEtec);

		selenium.clicar("loge:logar");

		selenium.assertTextEquals("Questionários", "menu:questionario");
		selenium.assertTextEquals("Resultados", "menu:resultados");
		selenium.assertTextEquals("Gestão", "menu:gestao");
		selenium.assertTextEquals("SAIR", "sair");
		selenium.assertAmountOfElements(3, By.cssSelector("[id='menu:menu'] > ul > li"));
		selenium.assertTextEquals(String.format("Bem-vindo, %s / %s", jhon.getNome(), jhon.getInstituicao().getSigla()), "uTa:bemvindo");
	}

	@Test
	public void menuComoCoordenadorGeral() throws Exception {
		EmMantenedora.get(ufscMantenedora).cadastrarCoordenadorGeral(jhonGestorGeral);

		selenium.clicar("loge:logar");

		selenium.assertTextEquals("Questionários", "menu:questionario");
		selenium.assertTextEquals("Resultados", "menu:resultados");
		selenium.assertTextEquals("Gestão", "menu:gestao");
		selenium.assertTextEquals("SAIR", "sair");
		selenium.assertAmountOfElements(3, By.cssSelector("[id='menu:menu'] > ul > li"));
		selenium.assertTextEquals(String.format("Bem-vindo, %s / %s", jhon.getNome(), jhon.getInstituicao().getSigla()), "uTa:bemvindo");
	}

	@Test
	public void menuComoCoordenadorGeralEtec() throws Exception {
		EmMantenedora.get(ufscMantenedora).cadastrarCoordenadorGeralEtec(jhonGeralEtec);

		selenium.clicar("loge:logar");

		selenium.assertTextEquals("Questionários", "menu:questionario");
		selenium.assertTextEquals("Resultados", "menu:resultados");
		selenium.assertTextEquals("Gestão", "menu:gestao");
		selenium.assertTextEquals("SAIR", "sair");
		selenium.assertAmountOfElements(3, By.cssSelector("[id='menu:menu'] > ul > li"));
		selenium.assertTextEquals(String.format("Bem-vindo, %s / %s", jhon.getNome(), jhon.getInstituicao().getSigla()), "uTa:bemvindo");
	}

	@Test
	public void menuComoCoordenadorPolo() throws Exception {
		EmInstituicao.get(douglas).cadastrarPolo(ufscJaragua);
		EmInstituicao.get(douglas).cadastrarCoordenadorPolo(jhonUfscJaragua);

		selenium.clicar("loge:logar");

		selenium.assertTextEquals("Questionários", "menu:questionario");
		selenium.assertTextEquals("Resultados", "menu:resultados");
		selenium.assertTextEquals("Gestão", "menu:gestao");
		selenium.assertTextEquals("SAIR", "sair");
		selenium.assertAmountOfElements(3, By.cssSelector("[id='menu:menu'] > ul > li"));
		selenium.assertTextEquals(String.format("Bem-vindo, %s / %s", jhon.getNome(), jhon.getInstituicao().getSigla()), "uTa:bemvindo");
	}

	@Test
	public void oSubMenuQuestionarios() throws Exception {
		EmInstituicao.get(douglas).cadastrarCurso(computacao);
		computacao2014_1.setCoordenador(jhon);
		EmInstituicao.get(douglas).cadastrarOfertaCurso(computacao2014_1);

		selenium.clicar("loge:logar");
		selenium.abrirMenu("menu:questionario");

		selenium.assertTextEquals("Pendentes", "menu:pendentes");
		selenium.assertTextEquals("Concluídos", "menu:concluidos");
		selenium.assertAmountOfElements(2, By.cssSelector("[id='menu:questionario'] > ul > li"));
	}

	@Test
	public void oSubMenuParticipacao() throws Exception {
		EmInstituicao.get(douglas).cadastrarCurso(computacao);
		computacao2014_1.setCoordenador(jhon);
		EmInstituicao.get(douglas).cadastrarOfertaCurso(computacao2014_1);

		selenium.clicar("loge:logar");
		selenium.abrirMenu("menu:participacao");

		selenium.assertTextEquals("Acompanhamento", "menu:acompanhamento");
		selenium.assertAmountOfElements(1, By.cssSelector("[id='menu:participacao'] > ul > li"));
	}

	@Test
	public void oSubMenuResultadosComoCoordenadorDeCurso() throws Exception {
		EmInstituicao.get(douglas).cadastrarCurso(computacao);
		computacao2014_1.setCoordenador(jhon);
		EmInstituicao.get(douglas).cadastrarOfertaCurso(computacao2014_1);

		selenium.clicar("loge:logar");
		selenium.abrirMenu("menu:resultados");

		selenium.assertTextEquals("Avaliação", "menu:avaliacao");
		selenium.assertTextEquals("Egressos", "menu:egressos");
		selenium.assertTextEquals("Evasão", "menu:evasao");
		selenium.assertAmountOfElements(3, By.cssSelector("[id='menu:resultados'] > ul > li "));
	}

	@Test
	public void oSubMenuResultadosComoNaoCoordenador() throws Exception {
		selenium.clicar("loge:logar");
		selenium.abrirMenu("menu:resultados");

		selenium.assertTextEquals("Avaliação", "menu:avaliacao");
		selenium.assertTextEquals("Egressos", "menu:egressos");
		selenium.assertAmountOfElements(2, By.cssSelector("[id='menu:resultados'] > ul > li"));
	}

	@Test
	public void oSubMenuGestao() throws Exception {
		EmInstituicao.get(douglas).cadastrarCurso(computacao);
		computacao2014_1.setCoordenador(jhon);
		EmInstituicao.get(douglas).cadastrarOfertaCurso(computacao2014_1);

		selenium.clicar("loge:logar");
		selenium.abrirMenu("menu:gestao");

		selenium.assertTextEquals("Resultados", "menu:resultadoGestao");
		selenium.assertTextEquals("Banco de experiências", "menu:bancoDeExperiencias");
		selenium.assertTextEquals("Fórum", "menu:acessarMoodle");
		selenium.assertAmountOfElements(3, By.cssSelector("[id='menu:gestao'] > ul > li"));
	}

	@Test
	public void oSubMenuBancoDeExperiencias() throws Exception {
		EmInstituicao.get(douglas).cadastrarCurso(computacao);
		computacao2014_1.setCoordenador(jhon);
		EmInstituicao.get(douglas).cadastrarOfertaCurso(computacao2014_1);

		selenium.clicar("loge:logar");
		selenium.abrirSubMenu("menu:gestao", "menu:bancoDeExperiencias");

		selenium.assertTextEquals("Listar", "menu:listarExperiencia");
		selenium.assertTextEquals("Cadastrar", "menu:cadastrarExperiencia");
		selenium.assertAmountOfElements(2, By.cssSelector("[id='menu:bancoDeExperiencias'] > ul > li"));
	}

	@After
	public void tearDown() {
		selenium.fechar();
	}

}
