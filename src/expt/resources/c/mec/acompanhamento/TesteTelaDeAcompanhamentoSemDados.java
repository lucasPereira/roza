package br.ufsc.saas.teste.selenium.mec.acompanhamento;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;

import br.ufsc.saas.entidade.Instancia;
import br.ufsc.saas.entidade.saas.Instituicao;
import br.ufsc.saas.entidade.saas.Mantenedora;
import br.ufsc.saas.entidade.saas.Usuario;
import br.ufsc.saas.fachada.EmBanco;
import br.ufsc.saas.fachada.EmSaas;
import br.ufsc.saas.teste.selenium.EmTestePagina;
import br.ufsc.saas.teste.selenium.SeleniumSaas;

public class TesteTelaDeAcompanhamentoSemDados {

	private SeleniumSaas selenium;
	private Usuario beatriz;
	private Instituicao ufsc;
	private Mantenedora ufscMantenedora;
	private Usuario juliana;
	private Instancia teste;

	@Before
	public void setUp() throws Exception {
		teste = EmBanco.novaInstancia();
		EmBanco.get(teste).cadastrarAdmin(beatriz);
		EmSaas.get(beatriz).cadastrarMantenedora(ufscMantenedora);
		EmSaas.get(beatriz).cadastrarInstituicao(ufsc);
		EmSaas.get(beatriz).cadastrarOUsuarioMec(juliana);

		selenium = EmTestePagina.selenium();
		EmTestePagina.get(selenium).trocarInstancia(teste);
		selenium.acessar();
		selenium.digitar("loge:usuario", juliana.getLogin());
		selenium.digitar("loge:senha", juliana.getSenha());
		selenium.clicar("loge:logar");
		selenium.selecionarItemDeMenu("menu:acompanhamento", "menu:participacao");
	}

	@Test
	public void aTelaEh() throws Exception {
		selenium.assertTextEquals("Acompanhamento", "tituloPanel_header");
		selenium.assertTextEquals("Foco", "form:focoLabel");
		selenium.assertTextEquals("Período", "form:periodoLabel");
		selenium.assertTextEquals("Mantenedora", "form:mantenedoraLabel");
		selenium.assertTextEquals("Baixar relatório CSV", "form:baixar_relatorio");
		selenium.assertTextEquals("Total de convites criados:", "form:enviados");
		selenium.assertTextEquals("Total de convites respondidos:", "form:respondidos");
		selenium.assertTextEquals("0", "form:totalEnviado");
		selenium.assertTextEquals("0", "form:totalRespondido");
		selenium.assertTextEquals("Região", "form:tabela:regiao_titulo");
		selenium.assertTextEquals("Ofertante", "form:tabela:instituicao_titulo");
		selenium.assertTextEquals("Mantenedora", "form:tabela:mantenedora_titulo");
		selenium.assertTextEquals("Foco", "form:tabela:foco_titulo");
		selenium.assertTextEquals("Criados", "form:tabela:enviados_titulo");
		selenium.assertTextEquals("Respondidos", "form:tabela:respondidos_titulo");
		selenium.assertTextEquals("Participação", "form:tabela:percentual_titulo");
		selenium.assertTextEquals("Não há coletas em andamento", "form:tabela_data");
		selenium.assertAmountOfElements(10, By.cssSelector("[id='form:tabela_head'] > tr > th"));
	}

	@After
	public void tearDown() {
		selenium.fechar();
	}

}
