import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass6 {

	private Funcionario funcionario;

	@Before()
	public void setup() {
		funcionario = new Funcionario();
		this.empresaEmTeste = new Empresa();
		empresaEmTeste.addFuncionario(funcionario);
		empresaEmTeste.setProjetos(createProjetosComOcorrencia(this.empresaEmTeste, funcionario));
	}

	@Test()
	public void deveAdicionarProjetoCorretamente() {
		int sizeAntes = this.empresaEmTeste.getProjetos().size();
		this.empresaEmTeste.addProjeto(new Projeto());
		int sizeDepois = this.empresaEmTeste.getProjetos().size();
		assertEquals(sizeDepois, sizeAntes + 1);
	}

	@Test()
	public void deveRetornarFuncionariosSemOcorrencia() {
		Funcionario funcionario = new Funcionario();
		this.empresaEmTeste.addFuncionario(funcionario);
		ArrayList<Funcionario> funcionarios = this.empresaEmTeste.funcionariosSemOcorrencia();
		assertEquals(1, funcionarios.size());
	}

	@Test()
	public void deveSubstituirProjetosCorretamente() {
		ArrayList<Projeto> projetos = new ArrayList<>();
		this.empresaEmTeste.setProjetos(projetos);
		assertEquals(this.empresaEmTeste.getProjetos(), projetos);
	}

	@Test()
	public void responsavelPorOcorrencia() {
		Funcionario novoFuncionario = new Funcionario();
		Ocorrencia ocorrencia = Ocorrencia.Bug(this.empresaEmTeste.novaChaveOcorrencia(), "resumo", novoFuncionario, Prioridade.MEDIA);
		this.empresaEmTeste.getProjetos().get(0).addOcorrencia(ocorrencia);
	}

	@Test()
	public void retornaProjetosComBugCorretamente() {
		ArrayList<Projeto> projetos = this.empresaEmTeste.projetosByTipoOcorrencia(TipoOcorrencia.BUG);
		assertEquals(projetos.size(), 1);
	}
}
