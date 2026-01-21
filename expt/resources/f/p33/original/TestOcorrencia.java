package tests;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import exceptions.EmpresasDiferentes;
import exceptions.LimiteOcorrencias;
import exceptions.NomeVazio;
import tdd_ocorrencias.Empresa;
import tdd_ocorrencias.Estado;
import tdd_ocorrencias.Funcionario;
import tdd_ocorrencias.Ocorrencia;
import tdd_ocorrencias.Prioridade;
import tdd_ocorrencias.Projeto;
import tdd_ocorrencias.Tipo;

public class TestOcorrencia {
	Empresa empresa1;
	Empresa empresa2;
	Projeto projeto1;
	Funcionario funcionario1;
	Funcionario funcionario2;
	Funcionario funcionario3;
	String resumo;
	Ocorrencia ocorrencia;
	CriarOcorrencia criarOcorrencia;
		
	@BeforeEach
	public void setUp() throws NomeVazio, EmpresasDiferentes, LimiteOcorrencias {
		empresa1 = new Empresa("Empresa1");
		empresa2 = new Empresa("Empresa2");
		projeto1 = new Projeto("Projeto1", empresa1);
		funcionario1 = new Funcionario("Funcionario1", empresa1);
		funcionario2 = new Funcionario("Funcionario2", empresa1);
		funcionario3 = new Funcionario("Funcionario3", empresa2);
		resumo = "resumo";
		
		ocorrencia = new Ocorrencia(Tipo.TAREFA, Prioridade.BAIXA, resumo, funcionario1, projeto1);
		
		criarOcorrencia = new CriarOcorrencia(resumo, funcionario1, projeto1);
	}
	
	@Test
	void criarOcorrencia() throws Exception {
		assertEquals(Estado.ABERTA, ocorrencia.getEstado());
		assertEquals(Tipo.TAREFA, ocorrencia.getTipo());
		assertEquals(Prioridade.BAIXA, ocorrencia.getPrioridade());
		assertEquals(resumo, ocorrencia.getResumo());
		assertEquals(funcionario1, ocorrencia.getResponsavel());
		assertEquals(projeto1, ocorrencia.getProjeto());
	}
	
	@Test
	void mudarResponsavelEPrioridadeQuandoEstadoEstaAberto() throws NomeVazio, LimiteOcorrencias {
		ocorrencia.setPrioridade(Prioridade.MEDIA);
		ocorrencia.setFuncionario(funcionario2);
		
		assertEquals(Prioridade.MEDIA, ocorrencia.getPrioridade());
		assertEquals(funcionario2, ocorrencia.getResponsavel());
	}
	
	@Test
	void mudarResponsavelEPrioridadeQuandoEstadoEstaFechado() throws NomeVazio, LimiteOcorrencias {
		ocorrencia.fecharOcorrencia();
		ocorrencia.setPrioridade(Prioridade.MEDIA);
		ocorrencia.setFuncionario(funcionario2);
		
		assertEquals(Prioridade.BAIXA, ocorrencia.getPrioridade());
		assertEquals(funcionario1, ocorrencia.getResponsavel());
	}
	
	@Test
	void funcionarioEProjetoSaoDeEmpresasDiferentes() {
		assertThrows(EmpresasDiferentes.class, () -> new Ocorrencia(Tipo.TAREFA, Prioridade.BAIXA, resumo, funcionario3, projeto1));
	}
	
	@Test
	void inserirOcorrencia() throws Exception {
		assertTrue(funcionario1.getOcorrencias().contains(ocorrencia));
	}
	
	@Test
	void limiteOcorrencia() throws LimiteOcorrencias, EmpresasDiferentes {
		criarOcorrencia.criar10ocorrencias();
		assertThrows(LimiteOcorrencias.class, () -> new Ocorrencia(Tipo.BUG, Prioridade.BAIXA, resumo, funcionario1, projeto1));
	}
	
	@Test
	void limiteOcorrenciaQuandoMudaFuncionario() throws LimiteOcorrencias, EmpresasDiferentes {
		criarOcorrencia.criar10ocorrencias();		
		Ocorrencia o11 = new Ocorrencia(Tipo.TAREFA, Prioridade.BAIXA, resumo, funcionario2, projeto1);
		
		assertThrows(LimiteOcorrencias.class, () -> o11.setFuncionario(funcionario1));
	}
}
