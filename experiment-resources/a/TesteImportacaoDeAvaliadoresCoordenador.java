package br.com.lepidus.omp.capes;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TesteImportacaoDeAvaliadoresCoordenador {

	private CorreioEletronico correioEletronico;
	private BancoDeDados bancoDeDados;
	private Servidor servidor;
	private Selenium selenium;

	@BeforeEach
	void configurar() throws Exception {
		correioEletronico = new CorreioEletronico();
		bancoDeDados = new BancoDeDados();
		servidor = new Servidor();
		selenium = new FabricaSelenium().construir("importacao-de-avaliadores-e-coordenador");
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
		selenium.clicar("#capes-abas > ul > li:nth-child(3) > a");
	}

	@Test
	void importarAvaliadoresCoordenadorPagina() throws Exception {
		String instrucoes = "Utilize o espaço abaixo para importar os avaliadores e o coordenador de área a partir de um arquivo em formato CSV codificado em ISO-8859-1. O arquivo deve ter os campos separados por vírgula e utilizar aspas duplas como delimitador textual. A primeira linha do arquivo CSV deve conter o cabeçalho, sendo que, pelo menos, as seguintes colunas são esperadas (não necessáriamente na ordem apresentada):";
		String aviso1 = "Usuários já existentes, isto é, com mesmo CPF, terão seus dados atualizados de acordo com a versão presente no arquivo de importação.";
		String aviso2 = "Definições de avaliadores e coordenador de área já existentes nesta área do conhecimento serão removidas.";
		selenium.assegurarTexto("Instruções", "#capes-importacao-avaliadores-e-coordenador-form > fieldset > div:nth-child(1) > label");
		selenium.assegurarTexto(instrucoes, "#capes-importacao-avaliadores-e-coordenador-form > fieldset > div:nth-child(1) > p:nth-of-type(1)");
		selenium.assegurarQuantidadeDeElementos(5, "#capes-importacao-avaliadores-e-coordenador-form > fieldset > div:nth-child(1) > ul > li");
		selenium.assegurarTexto("Documento", "#capes-importacao-avaliadores-e-coordenador-form > fieldset > div:nth-child(1) > ul > li:nth-child(1)");
		selenium.assegurarTexto("Nome do Consultor", "#capes-importacao-avaliadores-e-coordenador-form > fieldset > div:nth-child(1) > ul > li:nth-child(2)");
		selenium.assegurarTexto("Cargo", "#capes-importacao-avaliadores-e-coordenador-form > fieldset > div:nth-child(1) > ul > li:nth-child(3)");
		selenium.assegurarTexto("Instituição de Ensino", "#capes-importacao-avaliadores-e-coordenador-form > fieldset > div:nth-child(1) > ul > li:nth-child(4)");
		selenium.assegurarTexto("E-mail", "#capes-importacao-avaliadores-e-coordenador-form > fieldset > div:nth-child(1) > ul > li:nth-child(5)");
		selenium.assegurarTexto(aviso1, "#capes-importacao-avaliadores-e-coordenador-form > fieldset > div:nth-child(1) > p:nth-of-type(2)");
		selenium.assegurarTexto(aviso2, "#capes-importacao-avaliadores-e-coordenador-form > fieldset > div:nth-child(1) > p:nth-of-type(3)");
		selenium.assegurarTexto("Arquivo", "#capes-importacao-avaliadores-e-coordenador-form > fieldset > div:nth-child(2) > label");
		selenium.assegurarTexto("Enviar", "#capes-importacao-avaliadores-e-coordenador-form > fieldset > div.section.formButtons.form_buttons > button");
	}

	@Test
	void importarAvaliadoresCoordenadorComSucesso() throws Exception {
		String arquivo = "src/test/resources/csv/avaliadores-e-coordenador.csv";
		selenium.selecionarArquivo(arquivo, "#capes-importacao-avaliadores-e-coordenador-selecao-arquivo");
		selenium.clicar("#capes-importacao-avaliadores-e-coordenador-form > fieldset > div.section.formButtons.form_buttons > button");

		StringBuilder mensagem = new StringBuilder();
		mensagem.append("Importação realizada com sucesso\n\n");
		mensagem.append("Avaliadores definidos: 12\n");
		mensagem.append("Coordenadores definidos: 1\n\n");
		mensagem.append("Usuários novos: 13\n");
		mensagem.append("Usuários atualizados: 0");
		selenium.assegurarTexto(mensagem.toString(), ".ui-pnotify.notifySuccess .ui-pnotify-text");
	}

	@Test
	void importarAvaliadoresCoordenadorDuasVezesComSucesso() throws Exception {
		String arquivo = "src/test/resources/csv/avaliadores-e-coordenador-tres-exemplos.csv";
		selenium.selecionarArquivo(arquivo, "#capes-importacao-avaliadores-e-coordenador-selecao-arquivo");
		selenium.clicar("#capes-importacao-avaliadores-e-coordenador-form > fieldset > div.section.formButtons.form_buttons > button");
		selenium.esperarAparecer(".ui-pnotify.notifySuccess .ui-pnotify-text");
		selenium.esperarDesaparecer(".ui-pnotify.notifySuccess .ui-pnotify-text");

		String arquivoAtualizado = "src/test/resources/csv/avaliadores-e-coordenador-tres-exemplos-atualizados.csv";
		selenium.selecionarArquivo(arquivoAtualizado, "#capes-importacao-avaliadores-e-coordenador-selecao-arquivo");
		selenium.clicar("#capes-importacao-avaliadores-e-coordenador-form > fieldset > div.section.formButtons.form_buttons > button");

		StringBuilder mensagem = new StringBuilder();
		mensagem.append("Importação realizada com sucesso\n\n");
		mensagem.append("Avaliadores definidos: 2\n");
		mensagem.append("Coordenadores definidos: 1\n\n");
		mensagem.append("Usuários novos: 0\n");
		mensagem.append("Usuários atualizados: 3");
		selenium.assegurarTexto(mensagem.toString(), ".ui-pnotify.notifySuccess .ui-pnotify-text");
	}

	@Test
	void importarAvaliadoresCoordenadorSemArquivo() throws Exception {
		selenium.clicar("#capes-importacao-avaliadores-e-coordenador-form > fieldset > div.section.formButtons.form_buttons > button");
		selenium.assegurarTexto("Não foi possível realizar a importação, pois nenhum arquivo foi selecionado", ".ui-pnotify.notifyError .ui-pnotify-text");
	}

	@Test
	void importarAvaliadoresCoordenadorArquivoVazio() throws Exception {
		String arquivio = "src/test/resources/csv/vazio.csv";
		selenium.selecionarArquivo(arquivio, "#capes-importacao-avaliadores-e-coordenador-selecao-arquivo");
		selenium.clicar("#capes-importacao-avaliadores-e-coordenador-form > fieldset > div.section.formButtons.form_buttons > button");
		selenium.assegurarTexto("Não foi possível realizar a importação, pois o arquivo está vazio", ".ui-pnotify.notifyError .ui-pnotify-text");
	}

	@Test
	void importarAvaliadoresCoordenadorArquivoSemCabecalho() throws Exception {
		String arquivo = "src/test/resources/csv/avaliadores-e-coordenador-sem-cabecalhos.csv";
		selenium.selecionarArquivo(arquivo, "#capes-importacao-avaliadores-e-coordenador-selecao-arquivo");
		selenium.clicar("#capes-importacao-avaliadores-e-coordenador-form > fieldset > div.section.formButtons.form_buttons > button");
		selenium.assegurarTexto("Não foi possível realizar a importação, pois os seguintes cabeçalhos estão faltando: Documento, Nome do Consultor, Cargo, Instituição de Ensino, E-mail", ".ui-pnotify.notifyError .ui-pnotify-text");
	}

	@Test
	void importarAvaliadoresCoordenadorArquivoSemDocumento() throws Exception {
		String arquivo = "src/test/resources/csv/avaliadores-e-coordenador-sem-documento.csv";
		selenium.selecionarArquivo(arquivo, "#capes-importacao-avaliadores-e-coordenador-selecao-arquivo");
		selenium.clicar("#capes-importacao-avaliadores-e-coordenador-form > fieldset > div.section.formButtons.form_buttons > button");
		selenium.assegurarTexto("Não foi possível realizar a importação, pois os seguintes cabeçalhos estão faltando: Documento", ".ui-pnotify.notifyError .ui-pnotify-text");
	}

	@Test
	void importarAvaliadoresCoordenadorVerAvaliadoresDeImportacao() throws Exception {
		String arquivo = "src/test/resources/csv/avaliadores-e-coordenador-tres-exemplos.csv";
		selenium.selecionarArquivo(arquivo, "#capes-importacao-avaliadores-e-coordenador-selecao-arquivo");
		selenium.clicar("#capes-importacao-avaliadores-e-coordenador-form > fieldset > div.section.formButtons.form_buttons > button");
		selenium.esperarAparecer(".ui-pnotify.notifySuccess .ui-pnotify-text");

		selenium.passarOMouse("#navigationPrimary > li:nth-child(4) > a");
		selenium.clicar("#navigationPrimary > li:nth-child(4) > ul > li:nth-child(1) > a");
		selenium.clicar("#userGridContainer .pkp_linkaction_search");
		selenium.selecionar(17, "#userGroup");
		selenium.clicar("#userGridContainer .submitFormButton");

		selenium.assegurarQuantidadeDeElementos(2, "#userGridContainer tbody > tr.gridRow");
		selenium.assegurarTexto("CRISTIANO", "#userGridContainer tbody > tr:nth-child(1) > td:nth-child(1)");
		selenium.assegurarTexto("ALEXANDRE VIRGINIO CAVALCANTE", "#userGridContainer tbody > tr:nth-child(1) > td:nth-child(2)");
		selenium.assegurarTexto("02119771448", "#userGridContainer tbody > tr:nth-child(1) > td:nth-child(3)");
		selenium.assegurarTexto("cristianogesm@gmail.com", "#userGridContainer tbody > tr:nth-child(1) > td:nth-child(4)");
		selenium.assegurarTexto("ANA", "#userGridContainer tbody > tr:nth-child(3) > td:nth-child(1)");
		selenium.assegurarTexto("PAULA CABRAL SEIXAS COSTA", "#userGridContainer tbody > tr:nth-child(3) > td:nth-child(2)");
		selenium.assegurarTexto("50928147487", "#userGridContainer tbody > tr:nth-child(3) > td:nth-child(3)");
		selenium.assegurarTexto("apcabral@ufpe.br", "#userGridContainer tbody > tr:nth-child(3) > td:nth-child(4)");
	}

	@Test
	void importarAvaliadoresCoordenadorVerCoordenadorDeImportacao() throws Exception {
		String arquivo = "src/test/resources/csv/avaliadores-e-coordenador-tres-exemplos.csv";
		selenium.selecionarArquivo(arquivo, "#capes-importacao-avaliadores-e-coordenador-selecao-arquivo");
		selenium.clicar("#capes-importacao-avaliadores-e-coordenador-form > fieldset > div.section.formButtons.form_buttons > button");
		selenium.esperarAparecer(".ui-pnotify.notifySuccess .ui-pnotify-text");

		selenium.passarOMouse("#navigationPrimary > li:nth-child(4) > a");
		selenium.clicar("#navigationPrimary > li:nth-child(4) > ul > li:nth-child(1) > a");
		selenium.clicar("#userGridContainer .pkp_linkaction_search");
		selenium.selecionar(3, "#userGroup");
		selenium.clicar("#userGridContainer .submitFormButton");

		selenium.assegurarQuantidadeDeElementos(1, "#userGridContainer tbody > tr.gridRow");
		selenium.assegurarTexto("EDGAR", "#userGridContainer tbody > tr:nth-child(1) > td:nth-child(1)");
		selenium.assegurarTexto("NOBUO MAMIYA", "#userGridContainer tbody > tr:nth-child(1) > td:nth-child(2)");
		selenium.assegurarTexto("02011177898", "#userGridContainer tbody > tr:nth-child(1) > td:nth-child(3)");
		selenium.assegurarTexto("edmamiya@gmail.com", "#userGridContainer tbody > tr:nth-child(1) > td:nth-child(4)");
	}

	@Test
	void importarAvaliadoresCoordenadorVerAvaliadoresDeSegundaImportacaoComDadosAtualizados() throws Exception {
		String arquivo = "src/test/resources/csv/avaliadores-e-coordenador-tres-exemplos.csv";
		selenium.selecionarArquivo(arquivo, "#capes-importacao-avaliadores-e-coordenador-selecao-arquivo");
		selenium.clicar("#capes-importacao-avaliadores-e-coordenador-form > fieldset > div.section.formButtons.form_buttons > button");
		selenium.esperarAparecer(".ui-pnotify.notifySuccess .ui-pnotify-text");
		selenium.esperarDesaparecer(".ui-pnotify.notifySuccess .ui-pnotify-text");

		String arquivoAtualizado = "src/test/resources/csv/avaliadores-e-coordenador-tres-exemplos-atualizados.csv";
		selenium.selecionarArquivo(arquivoAtualizado, "#capes-importacao-avaliadores-e-coordenador-selecao-arquivo");
		selenium.clicar("#capes-importacao-avaliadores-e-coordenador-form > fieldset > div.section.formButtons.form_buttons > button");
		selenium.esperarAparecer(".ui-pnotify.notifySuccess .ui-pnotify-text");

		selenium.passarOMouse("#navigationPrimary > li:nth-child(4) > a");
		selenium.clicar("#navigationPrimary > li:nth-child(4) > ul > li:nth-child(1) > a");
		selenium.clicar("#userGridContainer .pkp_linkaction_search");
		selenium.selecionar(17, "#userGroup");
		selenium.clicar("#userGridContainer .submitFormButton");

		selenium.assegurarQuantidadeDeElementos(2, "#userGridContainer tbody > tr.gridRow");
		selenium.assegurarTexto("ANA", "#userGridContainer tbody > tr:nth-child(1) > td:nth-child(1)");
		selenium.assegurarTexto("COSTA", "#userGridContainer tbody > tr:nth-child(1) > td:nth-child(2)");
		selenium.assegurarTexto("50928147487", "#userGridContainer tbody > tr:nth-child(1) > td:nth-child(3)");
		selenium.assegurarTexto("apcabral@ufpe.br", "#userGridContainer tbody > tr:nth-child(1) > td:nth-child(4)");
		selenium.assegurarTexto("EDGAR", "#userGridContainer tbody > tr:nth-child(3) > td:nth-child(1)");
		selenium.assegurarTexto("MAMIYA", "#userGridContainer tbody > tr:nth-child(3) > td:nth-child(2)");
		selenium.assegurarTexto("02011177898", "#userGridContainer tbody > tr:nth-child(3) > td:nth-child(3)");
		selenium.assegurarTexto("edmamiya@ufpe.br", "#userGridContainer tbody > tr:nth-child(3) > td:nth-child(4)");
	}

	@Test
	void importarAvaliadoresCoordenadorVerCoordenadorDeSegundaImportacaoComDadosAtualizados() throws Exception {
		String arquivo = "src/test/resources/csv/avaliadores-e-coordenador-tres-exemplos.csv";
		selenium.selecionarArquivo(arquivo, "#capes-importacao-avaliadores-e-coordenador-selecao-arquivo");
		selenium.clicar("#capes-importacao-avaliadores-e-coordenador-form > fieldset > div.section.formButtons.form_buttons > button");
		selenium.esperarAparecer(".ui-pnotify.notifySuccess .ui-pnotify-text");
		selenium.esperarDesaparecer(".ui-pnotify.notifySuccess .ui-pnotify-text");

		String arquivoAtualizado = "src/test/resources/csv/avaliadores-e-coordenador-tres-exemplos-atualizados.csv";
		selenium.selecionarArquivo(arquivoAtualizado, "#capes-importacao-avaliadores-e-coordenador-selecao-arquivo");
		selenium.clicar("#capes-importacao-avaliadores-e-coordenador-form > fieldset > div.section.formButtons.form_buttons > button");
		selenium.esperarAparecer(".ui-pnotify.notifySuccess .ui-pnotify-text");

		selenium.passarOMouse("#navigationPrimary > li:nth-child(4) > a");
		selenium.clicar("#navigationPrimary > li:nth-child(4) > ul > li:nth-child(1) > a");
		selenium.clicar("#userGridContainer .pkp_linkaction_search");
		selenium.selecionar(3, "#userGroup");
		selenium.clicar("#userGridContainer .submitFormButton");

		selenium.assegurarQuantidadeDeElementos(1, "#userGridContainer tbody > tr.gridRow");
		selenium.assegurarTexto("CRISTIANO", "#userGridContainer tbody > tr:nth-child(1) > td:nth-child(1)");
		selenium.assegurarTexto("CAVALCANTE", "#userGridContainer tbody > tr:nth-child(1) > td:nth-child(2)");
		selenium.assegurarTexto("02119771448", "#userGridContainer tbody > tr:nth-child(1) > td:nth-child(3)");
		selenium.assegurarTexto("cristianogomes@ufpe.br", "#userGridContainer tbody > tr:nth-child(1) > td:nth-child(4)");
	}

	@Test
	void importarAvaliadoresCoordenadorVerAvaliadoresDeSegundaImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados() throws Exception {
		String arquivo = "src/test/resources/csv/avaliadores-e-coordenador-tres-exemplos.csv";
		selenium.selecionarArquivo(arquivo, "#capes-importacao-avaliadores-e-coordenador-selecao-arquivo");
		selenium.clicar("#capes-importacao-avaliadores-e-coordenador-form > fieldset > div.section.formButtons.form_buttons > button");
		selenium.esperarAparecer(".ui-pnotify.notifySuccess .ui-pnotify-text");
		selenium.esperarDesaparecer(".ui-pnotify.notifySuccess .ui-pnotify-text");

		String arquivoAtualizado = "src/test/resources/csv/avaliadores-e-coordenador-tres-exemplos-atualizados.csv";
		selenium.passarOMouse("#navigationContextMenu");
		selenium.clicar("#navigationContextMenu ul.pkp_contexts > li:nth-child(1) > a");
		selenium.passarOMouse("#navigationPrimary > li:nth-child(5) > a");
		selenium.clicar("#navigationPrimary > li:nth-child(5) > ul > li:nth-child(1) > a");
		selenium.clicar("#ui-id-2 > div > ul > li:nth-child(1) > a");
		selenium.clicar("#capes-abas > ul > li:nth-child(3) > a");
		selenium.selecionarArquivo(arquivoAtualizado, "#capes-importacao-avaliadores-e-coordenador-selecao-arquivo");
		selenium.clicar("#capes-importacao-avaliadores-e-coordenador-form > fieldset > div.section.formButtons.form_buttons > button");
		selenium.esperarAparecer(".ui-pnotify.notifySuccess .ui-pnotify-text");

		selenium.passarOMouse("#navigationPrimary > li:nth-child(4) > a");
		selenium.clicar("#navigationPrimary > li:nth-child(4) > ul > li:nth-child(1) > a");
		selenium.clicar("#userGridContainer .pkp_linkaction_search");
		selenium.selecionar(17, "#userGroup");
		selenium.clicar("#userGridContainer .submitFormButton");

		selenium.assegurarQuantidadeDeElementos(2, "#userGridContainer tbody > tr.gridRow");
		selenium.assegurarTexto("ANA", "#userGridContainer tbody > tr:nth-child(1) > td:nth-child(1)");
		selenium.assegurarTexto("COSTA", "#userGridContainer tbody > tr:nth-child(1) > td:nth-child(2)");
		selenium.assegurarTexto("50928147487", "#userGridContainer tbody > tr:nth-child(1) > td:nth-child(3)");
		selenium.assegurarTexto("apcabral@ufpe.br", "#userGridContainer tbody > tr:nth-child(1) > td:nth-child(4)");
		selenium.assegurarTexto("EDGAR", "#userGridContainer tbody > tr:nth-child(3) > td:nth-child(1)");
		selenium.assegurarTexto("MAMIYA", "#userGridContainer tbody > tr:nth-child(3) > td:nth-child(2)");
		selenium.assegurarTexto("02011177898", "#userGridContainer tbody > tr:nth-child(3) > td:nth-child(3)");
		selenium.assegurarTexto("edmamiya@ufpe.br", "#userGridContainer tbody > tr:nth-child(3) > td:nth-child(4)");
	}

	@Test
	void importarAvaliadoresCoordenadorVerCoordenadorDeSegundaImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados() throws Exception {
		String arquivo = "src/test/resources/csv/avaliadores-e-coordenador-tres-exemplos.csv";
		selenium.selecionarArquivo(arquivo, "#capes-importacao-avaliadores-e-coordenador-selecao-arquivo");
		selenium.clicar("#capes-importacao-avaliadores-e-coordenador-form > fieldset > div.section.formButtons.form_buttons > button");
		selenium.esperarAparecer(".ui-pnotify.notifySuccess .ui-pnotify-text");
		selenium.esperarDesaparecer(".ui-pnotify.notifySuccess .ui-pnotify-text");

		String arquivoAtualizado = "src/test/resources/csv/avaliadores-e-coordenador-tres-exemplos-atualizados.csv";
		selenium.passarOMouse("#navigationContextMenu");
		selenium.clicar("#navigationContextMenu ul.pkp_contexts > li:nth-child(1) > a");
		selenium.passarOMouse("#navigationPrimary > li:nth-child(5) > a");
		selenium.clicar("#navigationPrimary > li:nth-child(5) > ul > li:nth-child(1) > a");
		selenium.clicar("#ui-id-2 > div > ul > li:nth-child(1) > a");
		selenium.clicar("#capes-abas > ul > li:nth-child(3) > a");
		selenium.selecionarArquivo(arquivoAtualizado, "#capes-importacao-avaliadores-e-coordenador-selecao-arquivo");
		selenium.clicar("#capes-importacao-avaliadores-e-coordenador-form > fieldset > div.section.formButtons.form_buttons > button");
		selenium.esperarAparecer(".ui-pnotify.notifySuccess .ui-pnotify-text");

		selenium.passarOMouse("#navigationPrimary > li:nth-child(4) > a");
		selenium.clicar("#navigationPrimary > li:nth-child(4) > ul > li:nth-child(1) > a");
		selenium.clicar("#userGridContainer .pkp_linkaction_search");
		selenium.selecionar(3, "#userGroup");
		selenium.clicar("#userGridContainer .submitFormButton");

		selenium.assegurarQuantidadeDeElementos(1, "#userGridContainer tbody > tr.gridRow");
		selenium.assegurarTexto("CRISTIANO", "#userGridContainer tbody > tr:nth-child(1) > td:nth-child(1)");
		selenium.assegurarTexto("CAVALCANTE", "#userGridContainer tbody > tr:nth-child(1) > td:nth-child(2)");
		selenium.assegurarTexto("02119771448", "#userGridContainer tbody > tr:nth-child(1) > td:nth-child(3)");
		selenium.assegurarTexto("cristianogomes@ufpe.br", "#userGridContainer tbody > tr:nth-child(1) > td:nth-child(4)");
	}

	@Test
	void importarAvaliadoresCoordenadorVerAvaliadoresDePrimeiraImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados() throws Exception {
		String arquivo = "src/test/resources/csv/avaliadores-e-coordenador-tres-exemplos.csv";
		selenium.selecionarArquivo(arquivo, "#capes-importacao-avaliadores-e-coordenador-selecao-arquivo");
		selenium.clicar("#capes-importacao-avaliadores-e-coordenador-form > fieldset > div.section.formButtons.form_buttons > button");
		selenium.esperarAparecer(".ui-pnotify.notifySuccess .ui-pnotify-text");
		selenium.esperarDesaparecer(".ui-pnotify.notifySuccess .ui-pnotify-text");

		String arquivoAtualizado = "src/test/resources/csv/avaliadores-e-coordenador-tres-exemplos-atualizados.csv";
		selenium.passarOMouse("#navigationContextMenu");
		selenium.clicar("#navigationContextMenu ul.pkp_contexts > li:nth-child(1) > a");
		selenium.passarOMouse("#navigationPrimary > li:nth-child(5) > a");
		selenium.clicar("#navigationPrimary > li:nth-child(5) > ul > li:nth-child(1) > a");
		selenium.clicar("#ui-id-2 > div > ul > li:nth-child(1) > a");
		selenium.clicar("#capes-abas > ul > li:nth-child(3) > a");
		selenium.selecionarArquivo(arquivoAtualizado, "#capes-importacao-avaliadores-e-coordenador-selecao-arquivo");
		selenium.clicar("#capes-importacao-avaliadores-e-coordenador-form > fieldset > div.section.formButtons.form_buttons > button");
		selenium.esperarAparecer(".ui-pnotify.notifySuccess .ui-pnotify-text");

		selenium.passarOMouse("#navigationContextMenu");
		selenium.clicar("#navigationContextMenu ul.pkp_contexts > li:nth-child(1) > a");
		selenium.passarOMouse("#navigationPrimary > li:nth-child(4) > a");
		selenium.clicar("#navigationPrimary > li:nth-child(4) > ul > li:nth-child(1) > a");
		selenium.clicar("#userGridContainer .pkp_linkaction_search");
		selenium.selecionar(17, "#userGroup");
		selenium.clicar("#userGridContainer .submitFormButton");

		selenium.assegurarQuantidadeDeElementos(2, "#userGridContainer tbody > tr.gridRow");
		selenium.assegurarTexto("CRISTIANO", "#userGridContainer tbody > tr:nth-child(1) > td:nth-child(1)");
		selenium.assegurarTexto("CAVALCANTE", "#userGridContainer tbody > tr:nth-child(1) > td:nth-child(2)");
		selenium.assegurarTexto("02119771448", "#userGridContainer tbody > tr:nth-child(1) > td:nth-child(3)");
		selenium.assegurarTexto("cristianogomes@ufpe.br", "#userGridContainer tbody > tr:nth-child(1) > td:nth-child(4)");
		selenium.assegurarTexto("ANA", "#userGridContainer tbody > tr:nth-child(3) > td:nth-child(1)");
		selenium.assegurarTexto("COSTA", "#userGridContainer tbody > tr:nth-child(3) > td:nth-child(2)");
		selenium.assegurarTexto("50928147487", "#userGridContainer tbody > tr:nth-child(3) > td:nth-child(3)");
		selenium.assegurarTexto("apcabral@ufpe.br", "#userGridContainer tbody > tr:nth-child(3) > td:nth-child(4)");
	}

	@Test
	void importarAvaliadoresCoordenadorVerCoordenadorDePrimeiraImportacaoDeDuasImportacoesEmAreaDiferenteComDadosAtualizados() throws Exception {
		String arquivo = "src/test/resources/csv/avaliadores-e-coordenador-tres-exemplos.csv";
		selenium.selecionarArquivo(arquivo, "#capes-importacao-avaliadores-e-coordenador-selecao-arquivo");
		selenium.clicar("#capes-importacao-avaliadores-e-coordenador-form > fieldset > div.section.formButtons.form_buttons > button");
		selenium.esperarAparecer(".ui-pnotify.notifySuccess .ui-pnotify-text");
		selenium.esperarDesaparecer(".ui-pnotify.notifySuccess .ui-pnotify-text");

		String arquivoAtualizado = "src/test/resources/csv/avaliadores-e-coordenador-tres-exemplos-atualizados.csv";
		selenium.passarOMouse("#navigationContextMenu");
		selenium.clicar("#navigationContextMenu ul.pkp_contexts > li:nth-child(1) > a");
		selenium.passarOMouse("#navigationPrimary > li:nth-child(5) > a");
		selenium.clicar("#navigationPrimary > li:nth-child(5) > ul > li:nth-child(1) > a");
		selenium.clicar("#ui-id-2 > div > ul > li:nth-child(1) > a");
		selenium.clicar("#capes-abas > ul > li:nth-child(3) > a");
		selenium.selecionarArquivo(arquivoAtualizado, "#capes-importacao-avaliadores-e-coordenador-selecao-arquivo");
		selenium.clicar("#capes-importacao-avaliadores-e-coordenador-form > fieldset > div.section.formButtons.form_buttons > button");
		selenium.esperarAparecer(".ui-pnotify.notifySuccess .ui-pnotify-text");

		selenium.passarOMouse("#navigationContextMenu");
		selenium.clicar("#navigationContextMenu ul.pkp_contexts > li:nth-child(1) > a");
		selenium.passarOMouse("#navigationPrimary > li:nth-child(4) > a");
		selenium.clicar("#navigationPrimary > li:nth-child(4) > ul > li:nth-child(1) > a");
		selenium.clicar("#userGridContainer .pkp_linkaction_search");
		selenium.selecionar(3, "#userGroup");
		selenium.clicar("#userGridContainer .submitFormButton");

		selenium.assegurarQuantidadeDeElementos(1, "#userGridContainer tbody > tr.gridRow");
		selenium.assegurarTexto("EDGAR", "#userGridContainer tbody > tr:nth-child(1) > td:nth-child(1)");
		selenium.assegurarTexto("MAMIYA", "#userGridContainer tbody > tr:nth-child(1) > td:nth-child(2)");
		selenium.assegurarTexto("02011177898", "#userGridContainer tbody > tr:nth-child(1) > td:nth-child(3)");
		selenium.assegurarTexto("edmamiya@ufpe.br", "#userGridContainer tbody > tr:nth-child(1) > td:nth-child(4)");
	}

	@AfterEach
	void finalizar() throws Exception {
		selenium.fechar();
		servidor.finalizar();
		correioEletronico.finalizar();
	}

}
