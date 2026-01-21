package tests;

import tdd_ocorrencias.Ocorrencia;
import tdd_ocorrencias.Prioridade;
import tdd_ocorrencias.Tipo;
import exceptions.EmpresasDiferentes;
import exceptions.LimiteOcorrencias;
import tdd_ocorrencias.Funcionario;
import tdd_ocorrencias.Projeto;

public class CriarOcorrencia {
	String resumo;
	Funcionario funcionario;
	Projeto projeto;
	
	CriarOcorrencia(String resumo, Funcionario funcionario, Projeto projeto){
		this.resumo = resumo;
		this.funcionario = funcionario;
		this.projeto = projeto;
	}
	
	void criar10ocorrencias() throws EmpresasDiferentes, LimiteOcorrencias {	
		for(int i = 0; i < 10; i++) {
			new Ocorrencia(Tipo.TAREFA, Prioridade.MEDIA, resumo, funcionario, projeto);
		}
	}
}
