import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class EmpresaTest {
	
	private final String cnpj = "54.155.260/0001-65";
	private final String nome = "Empresa Falsa";
	private Empresa empresa;
	
	@Before
	public void setUp() throws Exception {
		empresa = new Empresa(cnpj, nome);
	}

	@Test
	public void testCriarEmpresa() {
		assertEquals(cnpj, empresa.cnpj());
		assertEquals(nome, empresa.nome());
		
		assertTrue(empresa.funcionarios().isEmpty());
		assertTrue(empresa.projetos().isEmpty());
	}
	
	@Test
	public void testAdicionarFuncionariaMaria() {
		Funcionario maria = new Funcionario("Maria");
		
		empresa.adicionarFuncionario(maria);
		
		assertEquals(1, empresa.funcionarios().size());
		assertTrue(empresa.funcionarios().contains(maria));
	}
	
	@Test
	public void testDemitirFuncionarioPedro() {
		Funcionario pedro = new Funcionario("Pedro");	
		empresa.adicionarFuncionario(pedro);
		
		empresa.demitirFuncionario(pedro);
		
		assertTrue(empresa.funcionarios().isEmpty());
		assertFalse(empresa.funcionarios().contains(pedro));
	}
	
	@Test
	public void testAdicionarProjetoHotel() {
		Projeto hotel = new Projeto("Hotel");
		
		empresa.adicionarProjeto(hotel);
		
		assertEquals(1, empresa.projetos().size());
		assertTrue(empresa.projetos().contains(hotel));
	}

	@Test
	public void testCancelarProjetoCasa() {
		Projeto casa = new Projeto("Casa");
		empresa.adicionarProjeto(casa);
		
		empresa.cancelarProjeto(casa);
		
		assertEquals(0, empresa.projetos().size());
		assertFalse(empresa.projetos().contains(casa));
	}
}
