import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.HashMap;

import org.junit.Before;
import org.junit.Test;

public class TesteOcorrencias {
	
	private Empresa empresa;
	private ArrayList<Projeto> projetos;
	private ArrayList<Funcionario> funcionarios;
	private HashMap<Integer, Ocorrencia> ocorrencias;
	
	@Before
	public void setup() {
		empresa = new Empresa();
		projetos = empresa.getProjetos();
		funcionarios = empresa.getFuncionarios();
	}
	
	@Test
	public void empresa() throws Exception {
		assertNotNull(empresa);
	}
	
	@Test
	public void funcionario() throws Exception {
		empresa.getFuncionarios().add(new Funcionario());
		Funcionario funcionario = empresa.getFuncionarios().get(0);
		assertNotNull(funcionario);
	}
	
	@Test
	public void projeto() throws Exception {
		empresa.getProjetos().add(new Projeto());
		Projeto projeto = empresa.getProjetos().get(0);
		assertNotNull(projeto);
	}
	
	@Test
	public void ocorrencia() throws Exception {
		empresa.getFuncionarios().add(new Funcionario());
		Funcionario funcionario = empresa.getFuncionarios().get(0);
		
		Ocorrencia ocorrencia = new Ocorrencia("ocorrencia fofinha", Prioridade.BAIXA, funcionario);
		
		assertNotNull(ocorrencia);
	}
	
	@Test
	public void responsavelPorOcorrencia() throws Exception {
		empresa.getFuncionarios().add(new Funcionario());
		Funcionario funcionario = empresa.getFuncionarios().get(0);
		
		Ocorrencia ocorrencia = new Ocorrencia("ocorrencia fofinha", Prioridade.BAIXA, funcionario);
		
		assertEquals(funcionario, ocorrencia.getResposavel());
	}
	
	@Test
	public void conclusaoDeOcorrencia() throws Exception {
		empresa.getFuncionarios().add(new Funcionario());
		Funcionario funcionario = empresa.getFuncionarios().get(0);
		
		Ocorrencia ocorrencia = new Ocorrencia("ocorrencia fofinha", Prioridade.BAIXA, funcionario);
		
		ocorrencia.chave = funcionario.getOcorrencias().indexOf(ocorrencia);
		
		funcionario.terminarOcorrencia(ocorrencia.chave);
		
		assertEquals(Estado.COMPLETADA, ocorrencia.getEstado());
	}
}
