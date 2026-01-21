package test.gerenciadorEmpresa;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Before;
import org.junit.Test;

import gerenciadorEmpresa.Empresa;
import gerenciadorEmpresa.Funcionario;
import gerenciadorEmpresa.Ocorrencia;
import gerenciadorEmpresa.Prioridades;
import gerenciadorEmpresa.Projeto;

public class TesteEmpresa {
	
	private Empresa empresa;
	private Funcionario joao;
	private Projeto projeto;
	

	@Before
	public void setUp() throws Exception {
		this.empresa =new Empresa();
		this.joao = new Funcionario();
		this.projeto = new Projeto();
	}


	@Test
	public void criarEmpresa() {
		assertNotEquals(null, new Empresa());
	}
	
	@Test
	public void criarFuncionario() throws Exception {
		Funcionario joao = new Funcionario();
		assertNotEquals(null, joao);
	}
	
	@Test
	public void criarProjeto() throws Exception {
		Projeto projeto = new Projeto();
		assertNotEquals(null, projeto);
	}
	
	@Test
	public void criarOcurrencia() throws Exception {
		Funcionario fonctionario = new Funcionario();
		Ocorrencia ocurencia= new Ocorrencia(Prioridades.ALTA, "resumo",fonctionario);
		assertEquals(Prioridades.ALTA, ocurencia.getPrioridade());
	}
	
	@Test
	public void adicionarFuncionariosEmpresa() throws Exception {
		empresa.addFuncionario(joao);
		assertEquals(joao, empresa.getFonctionario(0));
	}
	
	@Test
	public void adicionar2FonctionariosEmpresa() throws Exception {
		empresa.addFuncionario(joao);
		Funcionario mario =new Funcionario();
		empresa.addFuncionario(mario);
		assertEquals(joao, empresa.getFonctionario(0));
		assertEquals(mario, empresa.getFonctionario(1));
	}
	
	@Test
	public void adicionarProjetoNaEmpresa() throws Exception {
		empresa.addProjeto(projeto);
		assertEquals(projeto, empresa.getProjeto(0));
	}
	
	

	
	
	/*Lista de testes



	 */

}
