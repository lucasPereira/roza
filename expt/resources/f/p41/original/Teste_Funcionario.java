package Enunciado;
import static org.junit.Assert.assertEquals;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class Teste_Funcionario {
	public Funcionario funcionario;
	public Empresa empresa;
	
	@Before
	public void configurar() {
		funcionario = new Funcionario("Paulo");
		empresa = new Empresa ("Massashin");
	}
	
// FAZENDO TESTES EM RELAÇÃO A FUNCIONARIOS
	@Test
	public void criaFuncionarioNormal() throws Exception {
		String funcionarioComNome = "Paulo";
		assertEquals(funcionarioComNome, funcionario.geraNomeFun()); //altara Funcionario.java
	}
	
	@Test
	public void criaFuncionarioAnonimo() throws Exception {
		Funcionario funcionarioAnonimo = new Funcionario("");	
		assertEquals("", funcionarioAnonimo.geraNomeFun());

	}
	
//	@Test
//	public void criaFuncionarioComID() throws Exception {
//		Integer id = 123;
//		funcionario.geraID(id);
//		assertEquals(new Integer(123), id);
//	}
	
//	@Test
//	public void criaFuncionarioSemID() throws Exception {
//		Integer id = null;
//		funcionario.geraID(id);
//		assertEquals(null, id);
//	}
//	
//	@Test
//	public void criaFuncionarioSemCPF() throws Exception {
//		String cpf = "" ;
//		funcionario.cadastraCPF(cpf);
//		assertEquals("Sem identificação", cpf);
//	}
		
}
