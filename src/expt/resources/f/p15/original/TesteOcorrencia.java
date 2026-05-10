package tests.br.ufsc.leb.adan.gerenciador.ocorrencias;

import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;

import org.junit.jupiter.api.Test;

import br.ufsc.leb.adan.gerenciador.ocorrencias.Funcionario;
import br.ufsc.leb.adan.gerenciador.ocorrencias.Ocorrencia;

public class TesteOcorrencia {

	@Test
	void criaOcorrencia() throws Exception {
		UUID chave = UUID.randomUUID();
		String resumo = "Breve descrição da ocorrência.";
		Funcionario responsavel = new Funcionario("Lucas", "00011122233");
		Ocorrencia ocorrencia = new Ocorrencia(chave, resumo, responsavel);
		assertEquals(chave, ocorrencia.obterChave());
		assertEquals(resumo, ocorrencia.obterResumo());
		assertEquals(responsavel, ocorrencia.obterResponsavel());
	}
}
