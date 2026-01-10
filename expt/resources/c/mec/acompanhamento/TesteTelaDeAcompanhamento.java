package br.ufsc.saas.teste.selenium.mec.acompanhamento;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

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
import br.ufsc.saas.utilitario.Calendario;

public class TesteTelaDeAcompanhamento {

	private SeleniumSaas selenium;
	private Usuario beatriz;
	private Instituicao ufsc;
	private Mantenedora ufscMantenedora;
	private Coleta disciplina2014_1;
	private Avaliador jhon;
	private Curso computacao;
	private Disciplina programacao;
	private Polo ufscJaragua;
	private OfertaCurso computacao2014_1;
	private OfertaDisciplina programacaoComputacao2014_1;
	private Convite disciplina2014_1JhonComputacao;
	private Usuario juliana;
	private QuestionarioEmbrulhado disciplinaEstudantePessoasCurriculoNome;
	private Usuario douglas;
	private Instancia teste;

	@Before
	public void setUp() throws Exception {
		teste = EmBanco.novaInstancia();
		EmBanco.get(teste).cadastrarAdmin(beatriz);
		EmSaas.get(beatriz).cadastrarMantenedora(ufscMantenedora);
		EmSaas.get(beatriz).cadastrarInstituicao(ufsc);
		EmSaas.get(beatriz).cadastrarOGerente(douglas);
		EmSaas.get(beatriz).cadastrarOUsuarioMec(juliana);
		EmSaas.get(beatriz).sobreQuestionario().cadastrarQuestionario(disciplinaEstudantePessoasCurriculoNome);
		Calendar calendario = Calendario.obterCalendarioDeUmDiaParaFrente();
		disciplina2014_1.setEncerramento(calendario.getTime());
		EmSaas.get(beatriz).sobreColeta().cadastrarColeta(disciplina2014_1);
		EmInstituicao.get(douglas).cadastrarAvaliador(jhon);
		EmInstituicao.get(douglas).cadastrarCurso(computacao);
		EmInstituicao.get(douglas).cadastrarDisciplina(programacao);
		EmInstituicao.get(douglas).cadastrarPolo(ufscJaragua);
		EmInstituicao.get(douglas).cadastrarOfertaCurso(computacao2014_1);
		EmInstituicao.get(douglas).cadastrarOfertaDisciplina(programacaoComputacao2014_1);
		List<Convite> abc = EmSaas.get(beatriz).sobreColeta().cadastrarConvites(Arrays.asList(disciplina2014_1JhonComputacao));
		assertEquals(1, abc.size());
		EmBanco.get(teste).sobreColeta().salvaResposta(disciplinaEstudantePessoasCurriculoNome, disciplina2014_1JhonComputacao);

		selenium = EmTestePagina.selenium();
		EmTestePagina.get(selenium).trocarInstancia(teste);
		selenium.acessar();
		selenium.digitar("loge:usuario", juliana.getLogin());
		selenium.digitar("loge:senha", juliana.getSenha());
		selenium.clicar("loge:logar");
		selenium.selecionarItemDeMenu("menu:acompanhamento", "menu:participacao");
	}

	@Test
	public void acompanhamento() throws Exception {
		selenium.assertTextEquals("UFSC", "form:tabela:0:instituicao");
		selenium.assertTextEquals("Disciplina EaD (Técnico)", "form:tabela:0:foco");
		selenium.assertTextEquals("1", "form:tabela:0:enviados");
		selenium.assertTextEquals("1", "form:tabela:0:respondidos");
		selenium.assertTextEquals("100,00%", "form:tabela:0:percentual");
	}

	@After
	public void tearDown() {
		selenium.fechar();
	}

}
