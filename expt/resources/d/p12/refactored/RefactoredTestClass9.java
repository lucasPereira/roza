import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass9 {

	private SistemaBancario sistBanc;

	private Banco bb;

	@Before()
	public void setup() {
		sistBanc = new SistemaBancario();
		bb = sistBanc.criarBanco("Banco do Brasil", Moeda.BRL);
	}

	@Test()
	public void CriarAgenciaNomeVazio() {
		bb.criarAgencia("");
		fail("Não implementado o tratamento de exceção ao gerar agencia com nome vazio");
	}

	@Test()
	public void checarBancoDaAgencia() {
		Agencia bbTrindade = bb.criarAgencia("BB Trindade");
		Banco teste = bbTrindade.obterBanco();
		assertEquals(bb, teste);
	}

	@Test()
	public void checarCriarAgencia() {
		bb.criarAgencia("BB Trindade");
		bb.criarAgencia("BB Campeche");
		fail("Não foi implementado método para acessar as agencias do banco");
	}

	@Test()
	public void checarMoedaBanco() {
		Moeda moeda = bb.obterMoeda();
		assertEquals(Moeda.BRL, moeda);
	}

	@Test()
	public void checarNomeAgencia() {
		Agencia bbTrindade = bb.criarAgencia("BB Trindade");
		String teste = bbTrindade.obterNome();
		assertEquals("BB Trindade", teste);
	}

	@Test()
	public void checarNomeBanco() {
		String nome = bb.obterNome();
		assertEquals("Banco do Brasil", nome);
	}

	@Test()
	public void criarBanco() {
		assertEquals(1, sistBanc.obterBancos().size());
	}

	@Test()
	public void criarConta() {
		Agencia bbTrindade = bb.criarAgencia("BB Trindade");
		bbTrindade.criarConta("Karla Aparecida Justen");
		fail("Não foi implementado método para acessar as contas da Agencia");
	}

	@Test()
	public void criarContaNomeVazio() {
		Agencia bbTrindade = bb.criarAgencia("BB Trindade");
		bbTrindade.criarConta("");
		fail("Não implementado o tratamento de exceção ao gerar agencia com nome vazio");
	}
}
