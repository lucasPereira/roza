import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class FuncionarioTest {

	private Funcionario pedro;

	@Before
	public void setUp() throws Exception {
		pedro = new Funcionario("Pedro");
	}

	@Test
	public void testAdicionarOcorrenciaAoFuncionario() throws Exception {
		Ocorrencia o = new Ocorrencia("", Ocorrencia.Tipo.TAREFA, pedro, Ocorrencia.Prioridade.ALTA);
		
		pedro.adicionarOcorrencia(o);
		
		assertTrue(pedro.ocorrencias().contains(o));
	}
	
	@Test
	public void testRemoverOcorrenciaDoFuncionario() throws Exception {
		Ocorrencia o = new Ocorrencia("", Ocorrencia.Tipo.TAREFA, pedro, Ocorrencia.Prioridade.ALTA);
		pedro.adicionarOcorrencia(o);
		
		pedro.removerOcorrencia(o);
		
		assertFalse(pedro.ocorrencias().contains(o));
	}
	
	@Test(expected = Exception.class)
	public void testAdicionarOnzeOcorrenciasAoFuncionario() throws Exception {
		for (int i = 0; i < 10; i++) {
			pedro.adicionarOcorrencia(new Ocorrencia("", Ocorrencia.Tipo.TAREFA, pedro, Ocorrencia.Prioridade.ALTA));
		}
		// Adicionar a ocorrência 11, esperamos uma Exception
		pedro.adicionarOcorrencia(new Ocorrencia("", Ocorrencia.Tipo.TAREFA, pedro, Ocorrencia.Prioridade.ALTA));
	}

}
