package gerenciadordeocorrencias;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class TesteEmpresa {

	private Empresa empresa;
	private String nomeDoFuncionarioJoao;
	private String nomeDoFuncionarioMateus;
	private String nomeDoFuncionarioPedro;
	private String nomeDoProjetoBancario;
	private String nomeDoProjetoMedico;
	private String nomeDoProjetoComercial;

	
	@Before
	public void criarEmpresaENomes() {
		empresa = new Empresa();
		nomeDoFuncionarioJoao = "Joao";
		nomeDoFuncionarioMateus = "Mateus";
		nomeDoFuncionarioPedro = "Pedro";
		nomeDoProjetoBancario = "Sistema Bancario";
		nomeDoProjetoMedico = "Sistema Medico";
		nomeDoProjetoComercial = "Sistema Comercial";
	}
	
	@Test
	public void criarFuncionario() {
		Funcionario funcionario = empresa.criarFuncionario(nomeDoFuncionarioJoao);

		assertEquals(nomeDoFuncionarioJoao, funcionario.obterNome());
	}

	@Test
	public void obterFuncionarioCriado() {
		Funcionario funcionarioCriado = empresa.criarFuncionario(nomeDoFuncionarioJoao);

		Funcionario funcionarioObtido = empresa.obterFuncionario(nomeDoFuncionarioJoao);

		assertEquals(funcionarioCriado, funcionarioObtido);
	}

	@Test
	public void obterPrimeiroFuncionarioCriadoDentreDois() {
		Empresa empresa = new Empresa();
		Funcionario funcionarioCriado = empresa.criarFuncionario(nomeDoFuncionarioJoao);
		empresa.criarFuncionario(nomeDoFuncionarioMateus);

		Funcionario funcionarioObtido = empresa.obterFuncionario(nomeDoFuncionarioJoao);

		assertEquals(funcionarioCriado, funcionarioObtido);
	}

	@Test
	public void obterSegundoFuncionarioCriadoDentreTres() {
		empresa.criarFuncionario(nomeDoFuncionarioJoao);
		Funcionario funcionarioMateus = empresa.criarFuncionario(nomeDoFuncionarioMateus);
		empresa.criarFuncionario(nomeDoFuncionarioPedro);

		Funcionario funcionarioObtido = empresa.obterFuncionario(nomeDoFuncionarioMateus);

		assertEquals(funcionarioMateus, funcionarioObtido);
	}

	@Test
	public void criarProjeto() {

		Projeto projeto = empresa.criarProjeto(nomeDoProjetoBancario);

		assertEquals(nomeDoProjetoBancario, projeto.obterNome());
	}

	@Test
	public void obterProjetoCriado() {
		Projeto projetoCriado = empresa.criarProjeto(nomeDoProjetoBancario);

		Projeto projetoObtido = empresa.obterProjeto(nomeDoProjetoBancario);

		assertEquals(projetoCriado, projetoObtido);
	}

	@Test
	public void obterPrimeiroProjetoCriadoDentreDois() {
		Projeto projetoCriado = empresa.criarProjeto(nomeDoProjetoBancario);
		empresa.criarProjeto(nomeDoProjetoMedico);

		Projeto projetoObtido = empresa.obterProjeto(nomeDoProjetoBancario);

		assertEquals(projetoCriado, projetoObtido);
	}
	
	@Test
	public void obterSegundoProjetoCriadoDentreTres() {
		empresa.criarProjeto(nomeDoProjetoBancario);
		Projeto projetoMedico = empresa.criarProjeto(nomeDoProjetoMedico);
		empresa.criarProjeto(nomeDoProjetoComercial);

		Projeto projetoObtido = empresa.obterProjeto(nomeDoProjetoMedico);

		assertEquals(projetoMedico, projetoObtido);
	}

}
