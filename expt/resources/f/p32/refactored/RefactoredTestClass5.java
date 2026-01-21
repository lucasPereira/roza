import org.junit.Test;

public class RefactoredTestClass5 {

	@Test()
	public void verificarNomeFuncionario() {
		Empresa empresa = DelegatedSetups.novaEmpresaComFuncionario();
		assertEquals("Pedro", empresa.getFuncionario("Pedro").getNome());
		assertEquals(null, empresa.getFuncionario("Alexandre"));
	}
}
