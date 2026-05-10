package gerenciadordeocorrencias;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class TesteFuncionario {

	private String nomeDoFuncionario;
	private Funcionario funcionario;
	private Ocorrencia[] ocorrencias;

	@Before
	public void criarFuncionario() {
		nomeDoFuncionario = "Joao";
		funcionario = new Funcionario(nomeDoFuncionario);
		ocorrencias = new Ocorrencia[11];
		ocorrencias[0] = new Ocorrencia(1, "Ocorrencia 1", TipoDeOcorrencia.TAREFA, PrioridadeDeOcorrencia.MEDIA,
				funcionario);
		ocorrencias[1] = new Ocorrencia(2, "Ocorrencia 2", TipoDeOcorrencia.TAREFA, PrioridadeDeOcorrencia.MEDIA,
				funcionario);
		ocorrencias[2] = new Ocorrencia(3, "Ocorrencia 3", TipoDeOcorrencia.TAREFA, PrioridadeDeOcorrencia.MEDIA,
				funcionario);
		ocorrencias[3] = new Ocorrencia(4, "Ocorrencia 4", TipoDeOcorrencia.TAREFA, PrioridadeDeOcorrencia.MEDIA,
				funcionario);
		ocorrencias[4] = new Ocorrencia(5, "Ocorrencia 5", TipoDeOcorrencia.TAREFA, PrioridadeDeOcorrencia.MEDIA,
				funcionario);
		ocorrencias[5] = new Ocorrencia(6, "Ocorrencia 6", TipoDeOcorrencia.TAREFA, PrioridadeDeOcorrencia.MEDIA,
				funcionario);
		ocorrencias[6] = new Ocorrencia(7, "Ocorrencia 7", TipoDeOcorrencia.TAREFA, PrioridadeDeOcorrencia.MEDIA,
				funcionario);
		ocorrencias[7] = new Ocorrencia(8, "Ocorrencia 8", TipoDeOcorrencia.TAREFA, PrioridadeDeOcorrencia.MEDIA,
				funcionario);
		ocorrencias[8] = new Ocorrencia(9, "Ocorrencia 9", TipoDeOcorrencia.TAREFA, PrioridadeDeOcorrencia.MEDIA,
				funcionario);
		ocorrencias[9] = new Ocorrencia(10, "Ocorrencia 10", TipoDeOcorrencia.TAREFA, PrioridadeDeOcorrencia.MEDIA,
				funcionario);
		ocorrencias[10] = new Ocorrencia(11, "Ocorrencia 11", TipoDeOcorrencia.TAREFA, PrioridadeDeOcorrencia.MEDIA,
				funcionario);
	}

	@Test
	public void obterNome() {
		String nomeObtido = funcionario.obterNome();

		assertEquals(nomeDoFuncionario, nomeObtido);
	}

	@Test
	public void adicionarOcorrencia() {
		funcionario.adicionarOcorrenciaResponsavel(ocorrencias[0]);

		Ocorrencia ocorrencia = funcionario.obterOcorrenciaResponsavel(1);
		assertEquals(ocorrencias[0], ocorrencia);
	}

	@Test
	public void adicionarTresOcorrenciasEObterASegunda() {
		funcionario.adicionarOcorrenciaResponsavel(ocorrencias[0]);
		funcionario.adicionarOcorrenciaResponsavel(ocorrencias[1]);
		funcionario.adicionarOcorrenciaResponsavel(ocorrencias[2]);

		Ocorrencia ocorrencia = funcionario.obterOcorrenciaResponsavel(2);

		assertEquals(ocorrencias[1], ocorrencia);
	}

	private void adicionarOcorrencias(int numeroDeOcorrencias) {
		for (int i = 0; i < numeroDeOcorrencias; ++i) {
			funcionario.adicionarOcorrenciaResponsavel(ocorrencias[i]);
		}
	}

	@Test
	public void adicionarDezOcorrencias() {
		adicionarOcorrencias(10);

		Ocorrencia ocorrencia1 = funcionario.obterOcorrenciaResponsavel(1);
		assertEquals(ocorrencias[0], ocorrencia1);
		Ocorrencia ocorrencia2 = funcionario.obterOcorrenciaResponsavel(2);
		assertEquals(ocorrencias[1], ocorrencia2);
		Ocorrencia ocorrencia3 = funcionario.obterOcorrenciaResponsavel(3);
		assertEquals(ocorrencias[2], ocorrencia3);
		Ocorrencia ocorrencia4 = funcionario.obterOcorrenciaResponsavel(4);
		assertEquals(ocorrencias[3], ocorrencia4);
		Ocorrencia ocorrencia5 = funcionario.obterOcorrenciaResponsavel(5);
		assertEquals(ocorrencias[4], ocorrencia5);
		Ocorrencia ocorrencia6 = funcionario.obterOcorrenciaResponsavel(6);
		assertEquals(ocorrencias[5], ocorrencia6);
		Ocorrencia ocorrencia7 = funcionario.obterOcorrenciaResponsavel(7);
		assertEquals(ocorrencias[6], ocorrencia7);
		Ocorrencia ocorrencia8 = funcionario.obterOcorrenciaResponsavel(8);
		assertEquals(ocorrencias[7], ocorrencia8);
		Ocorrencia ocorrencia9 = funcionario.obterOcorrenciaResponsavel(9);
		assertEquals(ocorrencias[8], ocorrencia9);
		Ocorrencia ocorrencia10 = funcionario.obterOcorrenciaResponsavel(10);
		assertEquals(ocorrencias[9], ocorrencia10);
	}

	@Test(expected = Exception.class)
	public void adicionarOnzeOcorrencias() {
		adicionarOcorrencias(10);
		funcionario.adicionarOcorrenciaResponsavel(ocorrencias[10]);
	}

}
