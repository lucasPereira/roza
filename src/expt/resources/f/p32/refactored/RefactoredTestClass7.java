import org.junit.Test;

public class RefactoredTestClass7 {

	@Test()
	public void verificarQueUmFuncionarioPodeTrabalharEmVariosProjetosAoMesmoTempo() {
		Empresa empresa = DelegatedSetups.novaEmpresaComFuncionario();
		empresa.novoProjeto("Projeto TDD");
		empresa.novoProjeto("Projeto B");
		empresa.getProjeto("Projeto TDD").addResponsavel(empresa.getFuncionario("Pedro"));
		empresa.getProjeto("Projeto B").addResponsavel(empresa.getFuncionario("Pedro"));
		assertEquals(2, empresa.getFuncionario("Pedro").getProjetosQueEhResponsavel().size());
	}
}
