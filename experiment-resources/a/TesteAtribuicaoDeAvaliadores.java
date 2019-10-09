package br.com.lepidus.omp.capes;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TesteAtribuicaoDeAvaliadores {

	private CorreioEletronico correioEletronico;
	private BancoDeDados bancoDeDados;
	private Servidor servidor;
	private Selenium selenium;

	@BeforeEach
	void configurar() throws Exception {
		correioEletronico = new CorreioEletronico();
		bancoDeDados = new BancoDeDados();
		servidor = new Servidor();
		selenium = new FabricaSelenium().construir("atribuicao-de-avaliadores");
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
	}

	@Test
	void distribuirAvaliadoresPagina() throws Exception {
		String mensagem = "Para atribuir os avaliadores utilize o botão abaixo.";
		String aviso = "Atribuições já existentes serão removidas.";
		selenium.clicar("#capes-abas > ul > li:nth-child(5) > a");
		selenium.assegurarTexto(mensagem, "#capes-atribuicao-avaliadores-form > fieldset > p:nth-of-type(1)");
		selenium.assegurarTexto(aviso, "#capes-atribuicao-avaliadores-form > fieldset > p:nth-of-type(2)");
		selenium.assegurarTexto("Prazo limite para avaliação", "#capes-atribuicao-avaliadores-form > fieldset > div:nth-child(3) > label");
		selenium.assegurarTexto("Distribuir", "#capes-atribuicao-avaliadores-form > fieldset > div.section.formButtons.form_buttons > button");
	}

	@Test
	void distribuirAvaliadoresComSucesso() throws Exception {
		String arquivoAvaliadores = "src/test/resources/csv/avaliadores-e-coordenador-tres-exemplos.csv";
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

		StringBuilder mensagem = new StringBuilder();
		mensagem.append("Distribuição realizada com sucesso\n\n");
		mensagem.append("Avaliadores: 2\n");
		mensagem.append("Produções: 0");
		selenium.assegurarTexto(mensagem.toString(), ".ui-pnotify.notifySuccess .ui-pnotify-text");
	}

	@Test
	void distribuirAvaliadoresSemPreencherPrazoDeAvaliacao() throws Exception {
		selenium.clicar("#capes-abas > ul > li:nth-child(5) > a");
		selenium.fixarValor(new Utilitarios().daquiUmMes(), "input[type='text'][name='prazo-maximo']");
		selenium.clicar("#capes-atribuicao-avaliadores-form > fieldset > div.section.formButtons.form_buttons > button");
		selenium.assegurarTexto("Não foi possível realizar a atribuição, pois o prazo limite para avaliação não foi preenchido", ".ui-pnotify.notifyError .ui-pnotify-text");
	}

	@Test
	void distribuirAvaliadoresSemCoordenadorDeArea() throws Exception {
		String arquivoAvaliadores = "src/test/resources/csv/avaliadores-2-coordenadores-0.csv";
		selenium.clicar("#capes-abas > ul > li:nth-child(3) > a");
		selenium.selecionarArquivo(arquivoAvaliadores, "#capes-importacao-avaliadores-e-coordenador-selecao-arquivo");
		selenium.clicar("#capes-importacao-avaliadores-e-coordenador-form > fieldset > div.section.formButtons.form_buttons > button");
		selenium.esperarAparecer(".ui-pnotify.notifySuccess .ui-pnotify-text");
		selenium.esperarDesaparecer(".ui-pnotify.notifySuccess .ui-pnotify-text");

		selenium.clicar("#capes-abas > ul > li:nth-child(5) > a");
		selenium.fixarValor(new Utilitarios().daquiUmMes(), "input[type='text'][name='prazo-maximo']");
		selenium.fixarValor(new Utilitarios().daquiUmMes(), "input[type='hidden'][name='prazo-maximo']");
		selenium.clicar("#capes-atribuicao-avaliadores-form > fieldset > div.section.formButtons.form_buttons > button");
		selenium.assegurarTexto("Não foi possível realizar a atribuição, pois não existe nenhum coordenador de área", ".ui-pnotify.notifyError .ui-pnotify-text");
	}

	@Test
	void distribuirAvaliadoresComMaisDeUmCoordenadorDeArea() throws Exception {
		String arquivoAvaliadores = "src/test/resources/csv/avaliadores-1-coordenadores-2.csv";
		selenium.clicar("#capes-abas > ul > li:nth-child(3) > a");
		selenium.selecionarArquivo(arquivoAvaliadores, "#capes-importacao-avaliadores-e-coordenador-selecao-arquivo");
		selenium.clicar("#capes-importacao-avaliadores-e-coordenador-form > fieldset > div.section.formButtons.form_buttons > button");
		selenium.esperarAparecer(".ui-pnotify.notifySuccess .ui-pnotify-text");
		selenium.esperarDesaparecer(".ui-pnotify.notifySuccess .ui-pnotify-text");

		selenium.clicar("#capes-abas > ul > li:nth-child(5) > a");
		selenium.fixarValor(new Utilitarios().daquiUmMes(), "input[type='text'][name='prazo-maximo']");
		selenium.fixarValor(new Utilitarios().daquiUmMes(), "input[type='hidden'][name='prazo-maximo']");
		selenium.clicar("#capes-atribuicao-avaliadores-form > fieldset > div.section.formButtons.form_buttons > button");
		selenium.assegurarTexto("Não foi possível realizar a atribuição, pois existe mais de um coordenador de área", ".ui-pnotify.notifyError .ui-pnotify-text");
	}

	@Test
	void distribuirAvaliadoresSemAvaliadores() throws Exception {
		String arquivoAvaliadores = "src/test/resources/csv/avaliadores-0-coordenadores-1.csv";
		selenium.clicar("#capes-abas > ul > li:nth-child(3) > a");
		selenium.selecionarArquivo(arquivoAvaliadores, "#capes-importacao-avaliadores-e-coordenador-selecao-arquivo");
		selenium.clicar("#capes-importacao-avaliadores-e-coordenador-form > fieldset > div.section.formButtons.form_buttons > button");
		selenium.esperarAparecer(".ui-pnotify.notifySuccess .ui-pnotify-text");
		selenium.esperarDesaparecer(".ui-pnotify.notifySuccess .ui-pnotify-text");

		selenium.clicar("#capes-abas > ul > li:nth-child(5) > a");
		selenium.fixarValor(new Utilitarios().daquiUmMes(), "input[type='text'][name='prazo-maximo']");
		selenium.fixarValor(new Utilitarios().daquiUmMes(), "input[type='hidden'][name='prazo-maximo']");
		selenium.clicar("#capes-atribuicao-avaliadores-form > fieldset > div.section.formButtons.form_buttons > button");
		selenium.assegurarTexto("Não foi possível realizar a atribuição, pois não existe nenhum avaliador", ".ui-pnotify.notifyError .ui-pnotify-text");
	}

	@Test
	void distribuirAvaliadoresSemFichaDeAvaliacao() throws Exception {
		String arquivoAvaliadores = "src/test/resources/csv/avaliadores-e-coordenador-tres-exemplos.csv";
		selenium.clicar("#capes-abas > ul > li:nth-child(3) > a");
		selenium.selecionarArquivo(arquivoAvaliadores, "#capes-importacao-avaliadores-e-coordenador-selecao-arquivo");
		selenium.clicar("#capes-importacao-avaliadores-e-coordenador-form > fieldset > div.section.formButtons.form_buttons > button");
		selenium.esperarAparecer(".ui-pnotify.notifySuccess .ui-pnotify-text");
		selenium.esperarDesaparecer(".ui-pnotify.notifySuccess .ui-pnotify-text");

		selenium.clicar("#capes-abas > ul > li:nth-child(5) > a");
		selenium.fixarValor(new Utilitarios().daquiUmMes(), "input[type='text'][name='prazo-maximo']");
		selenium.fixarValor(new Utilitarios().daquiUmMes(), "input[type='hidden'][name='prazo-maximo']");
		selenium.clicar("#capes-atribuicao-avaliadores-form > fieldset > div.section.formButtons.form_buttons > button");
		selenium.assegurarTexto("Não foi possível realizar a atribuição, pois a ficha de avaliação não foi configurada", ".ui-pnotify.notifyError .ui-pnotify-text");
	}

	@Test
	void distribuirAvaliadoresVerPrimeiraProducao() throws Exception {
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

		selenium.rolarParaCima();
		selenium.clicar("#navigationPrimary > li:nth-child(1) > a");
		selenium.clicar("li.pkpListPanelItem--submission:nth-child(1) > div > a");

		selenium.assegurarQuantidadeDeElementos(1, "tr[id^='component-grid-users-stageparticipant-stageparticipantgrid-'].gridRow.has_extras");
		selenium.assegurarTexto("EDGAR NOBUO MAMIYA", "tr[id^='component-grid-users-stageparticipant-stageparticipantgrid-'].gridRow.has_extras:nth-child(1) > td.first_column > span > span.label");
		selenium.assegurarQuantidadeDeElementos(1, "tr[id^='component-grid-users-reviewer-reviewergrid-'].gridRow.has_extras");
		selenium.assegurarTexto("CRISTIANO ALEXANDRE VIRGINIO CAVALCANTE", "tr[id^='component-grid-users-reviewer-reviewergrid-'].gridRow.has_extras:nth-child(1) > td.first_column > span > span.label");
	}

	@Test
	void distribuirAvaliadoresVerSegundaProducao() throws Exception {
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

		selenium.rolarParaCima();
		selenium.clicar("#navigationPrimary > li:nth-child(1) > a");
		selenium.clicar("li.pkpListPanelItem--submission:nth-child(2) > div > a");

		selenium.assegurarQuantidadeDeElementos(1, "tr[id^='component-grid-users-stageparticipant-stageparticipantgrid-'].gridRow.has_extras");
		selenium.assegurarTexto("EDGAR NOBUO MAMIYA", "tr[id^='component-grid-users-stageparticipant-stageparticipantgrid-'].gridRow.has_extras:nth-child(1) > td.first_column > span > span.label");
		selenium.assegurarQuantidadeDeElementos(1, "tr[id^='component-grid-users-reviewer-reviewergrid-'].gridRow.has_extras");
		selenium.assegurarTexto("ANA PAULA CABRAL SEIXAS COSTA", "tr[id^='component-grid-users-reviewer-reviewergrid-'].gridRow.has_extras:nth-child(1) > td.first_column > span > span.label");
	}

	@Test
	void distribuirAvaliadoresVerPrimeiraProducaoAtualizada() throws Exception {
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
		selenium.esperarDesaparecer(".ui-pnotify.notifySuccess .ui-pnotify-text");

		String arquivoAtualizado = "src/test/resources/csv/avaliadores-e-coordenador-tres-exemplos-atualizados.csv";
		selenium.rolarParaCima();
		selenium.clicar("#capes-abas > ul > li:nth-child(3) > a");
		selenium.selecionarArquivo(arquivoAtualizado, "#capes-importacao-avaliadores-e-coordenador-selecao-arquivo");
		selenium.clicar("#capes-importacao-avaliadores-e-coordenador-form > fieldset > div.section.formButtons.form_buttons > button");
		selenium.esperarAparecer(".ui-pnotify.notifySuccess .ui-pnotify-text");
		selenium.esperarDesaparecer(".ui-pnotify.notifySuccess .ui-pnotify-text");

		selenium.clicar("#capes-abas > ul > li:nth-child(5) > a");
		selenium.fixarValor(new Utilitarios().daquiUmMes(), "input[type='text'][name='prazo-maximo']");
		selenium.fixarValor(new Utilitarios().daquiUmMes(), "input[type='hidden'][name='prazo-maximo']");
		selenium.clicar("#capes-atribuicao-avaliadores-form > fieldset > div.section.formButtons.form_buttons > button");
		selenium.esperarAparecer(".ui-pnotify.notifySuccess .ui-pnotify-text");

		selenium.rolarParaCima();
		selenium.clicar("#navigationPrimary > li:nth-child(1) > a");
		selenium.clicar("li.pkpListPanelItem--submission:nth-child(1) > div > a");

		selenium.assegurarQuantidadeDeElementos(1, "tr[id^='component-grid-users-stageparticipant-stageparticipantgrid-'].gridRow.has_extras");
		selenium.assegurarTexto("CRISTIANO CAVALCANTE", "tr[id^='component-grid-users-stageparticipant-stageparticipantgrid-'].gridRow.has_extras:nth-child(1) > td.first_column > span > span.label");
		selenium.assegurarQuantidadeDeElementos(1, "tr[id^='component-grid-users-reviewer-reviewergrid-'].gridRow.has_extras");
		selenium.assegurarTexto("ANA COSTA", "tr[id^='component-grid-users-reviewer-reviewergrid-'].gridRow.has_extras:nth-child(1) > td.first_column > span > span.label");
	}

	@Test
	void distribuirAvaliadoresVerSegundaProducaoAtualizada() throws Exception {
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
		selenium.esperarDesaparecer(".ui-pnotify.notifySuccess .ui-pnotify-text");

		String arquivoAtualizado = "src/test/resources/csv/avaliadores-e-coordenador-tres-exemplos-atualizados.csv";
		selenium.rolarParaCima();
		selenium.clicar("#capes-abas > ul > li:nth-child(3) > a");
		selenium.selecionarArquivo(arquivoAtualizado, "#capes-importacao-avaliadores-e-coordenador-selecao-arquivo");
		selenium.clicar("#capes-importacao-avaliadores-e-coordenador-form > fieldset > div.section.formButtons.form_buttons > button");
		selenium.esperarAparecer(".ui-pnotify.notifySuccess .ui-pnotify-text");
		selenium.esperarDesaparecer(".ui-pnotify.notifySuccess .ui-pnotify-text");

		selenium.clicar("#capes-abas > ul > li:nth-child(5) > a");
		selenium.fixarValor(new Utilitarios().daquiUmMes(), "input[type='text'][name='prazo-maximo']");
		selenium.fixarValor(new Utilitarios().daquiUmMes(), "input[type='hidden'][name='prazo-maximo']");
		selenium.clicar("#capes-atribuicao-avaliadores-form > fieldset > div.section.formButtons.form_buttons > button");
		selenium.esperarAparecer(".ui-pnotify.notifySuccess .ui-pnotify-text");

		selenium.rolarParaCima();
		selenium.clicar("#navigationPrimary > li:nth-child(1) > a");
		selenium.clicar("li.pkpListPanelItem--submission:nth-child(2) > div > a");

		selenium.assegurarQuantidadeDeElementos(1, "tr[id^='component-grid-users-stageparticipant-stageparticipantgrid-'].gridRow.has_extras");
		selenium.assegurarTexto("CRISTIANO CAVALCANTE", "tr[id^='component-grid-users-stageparticipant-stageparticipantgrid-'].gridRow.has_extras:nth-child(1) > td.first_column > span > span.label");
		selenium.assegurarQuantidadeDeElementos(1, "tr[id^='component-grid-users-reviewer-reviewergrid-'].gridRow.has_extras");
		selenium.assegurarTexto("EDGAR MAMIYA", "tr[id^='component-grid-users-reviewer-reviewergrid-'].gridRow.has_extras:nth-child(1) > td.first_column > span > span.label");
	}

	@AfterEach
	void finalizar() throws Exception {
		selenium.fechar();
		servidor.finalizar();
		correioEletronico.finalizar();
	}

}
