package br.ufsc.servico;

import org.junit.Assert;
import org.junit.Test;

import br.ufsc.modelo.EstadoOcorrencia;
import br.ufsc.modelo.Funcionario;
import br.ufsc.modelo.PrioridadeOcorrencia;
import br.ufsc.modelo.Projeto;
import br.ufsc.modelo.TipoOcorrencia;

public class AulaTddTest {
	
	@Test
	public void testeAlterarResponsavelPelaOcorrencia() {
		Funcionario f1 = new Funcionario("Chris", "0123");
		long criaNovaOcorrencia = new OcorrenciaService().criaNovaOcorrencia(f1, PrioridadeOcorrencia.ALTA, "", TipoOcorrencia.MELHORIA);
		
		Funcionario f2 = new Funcionario("ze", "0234");
		
		new OcorrenciaService().alterarFuncionarioResponsavelOcorrencia(f1, f2, criaNovaOcorrencia);
		
		Assert.assertEquals(f1.getOcorrencias().size(), 0);
		Assert.assertEquals(f2.getOcorrencias().size(), 1);
	}
	
	@Test
	public void adicionaOcorrenciaAoFuncionario() {
		Funcionario f1 = new Funcionario("Chris", "0123");
		new OcorrenciaService().criaNovaOcorrencia(f1, PrioridadeOcorrencia.ALTA, "", TipoOcorrencia.MELHORIA);
		Assert.assertEquals(f1.getOcorrencias().size(), 1);
	}
	
	@Test
	public void alterarEstadoOcorrencia() {
		Funcionario f1 = new Funcionario("Chris", "0123");
		long id = new OcorrenciaService().criaNovaOcorrencia(f1, PrioridadeOcorrencia.ALTA, "", TipoOcorrencia.MELHORIA);
		new OcorrenciaService().alterarEstadoOcorrencia(id, f1, PrioridadeOcorrencia.ALTA);
		Assert.assertEquals(f1.getOcorrencias().get(0).getPrioridade(), PrioridadeOcorrencia.ALTA);
	}
	
	
	@Test
	public void funcionarioTerminaOcorrencia() {
		Funcionario f1 = new Funcionario("Chris", "0123");
		long id = new OcorrenciaService().criaNovaOcorrencia(f1, PrioridadeOcorrencia.ALTA, "", TipoOcorrencia.MELHORIA);
		new FuncionarioService().terminarOcorrencia(id, f1);
		Assert.assertEquals(f1.getOcorrencias().get(0).getEstadoOcorrencia(), EstadoOcorrencia.FECHADA);
	}
	
	@Test
	public void pegarFuncionarioPorCpf() {
		Projeto projeto = new Projeto();
		Funcionario f1 = new Funcionario("Chris", "0123");
		new ProjetoService().addFuncionarioNoProjeto(projeto, f1);
		
		Funcionario funcionarioGetByCPF = new FuncionarioService().getFuncionarioByCpf("0123", projeto);
		
		Assert.assertEquals(f1, funcionarioGetByCPF);
	}
	
}
