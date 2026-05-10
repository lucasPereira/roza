package test.br.ufsc.testes.exercicio_tdd;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import modelo.br.ufsc.testes.exercicio_tdd.Funcionario;
import modelo.br.ufsc.testes.exercicio_tdd.Ocorrencia;
import modelo.br.ufsc.testes.exercicio_tdd.Projeto;

public class TesteProjeto {
	
	private Projeto projetoManhattan;
	private Funcionario einstein;
	private Funcionario oppenheimer;
	
	@Before
	public void setUp() {
		projetoManhattan = new Projeto("Projeto Manhattan");
		einstein = new Funcionario("Albert Einstein");
		oppenheimer = new Funcionario("Robert Oppenheimer");
	}
	
	@Test
	public void testeAdicionarUmaOcorrencia() throws Exception {
		projetoManhattan.adicionarOcorrencia("1", "Criar a bomba", einstein, Ocorrencia.Tipo.TAREFA, Ocorrencia.Prioridade.ALTA);
		assertEquals(1, projetoManhattan.obterNumeroDeOcorrencias());
	}
	
	@Test
	public void testeAdicionarDuasOcorrenciaComChavesDiferentes() throws Exception {
		projetoManhattan.adicionarOcorrencia("1", "Criar a bomba", einstein, Ocorrencia.Tipo.TAREFA, Ocorrencia.Prioridade.ALTA);
		projetoManhattan.adicionarOcorrencia("2", "Jogar a bomba no Japão", oppenheimer, Ocorrencia.Tipo.TAREFA, Ocorrencia.Prioridade.ALTA);
		assertEquals(2, projetoManhattan.obterNumeroDeOcorrencias());
	}
	
	@Test(expected = Exception.class)
	public void testeAdicionarDuasOcorreciasComChavesIguais() throws Exception {
		projetoManhattan.adicionarOcorrencia("1", "Criar a bomba", einstein, Ocorrencia.Tipo.TAREFA, Ocorrencia.Prioridade.ALTA);
		projetoManhattan.adicionarOcorrencia("1", "Jogar a bomba no Japão", oppenheimer, Ocorrencia.Tipo.TAREFA, Ocorrencia.Prioridade.ALTA);
	}

}
