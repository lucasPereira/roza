package br.ufsc.saas.teste.selenium.avaliador.pendencias;

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

public class TesteTelaPendenciasComConviteTodosFocos {

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

	private QuestionarioEmbrulhado cursoFicEadEstudante;
	private QuestionarioEmbrulhado cursoLatoEadEstudantePessoasCurriculoNome;
	private QuestionarioEmbrulhado cursoEstudantePessoasCurriculoIdade;
	private QuestionarioEmbrulhado egressoEstudanteInicioTrabalhandoEstudando;
	private QuestionarioEmbrulhado cursoPresencialEstudantePessoasCurriculoIdade;
	private QuestionarioEmbrulhado poloEstudante;
	private QuestionarioEmbrulhado socioEconomicoEstudantePessoasCurriculoIdade;
	private QuestionarioEmbrulhado socioEconomicoPresencialEstudanteComQuestoes;
	private QuestionarioEmbrulhado disciplinaEstudante;
	private QuestionarioEmbrulhado saasProfessor;
	private OfertaDisciplina redesSistemasPresencial2014_1;
	private QuestionarioEmbrulhado disciplinaPresencialEstudante;
	private Convite disciplinaPresencial2014_1MarinaSistemasEstudante;
	private Coleta disciplinaPresencial2014_1;
	private Coleta egressoEpt2015_2;
	private Convite egressoEpt2015_2MarinaComputacao2014_1_2014_1;
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
	}

	@Test
	public void aTelaFocoCursoFic() throws Exception {
		EmInstituicao.get(douglas).cadastrarCurso(javaFicEad);
		EmInstituicao.get(douglas).cadastrarOfertaCurso(javaFic2014_1);
		EmInstituicao.get(douglas).cadastrarPolo(ufscFlorianopolis);
		EmSaas.get(beatriz).sobreQuestionario().cadastrarQuestionario(cursoFicEadEstudante);
		EmSaas.get(beatriz).sobreColeta().cadastrarColeta(cursoFicEad2014Semestre1);
		EmSaas.get(beatriz).sobreColeta().cadastrarConvites(Arrays.asList(cursoFicEad2014Semestre1Marina));
		loginMarina();
		selenium.assertTextEquals("Curso FIC EaD", "form:pendencias:0:foco");
		selenium.assertTextEquals("(Avaliação) Java Básico", "form:pendencias:0:oque");
		selenium.assertTextEquals("Estudante", "form:pendencias:0:perfil");

	}

	@Test
	public void aTelaQuestionarioFocoCursoFic() throws Exception {
		EmInstituicao.get(douglas).cadastrarCurso(javaFicEad);
		EmInstituicao.get(douglas).cadastrarOfertaCurso(javaFic2014_1);
		EmInstituicao.get(douglas).cadastrarPolo(ufscFlorianopolis);
		EmSaas.get(beatriz).sobreQuestionario().cadastrarQuestionario(cursoFicEadEstudante);
		EmSaas.get(beatriz).sobreColeta().cadastrarColeta(cursoFicEad2014Semestre1);
		EmSaas.get(beatriz).sobreColeta().cadastrarConvites(Arrays.asList(cursoFicEad2014Semestre1Marina));
		loginMarina();
		selenium.clicar("form:pendencias:0:avaliar");
		selenium.assertUrlEndsWith(cursoFicEad2014Semestre1Marina.getChave());
		selenium.assertTextEquals("Questionário curso fic ead estudante", "titulo");
	}

	@Test
	public void aTelaFocoCursoLatoSensu() throws Exception {
		EmInstituicao.get(douglas).cadastrarCurso(docenciaLatoIfscEad);
		EmInstituicao.get(douglas).cadastrarOfertaCurso(docenciaLatoIfsc2014_1);
		EmInstituicao.get(douglas).cadastrarPolo(ufscJaragua);
		EmSaas.get(beatriz).sobreQuestionario().cadastrarQuestionario(cursoLatoEadEstudantePessoasCurriculoNome);
		EmSaas.get(beatriz).sobreColeta().cadastrarColeta(cursoLatoEpdEad2014Semestre1);
		EmSaas.get(beatriz).sobreColeta().cadastrarConvites(Arrays.asList(cursoLatoEpdEad2014Semestre1Marina));

		loginMarina();

		selenium.assertTextEquals("Curso Docência EPT", "form:pendencias:0:foco");
		selenium.assertTextEquals("(Avaliação) Docência para a Educação Profissional", "form:pendencias:0:oque");
		selenium.assertTextEquals("Estudante", "form:pendencias:0:perfil");
	}

	@Test
	public void aTelaQuestionarioFocoCursoLatoSensu() throws Exception {
		EmInstituicao.get(douglas).cadastrarCurso(docenciaLatoIfscEad);
		EmInstituicao.get(douglas).cadastrarOfertaCurso(docenciaLatoIfsc2014_1);
		EmInstituicao.get(douglas).cadastrarPolo(ufscJaragua);
		EmSaas.get(beatriz).sobreQuestionario().cadastrarQuestionario(cursoLatoEadEstudantePessoasCurriculoNome);
		EmSaas.get(beatriz).sobreColeta().cadastrarColeta(cursoLatoEpdEad2014Semestre1);
		EmSaas.get(beatriz).sobreColeta().cadastrarConvites(Arrays.asList(cursoLatoEpdEad2014Semestre1Marina));

		loginMarina();

		selenium.clicar("form:pendencias:0:avaliar");
		selenium.assertUrlEndsWith(cursoLatoEpdEad2014Semestre1Marina.getChave());
		selenium.assertTextEquals("Questionário curso Lato Sensu EPT EAD estudante", "titulo");
	}

	@Test
	public void aTelaFocoCursoEad() throws Exception {
		EmInstituicao.get(douglas).cadastrarCurso(sistemas);
		EmInstituicao.get(douglas).cadastrarOfertaCurso(sistemas2014_1);
		EmInstituicao.get(douglas).cadastrarPolo(ufscJaragua);
		EmSaas.get(beatriz).sobreQuestionario().cadastrarQuestionario(cursoEstudantePessoasCurriculoIdade);
		EmSaas.get(beatriz).sobreColeta().cadastrarColeta(curso2014_1);
		EmSaas.get(beatriz).sobreColeta().cadastrarConvites(Arrays.asList(curso2014_1MarinaUfsc));

		loginMarina();

		selenium.assertTextEquals("Curso Técnico EaD", "form:pendencias:0:foco");
		selenium.assertTextEquals("(Avaliação) Sistemas de Informação", "form:pendencias:0:oque");
		selenium.assertTextEquals("Estudante", "form:pendencias:0:perfil");
	}

	@Test
	public void aTelaQuestionarioFocoCursoEad() throws Exception {
		EmInstituicao.get(douglas).cadastrarCurso(sistemas);
		EmInstituicao.get(douglas).cadastrarOfertaCurso(sistemas2014_1);
		EmInstituicao.get(douglas).cadastrarPolo(ufscJaragua);
		EmSaas.get(beatriz).sobreQuestionario().cadastrarQuestionario(cursoEstudantePessoasCurriculoIdade);
		EmSaas.get(beatriz).sobreColeta().cadastrarColeta(curso2014_1);
		EmSaas.get(beatriz).sobreColeta().cadastrarConvites(Arrays.asList(curso2014_1MarinaUfsc));

		loginMarina();

		selenium.clicar("form:pendencias:0:avaliar");
		selenium.assertUrlEndsWith(curso2014_1MarinaUfsc.getChave());
		selenium.assertTextEquals("Questionário curso estudante", "titulo");
	}

	@Test
	public void aTelaFocoCursoPresencial() throws Exception {
		EmInstituicao.get(douglas).cadastrarCurso(sistemasPresencial);
		EmInstituicao.get(douglas).cadastrarOfertaCurso(sistemasPresencial2014Semestre1);
		EmInstituicao.get(douglas).cadastrarPolo(ufscJaragua);
		EmSaas.get(beatriz).sobreQuestionario().cadastrarQuestionario(cursoPresencialEstudantePessoasCurriculoIdade);
		EmSaas.get(beatriz).sobreColeta().cadastrarColeta(curso2014_1Presencial);
		EmSaas.get(beatriz).sobreColeta().cadastrarConvites(Arrays.asList(curso2014_1MarinaUfscPresencial));

		loginMarina();

		selenium.assertTextEquals("Curso Técnico Presencial", "form:pendencias:0:foco");
		selenium.assertTextEquals("(Avaliação) Sistemas de Informação", "form:pendencias:0:oque");
		selenium.assertTextEquals("Estudante", "form:pendencias:0:perfil");
	}

	@Test
	public void aTelaQuestionarioFocoCursoPresencial() throws Exception {
		EmInstituicao.get(douglas).cadastrarCurso(sistemasPresencial);
		EmInstituicao.get(douglas).cadastrarOfertaCurso(sistemasPresencial2014Semestre1);
		EmInstituicao.get(douglas).cadastrarPolo(ufscJaragua);
		EmSaas.get(beatriz).sobreQuestionario().cadastrarQuestionario(cursoPresencialEstudantePessoasCurriculoIdade);
		EmSaas.get(beatriz).sobreColeta().cadastrarColeta(curso2014_1Presencial);
		EmSaas.get(beatriz).sobreColeta().cadastrarConvites(Arrays.asList(curso2014_1MarinaUfscPresencial));

		loginMarina();

		selenium.clicar("form:pendencias:0:avaliar");
		selenium.assertUrlEndsWith(curso2014_1MarinaUfscPresencial.getChave());
		selenium.assertTextEquals("Questionário curso presencial estudante", "titulo");
	}

	@Test
	public void aTelaFocoPolo() throws Exception {
		EmInstituicao.get(douglas).cadastrarCurso(sistemasPresencial);
		EmInstituicao.get(douglas).cadastrarOfertaCurso(sistemasPresencial2014Semestre1);
		EmInstituicao.get(douglas).cadastrarPolo(ufscJaragua);
		EmSaas.get(beatriz).sobreQuestionario().cadastrarQuestionario(poloEstudante);
		EmSaas.get(beatriz).sobreColeta().cadastrarColeta(polo2014_1);
		EmSaas.get(beatriz).sobreColeta().cadastrarConvites(Arrays.asList(polo2014_1MarinaUfsc));

		loginMarina();

		selenium.assertTextEquals("Polo/Unidade de ensino EaD", "form:pendencias:0:foco");
		selenium.assertTextEquals("(Avaliação) UFSC de Jaragua", "form:pendencias:0:oque");
		selenium.assertTextEquals("Estudante", "form:pendencias:0:perfil");
	}

	@Test
	public void aTelaQuestionarioFocoPolo() throws Exception {
		EmInstituicao.get(douglas).cadastrarCurso(sistemasPresencial);
		EmInstituicao.get(douglas).cadastrarOfertaCurso(sistemasPresencial2014Semestre1);
		EmInstituicao.get(douglas).cadastrarPolo(ufscJaragua);
		EmSaas.get(beatriz).sobreQuestionario().cadastrarQuestionario(poloEstudante);
		EmSaas.get(beatriz).sobreColeta().cadastrarColeta(polo2014_1);
		EmSaas.get(beatriz).sobreColeta().cadastrarConvites(Arrays.asList(polo2014_1MarinaUfsc));

		loginMarina();

		selenium.clicar("form:pendencias:0:avaliar");
		selenium.assertUrlEndsWith(polo2014_1MarinaUfsc.getChave());
		selenium.assertTextEquals("Questionário polo estudante", "titulo");
	}

	@Test
	public void aTelaFocoEgresso() throws Exception {
		EmInstituicao.get(douglas).cadastrarCurso(computacao);
		EmInstituicao.get(douglas).cadastrarOfertaCurso(computacao2014_1_2014_1);
		EmInstituicao.get(douglas).cadastrarPolo(ufscJaragua);
		EmSaas.get(beatriz).sobreQuestionario().cadastrarQuestionario(egressoEstudanteInicioTrabalhandoEstudando);
		EmSaas.get(beatriz).sobreColeta().cadastrarColeta(egresso2015_2);
		EmSaas.get(beatriz).sobreColeta().cadastrarConvites(Arrays.asList(egresso2015_2MarinaComputacao2014_1_2014_1));

		loginMarina();

		selenium.assertTextEquals("Egressos (Técnico)", "form:pendencias:0:foco");
		selenium.assertTextEquals("Acompanhamento", "form:pendencias:0:oque");
		selenium.assertTextEquals("Estudante", "form:pendencias:0:perfil");
	}

	@Test
	public void aTelaQuestionarioFocoEgresso() throws Exception {
		EmInstituicao.get(douglas).cadastrarCurso(computacao);
		EmInstituicao.get(douglas).cadastrarOfertaCurso(computacao2014_1_2014_1);
		EmInstituicao.get(douglas).cadastrarPolo(ufscJaragua);
		EmSaas.get(beatriz).sobreQuestionario().cadastrarQuestionario(egressoEstudanteInicioTrabalhandoEstudando);
		EmSaas.get(beatriz).sobreColeta().cadastrarColeta(egresso2015_2);
		EmSaas.get(beatriz).sobreColeta().cadastrarConvites(Arrays.asList(egresso2015_2MarinaComputacao2014_1_2014_1));

		loginMarina();

		selenium.clicar("form:pendencias:0:avaliar");
		selenium.assertUrlEndsWith(egresso2015_2MarinaComputacao2014_1_2014_1.getChave());
		selenium.assertTextEquals("Questionário egresso estudante", "titulo");
	}

	@Test
	public void aTelaFocoEgressoEPT() throws Exception {
		EmInstituicao.get(douglas).cadastrarCurso(computacao);
		EmInstituicao.get(douglas).cadastrarOfertaCurso(computacao2014_1_2014_1);
		EmInstituicao.get(douglas).cadastrarPolo(ufscJaragua);
		EmSaas.get(beatriz).sobreQuestionario().cadastrarQuestionario(egressoEptEstudanteInicioTrabalhandoEstudando);
		EmSaas.get(beatriz).sobreBanco().sobreColeta().cadastrarColeta(egressoEpt2015_2);
		EmSaas.get(beatriz).sobreBanco().sobreColeta().cadastrarConvites(Arrays.asList(egressoEpt2015_2MarinaComputacao2014_1_2014_1));

		loginMarina();

		selenium.assertTextEquals("Egressos (Docência EPT)", "form:pendencias:0:foco");
		selenium.assertTextEquals("Acompanhamento", "form:pendencias:0:oque");
		selenium.assertTextEquals("Estudante", "form:pendencias:0:perfil");
	}
	
	@Test
	public void aTelaQuestionarioFocoEgressoEPT() throws Exception {
		EmInstituicao.get(douglas).cadastrarCurso(computacao);
		EmInstituicao.get(douglas).cadastrarOfertaCurso(computacao2014_1_2014_1);
		EmInstituicao.get(douglas).cadastrarPolo(ufscJaragua);
		EmSaas.get(beatriz).sobreQuestionario().cadastrarQuestionario(egressoEptEstudanteInicioTrabalhandoEstudando);
		EmSaas.get(beatriz).sobreBanco().sobreColeta().cadastrarColeta(egressoEpt2015_2);
		EmSaas.get(beatriz).sobreBanco().sobreColeta().cadastrarConvites(Arrays.asList(egressoEpt2015_2MarinaComputacao2014_1_2014_1));

		loginMarina();

		selenium.clicar("form:pendencias:0:avaliar");
		selenium.assertUrlEndsWith(egressoEpt2015_2MarinaComputacao2014_1_2014_1.getChave());
		selenium.assertTextEquals("Questionário egresso ept estudante", "titulo");
	}
	
	@Test
	public void aTelaFocoSocioEconomicoEad() throws Exception {
		EmInstituicao.get(douglas).cadastrarCurso(sistemas);
		EmInstituicao.get(douglas).cadastrarOfertaCurso(sistemas2014_1);
		EmInstituicao.get(douglas).cadastrarPolo(ufscJaragua);
		EmSaas.get(beatriz).sobreQuestionario().cadastrarQuestionario(socioEconomicoEstudantePessoasCurriculoIdade);
		EmSaas.get(beatriz).sobreColeta().cadastrarColeta(socioEconomico2014_1ComEncerramento);
		EmSaas.get(beatriz).sobreColeta().cadastrarConvites(Arrays.asList(socioEconomico2014_1Marina));

		loginMarina();

		selenium.assertTextEquals("Socioescolar EaD (Técnico)", "form:pendencias:0:foco");
		selenium.assertTextEquals("Perfil Socioescolar", "form:pendencias:0:oque");
		selenium.assertTextEquals("Estudante", "form:pendencias:0:perfil");
	}

	@Test
	public void aTelaQuestionarioFocoSocioEconomicoEad() throws Exception {
		EmInstituicao.get(douglas).cadastrarCurso(sistemas);
		EmInstituicao.get(douglas).cadastrarOfertaCurso(sistemas2014_1);
		EmInstituicao.get(douglas).cadastrarPolo(ufscJaragua);
		EmSaas.get(beatriz).sobreQuestionario().cadastrarQuestionario(socioEconomicoEstudantePessoasCurriculoIdade);
		EmSaas.get(beatriz).sobreColeta().cadastrarColeta(socioEconomico2014_1ComEncerramento);
		EmSaas.get(beatriz).sobreColeta().cadastrarConvites(Arrays.asList(socioEconomico2014_1Marina));

		loginMarina();

		selenium.clicar("form:pendencias:0:avaliar");
		selenium.assertUrlEndsWith(socioEconomico2014_1Marina.getChave());
		selenium.assertTextEquals("Questionário socioeconômico estudante", "titulo");
	}

	@Test
	public void aTelaFocoSocioEconomicoPresencial() throws Exception {
		EmInstituicao.get(douglas).cadastrarCurso(sistemasPresencial);
		EmInstituicao.get(douglas).cadastrarOfertaCurso(sistemasPresencial2014Semestre1);
		EmInstituicao.get(douglas).cadastrarPolo(ufscJaragua);
		EmSaas.get(beatriz).sobreQuestionario().cadastrarQuestionario(socioEconomicoPresencialEstudanteComQuestoes);
		EmSaas.get(beatriz).sobreColeta().cadastrarColeta(socioEconomicoPresencial2014Semestre1ComEncerramento);
		EmSaas.get(beatriz).sobreColeta().cadastrarConvites(Arrays.asList(socioEconomicoPresencial2014Semestre1Marina));

		loginMarina();

		selenium.assertTextEquals("Socioescolar Presencial (Técnico)", "form:pendencias:0:foco");
		selenium.assertTextEquals("Perfil Socioescolar", "form:pendencias:0:oque");
		selenium.assertTextEquals("Estudante", "form:pendencias:0:perfil");
	}

	@Test
	public void aTelaQuestionarioFocoSocioEconomicoPresencial() throws Exception {
		EmInstituicao.get(douglas).cadastrarCurso(sistemasPresencial);
		EmInstituicao.get(douglas).cadastrarOfertaCurso(sistemasPresencial2014Semestre1);
		EmInstituicao.get(douglas).cadastrarPolo(ufscJaragua);
		EmSaas.get(beatriz).sobreQuestionario().cadastrarQuestionario(socioEconomicoPresencialEstudanteComQuestoes);
		EmSaas.get(beatriz).sobreColeta().cadastrarColeta(socioEconomicoPresencial2014Semestre1ComEncerramento);
		EmSaas.get(beatriz).sobreColeta().cadastrarConvites(Arrays.asList(socioEconomicoPresencial2014Semestre1Marina));

		loginMarina();

		selenium.clicar("form:pendencias:0:avaliar");
		selenium.assertUrlEndsWith(socioEconomicoPresencial2014Semestre1Marina.getChave());
		selenium.assertTextEquals("Questionário socioeconômico", "titulo");
	}

	@Test
	public void aTelaFocoDisciplina() throws Exception {
		EmInstituicao.get(douglas).cadastrarCurso(sistemas);
		EmInstituicao.get(douglas).cadastrarOfertaCurso(sistemas2014_1);
		EmInstituicao.get(douglas).cadastrarDisciplina(redes);
		EmInstituicao.get(douglas).cadastrarOfertaDisciplina(redesSistemas2014_1);
		EmInstituicao.get(douglas).cadastrarPolo(ufscJaragua);
		EmSaas.get(beatriz).sobreQuestionario().cadastrarQuestionario(disciplinaEstudante);
		EmSaas.get(beatriz).sobreColeta().cadastrarColeta(disciplina2014_1);
		EmSaas.get(beatriz).sobreColeta().cadastrarConvites(Arrays.asList(disciplina2014_1MarinaSistemasEstudante));

		loginMarina();

		selenium.assertTextEquals("Disciplina EaD (Técnico)", "form:pendencias:0:foco");
		selenium.assertTextEquals("(Avaliação) Redes", "form:pendencias:0:oque");
		selenium.assertTextEquals("Estudante", "form:pendencias:0:perfil");
	}

	@Test
	public void aTelaQuestionarioFocoDisciplina() throws Exception {
		EmInstituicao.get(douglas).cadastrarCurso(sistemas);
		EmInstituicao.get(douglas).cadastrarOfertaCurso(sistemas2014_1);
		EmInstituicao.get(douglas).cadastrarDisciplina(redes);
		EmInstituicao.get(douglas).cadastrarOfertaDisciplina(redesSistemas2014_1);
		EmInstituicao.get(douglas).cadastrarPolo(ufscJaragua);
		EmSaas.get(beatriz).sobreQuestionario().cadastrarQuestionario(disciplinaEstudante);
		EmSaas.get(beatriz).sobreColeta().cadastrarColeta(disciplina2014_1);
		EmSaas.get(beatriz).sobreColeta().cadastrarConvites(Arrays.asList(disciplina2014_1MarinaSistemasEstudante));

		loginMarina();

		selenium.clicar("form:pendencias:0:avaliar");
		selenium.assertUrlEndsWith(disciplina2014_1MarinaSistemasEstudante.getChave());
		selenium.assertTextEquals("Questionário disciplina estudante", "titulo");
	}
	
	@Test
	public void aTelaFocoDisciplinaPresencial() throws Exception {
		EmInstituicao.get(douglas).cadastrarCurso(sistemasPresencial);
		EmInstituicao.get(douglas).cadastrarOfertaCurso(sistemasPresencial2014Semestre1);
		EmInstituicao.get(douglas).cadastrarDisciplina(redes);
		EmInstituicao.get(douglas).cadastrarOfertaDisciplina(redesSistemasPresencial2014_1);
		EmInstituicao.get(douglas).cadastrarPolo(ufscJaragua);
		EmSaas.get(beatriz).sobreQuestionario().cadastrarQuestionario(disciplinaPresencialEstudante);
		EmSaas.get(beatriz).sobreColeta().cadastrarColeta(disciplinaPresencial2014_1);
		EmSaas.get(beatriz).sobreColeta().cadastrarConvites(Arrays.asList(disciplinaPresencial2014_1MarinaSistemasEstudante));

		loginMarina();

		selenium.assertTextEquals("Disciplina Presencial (Técnico)", "form:pendencias:0:foco");
		selenium.assertTextEquals("(Avaliação) Redes", "form:pendencias:0:oque");
		selenium.assertTextEquals("Estudante", "form:pendencias:0:perfil");
	}

	@Test
	public void aTelaQuestionarioFocoDisciplinaPresencial() throws Exception {
		EmInstituicao.get(douglas).cadastrarCurso(sistemasPresencial);
		EmInstituicao.get(douglas).cadastrarOfertaCurso(sistemasPresencial2014Semestre1);
		EmInstituicao.get(douglas).cadastrarDisciplina(redes);
		EmInstituicao.get(douglas).cadastrarOfertaDisciplina(redesSistemasPresencial2014_1);
		EmInstituicao.get(douglas).cadastrarPolo(ufscJaragua);
		EmSaas.get(beatriz).sobreQuestionario().cadastrarQuestionario(disciplinaPresencialEstudante);
		EmSaas.get(beatriz).sobreColeta().cadastrarColeta(disciplinaPresencial2014_1);
		EmSaas.get(beatriz).sobreColeta().cadastrarConvites(Arrays.asList(disciplinaPresencial2014_1MarinaSistemasEstudante));

		loginMarina();

		selenium.clicar("form:pendencias:0:avaliar");
		selenium.assertUrlEndsWith(disciplinaPresencial2014_1MarinaSistemasEstudante.getChave());
		selenium.assertTextEquals("Questionário disciplina presencial estudante", "titulo");
	}

	@Test
	public void aTelaFocoSaas() throws Exception {
		EmSaas.get(beatriz).sobreQuestionario().cadastrarQuestionario(saasProfessor);
		EmSaas.get(beatriz).sobreColeta().cadastrarColeta(saas2015_2);
		EmSaas.get(beatriz).sobreColeta().cadastrarConvites(Arrays.asList(saas2015_2MarinaProfessor));

		loginMarina();

		selenium.assertTextEquals("SAAS", "form:pendencias:0:foco");
		selenium.assertTextEquals("-", "form:pendencias:0:oque");
		selenium.assertTextEquals("Professor", "form:pendencias:0:perfil");
	}

	@Test
	public void aTelaQuestionarioFocoSaas() throws Exception {
		EmSaas.get(beatriz).sobreQuestionario().cadastrarQuestionario(saasProfessor);
		EmSaas.get(beatriz).sobreColeta().cadastrarColeta(saas2015_2);
		EmSaas.get(beatriz).sobreColeta().cadastrarConvites(Arrays.asList(saas2015_2MarinaProfessor));

		loginMarina();

		selenium.clicar("form:pendencias:0:avaliar");
		selenium.assertUrlEndsWith(saas2015_2MarinaProfessor.getChave());
		selenium.assertTextEquals("Avaliação do SAAS pelo professor", "titulo");
	}

	private void loginMarina() {
		selenium = EmTestePagina.selenium();
		EmTestePagina.get(selenium).trocarInstancia(teste);
		selenium.acessar();
		selenium.digitar("loge:usuario", marina.getEmail().toString());
		selenium.digitar("loge:senha", "senha");
		selenium.clicar("loge:logar");
	}

	@After
	public void tearDown() {
		selenium.fechar();
	}

}
