package tests.br.ufsc.leb.adan.gerenciador.ocorrencias;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import br.ufsc.leb.adan.gerenciador.ocorrencias.Empresa;

public class TesteEmpresa {

	@Test
	void criaEmpresa() throws Exception {
		String nome = "UFSC";
		String cnpj = "83.899.526-0001/82";
		Empresa empresa = new Empresa(nome, cnpj);
		assertEquals(nome, empresa.obterNome());
		assertEquals(cnpj, empresa.obterCnpj());
		assertEquals(0, empresa.obterFuncionarios().size());
		assertEquals(0, empresa.obterProjetos().size());
	}
}
