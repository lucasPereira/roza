package test.br.ufsc.testes.exercicio_tdd;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import modelo.br.ufsc.testes.exercicio_tdd.Funcionario;
import modelo.br.ufsc.testes.exercicio_tdd.Ocorrencia;

public class TesteOcorrencia {
	
	private Funcionario einstein;
	private Ocorrencia criarBomba;
	
	@Before
	public void setUp() throws Exception {
		einstein = new Funcionario("Albert Einstein");
		criarBomba = new Ocorrencia("1", "Criar a bomba", einstein, Ocorrencia.Tipo.TAREFA, Ocorrencia.Prioridade.ALTA);
	}
	
	@Test(expected = Exception.class)
	public void testeFuncionarioComMaisDeDezOcorrencias() throws Exception {
		new Ocorrencia("2", "", einstein, Ocorrencia.Tipo.TAREFA, Ocorrencia.Prioridade.ALTA);
		new Ocorrencia("3", "", einstein, Ocorrencia.Tipo.TAREFA, Ocorrencia.Prioridade.ALTA);
		new Ocorrencia("4", "", einstein, Ocorrencia.Tipo.TAREFA, Ocorrencia.Prioridade.ALTA);
		new Ocorrencia("5", "", einstein, Ocorrencia.Tipo.TAREFA, Ocorrencia.Prioridade.ALTA);
		new Ocorrencia("6", "", einstein, Ocorrencia.Tipo.TAREFA, Ocorrencia.Prioridade.ALTA);
		new Ocorrencia("7", "", einstein, Ocorrencia.Tipo.TAREFA, Ocorrencia.Prioridade.ALTA);
		new Ocorrencia("8", "", einstein, Ocorrencia.Tipo.TAREFA, Ocorrencia.Prioridade.ALTA);
		new Ocorrencia("9", "", einstein, Ocorrencia.Tipo.TAREFA, Ocorrencia.Prioridade.ALTA);
		new Ocorrencia("10", "", einstein, Ocorrencia.Tipo.TAREFA, Ocorrencia.Prioridade.ALTA);
		new Ocorrencia("11", "", einstein, Ocorrencia.Tipo.TAREFA, Ocorrencia.Prioridade.ALTA);
	}
	
	@Test
	public void testeMudarFuncionarioDeAberta() throws Exception {
		Funcionario oppenheimer = new Funcionario("Robert Oppenheimer");
		criarBomba.mudarResponsavel(oppenheimer);
		assertEquals(oppenheimer, criarBomba.obterResponsavel());
	}
	
	@Test(expected = Exception.class)
	public void testeMudarFuncionarioDeOcorrenciaFechada() throws Exception {
		Funcionario oppenheimer = new Funcionario("Robert Oppenheimer");
		criarBomba.fechar();
		criarBomba.mudarResponsavel(oppenheimer);
	}
	
	@Test
	public void testeMudarPrioridadeDeOcorrenciaAberta() throws Exception {
		criarBomba.mudarPrioridade(Ocorrencia.Prioridade.MEDIA);
		assertEquals(Ocorrencia.Prioridade.MEDIA, criarBomba.obterPrioridade());
	}
	
	@Test(expected = Exception.class)
	public void testeMudarPrioridadeDeOcorrenciaFechada() throws Exception {
		criarBomba.fechar();
		criarBomba.mudarPrioridade(Ocorrencia.Prioridade.BAIXA);
	}
	
}
