import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class OcorrenciaTest {

	private Funcionario pedro;
	
	@Before
	public void setUp() throws Exception {
		pedro = new Funcionario("Pedro");
	}

	@Test
	public void testCriacaoDeOcorrenciasGeraChavesUnicas() {
		Ocorrencia o1 = new Ocorrencia("o1", Ocorrencia.Tipo.TAREFA, pedro, Ocorrencia.Prioridade.MEDIA);
		Ocorrencia o2 = new Ocorrencia("o2", Ocorrencia.Tipo.TAREFA, pedro, Ocorrencia.Prioridade.MEDIA);
		Ocorrencia o3 = new Ocorrencia("o3", Ocorrencia.Tipo.TAREFA, pedro, Ocorrencia.Prioridade.MEDIA);
		
		assertNotEquals(o1.chave(), o2.chave());
		assertNotEquals(o1.chave(), o3.chave());
		assertNotEquals(o2.chave(), o3.chave());
	}
	
	@Test
	public void testAtribuirResponsavelPedroParaUmaOcorrencia() {
		Ocorrencia o = new Ocorrencia("Tarefa", Ocorrencia.Tipo.TAREFA, pedro, Ocorrencia.Prioridade.MEDIA);
		
		assertEquals(pedro, o.responsavel());
	}

	@Test
	public void testCompletarOcorrencia() {
		Ocorrencia o = new Ocorrencia("Tarefa", Ocorrencia.Tipo.TAREFA, pedro, Ocorrencia.Prioridade.MEDIA);
		
		o.completar();
		
		assertFalse(o.aberta());
	}
	
	@Test
	public void testMudarPrioridadeDeUmaOcorrenciaAbertaDeMediaParaBaixa() throws Exception {
		Ocorrencia o = new Ocorrencia("Tarefa", Ocorrencia.Tipo.TAREFA, pedro, Ocorrencia.Prioridade.MEDIA);
		
		o.prioridade(Ocorrencia.Prioridade.BAIXA);
		
		assertEquals(Ocorrencia.Prioridade.BAIXA, o.prioridade());
	}
	
	@Test(expected = Exception.class)
	public void testMudarPrioridadeDeUmaOcorrenciaCompletadaDeMediaParaBaixa() throws Exception {
		Ocorrencia o = new Ocorrencia("Tarefa", Ocorrencia.Tipo.TAREFA, pedro, Ocorrencia.Prioridade.MEDIA);
		o.completar();
		// Esperamos uma Exception
		o.prioridade(Ocorrencia.Prioridade.BAIXA);
	}
}
