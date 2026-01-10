package br.ufsc.saas.teste.selenium.avaliador.editar.resposta;

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
import br.ufsc.saas.entidade.saas.questionario.QuestaoDiscursiva;
import br.ufsc.saas.entidade.saas.questionario.QuestaoObjetiva;
import br.ufsc.saas.entidade.saas.questionario.QuestionarioEmbrulhado;
import br.ufsc.saas.fachada.EmBanco;
import br.ufsc.saas.fachada.EmInstituicao;
import br.ufsc.saas.fachada.EmSaas;
import br.ufsc.saas.teste.selenium.EmTestePagina;
import br.ufsc.saas.teste.selenium.SeleniumSaas;

public class TesteTelaEditarRespostaComQuestaoFixa {

	private Instancia teste;
	private Usuario beatriz;
	private Mantenedora ufscMantenedora;
	private Instituicao ufsc;
	private Usuario douglas;
	private Avaliador jhon;
	private Polo ufscJaragua;
	private Curso computacao;
	private OfertaCurso computacao2014_1_2014_1;
	private QuestionarioEmbrulhado egressoEstudanteInicioTrabalhandoEstudando;
	private SeleniumSaas selenium;
	private Coleta egresso2015_2;
	private Convite egresso2015_2JhonComputacao2014_1_2014_1;
	private Coleta egresso2016_1;
	private Convite egresso2016_1JhonComputacao2014_1_2014_1;
	private QuestaoDiscursiva questaoGostouDoCurso;
	private QuestaoObjetiva estudandoObjetiva1;
	private QuestaoObjetiva inicioObjetiva2;
	private QuestaoObjetiva inicioObjetiva1;

	@Before
	public void setUp() throws Exception {
		teste = EmBanco.novaInstancia();
		EmBanco.get(teste).cadastrarAdmin(beatriz);
		EmSaas.get(beatriz).cadastrarMantenedora(ufscMantenedora);
		EmSaas.get(beatriz).cadastrarInstituicao(ufsc);
		EmSaas.get(beatriz).cadastrarOGerente(douglas);
		EmInstituicao.get(douglas).cadastrarAvaliador(jhon);
		EmInstituicao.get(douglas).cadastrarPolo(ufscJaragua);
		EmInstituicao.get(douglas).cadastrarCurso(computacao);
		EmInstituicao.get(douglas).cadastrarOfertaCurso(computacao2014_1_2014_1);
		EmSaas.get(beatriz).sobreQuestionario().cadastrarQuestionario(egressoEstudanteInicioTrabalhandoEstudando);
		selenium = EmTestePagina.selenium();
		EmTestePagina.get(selenium).trocarInstancia(teste);
		EmSaas.get(beatriz).sobreColeta().cadastrarColeta(egresso2015_2);
		EmSaas.get(beatriz).sobreColeta().cadastrarConvites(Arrays.asList(egresso2015_2JhonComputacao2014_1_2014_1));
		inicioObjetiva1 = egressoEstudanteInicioTrabalhandoEstudando.getDimensoes().get(0).getTopicos().get(0).getQuestoes().get(0).comoQuestaoEspecifica();
		inicioObjetiva2 = egressoEstudanteInicioTrabalhandoEstudando.getDimensoes().get(0).getTopicos().get(0).getQuestoes().get(1).comoQuestaoEspecifica();
		estudandoObjetiva1 = egressoEstudanteInicioTrabalhandoEstudando.getDimensoes().get(2).getTopicos().get(0).getQuestoes().get(0).comoQuestaoEspecifica();
		inicioObjetiva1.setFixa(true);
		EmSaas.get(beatriz).sobreQuestionario().sobreEditarQuestionario().editarQuestao(inicioObjetiva1);
		inicioObjetiva1.setRespostaObjetiva(inicioObjetiva1.getAlternativas().get(0));
		inicioObjetiva2.setRespostaObjetiva(inicioObjetiva2.getAlternativas().get(1));
		estudandoObjetiva1.setRespostaObjetiva(estudandoObjetiva1.getAlternativas().get(0));
		EmBanco.get(teste).sobreAutenticacao().atualizarSenhaAvaliador("senha", jhon);
		selenium.acessar();
		selenium.digitar("loge:usuario", jhon.getEmail().toString());
		selenium.digitar("loge:senha", "senha");
	}

	@Test
	public void editarRespostasComQuestaoFixaSemRespostaAnterior() throws Exception {
		EmBanco.get(teste).sobreColeta().salvaResposta(egressoEstudanteInicioTrabalhandoEstudando, egresso2015_2JhonComputacao2014_1_2014_1);
		
		selenium.clicar("loge:logar");
		selenium.selecionarItemDeMenu("menu:questionario", "menu:concluidos");
		selenium.clicar("form:concluidas:0:editar");
		
		selenium.assertTextEquals("Questionário egresso estudante", "titulo");
		selenium.assertTextEquals("Início", "resposta:dimensoes:0:dimensao");
		selenium.assertTextEquals("Tópico Início", "resposta:topicos:0:nomeDoTopico");
		selenium.assertTextEquals("Qual a sua situação no curso?", "resposta:topicos:0:questoes:0:enunciadoDaQuestao");
		selenium.assertTextEquals("Qual sua ocupação?", "resposta:topicos:0:questoes:1:enunciadoDaQuestao");
	}

	@Test
	public void editarRespostasComQuestaoFixaComRespostaAnterior() throws Exception {
		EmBanco.get(teste).sobreColeta().salvaResposta(egressoEstudanteInicioTrabalhandoEstudando, egresso2015_2JhonComputacao2014_1_2014_1);
		EmSaas.get(beatriz).sobreColeta().cadastrarColeta(egresso2016_1);
		EmSaas.get(beatriz).sobreColeta().cadastrarConvites(Arrays.asList(egresso2016_1JhonComputacao2014_1_2014_1));
		EmBanco.get(teste).sobreColeta().salvaResposta(egressoEstudanteInicioTrabalhandoEstudando, egresso2016_1JhonComputacao2014_1_2014_1);
		
		selenium.clicar("loge:logar");
		selenium.selecionarItemDeMenu("menu:questionario", "menu:concluidos");
		selenium.clicar("form:concluidas:0:editar");
		
		selenium.assertTextEquals("Questionário egresso estudante", "titulo");
		selenium.assertTextEquals("Início", "resposta:dimensoes:0:dimensao");
		selenium.assertTextEquals("Tópico Início", "resposta:topicos:0:nomeDoTopico");
		selenium.assertElementNotExists("resposta:topicos:0:questoes:0:enunciadoDaQuestao");
		selenium.assertTextEquals("Qual sua ocupação?", "resposta:topicos:0:questoes:1:enunciadoDaQuestao");
	}

	@Test
	public void editarRespostasComQuestaoFixaObjetivaDiscursiva() throws Exception {
		questaoGostouDoCurso.setTopico(egressoEstudanteInicioTrabalhandoEstudando.getDimensoes().get(2).getTopicos().get(0));
		egressoEstudanteInicioTrabalhandoEstudando.getDimensoes().get(2).getTopicos().get(0).adicionarQuestao(questaoGostouDoCurso);
		EmSaas.get(beatriz).sobreQuestionario().sobreEditarQuestionario().adicionarQuestao(questaoGostouDoCurso);
		EmSaas.get(beatriz).sobreQuestionario().sobreEditarQuestionario().editarQuestionario(egressoEstudanteInicioTrabalhandoEstudando);
		questaoGostouDoCurso.setFixa(true);
		EmSaas.get(beatriz).sobreQuestionario().sobreEditarQuestionario().editarQuestao(questaoGostouDoCurso);
		questaoGostouDoCurso.setRespostaDiscursiva("Daora");
		EmBanco.get(teste).sobreColeta().salvaResposta(egressoEstudanteInicioTrabalhandoEstudando, egresso2015_2JhonComputacao2014_1_2014_1);
		EmSaas.get(beatriz).sobreColeta().cadastrarColeta(egresso2016_1);
		EmSaas.get(beatriz).sobreColeta().cadastrarConvites(Arrays.asList(egresso2016_1JhonComputacao2014_1_2014_1));
		EmBanco.get(teste).sobreColeta().salvaResposta(egressoEstudanteInicioTrabalhandoEstudando, egresso2016_1JhonComputacao2014_1_2014_1);
		
		selenium.clicar("loge:logar");
		selenium.selecionarItemDeMenu("menu:questionario", "menu:concluidos");
		selenium.clicar("form:concluidas:0:editar");
		selenium.clicar("resposta:proxima");
		
		selenium.assertElementNotExists("resposta:topicos:0:questoes:1:enunciadoDaQuestao");
		selenium.assertElementNotExists("resposta:topicos:0:questoes:1:textArea");
	}

	@Test
	public void editarRespostasComQuestaoFixaObjetivaDiscursivaDesafixada() throws Exception {
		questaoGostouDoCurso.setTopico(egressoEstudanteInicioTrabalhandoEstudando.getDimensoes().get(2).getTopicos().get(0));
		egressoEstudanteInicioTrabalhandoEstudando.getDimensoes().get(2).getTopicos().get(0).adicionarQuestao(questaoGostouDoCurso);
		EmSaas.get(beatriz).sobreQuestionario().sobreEditarQuestionario().adicionarQuestao(questaoGostouDoCurso);
		EmSaas.get(beatriz).sobreQuestionario().sobreEditarQuestionario().editarQuestionario(egressoEstudanteInicioTrabalhandoEstudando);
		questaoGostouDoCurso.setFixa(true);
		EmSaas.get(beatriz).sobreQuestionario().sobreEditarQuestionario().editarQuestao(questaoGostouDoCurso);
		questaoGostouDoCurso.setRespostaDiscursiva("Daora");
		EmBanco.get(teste).sobreColeta().salvaResposta(egressoEstudanteInicioTrabalhandoEstudando, egresso2015_2JhonComputacao2014_1_2014_1);
		EmSaas.get(beatriz).sobreColeta().cadastrarColeta(egresso2016_1);
		EmSaas.get(beatriz).sobreColeta().cadastrarConvites(Arrays.asList(egresso2016_1JhonComputacao2014_1_2014_1));
		EmBanco.get(teste).sobreColeta().salvaResposta(egressoEstudanteInicioTrabalhandoEstudando, egresso2016_1JhonComputacao2014_1_2014_1);
		questaoGostouDoCurso.setFixa(false);
		EmSaas.get(beatriz).sobreQuestionario().sobreEditarQuestionario().editarQuestao(questaoGostouDoCurso);
		
		selenium.clicar("loge:logar");
		selenium.selecionarItemDeMenu("menu:questionario", "menu:concluidos");
		selenium.clicar("form:concluidas:0:editar");
		selenium.clicar("resposta:proxima");
		
		selenium.assertTextEquals("O que achou do curso?", "resposta:topicos:0:questoes:1:enunciadoDaQuestao");
		selenium.assertElementIsVisible("resposta:topicos:0:questoes:1:textArea");
	}

	@After
	public void tearDown() {
		selenium.fechar();
	}

}
