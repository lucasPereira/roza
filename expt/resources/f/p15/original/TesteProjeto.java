package tests.br.ufsc.leb.adan.gerenciador.ocorrencias;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import br.ufsc.leb.adan.gerenciador.ocorrencias.Ocorrencia;
import br.ufsc.leb.adan.gerenciador.ocorrencias.Projeto;

public class TesteProjeto {

	@Test
	void criaProjeto() throws Exception {
		Projeto projeto = new Projeto();
		List<Ocorrencia> ocorrencias = projeto.obterOcorrencias();
		assertEquals(0, ocorrencias.size());
	}
}
