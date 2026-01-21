package teste;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import projeto.Funcionario;
import projeto.Ocorrencia;
import projeto.Prioridade;
import projeto.Status;
import projeto.TipoOcorrencia;

public class TestaOFuncionario {
	private Funcionario umFuncionario;

	@Before
	public void setup() {
		umFuncionario = new Funcionario("Xisto");
	}
	
	@Test
	public void retornaInfoFuncionario() throws Exception {
		assertEquals("Xisto", umFuncionario.getNome());
	}
	
	@Test
	public void addOcorrenciaParaFuncionarioResolver() throws Exception {
		Ocorrencia umaOcorrencia = new Ocorrencia("Corrigir Bug",TipoOcorrencia.Bug, Status.Aberta , new Funcionario("Fabio"), Prioridade.Alta);
		umFuncionario.addOcorrencia(umaOcorrencia);
		
		assertEquals(umaOcorrencia, umFuncionario.getOcorrencias(0));
	}
	
	@Test(expected = Exception.class)
	public void excedeuLimiteDeOcorrenciasPermitidas() throws Exception {
		Ocorrencia umaOcorrencia = new Ocorrencia("Corrigir Bug",TipoOcorrencia.Bug, Status.Aberta , new Funcionario("Fabio"), Prioridade.Alta);
		for (int i = 0; i < 11; i++) {
			umFuncionario.addOcorrencia(umaOcorrencia);
		}	
	}
	
	
	@Test
	public void mudarStatusOcorrencia() throws Exception {
		Ocorrencia umaOcorrencia = new Ocorrencia("Corrigir Bug", TipoOcorrencia.Bug, Status.Aberta , new Funcionario("Fabio"), Prioridade.Alta);
		umFuncionario.addOcorrencia(umaOcorrencia);
		umFuncionario.getOcorrencias(0).setStatus(Status.Fechada);
		
		assertEquals(Status.Fechada, umFuncionario.getOcorrencias(0).getStatus());
	}
}
