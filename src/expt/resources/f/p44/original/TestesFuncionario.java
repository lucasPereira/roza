package tests;

import static org.junit.Assert.*;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class TestesFuncionario {
	private Empresa empresa;
	private Funcionario funcionario;
	
	@Before
	public void setup() throws Exception {
		empresa = new Empresa("Google");
		funcionario = new Funcionario("Bryan Lima", 0);
		empresa.addFunc(funcionario);
	}
	
	@Test
	public void funcionarioZeroProjetos() {
		List<Projeto> projetos = empresa.getProjetosPorFuncionario(funcionario.getId());
		assertEquals(0, projetos.size());
	}
	
	@Test
	public void funcionarioUmProjeto() throws Exception {
		Projeto projeto = new Projeto("Pixel 5");
		empresa.addProj(projeto);
		projeto.addFuncionario(funcionario);
		
		List<Projeto> projetos = empresa.getProjetosPorFuncionario(funcionario.getId());
		
		String nomeProjeto = empresa.getProjetosPorFuncionario(funcionario.getId()).get(0).getName();
		
		assertEquals(1, projetos.size());
		assertEquals("Pixel 5", nomeProjeto);
	}
	
	@Test
	public void funcionarioDoisProjetos() throws Exception {
		Projeto projeto = new Projeto("Pixel 5");
		empresa.addProj(projeto);
		projeto.addFuncionario(funcionario);
		Projeto projeto2 = new Projeto("Pixel 6");
		empresa.addProj(projeto2);
		projeto2.addFuncionario(funcionario);
		
		List<Projeto> projetos = empresa.getProjetosPorFuncionario(funcionario.getId());
		String nomeProjeto1 = empresa.getProjetosPorFuncionario(funcionario.getId()).get(0).getName();
		String nomeProjeto2 = empresa.getProjetosPorFuncionario(funcionario.getId()).get(1).getName();
		
		assertEquals(2, projetos.size());
		assertEquals("Pixel 5", nomeProjeto1);
		assertEquals("Pixel 6", nomeProjeto2);
	}
	
	@Test
	public void funcionarioZeroOcorrencias() throws Exception {
		List<Ocorrencia> ocorrencias = funcionario.getOcorrenciasAbertas();
		assertEquals(0, ocorrencias.size());
	}
	
	@Test
	public void funcionarioDezOcorrencia() throws Exception {
		Projeto projeto = new Projeto("Pixel 5");
		projeto.addFuncionario(funcionario);
		empresa.addProj(projeto);
		
		int numeroOcorrencias = 10;
		new TestsHelper().makeListaOcorrencias(numeroOcorrencias, funcionario, projeto);
		
		List<Ocorrencia> ocorrencias = funcionario.getOcorrenciasAbertas();
		assertEquals(10, ocorrencias.size());
	}
	
	@Test (expected = Exception.class)
	public void funcionarioOnzeOcorrencia() throws Exception {
		Projeto projeto = new Projeto("Pixel 5");
		int numeroOcorrencias = 11;
		new TestsHelper().makeListaOcorrencias(numeroOcorrencias, funcionario, projeto);
	}

}
