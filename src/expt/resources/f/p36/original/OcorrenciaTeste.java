import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OcorrenciaTeste {

	private Empresa empresa;
	private Funcionario funcionario;
	private Projeto projetoA;
	
	@BeforeEach
	void init() {
		empresa = new Empresa();
		funcionario = empresa.criarFuncionario("Joao");
		projetoA = empresa.criarProjeto("Projeto A");

	}

	@Test
	void mudarPrioridadeOcorrenciaBaixa() throws Exception {
		Ocorrencia melhoria = projetoA.criarMelhoria("Melhoria a", funcionario, PrioridadeOcorrencia.alta);
		melhoria.setPrioridade(PrioridadeOcorrencia.baixa);
		
		assertEquals(PrioridadeOcorrencia.baixa, melhoria.getPrioridade());
	}

	@Test
	void mudarPrioridadeOcorrenciaMedia() throws Exception {
		Ocorrencia melhoria = projetoA.criarMelhoria("Melhoria a", funcionario, PrioridadeOcorrencia.alta);
		melhoria.setPrioridade(PrioridadeOcorrencia.media);
		
		assertEquals(PrioridadeOcorrencia.media, melhoria.getPrioridade());
	}

	@Test
	void mudarPrioridadeOcorrenciaAlta() throws Exception {
		Ocorrencia melhoria = projetoA.criarMelhoria("Melhoria a", funcionario, PrioridadeOcorrencia.media);
		melhoria.setPrioridade(PrioridadeOcorrencia.alta);
		
		assertEquals(PrioridadeOcorrencia.alta, melhoria.getPrioridade());
	}

	@Test
	void mudarResponsavelOcorrencia() throws Exception {

	 	Ocorrencia melhoria = projetoA.criarMelhoria("Melhoria a", funcionario, PrioridadeOcorrencia.alta);
		Funcionario vitor = empresa.criarFuncionario("Vitor");
		
		projetoA.mudarResponsavelOcorrencia(melhoria, vitor);
		assertEquals(vitor, melhoria.getResponsavel());
		assertEquals(0, funcionario.getOcorrenciasAbertas().size());
		assertEquals(1, vitor.getOcorrenciasAbertas().size());
	}
}

