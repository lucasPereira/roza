import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TesteFuncionario {
	
private Empresa newEmpresa;
private Projeto newProjeto;
private Funcionario stefanoFuncionario;
	
	@BeforeEach
	void configuracao() {
		newEmpresa = new Empresa("Empresa X");
		newProjeto = new Projeto(newEmpresa, 1);
		stefanoFuncionario = new Funcionario(newEmpresa, "nome", 1);
		newEmpresa.addFuncionario(stefanoFuncionario);
		newEmpresa.addProjeto(newProjeto);
		stefanoFuncionario.addProjetoFuncionario(newProjeto);
	}
	
	@Test
	void adicionarProjetoFuncionario() throws Exception {
		assertEquals(newProjeto, stefanoFuncionario.getProjetoFuncionario(newProjeto));
	}
	
	@Test
	void adicionarOcorrenciaFuncionario() throws Exception {
		Ocorrencia newOcorrencia = new Ocorrencia(newProjeto, stefanoFuncionario, "bug", "alta", 1);
		newProjeto.addProjetoOcorrencia(newProjeto, newOcorrencia);
		stefanoFuncionario.addOcorrencia(newProjeto, newOcorrencia);
		assertEquals(newOcorrencia, stefanoFuncionario.getOcorrencia(newProjeto, newOcorrencia.getID()));
	}
	
	// da errado pois o funcionario não recebe essa ocorrencia
	@Test
	void errarOcorrenciaFuncionario() throws Exception {
		Ocorrencia newOcorrencia = new Ocorrencia(newProjeto, stefanoFuncionario, "bug", "alta", 1);
		newProjeto.addProjetoOcorrencia(newProjeto, newOcorrencia);
		assertNotEquals(newOcorrencia, stefanoFuncionario.getOcorrencia(newProjeto, newOcorrencia.getID()));
	}
	
	
	@Test
	void criarMaisDeDezOcorrencias() throws Exception {
		Utilitarios utilitarios = new Utilitarios();
		utilitarios.criarDezOcorrencias(newProjeto, stefanoFuncionario);
		assertEquals(utilitarios.getOcorrencia(1), stefanoFuncionario.getOcorrencia(newProjeto, 1));
		assertEquals(null, stefanoFuncionario.getOcorrencia(newProjeto, 10));
	}
	
	

}
