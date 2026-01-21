import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FuncionarioTeste {
	private Empresa empresa;
	private Projeto projetoA;
	private Projeto projetoB;
	private Funcionario joao;
	
	@BeforeEach
	void init() {
		empresa = new Empresa();
		projetoA = empresa.criarProjeto("Projeto A");
		projetoB = empresa.criarProjeto("Projeto B");
		joao = empresa.criarFuncionario("Joao");
	}
	
	@Test
	void funcionarioAtribuirOcorrenciaAberta() throws Exception {
		projetoA.criarMelhoria("M1", joao, PrioridadeOcorrencia.alta);		
		assertEquals(1, joao.getOcorrenciasAbertas().size());
	}
	
	@Test
	void funcionarioAtribuirDezOcorrenciasAbertas() throws Exception {
		projetoA.criarMelhoria("M1", joao, PrioridadeOcorrencia.alta);
		projetoA.criarMelhoria("M2", joao, PrioridadeOcorrencia.baixa);
		projetoB.criarMelhoria("M3", joao, PrioridadeOcorrencia.media);
		projetoA.criarMelhoria("M4", joao, PrioridadeOcorrencia.media);
		projetoB.criarMelhoria("M5", joao, PrioridadeOcorrencia.alta);
		projetoA.criarMelhoria("M6", joao, PrioridadeOcorrencia.alta);
		projetoB.criarMelhoria("M7", joao, PrioridadeOcorrencia.baixa);
		projetoA.criarMelhoria("M8", joao, PrioridadeOcorrencia.media);
		projetoB.criarMelhoria("M9", joao, PrioridadeOcorrencia.media);
		projetoB.criarMelhoria("M10", joao, PrioridadeOcorrencia.media);
	
		List<Ocorrencia> ocorrencias =  joao.getOcorrenciasAbertas();
		assertEquals(10, ocorrencias.size());		
	}
	

	@Test
	void funcionarioAtribuirOnzeOcorrenciasAbertas() throws Exception {
		projetoA.criarMelhoria("M1", joao, PrioridadeOcorrencia.alta);
		projetoA.criarMelhoria("M2", joao, PrioridadeOcorrencia.baixa);
		projetoB.criarMelhoria("M3", joao, PrioridadeOcorrencia.media);
		projetoA.criarMelhoria("M4", joao, PrioridadeOcorrencia.media);
		projetoB.criarMelhoria("M5", joao, PrioridadeOcorrencia.alta);
		projetoA.criarMelhoria("M6", joao, PrioridadeOcorrencia.alta);
		projetoB.criarMelhoria("M7", joao, PrioridadeOcorrencia.baixa);
		projetoA.criarMelhoria("M8", joao, PrioridadeOcorrencia.media);
		projetoB.criarMelhoria("M9", joao, PrioridadeOcorrencia.media);
		projetoB.criarMelhoria("M10", joao, PrioridadeOcorrencia.media);
		
		assertThrows(Exception.class, () -> projetoA.criarMelhoria("M11", joao, PrioridadeOcorrencia.alta));	
	}
	
	@Test
	void fecharOcorrencia() throws Exception {
		Funcionario joao = empresa.criarFuncionario("Joao");
		Ocorrencia melhoria = projetoA.criarMelhoria("Atualizar Botao na tela", joao, PrioridadeOcorrencia.alta);		
		joao.fecharOcorrencia(melhoria);
		
		assertEquals(EstadoOcorrencia.fechado, melhoria.getEstado());
		assertEquals(0, joao.getOcorrenciasAbertas().size());
	}
}
