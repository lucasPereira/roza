package gerenciador;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.*;
import java.util.Collection.*;

import org.junit.Before;
import org.junit.Test;

import calculadora.Calculadora;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class TestesEmpresa {

	private Empresa empresa;

	@Before
	public void configurar() {
		empresa = new Empresa("Gerenciador de Ocorrências");
	}
	
	@Test
	public void criarEmpresa() throws Exception {
		// Verifica se a empresa foi criada corretamente utilizando seu construtor. As listas de funcionarios e projetos devem estar vazias
		assertThat(empresa.getNome(), equalTo("Gerenciador de Ocorrências"));
		assertThat(empresa.getListaDeFuncionarios().size(), equalTo(0));
		assertThat(empresa.getListaDeProjetos().size(), equalTo(0));
	}
	
	@Test
	public void adicionarFuncionarioNaEmpresa() throws Exception {
		// Verifica se a lista de funcionarios da empresa contém o funcionario adicionado
		Funcionario funcionario = empresa.criarFuncionario("Fabiano Manschein");
		assertThat(empresa.getListaDeFuncionarios(), hasItem(funcionario));
		assertThat(empresa.getListaDeFuncionarios().size(), equalTo(1));
	}
	
	
	@Test
	public void adicionarProjetoNaEmpresa() throws Exception {
		// Verifica se a lista de projetos da empresa contém o projeto criado
		Projeto novoProjeto = empresa.criarProjeto("TDD");
		assertThat(empresa.getListaDeProjetos(), hasItem(novoProjeto));
		assertThat(empresa.getListaDeProjetos().size(), equalTo(1));
	}	
	
	@Test
	public void adicionarFuncionarioEmProjeto() throws Exception {
		// Verifica se o funcionario foi adicionado a lista de funcionarios do projeto
		Funcionario funcionario = empresa.criarFuncionario("Fabiano Manschein");
		Projeto projeto = empresa.criarProjeto("TDD");
		empresa.atribuirFuncionarioParaProjeto(funcionario, projeto);
		assertThat(projeto.getListaDeFuncionarios(), hasItem(funcionario));
		assertThat(projeto.getListaDeFuncionarios().size(), equalTo(1));
	}
	
	
	
}
