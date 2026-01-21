package testes;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import exercicioTdd.Empresa;
import exercicioTdd.Funcionario;
import exercicioTdd.Ocorrencia;
import exercicioTdd.Prioridade;
import exercicioTdd.Projeto;
import exercicioTdd.TipoDeOcorrencia;

class TestesOcorrencia {
	
	public Ocorrencia ocorrencia;
	public Projeto projeto;
	public Funcionario funcionario;
	public Empresa empresa;


	@BeforeEach
	void setarOcorrencia() {
		empresa = new Empresa("Empresa grande");
		empresa.contratarFuncionario("José");
		funcionario = empresa.pegarFuncionario(0);
		empresa.adicionarProjeto("BROG");
		projeto = empresa.pegarProjeto(0);
	}


	@Test
	void criarOcorrenciaSemResumo() {
		projeto.criarOcorrencia(funcionario, TipoDeOcorrencia.BUG, Prioridade.MEDIA, "");
		
		assertEquals("Falha ao criar a tarefa", projeto.pegarOcorrencia(0).pegarResumo());
	}
	
	@Test
	void criarOcorrenciaComResumo() {
		projeto.criarOcorrencia(funcionario, TipoDeOcorrencia.BUG, Prioridade.MEDIA, "Arrumar margem do componente");
		
		assertEquals("Arrumar margem do componente", projeto.pegarOcorrencia(0).pegarResumo());
	}

}
