import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TesteProjeto {
	
private Empresa newEmpresa;
private Projeto newProjeto;
private Funcionario newFuncionario;
	
	@BeforeEach
	void configuracao() {
		newEmpresa = new Empresa("Empresa X");
		newProjeto = new Projeto(newEmpresa, 1);
		newFuncionario = new Funcionario(newEmpresa, "nome", 1);
	}
	
	@Test
	void adicionarOcorrenciaFuncionario() throws Exception {
		Ocorrencia newOcorrencia = new Ocorrencia(newProjeto, newFuncionario, "alta", "bug", 1);
		newEmpresa.addProjeto(newProjeto);
		newProjeto.addProjetoOcorrencia(newProjeto, newOcorrencia);
		assertEquals(newOcorrencia, newProjeto.getProjetosOcorrencias(newOcorrencia.getID()));
	}
	
	@Test
	void retirarOcorrencia() throws Exception {
		Ocorrencia newOcorrencia = new Ocorrencia(newProjeto, newFuncionario, "bug", "alta", 1);
		newOcorrencia.finalizarOcorrencia(1);
		assertEquals(null, newProjeto.getProjetosOcorrencias(1));
	}
	
	
}
