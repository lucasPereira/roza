package testes;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import domain.*;

class TestesEmpresas {
	
	public Empresa empresaSemNome;
	public Empresa empresaBrasil;

	@BeforeEach
	void iniciarTestes() {
		empresaBrasil = new Empresa("Empresa Brasil");
		empresaBrasil.contratarFuncionario("Luiz Fernando");
	}
	
	@Test // 1
	void testarCriacaoEmpresaSemNome() throws Exception {
		String nomeEmpresa = "";
		
		empresaSemNome = new Empresa(nomeEmpresa);
		
		assertEquals("Empresa sem nome", empresaSemNome.getNomeDaEmpresa());
	}

	@Test // 2
	void testarCriacaoEmpresa() throws Exception {
		String nomeEmpresa = "Empresa Brasil";
		
		empresaSemNome = new Empresa(nomeEmpresa);
		
		assertEquals(nomeEmpresa, empresaSemNome.getNomeDaEmpresa());
		assertEquals(0, empresaSemNome.getNumeroDeFuncionarios());
	}
	
	@Test // 5
	void contratarFuncionarioVazio() throws Exception {
		empresaBrasil.contratarFuncionario("");
		assertEquals(1, empresaBrasil.getNumeroDeFuncionarios());
	}
	
	@Test // 6
	void contratarFuncionarioComNome() throws Exception {
		String nomeFuncionario = "Luiz Fernando";
		empresaBrasil.contratarFuncionario(nomeFuncionario);
		assertEquals(2, empresaBrasil.getNumeroDeFuncionarios());
	}

}
