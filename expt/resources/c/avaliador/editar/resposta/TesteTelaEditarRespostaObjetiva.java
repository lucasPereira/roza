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
import br.ufsc.saas.entidade.saas.questionario.QuestaoObjetiva;
import br.ufsc.saas.entidade.saas.questionario.QuestionarioEmbrulhado;
import br.ufsc.saas.fachada.EmBanco;
import br.ufsc.saas.fachada.EmInstituicao;
import br.ufsc.saas.fachada.EmSaas;
import br.ufsc.saas.teste.selenium.EmTestePagina;
import br.ufsc.saas.teste.selenium.SeleniumSaas;

public class TesteTelaEditarRespostaObjetiva {

	private Instancia teste;
	private Usuario beatriz;
	private Convite curso2014_1JhonUfscProfessor;
	private Instituicao ufsc;
	private Mantenedora ufscMantenedora;
	private Avaliador jhon;
	private Polo ufscJaragua;
	private Curso sistemas;
	private OfertaCurso sistemas2014_1;
	private Coleta curso2014_1;
	private SeleniumSaas selenium;
	private Usuario douglas;
	private QuestionarioEmbrulhado cursoProfessorPessoasCurriculoCafeBomRuim;

	@Before
	public void setUp() throws Exception {
		teste = EmBanco.novaInstancia();
		EmBanco.get(teste).cadastrarAdmin(beatriz);
		EmSaas.get(beatriz).cadastrarMantenedora(ufscMantenedora);
		EmSaas.get(beatriz).cadastrarInstituicao(ufsc);
		EmSaas.get(beatriz).cadastrarOGerente(douglas);
		EmInstituicao.get(douglas).cadastrarAvaliador(jhon);
		EmInstituicao.get(douglas).cadastrarPolo(ufscJaragua);
		EmInstituicao.get(douglas).cadastrarCurso(sistemas);
		EmInstituicao.get(douglas).cadastrarOfertaCurso(sistemas2014_1);
		EmSaas.get(beatriz).sobreQuestionario().cadastrarQuestionario(cursoProfessorPessoasCurriculoCafeBomRuim);
		EmSaas.get(beatriz).sobreColeta().cadastrarColeta(curso2014_1);
		EmSaas.get(beatriz).sobreColeta().cadastrarConvites(Arrays.asList(curso2014_1JhonUfscProfessor));
		QuestaoObjetiva objetiva = cursoProfessorPessoasCurriculoCafeBomRuim.getDimensoes().get(0).getTopicos().get(0).getQuestoes().get(0).comoQuestaoEspecifica();
		objetiva.setRespostaObjetiva(objetiva.getAlternativas().get(0));
		EmBanco.get(teste).sobreColeta().salvaResposta(cursoProfessorPessoasCurriculoCafeBomRuim, curso2014_1JhonUfscProfessor);
		EmBanco.get(teste).sobreAutenticacao().atualizarSenhaAvaliador("senha", jhon);
		selenium = EmTestePagina.selenium();
		EmTestePagina.get(selenium).trocarInstancia(teste);
		selenium.acessar();
		selenium.digitar("loge:usuario", jhon.getEmail().toString());
		selenium.digitar("loge:senha", "senha");
		selenium.clicar("loge:logar");
		selenium.selecionarItemDeMenu("menu:questionario", "menu:concluidos");
	}


	@Test
	public void comObjetiva() throws Exception {
		selenium.clicar("form:concluidas:0:editar");
		selenium.selecionarCheckbox("resposta:topicos:0:questoes:0:radio", 2);
		selenium.clicar("resposta:salvar");
		selenium.selecionarItemDeMenu("menu:questionario", "menu:concluidos");
		selenium.clicar("form:concluidas:0:editar");
		
		selenium.assertIsSelected("resposta:topicos:0:questoes:0:radio:1");
	}

	@After
	public void tearDown() {
		selenium.fechar();
	}

}
