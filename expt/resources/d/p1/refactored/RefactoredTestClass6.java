import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass6 {

	private Banco banco_universitario;

	@Before()
	public void setup() {
		banco_universitario = TestBancoAgenciaHelper.CriaBancoUniversitario();
	}

	@Test()
	public void criaAgencia() {
		Agencia agencia_ufsc = new Agencia(nome_agencia, codigo_agencia, banco_universitario);
		assertEquals(nome_agencia, agencia_ufsc.obterNome());
		assertEquals("00" + codigo_agencia.toString(), agencia_ufsc.obterIdentificador());
		assertEquals(banco_universitario, agencia_ufsc.obterBanco());
	}

	@Test()
	public void testeObtemMoedaBanco() {
		Moeda moeda_banco = banco_universitario.obterMoeda();
		assertEquals(Moeda.BRL, moeda_banco);
	}

	@Test()
	public void testeObtemNomeBanco() {
		String nome_banco = banco_universitario.obterNome();
		assertEquals("Universitario", nome_banco);
	}
}
