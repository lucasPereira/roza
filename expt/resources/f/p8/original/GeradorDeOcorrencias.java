package test;

import java.util.ArrayList;
import java.util.List;

import src.Funcionario;
import src.Ocorrencia;
import src.Prioridade;
import src.TipoOcorrencia;

public class GeradorDeOcorrencias {
	private Ocorrencia exemploOcorrencia(Funcionario funcionario, String resumo) {
		return new Ocorrencia(funcionario, TipoOcorrencia.Bug, Prioridade.Alta, resumo);
	}

	public List<Ocorrencia> gerarOcorrencias(Funcionario funcionario, int quantidade) {
		List<Ocorrencia> ocorrencias = new ArrayList<>();

		for (int i = 0; i < quantidade; i++) {
			ocorrencias.add(exemploOcorrencia(funcionario, "Ocorrência #" + i));
		}

		return ocorrencias;
	}
}
