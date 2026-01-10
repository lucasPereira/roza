package br.ufsc.saas.teste.selenium.avaliador.acompanhamento;

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

public class TesteTelaAcompanhamentoDownloadCsv {

	private SeleniumSaas selenium;
	private Usuario beatriz;
	private Instituicao ufsc;
	private Mantenedora ufscMantenedora;
	private Usuario douglas;
	private Avaliador jhon;
	private Instancia teste;
	private String nomeDoArquivo;
	private QuestionarioEmbrulhado disciplinaEstudantePessoasCurriculoNome;
	private Avaliador rodrigo;
	private Curso computacao;
	private OfertaCurso computacao2014_1;
	private Polo ufscJaragua;
	private Disciplina programacao;
	private OfertaDisciplina programacaoComputacao2014_1;
	private Coleta disciplina2014_1;
	private Convite disciplina2014_1Rodrigo;
	private Convite disciplina2014_1JhonComputacao;

	@Before
	public void setUp() throws Exception {
		nomeDoArquivo = "Convites_pendentes_Disciplina_EaD_Tecnico.csv";
		teste = EmBanco.novaInstancia();
		EmBanco.get(teste).cadastrarAdmin(beatriz);
		EmSaas.get(beatriz).cadastrarMantenedora(ufscMantenedora);
		EmSaas.get(beatriz).cadastrarInstituicao(ufsc);
		EmSaas.get(beatriz).cadastrarOGerente(douglas);
		EmInstituicao.get(douglas).cadastrarAvaliador(rodrigo);
		EmBanco.get(teste).sobreAutenticacao().atualizarSenhaAvaliador("senha", rodrigo);

		EmSaas.get(beatriz).sobreQuestionario().cadastrarQuestionario(disciplinaEstudantePessoasCurriculoNome);
		EmInstituicao.get(douglas).cadastrarAvaliador(jhon);
		EmInstituicao.get(douglas).cadastrarCurso(computacao);
		computacao2014_1.setCoordenador(rodrigo);
		EmInstituicao.get(douglas).cadastrarOfertaCurso(computacao2014_1);
		EmInstituicao.get(douglas).cadastrarPolo(ufscJaragua);
		EmInstituicao.get(douglas).cadastrarDisciplina(programacao);
		EmInstituicao.get(douglas).cadastrarOfertaDisciplina(programacaoComputacao2014_1);
		EmSaas.get(beatriz).sobreColeta().cadastrarColeta(disciplina2014_1);
		EmSaas.get(beatriz).sobreColeta().cadastrarConvites(Arrays.asList(disciplina2014_1Rodrigo, disciplina2014_1JhonComputacao));
		EmBanco.get(teste).sobreColeta().salvaResposta(disciplinaEstudantePessoasCurriculoNome, disciplina2014_1JhonComputacao);

		selenium = EmTestePagina.selenium();
		EmTestePagina.get(selenium).trocarInstancia(teste);
		selenium.acessar();
		selenium.digitar("loge:usuario", rodrigo.getEmail().toString());
		selenium.digitar("loge:senha", "senha");
		selenium.clicar("loge:logar");
		selenium.selecionarItemDeMenu("menu:participacao", "menu:acompanhamento");
		selenium.removerArquivoDaPastaDeDownload(nomeDoArquivo);
	}

	@Test
	public void aColeta() throws Exception {
		selenium.assertTextEquals("Disciplina EaD (Técnico)", "form:tabela:0:foco");
		selenium.assertTextEquals("2", "form:tabela:0:enviados");
		selenium.assertTextEquals("1", "form:tabela:0:respondidos");
		selenium.assertTextEquals("50,00%", "form:tabela:0:percentual");
	}

	@Test
	public void baixar() throws Exception {
		selenium.assertNotExistsFileInDownloadDir(nomeDoArquivo);
		selenium.clicar("form:tabela:0:baixar");
		selenium.assertElementIsNotVisible("carregamento");
		selenium.assertFileExistsInDownloadDir(nomeDoArquivo);
		selenium.assertNotEmptyFileInDownloadDir(nomeDoArquivo);
	}

	@After
	public void tearDown() {
		selenium.fechar();
	}

}
