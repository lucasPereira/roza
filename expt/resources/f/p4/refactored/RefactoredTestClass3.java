import org.junit.Test;

public class RefactoredTestClass3 {

	@Test()
	public void falhaAoSeResponsabilizarPorMaisQueDezTrabalhos() {
		Funcionario joao = new Funcionario();
		Projeto projetoNum01 = new Projeto();
		Projeto projetoNum02 = new Projeto();
		Projeto projetoNum03 = new Projeto();
		Projeto projetoNum04 = new Projeto();
		Projeto projetoNum05 = new Projeto();
		Projeto projetoNum06 = new Projeto();
		Projeto projetoNum07 = new Projeto();
		Projeto projetoNum08 = new Projeto();
		Projeto projetoNum09 = new Projeto();
		Projeto projetoNum10 = new Projeto();
		joao.adicionarProjeto(projetoNum01);
		joao.adicionarProjeto(projetoNum02);
		joao.adicionarProjeto(projetoNum03);
		joao.adicionarProjeto(projetoNum04);
		joao.adicionarProjeto(projetoNum05);
		joao.adicionarProjeto(projetoNum06);
		joao.adicionarProjeto(projetoNum07);
		joao.adicionarProjeto(projetoNum08);
		joao.adicionarProjeto(projetoNum09);
		joao.adicionarProjeto(projetoNum10);
		joao.responsavelPorNovoProjeto(projetoNum01);
		joao.responsavelPorNovoProjeto(projetoNum02);
		joao.responsavelPorNovoProjeto(projetoNum03);
		joao.responsavelPorNovoProjeto(projetoNum04);
		joao.responsavelPorNovoProjeto(projetoNum05);
		joao.responsavelPorNovoProjeto(projetoNum06);
		joao.responsavelPorNovoProjeto(projetoNum07);
		joao.responsavelPorNovoProjeto(projetoNum08);
		joao.responsavelPorNovoProjeto(projetoNum09);
		joao.responsavelPorNovoProjeto(projetoNum10);
		Projeto projetoNum11 = new Projeto();
		joao.adicionarProjeto(projetoNum11);
		joao.responsavelPorNovoProjeto(projetoNum11);
	}
}
