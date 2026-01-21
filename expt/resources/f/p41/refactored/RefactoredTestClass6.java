import org.junit.Before;
import org.junit.Test;

public class RefactoredTestClass6 {

	private Empresa empresa;

	private Funcionario funcionario;

	private Funcionario paulo;

	private Projetos projeto;

	@Before()
	public void setup() {
		empresa = new Empresa("Massashin");
		empresa.contrataFunc("igor");
		funcionario = empresa.indexFuncionario(0);
		empresa.contrataFunc("paulo");
		paulo = empresa.indexFuncionario(1);
		empresa.addProjeto("TestSoftware");
		projeto = empresa.pegarProjeto(0);
		projeto.addFuncionario(funcionario);
		projeto.addFuncionario(paulo);
		projeto.criaOcorrencia(funcionario, Ocorrencia_tipos.TAREFA, Prioridade.BAIXA, "transportar o peixe");
	}

	@Test()
	public void dezOcorrencias() {
		projeto.criaOcorrencia(funcionario, Ocorrencia_tipos.BUG, Prioridade.MEDIA, "Limpar o salm�o");
		projeto.criaOcorrencia(funcionario, Ocorrencia_tipos.TAREFA, Prioridade.BAIXA, "Limpar a cozinha");
		projeto.criaOcorrencia(funcionario, Ocorrencia_tipos.MELHORIA, Prioridade.ALTA, "Montar os pratos");
		projeto.criaOcorrencia(funcionario, Ocorrencia_tipos.BUG, Prioridade.MEDIA, "Atender clientes");
		projeto.criaOcorrencia(funcionario, Ocorrencia_tipos.TAREFA, Prioridade.BAIXA, "Esquentar a chapa");
		projeto.criaOcorrencia(funcionario, Ocorrencia_tipos.MELHORIA, Prioridade.ALTA, "Fritar as coisas");
		projeto.criaOcorrencia(funcionario, Ocorrencia_tipos.BUG, Prioridade.MEDIA, "Lavar verduras");
		projeto.criaOcorrencia(funcionario, Ocorrencia_tipos.TAREFA, Prioridade.BAIXA, "Cozinhar arroz");
		projeto.criaOcorrencia(funcionario, Ocorrencia_tipos.MELHORIA, Prioridade.ALTA, "Abrir o restaurante");
		assertEquals(new Integer(10), projeto.pegarNumOcorrencias());
		assertEquals(new Integer(10), funcionario.numeroOcorrencias());
	}

	@Test()
	public void fechaOcorrenciaAberta() {
		projeto.fechaOcorrencia(0);
		assertEquals(new Integer(0), projeto.pegarNumOcorrencias());
		assertEquals(new Integer(0), funcionario.numeroOcorrencias());
	}

	@Test()
	public void fechaOcorrenciaSemResponsavel() {
		projeto.criaOcorrencia(funcionario, Ocorrencia_tipos.BUG, Prioridade.BAIXA, "limpar balcao");
		projeto.criaOcorrencia(funcionario, Ocorrencia_tipos.TAREFA, Prioridade.ALTA, "Cozinhar arroz");
		projeto.criaOcorrencia(funcionario, Ocorrencia_tipos.MELHORIA, Prioridade.MEDIA, "Limpar bandejas");
		projeto.criaOcorrencia(funcionario, Ocorrencia_tipos.BUG, Prioridade.MEDIA, "Limpar o salm�o");
		projeto.criaOcorrencia(funcionario, Ocorrencia_tipos.TAREFA, Prioridade.BAIXA, "Limpar a cozinha");
		projeto.criaOcorrencia(funcionario, Ocorrencia_tipos.MELHORIA, Prioridade.ALTA, "Montar os pratos");
		projeto.criaOcorrencia(funcionario, Ocorrencia_tipos.BUG, Prioridade.MEDIA, "Atender clientes");
		projeto.criaOcorrencia(funcionario, Ocorrencia_tipos.TAREFA, Prioridade.BAIXA, "Esquentar a chapa");
		projeto.criaOcorrencia(funcionario, Ocorrencia_tipos.MELHORIA, Prioridade.ALTA, "Fritar as coisas");
		projeto.criaOcorrencia(funcionario, Ocorrencia_tipos.BUG, Prioridade.MEDIA, "Lavar verduras");
		projeto.criaOcorrencia(funcionario, Ocorrencia_tipos.TAREFA, Prioridade.BAIXA, "Cozinhar arroz");
		projeto.criaOcorrencia(funcionario, Ocorrencia_tipos.MELHORIA, Prioridade.ALTA, "Abrir o restaurante");
		projeto.criaOcorrencia(funcionario, Ocorrencia_tipos.BUG, Prioridade.MEDIA, "Fazer sushi");
		projeto.criaOcorrencia(funcionario, Ocorrencia_tipos.TAREFA, Prioridade.BAIXA, "Fazer sushi frito");
		projeto.fechaOcorrencia(11);
		assertEquals(new Integer(15), projeto.pegarNumOcorrencias());
		assertEquals(new Integer(10), funcionario.numeroOcorrencias());
	}

	@Test()
	public void maisDeDezOcorrencias() {
		projeto.criaOcorrencia(funcionario, Ocorrencia_tipos.BUG, Prioridade.MEDIA, "Limpar o salm�o");
		projeto.criaOcorrencia(funcionario, Ocorrencia_tipos.TAREFA, Prioridade.BAIXA, "Limpar a cozinha");
		projeto.criaOcorrencia(funcionario, Ocorrencia_tipos.MELHORIA, Prioridade.ALTA, "Montar os pratos");
		projeto.criaOcorrencia(funcionario, Ocorrencia_tipos.BUG, Prioridade.MEDIA, "Atender clientes");
		projeto.criaOcorrencia(funcionario, Ocorrencia_tipos.TAREFA, Prioridade.BAIXA, "Esquentar a chapa");
		projeto.criaOcorrencia(funcionario, Ocorrencia_tipos.MELHORIA, Prioridade.ALTA, "Fritar as coisas");
		projeto.criaOcorrencia(funcionario, Ocorrencia_tipos.BUG, Prioridade.MEDIA, "Lavar verduras");
		projeto.criaOcorrencia(funcionario, Ocorrencia_tipos.TAREFA, Prioridade.BAIXA, "Cozinhar arroz");
		projeto.criaOcorrencia(funcionario, Ocorrencia_tipos.MELHORIA, Prioridade.ALTA, "Abrir o restaurante");
		projeto.criaOcorrencia(funcionario, Ocorrencia_tipos.BUG, Prioridade.MEDIA, "Fazer sushi");
		assertEquals(new Integer(11), projeto.pegarNumOcorrencias());
		assertEquals(new Integer(10), funcionario.numeroOcorrencias());
	}

	@Test()
	public void tentaTrocaPrioridadeOcorrenciaFechada() {
		projeto.fechaOcorrencia(0);
		projeto.trocarDePrioridade(0, Prioridade.MEDIA);
		assertEquals(new Integer(0), projeto.pegarNumOcorrencias());
		assertEquals(new Integer(0), funcionario.numeroOcorrencias());
	}

	@Test()
	public void trocaDoisFuncDeDuasOcorrenciasAberta() {
		projeto.criaOcorrencia(funcionario, Ocorrencia_tipos.BUG, Prioridade.BAIXA, "limpar balcao");
		projeto.trocarResponsavel(0, paulo);
		projeto.trocarResponsavel(1, paulo);
		assertEquals(new Integer(2), paulo.numeroOcorrencias());
		assertEquals(new Integer(0), funcionario.numeroOcorrencias());
	}

	@Test()
	public void trocaFuncDeOcorrenciaAberta() {
		projeto.trocarResponsavel(0, paulo);
		assertEquals(new Integer(1), paulo.numeroOcorrencias());
		assertEquals(new Integer(0), funcionario.numeroOcorrencias());
	}

	@Test()
	public void trocaFuncDeOcorrenciaFechada() {
		projeto.fechaOcorrencia(0);
		projeto.trocarResponsavel(0, paulo);
		assertEquals(new Integer(0), paulo.numeroOcorrencias());
		assertEquals(new Integer(0), funcionario.numeroOcorrencias());
	}

	@Test()
	public void trocaPrioridadeDuasOcorrencias() {
		projeto.criaOcorrencia(funcionario, Ocorrencia_tipos.BUG, Prioridade.MEDIA, "limpar balcao");
		projeto.trocarDePrioridade(0, Prioridade.ALTA);
		projeto.trocarDePrioridade(1, Prioridade.ALTA);
		assertEquals(Prioridade.ALTA, projeto.pegarPrioridadeDeOcorrencia(0));
		assertEquals(Prioridade.ALTA, projeto.pegarPrioridadeDeOcorrencia(1));
	}

	@Test()
	public void trocaPrioridadeOcorrencia() {
		projeto.trocarDePrioridade(0, Prioridade.ALTA);
		assertEquals(Prioridade.ALTA, projeto.pegarPrioridadeDeOcorrencia(0));
	}
}
