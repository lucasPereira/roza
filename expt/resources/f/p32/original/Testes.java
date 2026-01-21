package testes;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import excecoes.ExceptionJaTemResponsavel;
import excecoes.ExceptionMesmaChave;
import excecoes.ExceptionOcorrenciaFechada;
import excecoes.ExceptionOcorrenciaNaoEncontrada;
import excecoes.ExceptionResponsabilidadesDemais;
import src.Empresa;
import src.Ocorrencia;
import src.Prioridade;
import src.TipoOcorrencia;

class Testes {

	@Test
	void verificarNomeEmpresa() {
		Empresa empresa = DelegatedSetups.novaEmpresa();

		assertEquals("Rizota", empresa.getNome());
	}

	@Test
	void verificarNomeFuncionario() {
		Empresa empresa = DelegatedSetups.novaEmpresaComFuncionario();

		assertEquals("Pedro", empresa.getFuncionario("Pedro").getNome());
		assertEquals(null, empresa.getFuncionario("Alexandre"));
	}

	@Test
	void verificarNomeProjeto() {
		Empresa empresa = DelegatedSetups.novaEmpresaComFuncionarioEProjeto();

		assertEquals("Projeto TDD", empresa.getProjeto("Projeto TDD").getNome());
		assertEquals(null, empresa.getProjeto("Projeto B"));
	}

	@Test
	void verificarOcorrencia() throws ExceptionMesmaChave {
		Empresa empresa = DelegatedSetups.novaEmpresaComFuncionarioProjetoEOcorrencia();

		assertEquals("Atraso", empresa.getProjeto("Projeto TDD").getOcorrencia(5).getNome());
		assertEquals(null, empresa.getProjeto("Projeto TDD").getOcorrencia(4));
	}

	@Test
	void verificarOResponsavelDeUmProjeto() {
		Empresa empresa = DelegatedSetups.novaEmpresaComFuncionarioEProjeto();
		empresa.getProjeto("Projeto TDD").addResponsavel(empresa.getFuncionario("Pedro"));

		assertEquals("Pedro",
				empresa.getProjeto("Projeto TDD").getResponsavel(empresa.getFuncionario("Pedro")).getNome());
	}

	@Test
	void verificarAConclusaoDeUmaOcorrencia()
			throws ExceptionMesmaChave, ExceptionOcorrenciaFechada, ExceptionOcorrenciaNaoEncontrada {
		Empresa empresa = DelegatedSetups.novaEmpresaComFuncionarioProjetoEOcorrencia();

		empresa.getProjeto("Projeto TDD").fecharOcorrencia(5);

		assertEquals(false, empresa.getProjeto("Projeto TDD").getOcorrencia(5).isAberta());
	}

	@Test
	void verificarOResponsavelDeUmaCertaOcorrencia() throws ExceptionMesmaChave, ExceptionJaTemResponsavel,
			ExceptionResponsabilidadesDemais, ExceptionOcorrenciaFechada {
		Empresa empresa = DelegatedSetups.novaEmpresaComFuncionarioProjetoEOcorrencia();

		empresa.getProjeto("Projeto TDD").getOcorrencia(5).setResponsavel(empresa.getFuncionario("Pedro"));

		assertEquals("Pedro", empresa.getProjeto("Projeto TDD").getOcorrencia(5).getResponsavel().getNome());
	}

	@Test
	void verificarOResumoDeUmaOcorrencia() throws ExceptionMesmaChave, ExceptionOcorrenciaFechada {
		Empresa empresa = DelegatedSetups.novaEmpresaComFuncionarioProjetoEOcorrencia();

		empresa.getProjeto("Projeto TDD").getOcorrencia(5).updateResumo("Resumo diferente do Delegated Setup");

		assertNotEquals("Eu fiz este trabalho muito tarde",
				empresa.getProjeto("Projeto TDD").getOcorrencia(5).getResumo());
		assertEquals("Resumo diferente do Delegated Setup",
				empresa.getProjeto("Projeto TDD").getOcorrencia(5).getResumo());
	}

	@Test
	void verificarRemocaoDeResponsavelDeUmaOcorrenciaQueFoiEncerrada()
			throws ExceptionMesmaChave, ExceptionOcorrenciaFechada, ExceptionOcorrenciaNaoEncontrada {
		Empresa empresa = DelegatedSetups.novaEmpresaComFuncionarioProjetoEOcorrencia();

		empresa.getProjeto("Projeto TDD").fecharOcorrencia(5);

		assertEquals(null, empresa.getProjeto("Projeto TDD").getOcorrencia(5).getResponsavel());
	}

	@Test
	void verificarModificacaoDePrioridadeDeOcorrencia() throws ExceptionMesmaChave, ExceptionOcorrenciaFechada {
		Empresa empresa = DelegatedSetups.novaEmpresaComFuncionarioProjetoEOcorrencia();
		empresa.getProjeto("Projeto TDD").getOcorrencia(5).setPrioridade(Prioridade.ALTA);

		// A primeira linha verifica que a prioridade nao eh a mesma que o
		// DelegatedSetups define
		assertNotEquals(Prioridade.MEDIA, empresa.getProjeto("Projeto TDD").getOcorrencia(5).getPrioridade());
		assertEquals(Prioridade.ALTA, empresa.getProjeto("Projeto TDD").getOcorrencia(5).getPrioridade());
	}

	@Test
	void verificarModificacaoDePrioridadeResultaEmErroCasoOcorrenciaEstejaFechada()
			throws ExceptionMesmaChave, ExceptionOcorrenciaNaoEncontrada {
		Empresa empresa = DelegatedSetups.novaEmpresaComFuncionarioProjetoEOcorrencia();
		empresa.getProjeto("Projeto TDD").getOcorrencia(5).fecharOcorrencia();

		assertThrows(ExceptionOcorrenciaFechada.class, () -> {
			empresa.getProjeto("Projeto TDD").getOcorrencia(5).setPrioridade(Prioridade.BAIXA);
		});
	}

	@Test
	void verificarTipoDaOcorrencia() throws ExceptionMesmaChave, ExceptionOcorrenciaFechada {
		Empresa empresa = DelegatedSetups.novaEmpresaComFuncionarioProjetoEOcorrencia();
		empresa.getProjeto("Projeto TDD").getOcorrencia(5).setTipo(TipoOcorrencia.BUG);

		// A primeira linha verifica que o tipo nao eh o mesmo que o DelegatedSetups
		// define
		assertNotEquals(TipoOcorrencia.TAREFA, empresa.getProjeto("Projeto TDD").getOcorrencia(5).getTipo());
		assertEquals(TipoOcorrencia.BUG, empresa.getProjeto("Projeto TDD").getOcorrencia(5).getTipo());
	}

	@Test
	void verificarMultiplasOcorrenciasEmUmProjeto() throws ExceptionMesmaChave {
		Empresa empresa = DelegatedSetups.novaEmpresaComFuncionarioEProjeto();
		empresa.getProjeto("Projeto TDD").novaOcorrencia(new Ocorrencia("Socorram-me!", 1, Prioridade.ALTA,
				"Subi no ônibus em Marrocos", TipoOcorrencia.TAREFA));
		empresa.getProjeto("Projeto TDD").novaOcorrencia(
				new Ocorrencia("socorraM me", 2, Prioridade.ALTA, "subinô on ibuS !em-marrocoS", TipoOcorrencia.BUG));

		assertEquals("Socorram-me!", empresa.getProjeto("Projeto TDD").getOcorrencia(1).getNome());
		assertEquals("socorraM me", empresa.getProjeto("Projeto TDD").getOcorrencia(2).getNome());
	}

	@Test
	void verificarErroAoCriarOcorrenciasEmUmMesmoProjetoComAMesmaChave() throws ExceptionMesmaChave {
		Empresa empresa = DelegatedSetups.novaEmpresaComFuncionarioEProjeto();
		empresa.getProjeto("Projeto TDD").novaOcorrencia(new Ocorrencia("Socorram-me!", 1, Prioridade.ALTA,
				"Subi no ônibus em Marrocos", TipoOcorrencia.TAREFA));

		assertThrows(ExceptionMesmaChave.class, () -> {
			empresa.getProjeto("Projeto TDD").novaOcorrencia(new Ocorrencia("socorraM me", 1, Prioridade.ALTA,
					"subinô on ibuS !em-marrocoS", TipoOcorrencia.BUG));
		});
	}

	@Test
	void verificarAsOcorrenciasPelasQuaisUmFuncionarioEhResponsavel() throws ExceptionMesmaChave,
			ExceptionJaTemResponsavel, ExceptionResponsabilidadesDemais, ExceptionOcorrenciaFechada {
		Empresa empresa = DelegatedSetups.novaEmpresaComFuncionarioEProjeto();
		empresa.getProjeto("Projeto TDD").addResponsavel(empresa.getFuncionario("Pedro"));
		empresa.getProjeto("Projeto TDD")
				.novaOcorrencia(new Ocorrencia("1", 1, Prioridade.BAIXA, "1", TipoOcorrencia.TAREFA));
		empresa.getProjeto("Projeto TDD").getOcorrencia(1).setResponsavel(empresa.getFuncionario("Pedro"));
		empresa.getProjeto("Projeto TDD")
				.novaOcorrencia(new Ocorrencia("2", 2, Prioridade.MEDIA, "2", TipoOcorrencia.BUG));
		empresa.getProjeto("Projeto TDD").getOcorrencia(2).setResponsavel(empresa.getFuncionario("Pedro"));

		assertEquals(2, empresa.getProjeto("Projeto TDD").getResponsavel(empresa.getFuncionario("Pedro"))
				.getOcorrenciasQueEhResponsavel().size());
	}

	@Test
	void verificarErroAoConfigurarDoisResponsaveisAUmaUnicaOcorrencia() throws ExceptionJaTemResponsavel,
			ExceptionResponsabilidadesDemais, ExceptionOcorrenciaFechada, ExceptionMesmaChave {
		Empresa empresa = DelegatedSetups.novaEmpresaComDoisFuncionariosEUmProjeto();
		empresa.getProjeto("Projeto TDD").addResponsavel(empresa.getFuncionario("Pedro"));
		empresa.getProjeto("Projeto TDD")
				.novaOcorrencia(new Ocorrencia("1", 1, Prioridade.BAIXA, "1", TipoOcorrencia.TAREFA));
		empresa.getProjeto("Projeto TDD").getOcorrencia(1).setResponsavel(empresa.getFuncionario("Pedro"));

		assertThrows(ExceptionJaTemResponsavel.class, () -> {
			empresa.getProjeto("Projeto TDD").getOcorrencia(1).setResponsavel(empresa.getFuncionario("Alexandre"));
		});
	}

	@Test
	void verificarCapacidadeDe10OcorrenciasParaUmUnicoFuncionario() throws ExceptionMesmaChave,
			ExceptionResponsabilidadesDemais, ExceptionJaTemResponsavel, ExceptionOcorrenciaFechada {
		Empresa empresa = DelegatedSetups.novaEmpresaComFuncionarioEProjeto();
		empresa.getProjeto("Projeto TDD").addResponsavel(empresa.getFuncionario("Pedro"));

		empresa.getProjeto("Projeto TDD").novaListaDeOcorrencias(DelegatedSetups.criarListaDeNOcorrencias(10));

		for (Ocorrencia ocorrencia : empresa.getProjeto("Projeto TDD").getOcorrencias()) {
			ocorrencia.setResponsavel(empresa.getFuncionario("Pedro"));
		}

		assertEquals(10, empresa.getFuncionario("Pedro").getOcorrenciasQueEhResponsavel().size());
	}

	@Test
	void verificarLimiteDe10OcorrenciasParaUmUnicoFuncionario() throws ExceptionMesmaChave,
			ExceptionResponsabilidadesDemais, ExceptionJaTemResponsavel, ExceptionOcorrenciaFechada {
		Empresa empresa = DelegatedSetups.novaEmpresaComFuncionarioEProjeto();
		empresa.getProjeto("Projeto TDD").addResponsavel(empresa.getFuncionario("Pedro"));

		empresa.getProjeto("Projeto TDD").novaListaDeOcorrencias(DelegatedSetups.criarListaDeNOcorrencias(10));

		for (Ocorrencia ocorrencia : empresa.getProjeto("Projeto TDD").getOcorrencias()) {
			ocorrencia.setResponsavel(empresa.getFuncionario("Pedro"));
		}

		empresa.getProjeto(empresa.getProjeto("Projeto TDD").getNome())
				.novaOcorrencia(new Ocorrencia("11", 11, Prioridade.BAIXA, "11", TipoOcorrencia.TAREFA));

		assertThrows(ExceptionResponsabilidadesDemais.class, () -> {
			empresa.getProjeto("Projeto TDD").getOcorrencia(11).setResponsavel(empresa.getFuncionario("Pedro"));
		});
		// Ps.: Descobri um erro no metodo que joga essa exception na revisao de codigo
	}

	@Test
	void verificarRemocaoDeFuncionarioDeUmProjeto() {
		Empresa empresa = DelegatedSetups.novaEmpresaComDoisFuncionariosEUmProjeto();
		empresa.getProjeto("Projeto TDD").addResponsavel(empresa.getFuncionario("Pedro"));
		empresa.getProjeto("Projeto TDD").addResponsavel(empresa.getFuncionario("Alexandre"));
		empresa.getProjeto("Projeto TDD").removerResponsavel(empresa.getFuncionario("Pedro"));

		assertEquals(null, empresa.getProjeto("Projeto TDD").getResponsavel(empresa.getFuncionario("Pedro")));
		assertEquals(empresa.getFuncionario("Alexandre"),
				empresa.getProjeto("Projeto TDD").getResponsavel(empresa.getFuncionario("Alexandre")));
	}

	@Test
	void verificarQueUmFuncionarioPodeTrabalharEmVariosProjetosAoMesmoTempo() {
		Empresa empresa = DelegatedSetups.novaEmpresaComFuncionario();

		empresa.novoProjeto("Projeto TDD");
		empresa.novoProjeto("Projeto B");

		empresa.getProjeto("Projeto TDD").addResponsavel(empresa.getFuncionario("Pedro"));
		empresa.getProjeto("Projeto B").addResponsavel(empresa.getFuncionario("Pedro"));

		assertEquals(2, empresa.getFuncionario("Pedro").getProjetosQueEhResponsavel().size());
		// Ps.: o metodo nao eh inutil, apenas precisava ser protected
	}

	@Test
	void verificarRemocaoDeUmaOcorrenciaDasResponsabilidadesDoFuncionario()
			throws ExceptionMesmaChave, ExceptionJaTemResponsavel, ExceptionResponsabilidadesDemais,
			ExceptionOcorrenciaFechada, ExceptionOcorrenciaNaoEncontrada {
		Empresa empresa = DelegatedSetups.novaEmpresaComFuncionarioEProjeto();

		empresa.getProjeto("Projeto TDD")
				.novaOcorrencia(new Ocorrencia("1", 1, Prioridade.BAIXA, "1", TipoOcorrencia.BUG));
		empresa.getProjeto("Projeto TDD")
				.novaOcorrencia(new Ocorrencia("2", 2, Prioridade.BAIXA, "2", TipoOcorrencia.BUG));

		empresa.getProjeto("Projeto TDD").getOcorrencia(1).setResponsavel(empresa.getFuncionario("Pedro"));
		empresa.getProjeto("Projeto TDD").getOcorrencia(2).setResponsavel(empresa.getFuncionario("Pedro"));

		empresa.getProjeto("Projeto TDD").getOcorrencia(1).removerResponsavel();

		assertEquals(1, empresa.getFuncionario("Pedro").getOcorrenciasQueEhResponsavel().size());
		assertEquals(null, empresa.getProjeto("Projeto TDD").getOcorrencia(1).getResponsavel());
		assertEquals(2, empresa.getFuncionario("Pedro").getOcorrenciasQueEhResponsavel().get(0).getChave());
	}
}
