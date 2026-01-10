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
import br.ufsc.saas.entidade.saas.questionario.QuestionarioEmbrulhado;
import br.ufsc.saas.fachada.EmBanco;
import br.ufsc.saas.fachada.EmInstituicao;
import br.ufsc.saas.fachada.EmSaas;
import br.ufsc.saas.teste.selenium.EmTestePagina;
import br.ufsc.saas.teste.selenium.SeleniumSaas;

public class TesteTelaEditarRespostaQuestionarioComUmaRespostaNull {

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
	private QuestionarioEmbrulhado cursoProfessorPessoasCurriculoIdadeCpf;
	private QuestaoDiscursiva idade;

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
		EmSaas.get(beatriz).sobreQuestionario().cadastrarQuestionario(cursoProfessorPessoasCurriculoIdadeCpf);
		EmSaas.get(beatriz).sobreColeta().cadastrarColeta(curso2014_1);
		cursoProfessorPessoasCurriculoIdadeCpf = EmBanco.get(teste).sobreColeta().obterQuestionarioParaAvaliacao(curso2014_1JhonUfscProfessor.getColeta().getFoco(), curso2014_1JhonUfscProfessor.getPapel());
		idade = cursoProfessorPessoasCurriculoIdadeCpf.getDimensoes().get(0).getTopicos().get(0).getQuestoes().get(0).comoQuestaoEspecifica();
		idade.setRespostaDiscursiva("idade");
		EmSaas.get(beatriz).sobreColeta().cadastrarConvites(Arrays.asList(curso2014_1JhonUfscProfessor));
		EmBanco.get(teste).sobreColeta().salvaResposta(cursoProfessorPessoasCurriculoIdadeCpf, curso2014_1JhonUfscProfessor);
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
	public void irEditar() throws Exception {
		selenium.clicar("form:concluidas:0:editar");
		
		selenium.assertTextEquals("Questionário curso professor", "titulo");
		selenium.assertTextEquals("Pessoas", "resposta:dimensoes:0:dimensao");
		selenium.assertTextEquals("Currículo", "resposta:topicos:0:nomeDoTopico");
		selenium.assertTextEquals("Qual sua idade?", "resposta:topicos:0:questoes:0:enunciadoDaQuestao");
		selenium.assertTextEquals("idade", "resposta:topicos:0:questoes:0:textArea");
		selenium.assertTextEquals("Qual o seu cpf?", "resposta:topicos:0:questoes:1:enunciadoDaQuestao");
		selenium.assertTextEquals("", "resposta:topicos:0:questoes:1:textArea");
	}

	@After
	public void tearDown() {
		selenium.fechar();
	}

}
