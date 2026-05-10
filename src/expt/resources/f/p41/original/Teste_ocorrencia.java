package Enunciado;
import static org.junit.Assert.assertEquals;
import java.util.List;
import org.junit.Before;
import org.junit.Test;

public class Teste_ocorrencia {
	public Ocorrencia ocorrencia;
	public Projetos projeto;
	public Funcionario funcionario;
	public Empresa empresa;
	
	@Before
	public void configurar() {
		
		empresa = new Empresa("Retaurante Massashin");
		empresa.contrataFunc("Paulo");
		funcionario = empresa.indexFuncionario(0);
		empresa.addProjeto("WEG-70");
		projeto = empresa.pegarProjeto(0);
				
	}
	
	@Test
	public void ocorrenciaCompletaComResumo() {
		projeto.criaOcorrencia(funcionario, Ocorrencia_tipos.TAREFA, Prioridade.ALTA, "Mal montado"); // cria o metodo criaOcorrencia em Projetos.java
		assertEquals("Mal montado", projeto.identificarOcorrencia(0).pegarResumo()); // cria metodo identificarOcorrencia() em Projetos.java
	}																				// Cria pegarResumo() em Ocorrencia.java
	
	@Test
	public void ocorrenciaSemResumo() {
		projeto.criaOcorrencia(funcionario, Ocorrencia_tipos.TAREFA, Prioridade.MEDIA, ""); // cria o metodo criaOcorrencia em Projetos.java
		assertEquals("Ocorrencia sem descrição", projeto.identificarOcorrencia(0).pegarResumo()); // cria metodo identificarOcorrencia() em Projetos.java
	}
	
	
	
}


