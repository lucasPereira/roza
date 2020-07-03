package br.com.lepidus.omp.capes;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TesteConfiguracaoDeFichaDeAvalicao {

	private CorreioEletronico correioEletronico;
	private BancoDeDados bancoDeDados;
	private Servidor servidor;
	private Selenium selenium;

	@BeforeEach
	void configurar() throws Exception {
		correioEletronico = new CorreioEletronico();
		bancoDeDados = new BancoDeDados();
		servidor = new Servidor();
		selenium = new FabricaSelenium().construir("configuracao-de-ficha-de-avaliacao");
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
		selenium.clicar("#capes-abas > ul > li:nth-child(4) > a");
	}

	@Test
	void cadastrarFichaDeAvaliacaoPagina() throws Exception {
		String mensagem = "Para cadastrar a ficha de avaliação utilize o botão abaixo. Para cada um dos indicadores abaixo é possível defenir o seu peso e a pontuação de cada uma das categorias pertencentes ao indicador em questão. A pontuação efetiva de cada categoria consistirá da multiplicação do peso do indicador (valor inteiro ou decimal separado por vírgula) pela pontuação da categoria (valor inteiro). O somatório da pontuação efetiva máxima da cada indicador não poderá ultrapassar 100, sendo que para efeito de cálculo não serão contabilizados os indicadores extras: premiação, indicação como obra referência e tradução para outros idiomas.";
		String aviso = "Caso a ficha de avaliação já exista, então a mesma será recadastrada.";
		selenium.assegurarTexto(mensagem, "#capes-configuracao-ficha-avaliacao-form > fieldset > div:nth-child(1) > p:nth-of-type(1)");
		selenium.assegurarTexto(aviso, "#capes-configuracao-ficha-avaliacao-form > fieldset > div:nth-child(1) > p:nth-of-type(2)");
		selenium.assegurarTexto("Cadastrar", "#capes-configuracao-ficha-avaliacao-form > fieldset > div.section.formButtons.form_buttons > button");
	}

	@Test
	void cadastrarFichaDeAvaliacaoComSucesso() throws Exception {
		StringBuilder mensagem = new StringBuilder();
		mensagem.append("Configuração realizada com sucesso\n\n");
		mensagem.append("Pontuação máxima: 100");
		selenium.limpar("#capes-configuracao-ficha-avaliacao-form  input[name=indicador-idioma]");
		selenium.digitar("0", "#capes-configuracao-ficha-avaliacao-form  input[name=indicador-idioma]");
		selenium.limpar("#capes-configuracao-ficha-avaliacao-form  input[name=indicador-editora]");
		selenium.digitar("0", "#capes-configuracao-ficha-avaliacao-form  input[name=indicador-editora]");
		selenium.limpar("#capes-configuracao-ficha-avaliacao-form  input[name=indicador-financiamento]");
		selenium.digitar("0,5", "#capes-configuracao-ficha-avaliacao-form  input[name=indicador-financiamento]");
		selenium.limpar("#capes-configuracao-ficha-avaliacao-form  input[name=categoria-financiamento-proprio]");
		selenium.digitar("20", "#capes-configuracao-ficha-avaliacao-form  input[name=categoria-financiamento-proprio]");
		selenium.clicar("#capes-configuracao-ficha-avaliacao-form > fieldset > div.section.formButtons.form_buttons > button");
		selenium.assegurarTexto(mensagem.toString(), ".ui-pnotify.notifySuccess .ui-pnotify-text");
	}

	@Test
	void cadastrarFichaDeAvaliacaoSomatorioDePontosAcimaDe100() throws Exception {
		StringBuilder mensagem = new StringBuilder();
		mensagem.append("A pontuação máxima dos indicadores não pode ultrapassar 100 (não são considerados neste cálculo os indicadores extras: premiação, indicação como obra referência e tradução para outros idiomas)\n\n");
		mensagem.append("Pontuação máxima atual: 120");
		selenium.clicar("#capes-configuracao-ficha-avaliacao-form > fieldset > div.section.formButtons.form_buttons > button");
		selenium.assegurarTexto(mensagem.toString(), ".ui-pnotify.notifyError .ui-pnotify-text");
	}

	@Test
	void cadastrarFichaDeAvaliacaoDuasVezesComSucesso() throws Exception {
		selenium.limpar("#capes-configuracao-ficha-avaliacao-form  input[name=indicador-idioma]");
		selenium.digitar("0", "#capes-configuracao-ficha-avaliacao-form  input[name=indicador-idioma]");
		selenium.limpar("#capes-configuracao-ficha-avaliacao-form  input[name=indicador-editora]");
		selenium.digitar("0", "#capes-configuracao-ficha-avaliacao-form  input[name=indicador-editora]");
		selenium.clicar("#capes-configuracao-ficha-avaliacao-form > fieldset > div.section.formButtons.form_buttons > button");
		selenium.esperarAparecer(".ui-pnotify.notifySuccess .ui-pnotify-text");
		selenium.esperarDesaparecer(".ui-pnotify.notifySuccess .ui-pnotify-text");

		StringBuilder mensagem = new StringBuilder();
		mensagem.append("Reconfiguração realizada com sucesso\n\n");
		mensagem.append("Pontuação máxima: 95");
		selenium.limpar("#capes-configuracao-ficha-avaliacao-form  input[name=indicador-financiamento]");
		selenium.digitar("0,5", "#capes-configuracao-ficha-avaliacao-form  input[name=indicador-financiamento]");
		selenium.clicar("#capes-configuracao-ficha-avaliacao-form > fieldset > div.section.formButtons.form_buttons > button");
		selenium.assegurarTexto(mensagem.toString(), ".ui-pnotify.notifySuccess .ui-pnotify-text");
	}

	@Test
	void cadastrarFichaDeAvaliacaoVerFichaDeAvaliacao() throws Exception {
		selenium.limpar("#capes-configuracao-ficha-avaliacao-form  input[name=indicador-idioma]");
		selenium.digitar("0", "#capes-configuracao-ficha-avaliacao-form  input[name=indicador-idioma]");
		selenium.limpar("#capes-configuracao-ficha-avaliacao-form  input[name=indicador-editora]");
		selenium.digitar("0", "#capes-configuracao-ficha-avaliacao-form  input[name=indicador-editora]");
		selenium.clicar("#capes-configuracao-ficha-avaliacao-form > fieldset > div.section.formButtons.form_buttons > button");
		selenium.esperarAparecer(".ui-pnotify.notifySuccess .ui-pnotify-text");

		selenium.rolarParaCima();
		selenium.passarOMouse("#navigationPrimary > li:nth-child(3) > a");
		selenium.clicar("#navigationPrimary > li:nth-child(3) > ul > li:nth-child(3) > a");
		selenium.clicar("#ui-id-5");

		selenium.assegurarQuantidadeDeElementos(1, "#reviewFormGridContainer table > tbody:not(.empty) > tr.gridRow");
		selenium.assegurarTexto("Ficha de Avaliação", "#reviewFormGridContainer table > tbody:not(.empty) > tr.gridRow:nth-child(1) > td:nth-child(1)");
	}

	@Test
	void cadastrarFichaDeAvaliacaoVerFormulario() throws Exception {
		selenium.limpar("#capes-configuracao-ficha-avaliacao-form  input[name=indicador-idioma]");
		selenium.digitar("0", "#capes-configuracao-ficha-avaliacao-form  input[name=indicador-idioma]");
		selenium.limpar("#capes-configuracao-ficha-avaliacao-form  input[name=indicador-editora]");
		selenium.digitar("0", "#capes-configuracao-ficha-avaliacao-form  input[name=indicador-editora]");
		selenium.clicar("#capes-configuracao-ficha-avaliacao-form > fieldset > div.section.formButtons.form_buttons > button");
		selenium.esperarAparecer(".ui-pnotify.notifySuccess .ui-pnotify-text");

		selenium.rolarParaCima();
		selenium.passarOMouse("#navigationPrimary > li:nth-child(3) > a");
		selenium.clicar("#navigationPrimary > li:nth-child(3) > ul > li:nth-child(3) > a");
		selenium.clicar("#ui-id-5");
		selenium.clicar("#reviewFormGridContainer table > tbody > tr:nth-child(1) > td:nth-child(1) > a");
		selenium.clicar("#reviewFormGridContainer table > tbody > tr:nth-child(2) > td:nth-child(1) > a:nth-of-type(3)");

		selenium.assegurarTexto("Ficha de Avaliação", "#ui-id-18 h3");
	}

	@Test
	void cadastrarFichaDeAvaliacaoVerFichaDeAvaliacaoRecadastrada() throws Exception {
		selenium.limpar("#capes-configuracao-ficha-avaliacao-form  input[name=indicador-idioma]");
		selenium.digitar("0", "#capes-configuracao-ficha-avaliacao-form  input[name=indicador-idioma]");
		selenium.limpar("#capes-configuracao-ficha-avaliacao-form  input[name=indicador-editora]");
		selenium.digitar("0", "#capes-configuracao-ficha-avaliacao-form  input[name=indicador-editora]");
		selenium.clicar("#capes-configuracao-ficha-avaliacao-form > fieldset > div.section.formButtons.form_buttons > button");
		selenium.esperarAparecer(".ui-pnotify.notifySuccess .ui-pnotify-text");
		selenium.esperarDesaparecer(".ui-pnotify.notifySuccess .ui-pnotify-text");
		bancoDeDados.renomearFormulariosDeRevisao("Ficha de Avaliação Renomeada");

		selenium.clicar("#capes-configuracao-ficha-avaliacao-form > fieldset > div.section.formButtons.form_buttons > button");
		selenium.esperarAparecer(".ui-pnotify.notifySuccess .ui-pnotify-text");

		selenium.rolarParaCima();
		selenium.passarOMouse("#navigationPrimary > li:nth-child(3) > a");
		selenium.clicar("#navigationPrimary > li:nth-child(3) > ul > li:nth-child(3) > a");
		selenium.clicar("#ui-id-5");

		selenium.assegurarQuantidadeDeElementos(1, "#reviewFormGridContainer table > tbody:not(.empty) > tr.gridRow");
		selenium.assegurarTexto("Ficha de Avaliação", "#reviewFormGridContainer table > tbody:not(.empty) > tr.gridRow:nth-child(1) > td:nth-child(1)");
	}

	@AfterEach
	void finalizar() throws Exception {
		selenium.fechar();
		servidor.finalizar();
		correioEletronico.finalizar();
	}

}
