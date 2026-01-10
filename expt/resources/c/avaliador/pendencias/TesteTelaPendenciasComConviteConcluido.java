package br.ufsc.saas.teste.selenium.avaliador.pendencias;

import java.util.Arrays;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.ufsc.saas.entidade.Instancia;
import br.ufsc.saas.entidade.instituicao.Avaliador;
import br.ufsc.saas.entidade.instituicao.Curso;
import br.ufsc.saas.entidade.instituicao.OfertaCurso;
import br.ufsc.saas.entidade.instituicao.Polo;
import br.ufsc.saas.entidade.saas.Coleta;
import br.ufsc.saas.entidade.saas.Convite;
import br.ufsc.saas.entidade.saas.Instituicao;
import br.ufsc.saas.entidade.saas.Mantenedora;
import br.ufsc.saas.entidade.saas.Usuario;
import br.ufsc.saas.entidade.saas.questionario.QuestionarioEmbrulhado;
import br.ufsc.saas.fachada.EmBanco;
import br.ufsc.saas.fachada.EmInstituicao;
import br.ufsc.saas.fachada.EmSaas;
import br.ufsc.saas.teste.selenium.EmTestePagina;
import br.ufsc.saas.teste.selenium.SeleniumSaas;
import br.ufsc.saas.utilitario.Calendario;

public class TesteTelaPendenciasComConviteConcluido {

	private SeleniumSaas selenium;
	private Avaliador marina;
	private Instancia teste;
	private Usuario beatriz;
	private Instituicao ufsc;
	private Mantenedora ufscMantenedora;
	private Usuario douglas;
	private Curso sistemas;
	private OfertaCurso sistemas2014_1;
	private Polo ufscJaragua;
	private QuestionarioEmbrulhado cursoEstudantePessoasCurriculoIdade;
	private Coleta curso2014_1;
	private Convite curso2014_1MarinaUfsc;

	@Before
	public void setUp() throws Exception {
		teste = EmBanco.novaInstancia();
		EmBanco.get(teste).cadastrarAdmin(beatriz);
		EmSaas.get(beatriz).cadastrarMantenedora(ufscMantenedora);
		EmSaas.get(beatriz).cadastrarInstituicao(ufsc);
		EmSaas.get(beatriz).cadastrarOGerente(douglas);
		EmInstituicao.get(douglas).cadastrarAvaliador(marina);
		EmBanco.get(teste).sobreAutenticacao().atualizarSenhaAvaliador("senha", marina);
		EmInstituicao.get(douglas).cadastrarCurso(sistemas);
		EmInstituicao.get(douglas).cadastrarOfertaCurso(sistemas2014_1);
		EmInstituicao.get(douglas).cadastrarPolo(ufscJaragua);
		EmSaas.get(beatriz).sobreQuestionario().cadastrarQuestionario(cursoEstudantePessoasCurriculoIdade);

		selenium = EmTestePagina.selenium();
		EmTestePagina.get(selenium).trocarInstancia(teste);
		selenium.acessar();
		selenium.digitar("loge:usuario", marina.getEmail().toString());
		selenium.digitar("loge:senha", "senha");
		selenium.clicar("loge:logar");
	}

	@Test
	public void respondido() throws Exception {
		EmSaas.get(beatriz).sobreColeta().cadastrarColeta(curso2014_1);
		EmSaas.get(beatriz).sobreColeta().cadastrarConvites(Arrays.asList(curso2014_1MarinaUfsc));
		EmBanco.get(teste).sobreColeta().salvaResposta(cursoEstudantePessoasCurriculoIdade, curso2014_1MarinaUfsc);

		selenium.selecionarItemDeMenu("menu:questionario","menu:concluidos");
		
		selenium.assertTextEquals("Curso Técnico EaD", "form:concluidas:0:foco");
		selenium.assertTextEquals("(Avaliação) Sistemas de Informação", "form:concluidas:0:oque");
		selenium.assertTextEquals("Estudante", "form:concluidas:0:perfil");
		selenium.assertTextEquals("2014/1", "form:concluidas:0:periodo");
		selenium.assertElementIsVisible("form:concluidas:0:respondido");
	}

	@Test
	public void encerrado() throws Exception {
		curso2014_1.setEncerramento(Calendario.obterCalendarioDoDiaAnterior().getTime());
		EmSaas.get(beatriz).sobreColeta().cadastrarColeta(curso2014_1);
		EmSaas.get(beatriz).sobreColeta().cadastrarConvites(Arrays.asList(curso2014_1MarinaUfsc));

		selenium.selecionarItemDeMenu("menu:questionario","menu:concluidos");
		
		selenium.assertTextEquals("Curso Técnico EaD", "form:concluidas:0:foco");
		selenium.assertTextEquals("(Avaliação) Sistemas de Informação", "form:concluidas:0:oque");
		selenium.assertTextEquals("Estudante", "form:concluidas:0:perfil");
		selenium.assertTextEquals("2014/1", "form:concluidas:0:periodo");
		selenium.assertElementIsVisible("form:concluidas:0:naoRespondido");
	}

	@After
	public void tearDown() {
		selenium.fechar();
	}

}
