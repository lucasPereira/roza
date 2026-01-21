import org.junit.Test;

public class RefactoredTestClass9 {

	@Test()
	public void testProjeto() {
		Funcionario juninho = new Funcionario("Juninho", "0000007");
		empresa.addFuncionario(juninho);
		Projeto p = new Projeto("Tibia 2", "001", juninho.id());
		empresa.addProjeto(p);
		assertNotNull(empresa.projetos());
		assertEquals(p.id(), empresa.getProjetoByID("001").id());
	}
}
