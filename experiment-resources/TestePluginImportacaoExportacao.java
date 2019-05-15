package br.com.lepidus.omp.capes;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestePluginImportacaoExportacao {

	private CorreioEletronico correioEletronico;
	private BancoDeDados bancoDeDados;
	private Servidor servidor;
	private Selenium selenium;

	@BeforeEach
	void configurar() throws Exception {
		correioEletronico = new CorreioEletronico();
		bancoDeDados = new BancoDeDados();
		servidor = new Servidor();
		selenium = new FabricaSelenium().construir("plugin-capes");
		correioEletronico.iniciar();
		bancoDeDados.criarBaseParaTestes();
		bancoDeDados.limpar();
		servidor.iniciar();

		selenium.acessar("http://localhost:8000");
		selenium.clicar("#navigationUser > li:nth-child(2) > a");
		selenium.digitar("administrador", "#username");
		selenium.digitar("senha", "#password");
		selenium.clicar("#login button");
		selenium.passarOMouse("#navigationUser > li > a");
		selenium.clicar("#navigationUser > li > ul > li:nth-child(1) > a");
		selenium.passarOMouse("#navigationPrimary > li:nth-child(5) > a");
		selenium.clicar("#navigationPrimary > li:nth-child(5) > ul > li:nth-child(1) > a");
	}

	@Test
	void pluginsPaginaGeral() throws Exception {
		selenium.assegurarTexto("Tools", ".pkp_page_title");
		selenium.assegurarTexto("Produções de livros CAPES: Importação e exportação de produções de livros", ".pkp_page_content > ul:nth-child(2) > li:nth-child(1)");
	}

	@Test
	void pluginsPaginaDoPlugin() throws Exception {
		selenium.clicar("#ui-id-2 > div > ul > li:nth-child(1) > a");
		selenium.assegurarTexto("Produções de livros CAPES", ".pkp_page_title");
		selenium.assegurarQuantidadeDeElementos(5, "#capes-abas > ul > li");
		selenium.assegurarTexto("Início", "#capes-abas > ul > li:nth-child(1)");
		selenium.assegurarTexto("Importação de produções", "#capes-abas > ul > li:nth-child(2)");
		selenium.assegurarTexto("Importação de avaliadores e coordenador de área", "#capes-abas > ul > li:nth-child(3)");
		selenium.assegurarTexto("Configuração de ficha de avaliação", "#capes-abas > ul > li:nth-child(4)");
		selenium.assegurarTexto("Atribuição de avaliadores", "#capes-abas > ul > li:nth-child(5)");
	}

	@AfterEach
	void finalizar() throws Exception {
		selenium.fechar();
		servidor.finalizar();
		correioEletronico.finalizar();
	}

}
