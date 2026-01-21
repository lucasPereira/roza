import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass8 {

	private Funcionario joao;

	private Projeto novoProjeto;

	@Before()
	public void setup() {
		joao = new Funcionario();
		novoProjeto = new Projeto();
	}

	@Test()
	public void falhaAoSeResponsabilizarPorProjetoQueNaoTrabalha() {
		joao.responsavelPorNovoProjeto(novoProjeto);
	}

	@Test()
	public void responsavelPorProjeto() {
		joao.adicionarProjeto(novoProjeto);
		joao.responsavelPorNovoProjeto(novoProjeto);
		List<Projeto> projetosResponsavel = joao.obterProjetosQueSeResponsabiliza();
		assertEquals(1, projetosResponsavel.size());
	}

	@Test()
	public void saiDeUmProjeto() {
		joao.adicionarProjeto(novoProjeto);
		joao.sairProjeto(novoProjeto);
		List<Projeto> projetosQueTrabalha = joao.obterProjetosQueTrabalha();
		assertEquals(0, projetosQueTrabalha.size());
	}

	@Test()
	public void saiDeUmProjetoQueEResponasvel() {
		joao.adicionarProjeto(novoProjeto);
		joao.responsavelPorNovoProjeto(novoProjeto);
		joao.sairProjeto(novoProjeto);
	}

	@Test()
	public void saiDeUmProjetoQueNaoFazParte() {
		joao.sairProjeto(novoProjeto);
	}

	@Test()
	public void trabalhaEmNovoProjeto() {
		joao.adicionarProjeto(novoProjeto);
		List<Projeto> projetosQueTrabalha = joao.obterProjetosQueTrabalha();
		assertEquals(1, projetosQueTrabalha.size());
	}
}
