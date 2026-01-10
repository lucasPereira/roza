package br.ufsc.saas.teste.selenium.mec.relatorio;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.ufsc.saas.entidade.Instancia;
import br.ufsc.saas.entidade.saas.Instituicao;
import br.ufsc.saas.entidade.saas.Mantenedora;
import br.ufsc.saas.entidade.saas.Usuario;
import br.ufsc.saas.fachada.EmBanco;
import br.ufsc.saas.fachada.EmSaas;
import br.ufsc.saas.teste.selenium.EmTestePagina;
import br.ufsc.saas.teste.selenium.SeleniumSaas;

public class TesteTelaRelatoriosDownloadPdf {

	private SeleniumSaas selenium;
	private Usuario beatriz;
	private Usuario juliana;
	private Instituicao ufsc;
	private Mantenedora ufscMantenedora;
	private String arquivoDetalhado;
	private Usuario douglas;
	private String arquivoResumido;
	private Instancia teste;

	@Before
	public void setUp() throws Exception {
		teste = EmBanco.novaInstancia();
		EmBanco.get(teste).cadastrarAdmin(beatriz);
		EmSaas.get(beatriz).cadastrarOUsuarioMec(juliana);
		EmSaas.get(beatriz).cadastrarMantenedora(ufscMantenedora);
		EmSaas.get(beatriz).cadastrarInstituicao(ufsc);
		EmSaas.get(beatriz).cadastrarOGerente(douglas);
		selenium = EmTestePagina.selenium();
		EmTestePagina.get(selenium).trocarInstancia(teste);

		selenium.acessar();
		selenium.digitar("loge:usuario", juliana.getLogin());
		selenium.digitar("loge:senha", juliana.getSenha());
		selenium.clicar("loge:logar");
		selenium.selecionarItemDeMenu("menu:acompanhamento", "menu:relatorio");

		arquivoDetalhado = "relatorioDetalhado_UFSC.pdf";
		arquivoResumido = "relatorioResumido_UFSC.pdf";
		selenium.removerArquivoDaPastaDeDownload(arquivoDetalhado);
		selenium.removerArquivoDaPastaDeDownload(arquivoResumido);
	}

	@Test
	public void baixarRelatorioDetalhadoPdf() throws InterruptedException {
		selenium.assertNotExistsFileInDownloadDir(arquivoDetalhado);
		selenium.clicar("form:lista:0:detalhado");
		selenium.assertElementIsNotVisible("carregamento");
		selenium.assertFileExistsInDownloadDir(arquivoDetalhado);
		selenium.assertNotEmptyFileInDownloadDir(arquivoDetalhado);
	}

	@Test
	public void baixarRelatorioResumidoPdf() throws InterruptedException {
		selenium.assertNotExistsFileInDownloadDir(arquivoResumido);
		selenium.clicar("form:lista:0:resumido");
		selenium.assertElementIsNotVisible("carregamento");
		selenium.assertFileExistsInDownloadDir(arquivoResumido);
		selenium.assertNotEmptyFileInDownloadDir(arquivoResumido);
	}

	@After
	public void tearDown() {
		selenium.fechar();
	}

}
