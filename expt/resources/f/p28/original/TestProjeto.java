package tests;

import main.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestProjeto {
    Projeto projeto;
    String nome;

    @Before
    public void setup() {
        nome = "Gmail";
        projeto = new Projeto(nome);
    }

    @Test
    public void criarProjeto() {
        assertEquals(nome, projeto.getNomeProjeto());
    }

    @Test
    public void alterarNomeProjeto() {
        String novoNome = "Youtube";
        projeto.setNome(novoNome);

        assertEquals(novoNome, projeto.getNomeProjeto());
    }

    @Test
    public void cadastrarOcorrencia() throws FuncionarioComMaximoOcorrencias{
        String resumoOcorrencia = "Problemas ao visualizar email";
        int maxOcorrencias = 10;
        Funcionario funcionario = new Funcionario("Gilmar Douglas", maxOcorrencias);
        Ocorrencia ocorrencia = new Ocorrencia(resumoOcorrencia, funcionario, TipoOcorrenciaEnum.TAREFA, PrioridadeOcorrenciaEnum.ALTA);
        projeto.adicionarOcorrencia(ocorrencia);

        assertTrue(projeto.ocorrenciaPertenceProjeto(ocorrencia));
    }

    @Test
    public void verificaOcorrenciaChaveUnica() throws FuncionarioComMaximoOcorrencias{
        String resumoOcorrencia = "Problema para pesquisar email";
        int maxOcorrencias = 10;
        Funcionario funcionario = new Funcionario("Patricia Vilain", maxOcorrencias);

        Ocorrencia ocorrencia1 = new Ocorrencia(resumoOcorrencia, funcionario, TipoOcorrenciaEnum.TAREFA, PrioridadeOcorrenciaEnum.ALTA);
        Ocorrencia ocorrencia2 = new Ocorrencia(resumoOcorrencia, funcionario, TipoOcorrenciaEnum.TAREFA, PrioridadeOcorrenciaEnum.ALTA);
        projeto.adicionarOcorrencia(ocorrencia1);
        projeto.adicionarOcorrencia(ocorrencia2);

        assertTrue(ocorrencia1.getId() != ocorrencia2.getId());
    }

    @Test
    public void adicionarMaisDeUmacorrenciaProjeto() throws FuncionarioComMaximoOcorrencias{
        String resumo1 = "Filtro nao é aplicado corretamente";
        String resumo2 = "Video não carrega corretamente";

        int maxOcorrencias = 10;
        Funcionario funcionario = new Funcionario("Roberto Alves", maxOcorrencias);

        Ocorrencia ocorrencia1 = new Ocorrencia(resumo1, funcionario, TipoOcorrenciaEnum.TAREFA, PrioridadeOcorrenciaEnum.ALTA);
        Ocorrencia ocorrencia2 = new Ocorrencia(resumo2, funcionario, TipoOcorrenciaEnum.TAREFA, PrioridadeOcorrenciaEnum.ALTA);

        projeto.adicionarOcorrencia(ocorrencia1);
        projeto.adicionarOcorrencia(ocorrencia2);

        assertEquals(2, projeto.getQuatidadeOcorrencias());
        assertEquals(ocorrencia1, projeto.getOcorrencia(ocorrencia1));
        assertEquals(ocorrencia2, projeto.getOcorrencia(ocorrencia2));
    }

    @Test(expected = FuncionarioComMaximoOcorrencias.class)
    public void verificaOcorrenciaCadastradaComSucesso() throws FuncionarioComMaximoOcorrencias{
        String resumoOcorrencia = "Falha ao carregar rascunho";
        int maxOcorrencias = 0;
        Funcionario funcionario = new Funcionario("Thiago Souza", maxOcorrencias);
        Ocorrencia ocorrencia = new Ocorrencia(resumoOcorrencia, funcionario, TipoOcorrenciaEnum.TAREFA, PrioridadeOcorrenciaEnum.ALTA);
        projeto.adicionarOcorrencia(ocorrencia);
    }
}
