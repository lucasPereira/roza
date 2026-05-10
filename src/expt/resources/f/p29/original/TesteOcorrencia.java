
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class TesteOcorrencia {
	
	private Empresa newEmpresa;
	private Projeto newProjeto;
	private Funcionario stefanoFuncionario;
		
	@BeforeEach
	void configuracao() {
		newEmpresa = new Empresa("Empresa X");
		newProjeto = new Projeto(newEmpresa, 1);
		stefanoFuncionario = new Funcionario(newEmpresa, "Stefano Bergamini Poletto", 1);
		newEmpresa.addFuncionario(stefanoFuncionario);
		newEmpresa.addProjeto(newProjeto);
	}
	
	// testes mais simples
	
	@Test
	void testarProjeto() throws Exception {
		Ocorrencia newOcorrencia = new Ocorrencia(newProjeto, stefanoFuncionario, "bug", "alta", 1);
		assertEquals(newProjeto, newOcorrencia.getProjeto());
	}
	
	@Test
	void testarFuncionario() throws Exception {
		Ocorrencia newOcorrencia = new Ocorrencia(newProjeto, stefanoFuncionario, "bug", "alta", 1);
		assertEquals(stefanoFuncionario, newOcorrencia.getFuncionario());
	}
	
	@Test
	void testarPropriedade() throws Exception {
		Ocorrencia newOcorrencia = new Ocorrencia(newProjeto, stefanoFuncionario, "bug", "alta", 1);
		assertEquals("alta", newOcorrencia.getPrioridade());
	}
	
	@Test
	void testarTipo() throws Exception {
		Ocorrencia newOcorrencia = new Ocorrencia(newProjeto, stefanoFuncionario, "bug", "alta", 1);
		assertEquals("bug", newOcorrencia.getTipo());
	}
	
	@Test
	void testarID() throws Exception {
		Ocorrencia newOcorrencia = new Ocorrencia(newProjeto, stefanoFuncionario, "bug", "alta", 1);
		assertEquals(1, newOcorrencia.getID());
	}
	
	// testes com mudança de elementos
	
	@Test
	void mudarPropriedadeOcorrencia() throws Exception {
		Ocorrencia newOcorrencia = new Ocorrencia(newProjeto, stefanoFuncionario, "bug", "alta", 1);
		newOcorrencia.changePrioridade("baixa");
		assertEquals("baixa", newOcorrencia.getPrioridade());
	}
	
	@Test
	void mudarPropriedadeOcorrenciaAposFechamento() throws Exception {
		Ocorrencia newOcorrencia = new Ocorrencia(newProjeto, stefanoFuncionario, "bug", "alta", 1);
		newOcorrencia.finalizarOcorrencia(1);
		newOcorrencia.changePrioridade("baixa");
		assertEquals("alta", newOcorrencia.getPrioridade());
	}
	
	@Test
	void mudarFuncionarioOcorrencia() throws Exception {
		Funcionario joaoFuncionario = new Funcionario(newEmpresa, "Joao santana", 2);
		Ocorrencia newOcorrencia = new Ocorrencia(newProjeto, stefanoFuncionario, "bug", "alta", 1);
		newOcorrencia.changeFuncionario(joaoFuncionario);
		assertEquals(joaoFuncionario, newOcorrencia.getFuncionario());
	}
	
	@Test
	void mudarFuncionarioOcorrenciaAposFechamento() throws Exception {
		Funcionario joaoFuncionario = new Funcionario(newEmpresa, "Joao santana", 2);
		Ocorrencia newOcorrencia = new Ocorrencia(newProjeto, stefanoFuncionario, "bug", "alta", 1);
		newOcorrencia.finalizarOcorrencia(1);
		newOcorrencia.changeFuncionario(joaoFuncionario);
		assertEquals(stefanoFuncionario, newOcorrencia.getFuncionario());
	}
	
	
	
	
	

}
