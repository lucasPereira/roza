package br.ufsc.saas.teste.selenium.avaliador.concluidos;

import java.util.Arrays;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.ufsc.saas.entidade.Instancia;
import br.ufsc.saas.entidade.instituicao.Avaliador;
import br.ufsc.saas.entidade.instituicao.Curso;
import br.ufsc.saas.entidade.instituicao.Disciplina;
import br.ufsc.saas.entidade.instituicao.OfertaCurso;
import br.ufsc.saas.entidade.instituicao.OfertaDisciplina;
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

public class TesteTelaConcluidosComConviteTodosFocos {

	private SeleniumSaas selenium;
	private Instancia teste;
	private Avaliador marina;
	private Usuario beatriz;
	private Instituicao ufsc;
	private Mantenedora ufscMantenedora;
	private Usuario douglas;

	private Polo ufscFlorianopolis;
	private Polo ufscJaragua;

	private Disciplina redes;
	private OfertaDisciplina redesSistemas2014_1;

	private Curso javaFicEad;
	private Curso docenciaLatoIfscEad;
	private Curso sistemas;
	private Curso computacao;
	private Curso sistemasPresencial;

	private OfertaCurso javaFic2014_1;
	private OfertaCurso docenciaLatoIfsc2014_1;
	private OfertaCurso sistemas2014_1;
	private OfertaCurso computacao2014_1_2014_1;
	private OfertaCurso sistemasPresencial2014Semestre1;

	private Coleta cursoFicEad2014Semestre1;
	private Coleta cursoLatoEpdEad2014Semestre1;
	private Coleta curso2014_1;
	private Coleta egresso2015_2;
	private Coleta curso2014_1Presencial;
	private Coleta polo2014_1;
	private Coleta socioEconomico2014_1ComEncerramento;
	private Coleta socioEconomicoPresencial2014Semestre1ComEncerramento;
	private Coleta disciplina2014_1;
	private Coleta saas2015_2;
	private Coleta egressoEpt2015_2;

	private Convite cursoFicEad2014Semestre1Marina;
	private Convite cursoLatoEpdEad2014Semestre1Marina;
	private Convite curso2014_1MarinaUfsc;
	private Convite egresso2015_2MarinaComputacao2014_1_2014_1;
	private Convite curso2014_1MarinaUfscPresencial;
	private Convite polo2014_1MarinaUfsc;
	private Convite socioEconomico2014_1Marina;
	private Convite socioEconomicoPresencial2014Semestre1Marina;
	private Convite disciplina2014_1MarinaSistemasEstudante;
	private Convite saas2015_2MarinaProfessor;
	private Convite egressoEpt2015_2MarinaComputacao2014_1_2014_1;

	private QuestionarioEmbrulhado cursoFicEadEstudante;
	private QuestionarioEmbrulhado cursoLatoEadEstudantePessoasCurriculoNome;
	private QuestionarioEmbrulhado cursoEstudantePessoasCurriculoIdade;
	private QuestionarioEmbrulhado cursoPresencialEstudantePessoasCurriculoIdade;
	private QuestionarioEmbrulhado poloEstudante;
	private QuestionarioEmbrulhado disciplinaEstudante;
	private QuestionarioEmbrulhado saasProfessor;
	private QuestionarioEmbrulhado egressoEstudante;
	private QuestionarioEmbrulhado socioEconomicoEstudante;
	private QuestionarioEmbrulhado socioEconomicoPresencialEstudante;
	private QuestionarioEmbrulhado egressoEptEstudanteInicioTrabalhandoEstudando;

	@Before
	public void setUp() throws Exception {

		teste = EmBanco.novaInstancia();
		EmBanco.get(teste).cadastrarAdmin(beatriz);
		EmSaas.get(beatriz).cadastrarMantenedora(ufscMantenedora);
		EmSaas.get(beatriz).cadastrarInstituicao(ufsc);
		EmSaas.get(beatriz).cadastrarOGerente(douglas);
		EmInstituicao.get(douglas).cadastrarAvaliador(marina);
		EmBanco.get(teste).sobreAutenticacao().atualizarSenhaAvaliador("senha", marina);

		selenium = EmTestePagina.selenium();
		EmTestePagina.get(selenium).trocarInstancia(teste);

		selenium.acessar();
	}

	@Test
	public void aTelaQuestionarioConcluidoFocoCursoFic() throws Exception {
		EmInstituicao.get(douglas).cadastrarCurso(javaFicEad);
		EmInstituicao.get(douglas).cadastrarOfertaCurso(javaFic2014_1);
		EmInstituicao.get(douglas).cadastrarPolo(ufscFlorianopolis);
		EmSaas.get(beatriz).sobreQuestionario().cadastrarQuestionario(cursoFicEadEstudante);
		EmSaas.get(beatriz).sobreColeta().cadastrarColeta(cursoFicEad2014Semestre1);
		EmSaas.get(beatriz).sobreColeta().cadastrarConvites(Arrays.asList(cursoFicEad2014Semestre1Marina));

		selenium.acessarComParametro("/quest.jsf", "c", cursoFicEad2014Semestre1Marina.getChave());
		selenium.clicar("resposta:salvar");
		selenium.abrirMenu("menu:questionario");
		selenium.clicar("menu:concluidos");

		selenium.assertTextEquals("Curso FIC EaD", "form:concluidas:0:foco");
		selenium.assertTextEquals("(Avaliação) Java Básico", "form:concluidas:0:oque");
		selenium.assertTextEquals("Estudante", "form:concluidas:0:perfil");
		selenium.assertTextEquals("2014/1", "form:concluidas:0:periodo");
	}

	@Test
	public void aTelaQuestionarioConcluidoFocoCursoLatoSensu() throws Exception {
		EmInstituicao.get(douglas).cadastrarCurso(docenciaLatoIfscEad);
		EmInstituicao.get(douglas).cadastrarOfertaCurso(docenciaLatoIfsc2014_1);
		EmInstituicao.get(douglas).cadastrarPolo(ufscJaragua);
		EmSaas.get(beatriz).sobreQuestionario().cadastrarQuestionario(cursoLatoEadEstudantePessoasCurriculoNome);
		EmSaas.get(beatriz).sobreColeta().cadastrarColeta(cursoLatoEpdEad2014Semestre1);
		EmSaas.get(beatriz).sobreColeta().cadastrarConvites(Arrays.asList(cursoLatoEpdEad2014Semestre1Marina));

		selenium.acessarComParametro("/quest.jsf", "c", cursoLatoEpdEad2014Semestre1Marina.getChave());
		selenium.clicar("resposta:salvar");
		selenium.abrirMenu("menu:questionario");
		selenium.clicar("menu:concluidos");

		selenium.assertTextEquals("Curso Docência EPT", "form:concluidas:0:foco");
		selenium.assertTextEquals("(Avaliação) Docência para a Educação Profissional", "form:concluidas:0:oque");
		selenium.assertTextEquals("Estudante", "form:concluidas:0:perfil");
		selenium.assertTextEquals("2014/1", "form:concluidas:0:periodo");
	}

	@Test
	public void aTelaQuestionarioConcluidoFocoCursoEad() throws Exception {
		EmInstituicao.get(douglas).cadastrarCurso(sistemas);
		EmInstituicao.get(douglas).cadastrarOfertaCurso(sistemas2014_1);
		EmInstituicao.get(douglas).cadastrarPolo(ufscJaragua);
		EmSaas.get(beatriz).sobreQuestionario().cadastrarQuestionario(cursoEstudantePessoasCurriculoIdade);
		EmSaas.get(beatriz).sobreColeta().cadastrarColeta(curso2014_1);
		EmSaas.get(beatriz).sobreColeta().cadastrarConvites(Arrays.asList(curso2014_1MarinaUfsc));

		selenium.acessarComParametro("/quest.jsf", "c", curso2014_1MarinaUfsc.getChave());
		selenium.clicar("resposta:salvar");
		selenium.abrirMenu("menu:questionario");
		selenium.clicar("menu:concluidos");

		selenium.assertTextEquals("Curso Técnico EaD", "form:concluidas:0:foco");
		selenium.assertTextEquals("(Avaliação) Sistemas de Informação", "form:concluidas:0:oque");
		selenium.assertTextEquals("Estudante", "form:concluidas:0:perfil");
		selenium.assertTextEquals("2014/1", "form:concluidas:0:periodo");
	}

	@Test
	public void aTelaQuestionarioConcluidoFocoCursoPresencial() throws Exception {
		EmInstituicao.get(douglas).cadastrarCurso(sistemasPresencial);
		EmInstituicao.get(douglas).cadastrarOfertaCurso(sistemasPresencial2014Semestre1);
		EmInstituicao.get(douglas).cadastrarPolo(ufscJaragua);
		EmSaas.get(beatriz).sobreQuestionario().cadastrarQuestionario(cursoPresencialEstudantePessoasCurriculoIdade);
		EmSaas.get(beatriz).sobreColeta().cadastrarColeta(curso2014_1Presencial);
		EmSaas.get(beatriz).sobreColeta().cadastrarConvites(Arrays.asList(curso2014_1MarinaUfscPresencial));

		selenium.acessarComParametro("/quest.jsf", "c", curso2014_1MarinaUfscPresencial.getChave());
		selenium.clicar("resposta:salvar");
		selenium.abrirMenu("menu:questionario");
		selenium.clicar("menu:concluidos");

		selenium.assertTextEquals("Curso Técnico Presencial", "form:concluidas:0:foco");
		selenium.assertTextEquals("(Avaliação) Sistemas de Informação", "form:concluidas:0:oque");
		selenium.assertTextEquals("Estudante", "form:concluidas:0:perfil");
		selenium.assertTextEquals("2014/1", "form:concluidas:0:periodo");
	}

	@Test
	public void aTelaQuestionarioConcluidoFocoPolo() throws Exception {
		EmInstituicao.get(douglas).cadastrarCurso(sistemasPresencial);
		EmInstituicao.get(douglas).cadastrarOfertaCurso(sistemasPresencial2014Semestre1);
		EmInstituicao.get(douglas).cadastrarPolo(ufscJaragua);
		EmSaas.get(beatriz).sobreQuestionario().cadastrarQuestionario(poloEstudante);
		EmSaas.get(beatriz).sobreColeta().cadastrarColeta(polo2014_1);
		EmSaas.get(beatriz).sobreColeta().cadastrarConvites(Arrays.asList(polo2014_1MarinaUfsc));

		selenium.acessarComParametro("/quest.jsf", "c", polo2014_1MarinaUfsc.getChave());
		selenium.clicar("resposta:salvar");
		selenium.abrirMenu("menu:questionario");
		selenium.clicar("menu:concluidos");

		selenium.assertTextEquals("Polo/Unidade de ensino EaD", "form:concluidas:0:foco");
		selenium.assertTextEquals("(Avaliação) UFSC de Jaragua", "form:concluidas:0:oque");
		selenium.assertTextEquals("Estudante", "form:concluidas:0:perfil");
		selenium.assertTextEquals("2014/1", "form:concluidas:0:periodo");
	}

	@Test
	public void aTelaQuestionarioConcluidoFocoEgresso() throws Exception {
		EmInstituicao.get(douglas).cadastrarCurso(computacao);
		EmInstituicao.get(douglas).cadastrarOfertaCurso(computacao2014_1_2014_1);
		EmInstituicao.get(douglas).cadastrarPolo(ufscJaragua);
		EmSaas.get(beatriz).sobreQuestionario().cadastrarQuestionario(egressoEstudante);
		EmSaas.get(beatriz).sobreColeta().cadastrarColeta(egresso2015_2);
		EmSaas.get(beatriz).sobreColeta().cadastrarConvites(Arrays.asList(egresso2015_2MarinaComputacao2014_1_2014_1));

		selenium.acessarComParametro("/quest.jsf", "c", egresso2015_2MarinaComputacao2014_1_2014_1.getChave());
		selenium.clicar("resposta:salvar");
		selenium.abrirMenu("menu:questionario");
		selenium.clicar("menu:concluidos");

		selenium.assertTextEquals("Egressos (Técnico)", "form:concluidas:0:foco");
		selenium.assertTextEquals("Acompanhamento", "form:concluidas:0:oque");
		selenium.assertTextEquals("Estudante", "form:concluidas:0:perfil");
		selenium.assertTextEquals("2015/2", "form:concluidas:0:periodo");
	}
	
	@Test
	public void aTelaQuestionarioConcluidoFocoEgressoEPT() throws Exception {
		EmInstituicao.get(douglas).cadastrarCurso(computacao);
		EmInstituicao.get(douglas).cadastrarOfertaCurso(computacao2014_1_2014_1);
		EmInstituicao.get(douglas).cadastrarPolo(ufscJaragua);
		EmSaas.get(beatriz).sobreQuestionario().cadastrarQuestionario(egressoEptEstudanteInicioTrabalhandoEstudando);
		EmSaas.get(beatriz).sobreBanco().sobreColeta().cadastrarColeta(egressoEpt2015_2);
		EmSaas.get(beatriz).sobreBanco().sobreColeta().cadastrarConvites(Arrays.asList(egressoEpt2015_2MarinaComputacao2014_1_2014_1));

		selenium.acessarComParametro("/quest.jsf", "c", egressoEpt2015_2MarinaComputacao2014_1_2014_1.getChave());
		selenium.clicar("resposta:salvar");
		selenium.abrirMenu("menu:questionario");
		selenium.clicar("menu:concluidos");

		selenium.assertTextEquals("Egressos (Docência EPT)", "form:concluidas:0:foco");
		selenium.assertTextEquals("Acompanhamento", "form:concluidas:0:oque");
		selenium.assertTextEquals("Estudante", "form:concluidas:0:perfil");
		selenium.assertTextEquals("2015/2", "form:concluidas:0:periodo");
	}

	@Test
	public void aTelaQuestionarioConcluidoFocoSocioEconomicoEad() throws Exception {
		EmInstituicao.get(douglas).cadastrarCurso(sistemas);
		EmInstituicao.get(douglas).cadastrarOfertaCurso(sistemas2014_1);
		EmInstituicao.get(douglas).cadastrarPolo(ufscJaragua);
		EmSaas.get(beatriz).sobreQuestionario().cadastrarQuestionario(socioEconomicoEstudante);
		EmSaas.get(beatriz).sobreColeta().cadastrarColeta(socioEconomico2014_1ComEncerramento);
		EmSaas.get(beatriz).sobreColeta().cadastrarConvites(Arrays.asList(socioEconomico2014_1Marina));

		selenium.acessarComParametro("/quest.jsf", "c", socioEconomico2014_1Marina.getChave());
		selenium.clicar("resposta:salvar");
		selenium.abrirMenu("menu:questionario");
		selenium.clicar("menu:concluidos");

		selenium.assertTextEquals("Socioescolar EaD (Técnico)", "form:concluidas:0:foco");
		selenium.assertTextEquals("Perfil Socioescolar", "form:concluidas:0:oque");
		selenium.assertTextEquals("Estudante", "form:concluidas:0:perfil");
		selenium.assertTextEquals("2014/1", "form:concluidas:0:periodo");
	}

	@Test
	public void aTelaQuestionarioConcluidoFocoSocioEconomicoPresencial() throws Exception {
		EmInstituicao.get(douglas).cadastrarCurso(sistemasPresencial);
		EmInstituicao.get(douglas).cadastrarOfertaCurso(sistemasPresencial2014Semestre1);
		EmInstituicao.get(douglas).cadastrarPolo(ufscJaragua);
		EmSaas.get(beatriz).sobreQuestionario().cadastrarQuestionario(socioEconomicoPresencialEstudante);
		EmSaas.get(beatriz).sobreColeta().cadastrarColeta(socioEconomicoPresencial2014Semestre1ComEncerramento);
		EmSaas.get(beatriz).sobreColeta().cadastrarConvites(Arrays.asList(socioEconomicoPresencial2014Semestre1Marina));

		selenium.acessarComParametro("/quest.jsf", "c", socioEconomicoPresencial2014Semestre1Marina.getChave());
		selenium.clicar("resposta:salvar");
		selenium.abrirMenu("menu:questionario");
		selenium.clicar("menu:concluidos");

		selenium.assertTextEquals("Socioescolar Presencial (Técnico)", "form:concluidas:0:foco");
		selenium.assertTextEquals("Perfil Socioescolar", "form:concluidas:0:oque");
		selenium.assertTextEquals("Estudante", "form:concluidas:0:perfil");
		selenium.assertTextEquals("2014/1", "form:concluidas:0:periodo");
	}

	@Test
	public void aTelaQuestionarioConcluidoFocoDisciplina() throws Exception {
		EmInstituicao.get(douglas).cadastrarCurso(sistemas);
		EmInstituicao.get(douglas).cadastrarOfertaCurso(sistemas2014_1);
		EmInstituicao.get(douglas).cadastrarDisciplina(redes);
		EmInstituicao.get(douglas).cadastrarOfertaDisciplina(redesSistemas2014_1);
		EmInstituicao.get(douglas).cadastrarPolo(ufscJaragua);
		EmSaas.get(beatriz).sobreQuestionario().cadastrarQuestionario(disciplinaEstudante);
		EmSaas.get(beatriz).sobreColeta().cadastrarColeta(disciplina2014_1);
		EmSaas.get(beatriz).sobreColeta().cadastrarConvites(Arrays.asList(disciplina2014_1MarinaSistemasEstudante));

		selenium.acessarComParametro("/quest.jsf", "c", disciplina2014_1MarinaSistemasEstudante.getChave());
		selenium.clicar("resposta:salvar");
		selenium.abrirMenu("menu:questionario");
		selenium.clicar("menu:concluidos");

		selenium.assertTextEquals("Disciplina EaD (Técnico)", "form:concluidas:0:foco");
		selenium.assertTextEquals("(Avaliação) Redes", "form:concluidas:0:oque");
		selenium.assertTextEquals("Estudante", "form:concluidas:0:perfil");
		selenium.assertTextEquals("2014/1", "form:concluidas:0:periodo");
	}

	@Test
	public void aTelaQuestionarioConcluidoFocoSaas() throws Exception {
		EmSaas.get(beatriz).sobreQuestionario().cadastrarQuestionario(saasProfessor);
		EmSaas.get(beatriz).sobreColeta().cadastrarColeta(saas2015_2);
		EmSaas.get(beatriz).sobreColeta().cadastrarConvites(Arrays.asList(saas2015_2MarinaProfessor));

		selenium.acessarComParametro("/quest.jsf", "c", saas2015_2MarinaProfessor.getChave());
		selenium.clicar("resposta:salvar");
		selenium.abrirMenu("menu:questionario");
		selenium.clicar("menu:concluidos");

		selenium.assertTextEquals("SAAS", "form:concluidas:0:foco");
		selenium.assertTextEquals("-", "form:concluidas:0:oque");
		selenium.assertTextEquals("Professor", "form:concluidas:0:perfil");
		selenium.assertTextEquals("2015/2", "form:concluidas:0:periodo");
	}

	@After
	public void tearDown() {
		selenium.fechar();
	}

}
