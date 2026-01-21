package Enunciado;
import static org.junit.Assert.assertEquals;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class Teste_responsavel {
	
	public Funcionario funcionario;
	public Funcionario paulo;
	public Empresa empresa;
	public Projetos projeto;
	public Ocorrencia ocorrencia;
	
	@Before
	public void configurar() throws Exception{
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
	
	@Test
	public void dezOcorrencias() throws Exception{// na verdd cria 9 mas tinha uma no inicio
		projeto.criaOcorrencia(funcionario,Ocorrencia_tipos.BUG, Prioridade.MEDIA, "Limpar o salmão");
		projeto.criaOcorrencia(funcionario,Ocorrencia_tipos.TAREFA, Prioridade.BAIXA, "Limpar a cozinha");
		projeto.criaOcorrencia(funcionario,Ocorrencia_tipos.MELHORIA, Prioridade.ALTA, "Montar os pratos");
		projeto.criaOcorrencia(funcionario,Ocorrencia_tipos.BUG, Prioridade.MEDIA, "Atender clientes");
		projeto.criaOcorrencia(funcionario,Ocorrencia_tipos.TAREFA, Prioridade.BAIXA, "Esquentar a chapa");
		projeto.criaOcorrencia(funcionario,Ocorrencia_tipos.MELHORIA, Prioridade.ALTA, "Fritar as coisas");
		projeto.criaOcorrencia(funcionario,Ocorrencia_tipos.BUG, Prioridade.MEDIA, "Lavar verduras");
		projeto.criaOcorrencia(funcionario,Ocorrencia_tipos.TAREFA, Prioridade.BAIXA, "Cozinhar arroz");
		projeto.criaOcorrencia(funcionario,Ocorrencia_tipos.MELHORIA, Prioridade.ALTA, "Abrir o restaurante");
		
		assertEquals(new Integer (10), projeto.pegarNumOcorrencias());
		assertEquals(new Integer (10), funcionario.numeroOcorrencias());
	}
	
	@Test
	public void maisDeDezOcorrencias() throws Exception{//Cria 11 ocorrencias
		// Testar sempre os limiter max inferior e min superior
		projeto.criaOcorrencia(funcionario,Ocorrencia_tipos.BUG, Prioridade.MEDIA, "Limpar o salmão");
		projeto.criaOcorrencia(funcionario,Ocorrencia_tipos.TAREFA, Prioridade.BAIXA, "Limpar a cozinha");
		projeto.criaOcorrencia(funcionario,Ocorrencia_tipos.MELHORIA, Prioridade.ALTA, "Montar os pratos");
		projeto.criaOcorrencia(funcionario,Ocorrencia_tipos.BUG, Prioridade.MEDIA, "Atender clientes");
		projeto.criaOcorrencia(funcionario,Ocorrencia_tipos.TAREFA, Prioridade.BAIXA, "Esquentar a chapa");
		projeto.criaOcorrencia(funcionario,Ocorrencia_tipos.MELHORIA, Prioridade.ALTA, "Fritar as coisas");
		projeto.criaOcorrencia(funcionario,Ocorrencia_tipos.BUG, Prioridade.MEDIA, "Lavar verduras");
		projeto.criaOcorrencia(funcionario,Ocorrencia_tipos.TAREFA, Prioridade.BAIXA, "Cozinhar arroz");
		projeto.criaOcorrencia(funcionario,Ocorrencia_tipos.MELHORIA, Prioridade.ALTA, "Abrir o restaurante");
		projeto.criaOcorrencia(funcionario,Ocorrencia_tipos.BUG, Prioridade.MEDIA, "Fazer sushi");
		
		assertEquals(new Integer (11), projeto.pegarNumOcorrencias()); // 12 pq ja tinha uma ocorrencia no incio
		assertEquals(new Integer (10), funcionario.numeroOcorrencias()); // Max 10 ocorrencias
	}
	
	@Test
	public void trocaFuncDeOcorrenciaFechada() throws Exception{
		projeto.fechaOcorrencia(0); //modifica Projeto.java para add fechaOcorrencia()
		projeto.trocarResponsavel(0, paulo); //modifica Projeto.java para add trocarResponsavel()
		
		assertEquals(new Integer (0), paulo.numeroOcorrencias()); // mesmo alterando responsabilidade nao contabiliza em Paulo pq ocorrencia foi fechada
		assertEquals(new Integer (0), funcionario.numeroOcorrencias());
	}
	
	
	@Test
	public void trocaFuncDeOcorrenciaAberta() throws Exception{
		projeto.trocarResponsavel(0, paulo); //modifica Projeto.java para add trocarResponsavel()
		
		assertEquals(new Integer (1), paulo.numeroOcorrencias()); // Paulo foi movido para a ocorrencia aberta criada noinicio
		assertEquals(new Integer (0), funcionario.numeroOcorrencias()); // Igor fica sem ocorrencia
	}
	
	@Test
	public void trocaDoisFuncDeDuasOcorrenciasAberta() throws Exception{
		projeto.criaOcorrencia(funcionario, Ocorrencia_tipos.BUG, Prioridade.BAIXA, "limpar balcao"); // cria nova ocorrencia, resultando no total de 2 ocorrencias
		projeto.trocarResponsavel(0, paulo);
		projeto.trocarResponsavel(1, paulo);
		// Os dois projetos agora tem Paulo como responsavel e igor nao esta responsavel por nenhuma ocorrencia
		assertEquals(new Integer (2), paulo.numeroOcorrencias());
		assertEquals(new Integer (0), funcionario.numeroOcorrencias());
	}
	
	

	@Test
	public void fechaOcorrenciaSemResponsavel() throws Exception{
		projeto.criaOcorrencia(funcionario, Ocorrencia_tipos.BUG, Prioridade.BAIXA, "limpar balcao");
		projeto.criaOcorrencia(funcionario, Ocorrencia_tipos.TAREFA, Prioridade.ALTA, "Cozinhar arroz");
		projeto.criaOcorrencia(funcionario, Ocorrencia_tipos.MELHORIA, Prioridade.MEDIA, "Limpar bandejas");
		
		projeto.criaOcorrencia(funcionario,Ocorrencia_tipos.BUG, Prioridade.MEDIA, "Limpar o salmão");
		projeto.criaOcorrencia(funcionario,Ocorrencia_tipos.TAREFA, Prioridade.BAIXA, "Limpar a cozinha");
		projeto.criaOcorrencia(funcionario,Ocorrencia_tipos.MELHORIA, Prioridade.ALTA, "Montar os pratos");
		projeto.criaOcorrencia(funcionario,Ocorrencia_tipos.BUG, Prioridade.MEDIA, "Atender clientes");
		projeto.criaOcorrencia(funcionario,Ocorrencia_tipos.TAREFA, Prioridade.BAIXA, "Esquentar a chapa");
		projeto.criaOcorrencia(funcionario,Ocorrencia_tipos.MELHORIA, Prioridade.ALTA, "Fritar as coisas");
		projeto.criaOcorrencia(funcionario,Ocorrencia_tipos.BUG, Prioridade.MEDIA, "Lavar verduras");
		projeto.criaOcorrencia(funcionario,Ocorrencia_tipos.TAREFA, Prioridade.BAIXA, "Cozinhar arroz");
		projeto.criaOcorrencia(funcionario,Ocorrencia_tipos.MELHORIA, Prioridade.ALTA, "Abrir o restaurante");
		projeto.criaOcorrencia(funcionario,Ocorrencia_tipos.BUG, Prioridade.MEDIA, "Fazer sushi");
		projeto.criaOcorrencia(funcionario,Ocorrencia_tipos.TAREFA, Prioridade.BAIXA, "Fazer sushi frito");
		
		projeto.fechaOcorrencia(11); // "fechando uma ocorrencia invalida que ja nao tem responsave (>10)"
		
		assertEquals(new Integer (15), projeto.pegarNumOcorrencias());// ja tinha add uma ocorrencia no inicio. Verificamos que nao foi possivel fechar ocorrencia sem repsonsvel
		assertEquals(new Integer (10), funcionario.numeroOcorrencias());// Max 10 ocorrencias
	}
	
	@Test 
	public void fechaOcorrenciaAberta() throws Exception{
		projeto.fechaOcorrencia(0); // ja tinha uma ocorrencia adicionada no inicio
		
		assertEquals(new Integer (0), projeto.pegarNumOcorrencias());
		assertEquals(new Integer (0), funcionario.numeroOcorrencias()); // igor ja nao tem mais ocorrencia
	}
	
	@Test
	public void trocaPrioridadeOcorrencia() throws Exception{
		projeto.trocarDePrioridade(0, Prioridade.ALTA); // NO inicio foi setado como baixa
														// Adiciona metodo trocarDePrioridade() em Projetos.java
		
		assertEquals(Prioridade.ALTA, projeto.pegarPrioridadeDeOcorrencia(0)); // Adiciona metodo pegarPrioridadeDeOcorrencia() em Projeto.java
	}
	

	@Test
	public void tentaTrocaPrioridadeOcorrenciaFechada() throws Exception{
		projeto.fechaOcorrencia(0);
		projeto.trocarDePrioridade(0, Prioridade.MEDIA); // NO inicio foi setado como baixa
		
		assertEquals(new Integer (0), projeto.pegarNumOcorrencias()); // Ocorrencia ja nao existe
		assertEquals(new Integer (0), funcionario.numeroOcorrencias());
//		assertEquals(Prioridade.BAIXA, projeto.pegarPrioridadeDeOcorrencia(0)); // Nao tem como comparar pq nao existe mais ocorrencia
	}
	
	@Test
	public void trocaPrioridadeDuasOcorrencias() throws Exception{
		projeto.criaOcorrencia(funcionario, Ocorrencia_tipos.BUG, Prioridade.MEDIA, "limpar balcao");
		
		projeto.trocarDePrioridade(0, Prioridade.ALTA);// NO inicio foi setado como baixa
		projeto.trocarDePrioridade(1, Prioridade.ALTA);// era prioridade MEDIA 
		
		assertEquals(Prioridade.ALTA, projeto.pegarPrioridadeDeOcorrencia(0));
		assertEquals(Prioridade.ALTA, projeto.pegarPrioridadeDeOcorrencia(1));
	}
	
	

	
	
	
	
	
}
