import org.junit.Test;

public class RefactoredTestClass1 {

	@Test()
	public void AddMaisDeUmFuncionario() {
		Empresa empresa = new Empresa("Massashin");
		empresa.contrataFunc("Paulo");
		Funcionario paulo = empresa.indexFuncionario(0);
		empresa.contrataFunc("Igor");
		Funcionario igor = empresa.indexFuncionario(1);
		empresa.addProjeto("WEG-67");
		Projetos WEG67 = empresa.pegarProjeto(0);
		WEG67.addFuncionario(paulo);
		WEG67.addFuncionario(igor);
		assertEquals(2, WEG67.numeroDeFuncionarios());
	}
}
