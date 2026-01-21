import org.junit.Test;

public class RefactoredTestClass1 {

	@Test()
	public void adicionarUmFuncionarioDaEmpresaEmMultiplosProjetos() {
		String nomeEmpresa = "DelaRocaCompany";
		Empresa empresa = new Empresa(nomeEmpresa);
		java.util.List<String> nomesProj = java.util.List.of("XY", "YZ");
		Projeto projetoUm = new Projeto(nomesProj.get(0));
		Projeto projetoDois = new Projeto(nomesProj.get(1));
		String nomeFuncionario = "Fabio";
		empresa.adicionarFuncionario(nomeFuncionario);
		projetoUm.adicionarFuncionario(nomeFuncionario);
		projetoDois.adicionarFuncionario(nomeFuncionario);
		assertEquals(0, empresa.obterFuncionarios().size());
		assertEquals(1, empresa.obterFuncionarios().size());
		assertEquals(0, projetoUm.obterFuncionarios().size());
		assertEquals(0, projetoDois.obterFuncionarios().size());
		assertEquals(1, projetoUm.obterFuncionarios().size());
		assertEquals(empresa.obterFuncionarioPeloNome(nomeFuncionario), projetoUm.obterFuncionarioPeloNome(nomeFuncionario));
		assertEquals(1, projetoDois.obterFuncionarios().size());
		assertEquals(empresa.obterFuncionarioPeloNome(nomeFuncionario), projetoDois.obterFuncionarioPeloNome(nomeFuncionario));
	}
}
