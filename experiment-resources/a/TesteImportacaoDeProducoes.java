package br.com.lepidus.omp.capes;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TesteImportacaoDeProducoes {

	private CorreioEletronico correioEletronico;
	private BancoDeDados bancoDeDados;
	private Servidor servidor;
	private Selenium selenium;

	@BeforeEach
	void configurar() throws Exception {
		correioEletronico = new CorreioEletronico();
		bancoDeDados = new BancoDeDados();
		servidor = new Servidor();
		selenium = new FabricaSelenium().construir("importacao-de-producoes");
		correioEletronico.iniciar();
		bancoDeDados.criarBaseParaTestes();
		bancoDeDados.limpar();
		servidor.iniciar();

		selenium.acessar("http://localhost:8000");
		selenium.clicar("#navigationUser > li:nth-child(2) > a");
		selenium.digitar("gerente", "#username");
		selenium.digitar("senha", "#password");
		selenium.clicar("#login button");
		selenium.passarOMouse("#navigationUser > li > a");
		selenium.clicar("#navigationUser > li > ul > li:nth-child(1) > a");
		selenium.passarOMouse("#navigationPrimary > li:nth-child(5) > a");
		selenium.clicar("#navigationPrimary > li:nth-child(5) > ul > li:nth-child(1) > a");
		selenium.clicar("#ui-id-2 > div > ul > li:nth-child(1) > a");
		selenium.clicar("#capes-abas > ul > li:nth-child(2) > a");
	}

	@Test
	void importarProducoesPagina() throws Exception {
		String instrucoes = "Utilize o espaço abaixo para importar as produções de livros a partir de um arquivo em formato CSV codificado em ISO-8859-1. O arquivo deve ter os campos separados por vírgula e utilizar aspas duplas como delimitador textual. A primeira linha do arquivo CSV deve conter o cabeçalho, sendo que, pelo menos, as seguintes colunas são esperadas (não necessáriamente na ordem apresentada):";
		String aviso = "Produções já existentes, bem como seus respectivos dados, serão perdidas.";
		selenium.assegurarTexto("Instruções", "#capes-importacao-livros-form > fieldset > div:nth-child(1) > label");
		selenium.assegurarTexto(instrucoes, "#capes-importacao-livros-form > fieldset > div:nth-child(1) > p:nth-of-type(1)");
		selenium.assegurarQuantidadeDeElementos(21, "#capes-importacao-livros-form > fieldset > div:nth-child(1) > ul > li");
		selenium.assegurarTexto("ID_PRODUCAO_INTELECTUAL", "#capes-importacao-livros-form > fieldset > div:nth-child(1) > ul > li:nth-child(1)");
		selenium.assegurarTexto("ID_ADD_PRODUCAO_INTELECTUAL", "#capes-importacao-livros-form > fieldset > div:nth-child(1) > ul > li:nth-child(2)");
		selenium.assegurarTexto("autores", "#capes-importacao-livros-form > fieldset > div:nth-child(1) > ul > li:nth-child(3)");
		selenium.assegurarTexto("NM_PRODUCAO", "#capes-importacao-livros-form > fieldset > div:nth-child(1) > ul > li:nth-child(4)");
		selenium.assegurarTexto("AN_BASE", "#capes-importacao-livros-form > fieldset > div:nth-child(1) > ul > li:nth-child(5)");
		selenium.assegurarTexto("ISBN", "#capes-importacao-livros-form > fieldset > div:nth-child(1) > ul > li:nth-child(6)");
		selenium.assegurarTexto("URL", "#capes-importacao-livros-form > fieldset > div:nth-child(1) > ul > li:nth-child(7)");
		selenium.assegurarTexto("NM_IES", "#capes-importacao-livros-form > fieldset > div:nth-child(1) > ul > li:nth-child(8)");
		selenium.assegurarTexto("SG_IES", "#capes-importacao-livros-form > fieldset > div:nth-child(1) > ul > li:nth-child(9)");
		selenium.assegurarTexto("NM_PROGRAMA", "#capes-importacao-livros-form > fieldset > div:nth-child(1) > ul > li:nth-child(10)");
		selenium.assegurarTexto("NM_PROJETO", "#capes-importacao-livros-form > fieldset > div:nth-child(1) > ul > li:nth-child(11)");
		selenium.assegurarTexto("NM_AREA_CONCENTRACAO", "#capes-importacao-livros-form > fieldset > div:nth-child(1) > ul > li:nth-child(12)");
		selenium.assegurarTexto("NM_LINHA_PESQUISA", "#capes-importacao-livros-form > fieldset > div:nth-child(1) > ul > li:nth-child(13)");
		selenium.assegurarTexto("NOME DA EDITORA", "#capes-importacao-livros-form > fieldset > div:nth-child(1) > ul > li:nth-child(14)");
		selenium.assegurarTexto("CIDADE / PAÍS", "#capes-importacao-livros-form > fieldset > div:nth-child(1) > ul > li:nth-child(15)");
		selenium.assegurarTexto("NATUREZA DA OBRA", "#capes-importacao-livros-form > fieldset > div:nth-child(1) > ul > li:nth-child(16)");
		selenium.assegurarTexto("NATUREZA DO CONTEÚDO", "#capes-importacao-livros-form > fieldset > div:nth-child(1) > ul > li:nth-child(17)");
		selenium.assegurarTexto("MEIO DE DIVULGAÇÃO", "#capes-importacao-livros-form > fieldset > div:nth-child(1) > ul > li:nth-child(18)");
		selenium.assegurarTexto("IDIOMA", "#capes-importacao-livros-form > fieldset > div:nth-child(1) > ul > li:nth-child(19)");
		selenium.assegurarTexto("TIPO DA CONTRIBUIÇÃO NA OBRA", "#capes-importacao-livros-form > fieldset > div:nth-child(1) > ul > li:nth-child(20)");
		selenium.assegurarTexto("NÚMERO DE PÁGINAS DA CONTRIBUIÇÃ", "#capes-importacao-livros-form > fieldset > div:nth-child(1) > ul > li:nth-child(21)");
		selenium.assegurarTexto(aviso, "#capes-importacao-livros-form > fieldset > div:nth-child(1) > p:nth-of-type(2)");
		selenium.assegurarTexto("Arquivo", "#capes-importacao-livros-form > fieldset > div:nth-child(2) > label");
		selenium.assegurarTexto("Enviar", "#capes-importacao-livros-form > fieldset > div.section.formButtons.form_buttons > button");
	}

	@Test
	void importarProducoesComSucesso() throws Exception {
		String arquivo = "src/test/resources/csv/livros-computacao-dois-exemplos.csv";
		selenium.selecionarArquivo(arquivo, "#capes-importacao-livros-selecao-arquivo");
		selenium.clicar("#capes-importacao-livros-form > fieldset > div.section.formButtons.form_buttons > button");

		StringBuilder mensagem = new StringBuilder();
		mensagem.append("Importação realizada com sucesso\n\n");
		mensagem.append("Produções importadas: 2");
		selenium.assegurarTexto(mensagem.toString(), ".ui-pnotify.notifySuccess .ui-pnotify-text");
	}

	@Test
	void importarProducoesDuasVezesComSucesso() throws Exception {
		String arquivo = "src/test/resources/csv/livros-computacao-dois-exemplos.csv";
		selenium.selecionarArquivo(arquivo, "#capes-importacao-livros-selecao-arquivo");
		selenium.clicar("#capes-importacao-livros-form > fieldset > div.section.formButtons.form_buttons > button");
		selenium.esperarAparecer(".ui-pnotify.notifySuccess .ui-pnotify-text");
		selenium.esperarDesaparecer(".ui-pnotify.notifySuccess .ui-pnotify-text");

		String arquivioAtualizado = "src/test/resources/csv/livros-computacao-dois-exemplos-atualizados.csv";
		selenium.selecionarArquivo(arquivioAtualizado, "#capes-importacao-livros-selecao-arquivo");
		selenium.clicar("#capes-importacao-livros-form > fieldset > div.section.formButtons.form_buttons > button");

		StringBuilder mensagem = new StringBuilder();
		mensagem.append("Importação realizada com sucesso\n\n");
		mensagem.append("Produções importadas: 2");
		selenium.assegurarTexto(mensagem.toString(), ".ui-pnotify.notifySuccess .ui-pnotify-text");
	}

	@Test
	void importarProducoesSemArquivo() throws Exception {
		selenium.clicar("#capes-importacao-livros-form > fieldset > div.section.formButtons.form_buttons > button");
		selenium.assegurarTexto("Não foi possível realizar a importação, pois nenhum arquivo foi selecionado", ".ui-pnotify.notifyError .ui-pnotify-text");
	}

	@Test
	void importarProducoesArquivoVazio() throws Exception {
		String arquivo = "src/test/resources/csv/vazio.csv";
		selenium.selecionarArquivo(arquivo, "#capes-importacao-livros-selecao-arquivo");
		selenium.clicar("#capes-importacao-livros-form > fieldset > div.section.formButtons.form_buttons > button");
		selenium.assegurarTexto("Não foi possível realizar a importação, pois o arquivo está vazio", ".ui-pnotify.notifyError .ui-pnotify-text");
	}

	@Test
	void importarProducoesArquivoSemCabecalho() throws Exception {
		String arquivo = "src/test/resources/csv/livros-computacao-sem-cabecalhos.csv";
		selenium.selecionarArquivo(arquivo, "#capes-importacao-livros-selecao-arquivo");
		selenium.clicar("#capes-importacao-livros-form > fieldset > div.section.formButtons.form_buttons > button");
		selenium.assegurarTexto("Não foi possível realizar a importação, pois os seguintes cabeçalhos estão faltando: ID_PRODUCAO_INTELECTUAL, ID_ADD_PRODUCAO_INTELECTUAL, autores, NM_PRODUCAO, AN_BASE, ISBN, URL, NM_IES, SG_IES, NM_PROGRAMA, NM_PROJETO, NM_AREA_CONCENTRACAO, NM_LINHA_PESQUISA, NOME DA EDITORA, CIDADE / PAÍS, NATUREZA DA OBRA, NATUREZA DO CONTEÚDO, MEIO DE DIVULGAÇÃO, IDIOMA, TIPO DA CONTRIBUIÇÃO NA OBRA, NÚMERO DE PÁGINAS DA CONTRIBUIÇÃ", ".ui-pnotify.notifyError .ui-pnotify-text");
	}

	@Test
	void importarProducoesArquivoSemIdentificador() throws Exception {
		String arquivo = "src/test/resources/csv/livros-computacao-sem-identificador.csv";
		selenium.selecionarArquivo(arquivo, "#capes-importacao-livros-selecao-arquivo");
		selenium.clicar("#capes-importacao-livros-form > fieldset > div.section.formButtons.form_buttons > button");
		selenium.assegurarTexto("Não foi possível realizar a importação, pois os seguintes cabeçalhos estão faltando: ID_PRODUCAO_INTELECTUAL", ".ui-pnotify.notifyError .ui-pnotify-text");
	}

	@Test
	void importarProducoesVerSubmissoesDeImportacaoComSucesso() throws Exception {
		String arquivo = "src/test/resources/csv/livros-computacao-dois-exemplos.csv";
		selenium.selecionarArquivo(arquivo, "#capes-importacao-livros-selecao-arquivo");
		selenium.clicar("#capes-importacao-livros-form > fieldset > div.section.formButtons.form_buttons > button");
		selenium.esperarAparecer(".ui-pnotify.notifySuccess .ui-pnotify-text");

		selenium.rolarParaCima();
		selenium.clicar("#navigationPrimary > li:nth-child(1) > a");

		selenium.assegurarQuantidadeDeElementos(2, "#myQueue .pkpListPanelItem .pkpListPanelItem--submission__author");
		selenium.assegurarQuantidadeDeElementos(2, "#myQueue .pkpListPanelItem .pkpListPanelItem--submission__title");
		selenium.assegurarQuantidadeDeElementos(2, "#myQueue .pkpListPanelItem .pkpListPanelItem--submission__activity");
		selenium.assegurarTexto("DALVA MARIA DE CASTRO VITTI, CLAUDIO BIELENKI JUNIOR, LEONARDO CAMPOS INOCENCIO, JOSE CARLOS DA SILVA FREITAS JUNIOR, FREDERICO FABIO MAUAD, MAURICIO ROBERTO VERONEZ", "#myQueue .pkpListPanelItem:nth-child(1) .pkpListPanelItem--submission__author");
		selenium.assegurarTexto("MARIO ALEXANDRE GAZZIRO", "#myQueue .pkpListPanelItem:nth-child(2) .pkpListPanelItem--submission__author");
		selenium.assegurarTexto("ACURÁCIA HORIZONTAL", "#myQueue .pkpListPanelItem:nth-child(1) .pkpListPanelItem--submission__title");
		selenium.assegurarTexto("O ÚLTIMO CICLO", "#myQueue .pkpListPanelItem:nth-child(2) .pkpListPanelItem--submission__title");
		selenium.assegurarTexto("No editor has been assigned to this submission.", "#myQueue .pkpListPanelItem:nth-child(1) .pkpListPanelItem--submission__activity");
		selenium.assegurarTexto("No editor has been assigned to this submission.", "#myQueue .pkpListPanelItem:nth-child(2) .pkpListPanelItem--submission__activity");
	}

	@Test
	void importarProducoesVerSubmissoesDeDuasImportacoesDiferentesComSucesso() throws Exception {
		String arquivo = "src/test/resources/csv/livros-computacao-dois-exemplos.csv";
		selenium.selecionarArquivo(arquivo, "#capes-importacao-livros-selecao-arquivo");
		selenium.clicar("#capes-importacao-livros-form > fieldset > div.section.formButtons.form_buttons > button");
		selenium.esperarAparecer(".ui-pnotify.notifySuccess .ui-pnotify-text");
		selenium.esperarDesaparecer(".ui-pnotify.notifySuccess .ui-pnotify-text");

		String arquivioAtualizado = "src/test/resources/csv/livros-computacao-dois-exemplos-atualizados.csv";
		selenium.selecionarArquivo(arquivioAtualizado, "#capes-importacao-livros-selecao-arquivo");
		selenium.clicar("#capes-importacao-livros-form > fieldset > div.section.formButtons.form_buttons > button");
		selenium.esperarAparecer(".ui-pnotify.notifySuccess .ui-pnotify-text");

		selenium.rolarParaCima();
		selenium.clicar("#navigationPrimary > li:nth-child(1) > a");

		selenium.assegurarTexto("DALVA VITTI, CLAUDIO BIELENKI JUNIOR, LEONARDO CAMPOS INOCENCIO, JOSE CARLOS DA SILVA FREITAS JUNIOR, FREDERICO FABIO MAUAD, MAURICIO ROBERTO VERONEZ", "#myQueue .pkpListPanelItem:nth-child(1) .pkpListPanelItem--submission__author");
		selenium.assegurarTexto("MARIO GAZZIRO", "#myQueue .pkpListPanelItem:nth-child(2) .pkpListPanelItem--submission__author");
		selenium.assegurarTexto("A ACURÁCIA HORIZONTAL", "#myQueue .pkpListPanelItem:nth-child(1) .pkpListPanelItem--submission__title");
		selenium.assegurarTexto("ÚLTIMO CICLO", "#myQueue .pkpListPanelItem:nth-child(2) .pkpListPanelItem--submission__title");
		selenium.assegurarTexto("No editor has been assigned to this submission.", "#myQueue .pkpListPanelItem:nth-child(1) .pkpListPanelItem--submission__activity");
		selenium.assegurarTexto("No editor has been assigned to this submission.", "#myQueue .pkpListPanelItem:nth-child(2) .pkpListPanelItem--submission__activity");
		selenium.assegurarQuantidadeDeElementos(2, "#myQueue .pkpListPanelItem .pkpListPanelItem--submission__author");
		selenium.assegurarQuantidadeDeElementos(2, "#myQueue .pkpListPanelItem .pkpListPanelItem--submission__title");
		selenium.assegurarQuantidadeDeElementos(2, "#myQueue .pkpListPanelItem .pkpListPanelItem--submission__activity");
	}

	@AfterEach
	void finalizar() throws Exception {
		selenium.fechar();
		servidor.finalizar();
		correioEletronico.finalizar();
	}

}
