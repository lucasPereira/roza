import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass6 {

	private Banco bancoBradesco;

	private Agencia agenciaUFSC;

	@Before()
	public void setup() {
		bancoBradesco = TestHelper.criarBancoBradesco();
		agenciaUFSC = bancoBradesco.criarAgencia("UFSC");
	}

	@Test()
	public void testCriarAgencia() {
		assertEquals("001", agenciaUFSC.obterIdentificador());
		assertEquals("UFSC", agenciaUFSC.obterNome());
		assertEquals(bancoBradesco, agenciaUFSC.obterBanco());
	}

	@Test()
	public void testCriarConta() {
		Conta conta = agenciaUFSC.criarConta("Anna");
		assertEquals("0001-4", conta.obterIdentificador());
		assertEquals("Anna", conta.obterTitular());
		assertEquals("0,00", conta.calcularSaldo().formatado());
		assertEquals("UFSC", conta.obterAgencia().obterNome());
	}
}
