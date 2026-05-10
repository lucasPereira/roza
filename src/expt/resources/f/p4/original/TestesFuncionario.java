package testes;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import projeto.Funcionario;
import projeto.Projeto;

public class TestesFuncionario {
	
	Funcionario joao;
	
	@Before
	public void inicializacao() {
		joao = new Funcionario();
	}

	@Test
	public void chaveUnicaDoisFuncionarios() throws Exception {
		Funcionario carlos = new Funcionario();
		assertNotEquals(joao.obtemChave(), carlos.obtemChave());
	}
	
	@Test
	public void chaveSequencialDoisFuncionarios() throws Exception {
		Funcionario carlos = new Funcionario();
		assertTrue(joao.obtemChave() < carlos.obtemChave());
	}
	
	@Test
	public void trabalhaEmNovoProjeto() throws Exception {
		Projeto novoProjeto = new Projeto();
		joao.adicionarProjeto(novoProjeto);
		List<Projeto> projetosQueTrabalha = joao.obterProjetosQueTrabalha();
		
		assertEquals(1, projetosQueTrabalha.size());
	}
	
	@Test
	public void saiDeUmProjeto() throws Exception {
		Projeto novoProjeto = new Projeto();
		joao.adicionarProjeto(novoProjeto);
		joao.sairProjeto(novoProjeto);
		List<Projeto> projetosQueTrabalha = joao.obterProjetosQueTrabalha();
		
		assertEquals(0, projetosQueTrabalha.size());
	}
	
	@Test(expected=Exception.class)
	public void saiDeUmProjetoQueNaoFazParte() throws Exception {
		Projeto novoProjeto = new Projeto();
		joao.sairProjeto(novoProjeto);
	}
	
	@Test
	public void responsavelPorProjeto() throws Exception {
		Projeto novoProjeto = new Projeto();
		joao.adicionarProjeto(novoProjeto);
		joao.responsavelPorNovoProjeto(novoProjeto);
		
		List<Projeto> projetosResponsavel = joao.obterProjetosQueSeResponsabiliza();
		
		assertEquals(1, projetosResponsavel.size());
	}
	
	@Test(expected=Exception.class)
	public void falhaAoSeResponsabilizarPorProjetoQueNaoTrabalha() throws Exception {
		Projeto novoProjeto = new Projeto();
		joao.responsavelPorNovoProjeto(novoProjeto);
	}
	
	@Test(expected=Exception.class)
	public void falhaAoSeResponsabilizarPorMaisQueDezTrabalhos() throws Exception {
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
	
	@Test(expected=Exception.class)
	public void saiDeUmProjetoQueEResponasvel() throws Exception {
		Projeto novoProjeto = new Projeto();
		joao.adicionarProjeto(novoProjeto);
		joao.responsavelPorNovoProjeto(novoProjeto);
		joao.sairProjeto(novoProjeto);
	}

}
