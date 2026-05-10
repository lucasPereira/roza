package teste;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import projeto.Funcionario;
import projeto.Ocorrencia;
import projeto.Prioridade;
import projeto.Status;
import projeto.TipoOcorrencia;

public class TestaAOcorrencia {
	private Ocorrencia umaOcorrencia;
	
	@Before
	public void setup() {
		umaOcorrencia = new Ocorrencia("Trocar a caixeta azul", TipoOcorrencia.Melhoria, Status.Aberta , new Funcionario("Fabio"), Prioridade.Alta);
	}
	
	@Test
	public void retornarChave() throws Exception {
		assertNotNull(umaOcorrencia.getChave());
	}	
	
	@Test
	public void unicidade() throws Exception {
		Ocorrencia ocorrenciaChaveDiferente = new Ocorrencia("Trocar a caixeta azul", TipoOcorrencia.Melhoria, Status.Aberta , new Funcionario("Fabio"), Prioridade.Alta);
		assertNotSame(umaOcorrencia.getChave(), ocorrenciaChaveDiferente.getChave());
		assertEquals(new Integer(umaOcorrencia.getChave()+1), ocorrenciaChaveDiferente.getChave());
	}
	
	@Test
	public void retornaNomeOcorrencia() throws Exception {
		assertEquals("Trocar a caixeta azul", umaOcorrencia.getDescricaoOcorrencia());
	}
	
	@Test
	public void tipoDeTarefaDaOcorrencia() {
		assertEquals(TipoOcorrencia.Melhoria, umaOcorrencia.getTarefa());
	}
	
	@Test
	public void statusAtualDaOcorrencia() throws Exception {
		assertEquals(Status.Aberta, umaOcorrencia.getStatus());
	}
	
	@Test
	public void funcionarioResponsavelPelaOcorrencia() throws Exception {
		assertEquals(new Funcionario("Fabio").getNome(), umaOcorrencia.getResponsavel().getNome());
	}
	
	@Test
	public void modificarPrioridadeOcorrencia() throws Exception {
		umaOcorrencia.setPrioridade(Prioridade.Baixa);
		
		assertEquals(Prioridade.Baixa, umaOcorrencia.getPrioridade());
	}
	
	@Test
	public void modificarStatusOcorrencia() throws Exception {
		umaOcorrencia.setStatus(Status.Fechada);
		
		assertEquals(Status.Fechada, umaOcorrencia.getStatus());
	}
	
	@Test
	public void modificaResponsavelDaOcorrencia() throws Exception {
		umaOcorrencia.setResponsavel(new Funcionario("Carbine"));
		
		assertEquals(new Funcionario("Carbine").getNome(), umaOcorrencia.getResponsavel().getNome());
	}
	
	@Test(expected = Exception.class)
	public void modificarResponsavelAoFecharOcorrencia() throws Exception{
		umaOcorrencia.setStatus(Status.Fechada);
		umaOcorrencia.setResponsavel(new Funcionario("ABC D Lima"));
	}
	
	@Test(expected = Exception.class)
	public void modificarPrioridadeAoFecharOcorrencia() throws Exception {
		umaOcorrencia.setStatus(Status.Fechada);
		umaOcorrencia.setPrioridade(Prioridade.Media);
	}
}
