import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ProjetoTeste {
	private Empresa empresa;
	private Projeto projeto;
	
	@BeforeEach
	void init() {
		empresa = new Empresa();
		projeto = empresa.criarProjeto("Projeto Software");
	}

	@Test
	void projetoCriarOcorrenciaTarefa() throws Exception {
		Funcionario joao = empresa.criarFuncionario("Joao");
		Ocorrencia tarefa = projeto.criarTarefa("Criar Botao na tela", joao, PrioridadeOcorrencia.alta);
		Ocorrencia esperado = new Ocorrencia(0, 0,"Criar Botao na tela", joao,TipoOcorrencia.tarefa, PrioridadeOcorrencia.alta);
		assertEquals(esperado, tarefa);
	}
	
	@Test
	void projetoCriarOcorrenciaBug() throws Exception {
		Funcionario joao = empresa.criarFuncionario("Joao");
		Ocorrencia bug = projeto.criarBug("Criar Botao na tela", joao, PrioridadeOcorrencia.alta);
		Ocorrencia esperado = new Ocorrencia(0,0, "Criar Botao na tela", joao,TipoOcorrencia.bug, PrioridadeOcorrencia.alta);
		assertEquals(esperado, bug);
	}
	
	@Test
	void projetoCriarOcorrenciaMelhoria() throws Exception {
		Funcionario joao = empresa.criarFuncionario("Joao");
		Ocorrencia melhoria = projeto.criarMelhoria("Atualizar Botao na tela", joao, PrioridadeOcorrencia.alta);
		Ocorrencia esperado = new Ocorrencia(0,0, "Atualizar Botao na tela", joao,TipoOcorrencia.melhoria, PrioridadeOcorrencia.alta);
		assertEquals(esperado, melhoria);		
	}
}
