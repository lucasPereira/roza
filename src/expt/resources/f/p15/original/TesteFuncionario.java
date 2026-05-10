package tests.br.ufsc.leb.adan.gerenciador.ocorrencias;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import br.ufsc.leb.adan.gerenciador.ocorrencias.Funcionario;

public class TesteFuncionario {

	@Test
	void criaFuncionario() throws Exception {
		String nome = "Lucas";
		String cpf = "000.111.222-33";
		Funcionario lucas = new Funcionario(nome, cpf);
		assertEquals(nome, lucas.obterNome());
		assertEquals(cpf, lucas.obterCpf());
		assertEquals(0, lucas.obterProjetos().size());
		assertEquals(0, lucas.obterOcorrencias().size());
	}

}
