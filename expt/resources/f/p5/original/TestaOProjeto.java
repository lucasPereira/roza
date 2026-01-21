package teste;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import projeto.Funcionario;
import projeto.Ocorrencia;
import projeto.Prioridade;
import projeto.Projeto;
import projeto.Status;
import projeto.TipoOcorrencia;

public class TestaOProjeto {
	private Projeto umProjeto;

	@Before
	public void setup() {
		umProjeto = new Projeto("Projeto X");
	}
	
	@Test
	public void retornaNomeProjeto() throws Exception {
		assertEquals("Projeto X", umProjeto.getNomeProjeto());
	}
	
	@Test
	public void addUmaOcorrencia() throws Exception {
		Ocorrencia umaOcorrencia = new Ocorrencia("Corrigir Bug", TipoOcorrencia.Bug, Status.Aberta , new Funcionario("Fabio"), Prioridade.Alta);
		umProjeto.addOcorrencia(umaOcorrencia);
		
		assertEquals(umaOcorrencia, umProjeto.getOcorrencia(0));
	}
	
	@Test
	public void addFuncionarioAoProjeto() {
		Funcionario umFuncionario = new Funcionario("Xisto");
		umProjeto.addFuncionarioAoProjeto(umFuncionario);
		
		assertEquals(umFuncionario, umProjeto.getFuncionario(0));
	}
	
	@Test
	public void addOcorrenciaParaFuncionarioResolver() throws Exception {
		Integer funcionarioEscolhidoParaResolver = 0;
		Integer ocorrenciaAberta = 0;
		Funcionario umFuncionario = new Funcionario("Xisto");
		
		umProjeto.addFuncionarioAoProjeto(umFuncionario);
		Ocorrencia umaOcorrencia = new Ocorrencia("Corrigir Bug", TipoOcorrencia.Bug, Status.Aberta , umFuncionario, Prioridade.Alta);
		umProjeto.addOcorrencia(umaOcorrencia);
		umProjeto.addOcorrenciaAFuncionario(funcionarioEscolhidoParaResolver, ocorrenciaAberta);
		
		assertEquals(umaOcorrencia, umProjeto.getFuncionario(0).getOcorrencias(0));
	}

	
}
