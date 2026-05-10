package Enunciado;
import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class Teste_projeto {
	public Funcionario paulo;
	public Empresa empresa;
	public Ocorrencia ocorrencia;
	
	@Before
	public void configurar() {
		empresa = new Empresa("Massashin");
		empresa.contrataFunc("Paulo");
		paulo = empresa.indexFuncionario(0); //primeiro na lista para pegar funcionario
												// Adiciona o metodo indexFuncionario() em Empresa.java
	}
	
	
	@Test
	public void criaProjetoComIdentificacao() throws Exception{
		empresa.addProjeto("WEG-67"); // Adiciona metodo addProjeto() em Empresa.java
		assertEquals("WEG-67", empresa.pegarProjeto(0).localizaCodProjeto()); // altera Empresa.java e Projetos.java
		assertEquals(1, empresa.pegarNumProjetos());
	}
	
	@Test
	public void criaProjetoNaoIdentificadoIdNull() throws Exception{ 
		empresa.addProjeto(""); // como é "", nem se cria o projeto, portanto, o numero de projetos = 0
		assertEquals(0, empresa.pegarNumProjetos());
	}
	
	@Test
	public void criaMaisDeUmProjetos() throws Exception{
		empresa.addProjeto("WEG-67");
		empresa.addProjeto("WEG-68");
		empresa.addProjeto("WEG-69");
		assertEquals("WEG-67", empresa.pegarProjeto(0).localizaCodProjeto());// altera Projetos.java
		assertEquals("WEG-68", empresa.pegarProjeto(1).localizaCodProjeto());// altera Projetos.java
		assertEquals("WEG-69", empresa.pegarProjeto(2).localizaCodProjeto());// altera Projetos.java
		assertEquals(3, empresa.pegarNumProjetos());
	}
	
	@Test
	public void AddFuncionario() throws Exception{
		empresa.addProjeto("WEG-67");
		Projetos WEG67 = empresa.pegarProjeto(0); 
		WEG67.addFuncionario(paulo); // altera Projeto.java para add o metodo addFuncionario()
		assertEquals(1, WEG67.numeroDeFuncionarios());// altera Projeto.java para add o metodo numeroDeFuncionarios()
		//ao inves de funcionario chamar Paulo ou Igor
	}
	
	@Test
	public void AddMaisDeUmFuncionario() throws Exception{
		empresa.contrataFunc("Igor"); // Faz a empresa contratar mais um funcionario para ser adicionado no projeto,
		Funcionario igor = empresa.indexFuncionario(1);//  ja tinha funcionario Paulo no index 0
		empresa.addProjeto("WEG-67");
		Projetos WEG67 = empresa.pegarProjeto(0);
		WEG67.addFuncionario(paulo); // Paulo era o unico funcionario
		WEG67.addFuncionario(igor);
		assertEquals(2, WEG67.numeroDeFuncionarios());
		//ao inves de funcionario chamar Paulo ou Igor
	}
	
	@Test
	public void criaAOcorrencia() throws Exception{
		empresa.addProjeto("WEG-67");
		Projetos WEG67 = empresa.pegarProjeto(0);
		WEG67.addFuncionario(paulo);
		WEG67.criaOcorrencia(paulo,Ocorrencia_tipos.BUG, Prioridade.ALTA, "Falta montar prato"); // criaOcorrencia()-> Projetos.java
		assertEquals(new Integer(1), WEG67.pegarNumOcorrencias()); // Add pegaNumOcorrencias() - Projetos.java
		assertEquals(new Integer (1), paulo.numeroOcorrencias()); // Add pegarNumOcorrencias() - Funcionario.java
	}

}
