package testes;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import domain.*;

class TestesOcorrencia {
	
	public Projeto projetoWPP;
	public Funcionario funcionarioLuiz;
	public Empresa empresaBrasil;


	@BeforeEach
	void setarOcorrencia() {
		empresaBrasil = new Empresa("Empresa Brasil");
		empresaBrasil.contratarFuncionario("Luiz Fernando");
		funcionarioLuiz = empresaBrasil.getFuncionario(0);
		empresaBrasil.adicionarProjeto("WPP");
		projetoWPP = empresaBrasil.getProjeto(0);
	}


	@Test // 12
	void criarOcorrenciaSemResumo() {
		projetoWPP.criarOcorrencia(funcionarioLuiz, TipoDeOcorrencia.BUG, Prioridade.MEDIA, "");
		
		assertEquals("Falha ao criar a tarefa", projetoWPP.getOcorrencia(0).getResumo());
	}
	
	@Test // 13
	void criarOcorrenciaComResumo() {
		projetoWPP.criarOcorrencia(funcionarioLuiz, TipoDeOcorrencia.BUG, Prioridade.MEDIA, "Arrumar margem do componente");
		
		assertEquals("Arrumar margem do componente", projetoWPP.getOcorrencia(0).getResumo());
	}

}
