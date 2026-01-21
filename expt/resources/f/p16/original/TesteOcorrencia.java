package testes;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import aplicação.*;

public class TesteOcorrencia {
	
	private Empresa empresa;
	private Projeto projeto;
	private Funcionario funcionario;
	private Ocorrencia ocorrencia;
	private TipoOcorrencia tipo;
	private PrioridadeOcorrencia prioridade;
	
	@Before
	public void configurar() {
		empresa = new Empresa();
		projeto = empresa.addProjeto("Projeto 1");
		funcionario = empresa.addFuncionario("Responsavel");
		projeto.addFuncionario(funcionario);
		tipo = TipoOcorrencia.BUG;
		prioridade = PrioridadeOcorrencia.MEDIA;
	}
	
	@Test
	public void criarPrimeiraOcorrencia() throws Exception {
		ocorrencia = empresa.criarOcorrencia("Ocorrencia 1", projeto, tipo, prioridade, funcionario);
		assertEquals(1, ocorrencia.id().intValue());
		assertEquals("Ocorrencia 1", ocorrencia.nome());
		assertEquals(funcionario.id(), ocorrencia.responsavel().id());
	}
	
	@Test
	public void criarSegundaOcorrencia() throws Exception {
		ocorrencia = empresa.criarOcorrencia("Ocorrencia 1", projeto, tipo, prioridade, funcionario);
		Ocorrencia ocorrencia2 = empresa.criarOcorrencia("Ocorrencia 2", projeto, tipo, prioridade, funcionario);
		assertEquals(1, ocorrencia.id().intValue());
		assertEquals(2, ocorrencia2.id().intValue());
	}
	
	@Test
	public void criarDecimaPrimeiraOcorrenciaComMesmoResponsavel() throws Exception {
		for (int i = 1; i < 11; ++i) {
			ocorrencia = empresa.criarOcorrencia("Ocorrencia " + String.valueOf(i), projeto, tipo, prioridade, funcionario);
		}
		try {
			ocorrencia = empresa.criarOcorrencia("Ocorrencia 11" , projeto, tipo, prioridade, funcionario);
		}
		catch(Exception e) {
			
		}
		finally {}

		assertEquals(10, ocorrencia.id().intValue());

	}
	@Test
	public void criarEConcluirOcorrencia() throws Exception {
		ocorrencia = empresa.criarOcorrencia("Ocorrencia 1", projeto, tipo, prioridade, funcionario);
		empresa.concluirOcorrencia(ocorrencia);
		assertEquals(0, funcionario.numOcorrenciasAtribuidas().intValue());
		assertEquals(EstadoOcorrencia.FECHADA, ocorrencia.estado());
	}
	
	
}
