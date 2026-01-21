import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;


public class TestEmpresa {

	//--- Private variables
	private Empresa empresa_CIA;
	
	private Funcionario funcionario_guilherme;
	
	private Projeto digiclad;
	
	@Before
	public void configurarEmpresa() {
		
		String nome_empresa = "CIA";
		
		// Cria uma nova empresa com o construtor para executar os testes
		empresa_CIA = new Empresa(nome_empresa);
		
		// Cria funcionario_guilherme para testes
		Integer codigo = 1;
		String nome = "Guilherme";
		funcionario_guilherme = new Funcionario(codigo, nome);
		
		// Cria um novo projeto com o construtor para executar os testes
		Integer codigo_projeto = 1;
		String nome_projeto = "DIGICLAD";
		digiclad = new Projeto(codigo_projeto, nome_projeto, funcionario_guilherme);
		
	}
	
	@Test
	public void testCriaEmpresa() {
		
		Empresa empresa_NOVO = new Empresa("NOVO");
		String nome_empresa = empresa_NOVO.getNomeEmpresa();
		
		assertEquals("NOVO", nome_empresa);

	}
	
	@Test
	public void testAddFuncionario() {
		
		Funcionario teste_funcionario = empresa_CIA.AddFuncionario(funcionario_guilherme);
		
		assertEquals(funcionario_guilherme, teste_funcionario);

	}
	
	@Test
	public void testGetFuncionario() {
		
		empresa_CIA.AddFuncionario(funcionario_guilherme);
		Integer codigo = 1;
		Funcionario teste_funcionario = empresa_CIA.getFuncionarioProcurado(codigo);
		
		assertEquals(funcionario_guilherme, teste_funcionario);

	}
	
	@Test
	public void testGetFuncionarioInvalido() {
		
		empresa_CIA.AddFuncionario(funcionario_guilherme);
		Integer codigo = 2;
		Funcionario teste_funcionario = empresa_CIA.getFuncionarioProcurado(codigo);
		
		assertEquals(null, teste_funcionario);

	}
	
	@Test
	public void testGetNumeroFuncionarios() {
		
		Integer codigo;
		Funcionario funcionario_2 = new Funcionario(codigo = 2, "Lucas");
		Funcionario funcionario_3 = new Funcionario(codigo = 3, "Carlos");
		Funcionario funcionario_4 = new Funcionario(codigo = 4, "Pedro");
		
		empresa_CIA.AddFuncionario(funcionario_2);
		empresa_CIA.AddFuncionario(funcionario_3);
		empresa_CIA.AddFuncionario(funcionario_4);
		Integer qtde = 3;
		
		Integer numero_funcionarios = empresa_CIA.getNumeroFuncionarios();
		
		assertEquals(qtde, numero_funcionarios);

	}
	
	@Test
	public void testAddProjeto() {
		
		Projeto teste_projeto = empresa_CIA.AddProjeto(digiclad);
		
		assertEquals(digiclad, teste_projeto);

	}
	
	@Test
	public void testGetProjeto() {
		
		empresa_CIA.AddProjeto(digiclad);
		Integer codigo_projeto = 1;
		
		Projeto teste_projeto = empresa_CIA.getProjetoProcurado(codigo_projeto);
		
		assertEquals(digiclad, teste_projeto);

	}
	
	@Test
	public void testGetProjetoInvalido() {
		
		empresa_CIA.AddProjeto(digiclad);
		Integer codigo_projeto = 2;
		
		Projeto teste_projeto = empresa_CIA.getProjetoProcurado(codigo_projeto);
		
		assertEquals(null, teste_projeto);

	}
	
	@Test
	public void testGetNumeroProjetos() {
		
		empresa_CIA.AddProjeto(digiclad);
		
		Integer qtde = 1;
		
		assertEquals(qtde, empresa_CIA.getNumeroProjetos());

	}
	
}
