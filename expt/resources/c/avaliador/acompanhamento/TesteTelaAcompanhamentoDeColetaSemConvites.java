package br.ufsc.saas.teste.selenium.avaliador.acompanhamento;

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
import br.ufsc.saas.entidade.saas.Instituicao;
import br.ufsc.saas.entidade.saas.Mantenedora;
import br.ufsc.saas.entidade.saas.Usuario;
import br.ufsc.saas.entidade.saas.questionario.QuestionarioEmbrulhado;
import br.ufsc.saas.fachada.EmBanco;
import br.ufsc.saas.fachada.EmInstituicao;
import br.ufsc.saas.fachada.EmSaas;
import br.ufsc.saas.teste.selenium.EmTestePagina;
import br.ufsc.saas.teste.selenium.SeleniumSaas;

public class TesteTelaAcompanhamentoDeColetaSemConvites {

	private SeleniumSaas selenium;
	private Usuario beatriz;
	private Instituicao ufsc;
	private Mantenedora ufscMantenedora;
	private Usuario douglas;
	private Avaliador jhon;
	private Instancia teste;
	private QuestionarioEmbrulhado disciplinaEstudantePessoasCurriculoNome;
	private Coleta disciplina2014_1;
	private Curso computacao;
	private Polo ufscJaragua;
	private Disciplina programacao;
	private OfertaCurso computacao2014_1;
	private OfertaDisciplina programacaoComputacao2014_1;
	private Avaliador rodrigo;
	private Avaliador fabio;
	private Curso sistemas;

	@Before
	public void setUp() throws Exception {
		teste = EmBanco.novaInstancia();
		EmBanco.get(teste).cadastrarAdmin(beatriz);
		EmSaas.get(beatriz).cadastrarMantenedora(ufscMantenedora);
		EmSaas.get(beatriz).cadastrarInstituicao(ufsc);
		EmSaas.get(beatriz).cadastrarOGerente(douglas);
		EmInstituicao.get(douglas).cadastrarAvaliador(rodrigo);
		EmBanco.get(teste).sobreAutenticacao().atualizarSenhaAvaliador("senha", rodrigo);
		EmSaas.get(beatriz).sobreQuestionario().cadastrarQuestionario(disciplinaEstudantePessoasCurriculoNome);
		EmInstituicao.get(douglas).cadastrarAvaliador(jhon);
		EmInstituicao.get(douglas).cadastrarAvaliador(fabio);
		EmInstituicao.get(douglas).cadastrarCurso(computacao);
		EmInstituicao.get(douglas).cadastrarCurso(sistemas);
		computacao2014_1.setCoordenador(rodrigo);
		EmInstituicao.get(douglas).cadastrarOfertaCurso(computacao2014_1);
		EmInstituicao.get(douglas).cadastrarPolo(ufscJaragua);
		EmInstituicao.get(douglas).cadastrarDisciplina(programacao);
		EmInstituicao.get(douglas).cadastrarOfertaDisciplina(programacaoComputacao2014_1);
		EmSaas.get(beatriz).sobreColeta().cadastrarColeta(disciplina2014_1);

		selenium = EmTestePagina.selenium();
		EmTestePagina.get(selenium).trocarInstancia(teste);
		selenium.acessar();
		selenium.digitar("loge:usuario", rodrigo.getEmail().toString());
		selenium.digitar("loge:senha", "senha");
		selenium.clicar("loge:logar");
		selenium.selecionarItemDeMenu("menu:participacao","menu:acompanhamento");

	}

	@Test
	public void semConvitesFocoDisciplina() throws Exception {
		selenium.assertTextEquals("Disciplina EaD (Técnico)", "form:tabela:0:foco");
		selenium.assertTextEquals("0", "form:tabela:0:enviados");
		selenium.assertTextEquals("0", "form:tabela:0:respondidos");
		selenium.assertTextEquals("0,00%", "form:tabela:0:percentual");
	}

	@After
	public void tearDown() {
		selenium.fechar();
	}

}
