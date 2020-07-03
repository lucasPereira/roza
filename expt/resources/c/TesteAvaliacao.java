package br.com.lepidus.omp.capes;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TesteAvaliacao {

	private CorreioEletronico correioEletronico;
	private BancoDeDados bancoDeDados;
	private Servidor servidor;
	private Selenium selenium;

	@BeforeEach
	void configurar() throws Exception {
		correioEletronico = new CorreioEletronico();
		bancoDeDados = new BancoDeDados();
		servidor = new Servidor();
		selenium = new FabricaSelenium().construir("avaliacao-e-ratificacao");
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

		String arquivoProducoes = "src/test/resources/csv/livros-computacao-dois-exemplos.csv";
		selenium.clicar("#capes-abas > ul > li:nth-child(2) > a");
		selenium.selecionarArquivo(arquivoProducoes, "#capes-importacao-livros-selecao-arquivo");
		selenium.clicar("#capes-importacao-livros-form > fieldset > div.section.formButtons.form_buttons > button");
		selenium.esperarAparecer(".ui-pnotify.notifySuccess .ui-pnotify-text");
		selenium.esperarDesaparecer(".ui-pnotify.notifySuccess .ui-pnotify-text");

		String arquivoAvaliadores = "src/test/resources/csv/avaliadores-e-coordenador-tres-exemplos.csv";
		selenium.rolarParaCima();
		selenium.clicar("#capes-abas > ul > li:nth-child(3) > a");
		selenium.selecionarArquivo(arquivoAvaliadores, "#capes-importacao-avaliadores-e-coordenador-selecao-arquivo");
		selenium.clicar("#capes-importacao-avaliadores-e-coordenador-form > fieldset > div.section.formButtons.form_buttons > button");
		selenium.esperarAparecer(".ui-pnotify.notifySuccess .ui-pnotify-text");
		selenium.esperarDesaparecer(".ui-pnotify.notifySuccess .ui-pnotify-text");

		selenium.clicar("#capes-abas > ul > li:nth-child(4) > a");
		selenium.limpar("#capes-configuracao-ficha-avaliacao-form  input[name=indicador-idioma]");
		selenium.digitar("0", "#capes-configuracao-ficha-avaliacao-form  input[name=indicador-idioma]");
		selenium.limpar("#capes-configuracao-ficha-avaliacao-form  input[name=indicador-editora]");
		selenium.digitar("0", "#capes-configuracao-ficha-avaliacao-form  input[name=indicador-editora]");
		selenium.clicar("#capes-configuracao-ficha-avaliacao-form > fieldset > div.section.formButtons.form_buttons > button");
		selenium.esperarAparecer(".ui-pnotify.notifySuccess .ui-pnotify-text");
		selenium.esperarDesaparecer(".ui-pnotify.notifySuccess .ui-pnotify-text");
		selenium.rolarParaCima();

		selenium.clicar("#capes-abas > ul > li:nth-child(5) > a");
		selenium.fixarValor(new Utilitarios().daquiUmMes(), "input[type='text'][name='prazo-maximo']");
		selenium.fixarValor(new Utilitarios().daquiUmMes(), "input[type='hidden'][name='prazo-maximo']");
		selenium.clicar("#capes-atribuicao-avaliadores-form > fieldset > div.section.formButtons.form_buttons > button");
		selenium.esperarAparecer(".ui-pnotify.notifySuccess .ui-pnotify-text");
	}

	@Test
	void avaliarAvaliador() throws Exception {
		selenium.passarOMouse("ul#navigationUser > li:nth-child(3)");
		selenium.clicar("ul#navigationUser > li:nth-child(3) > ul > li:nth-child(2)");
		selenium.limpar("#username");
		selenium.digitar("02119771448", "#username");
		selenium.digitar("omp-capes-2019", "#password");
		selenium.clicar("#login button");

		selenium.clicar("li.pkpListPanelItem--submission > div > a");
		selenium.clicar("#privacyConsent");
		selenium.clicar("#reviewStep1 > div.section.formButtons.form_buttons > button");
		selenium.clicar("#reviewStep2 > div.section.formButtons.form_buttons > button");
		selenium.clicar("#reviewStep3 > div.section.formButtons.form_buttons > button");
		selenium.clicar("a.ok.pkpModalConfirmButton");

		selenium.assegurarTexto("Review Submitted", "#ui-id-8 > h2");
	}

	@Test
	void avaliarCoordenadorDeArea() throws Exception {
		selenium.passarOMouse("ul#navigationUser > li:nth-child(3)");
		selenium.clicar("ul#navigationUser > li:nth-child(3) > ul > li:nth-child(2)");
		selenium.limpar("#username");
		selenium.digitar("02011177898", "#username");
		selenium.digitar("omp-capes-2019", "#password");
		selenium.clicar("#login button");

		selenium.clicar("li.pkpListPanelItem--submission > div > a");
		selenium.clicar("#privacyConsent");
		selenium.clicar("#reviewStep1 > div.section.formButtons.form_buttons > button");
		selenium.clicar("#reviewStep2 > div.section.formButtons.form_buttons > button");
		selenium.clicar("#reviewStep3 > div.section.formButtons.form_buttons > button");
		selenium.clicar("a.ok.pkpModalConfirmButton");

		selenium.assegurarTexto("Review Submitted", "#ui-id-8 > h2");
	}

	@AfterEach
	void finalizar() throws Exception {
		selenium.fechar();
		servidor.finalizar();
		correioEletronico.finalizar();
	}

}
