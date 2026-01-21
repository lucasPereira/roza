package tdd.test;

import tdd.PrioridadeOcorrencia;
import tdd.TipoOcorrencia;
import tdd.model.Funcionario;
import tdd.model.Projeto;

public class TestProjetoHelper {

	public static void createOcorrencias(Projeto projeto, Funcionario funcionario,
			String resumo, PrioridadeOcorrencia prioridade, TipoOcorrencia tipo, int amount) {
		for(int i = 0; i < amount; i++) {
			projeto.createOcorrencia(funcionario, resumo,
					prioridade, tipo);			
		}
	}
}
