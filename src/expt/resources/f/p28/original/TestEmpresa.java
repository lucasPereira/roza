package tests;

import main.Empresa;
import main.Funcionario;
import main.Projeto;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestEmpresa {
    Empresa empresa;
    String nome;

    @Before
    public void setup() {
        nome = "Google";
        empresa = new Empresa(nome);
    }

    @Test
    public void criarEmpresa() {
        assertEquals(nome, empresa.getNome());
    }

    @Test
    public void alterarNomeEmpresa() {
        String novoNome = "Google Company";
        empresa.setNome(novoNome);

        assertEquals(novoNome, empresa.getNome());
    }

    @Test
    public void verificaProjetoPertenceEmpresa() {
        String nomeProjeto = "Google Docs";
        Projeto projeto = new Projeto(nomeProjeto);
        empresa.adicionarProjeto(projeto);

        assertTrue(empresa.projetoPertenceEmpresa(projeto));
    }

    @Test
    public void verificaFuncionarioPertenceEmpresa() {
        String funcionarioTest = "Gmail";
        int maxOcorrencias = 10;
        Funcionario funcionario = new Funcionario(funcionarioTest, maxOcorrencias);
        empresa.adionarFuncionario(funcionario);

        assertTrue(empresa.funcionarioPertenceEmpresa(funcionario));
    }

    @Test
    public void adicionarMaisDeUmProjetoEmpresa() {
        String nome1 = "Google Drive";
        Projeto projeto1 = new Projeto(nome1);

        String nome2 = "Youtube";
        Projeto projeto2 = new Projeto(nome2);

        empresa.adicionarProjeto(projeto1);
        empresa.adicionarProjeto(projeto2);

        assertEquals(2, empresa.getQuatidadeProjeto());
        assertEquals(projeto1, empresa.getProjeto(projeto1));
        assertEquals(projeto2, empresa.getProjeto(projeto2));
    }

    @Test
    public void adicionarMaisDeUmFuncionarioEmpresa() {
        String nome1 = "Gilmar Douglas";
        int maxOcorrencias = 10;
        Funcionario funcionario1 = new Funcionario(nome1, maxOcorrencias);

        String nome2 = "Patricia Vilain";
        Funcionario funcionario2 = new Funcionario(nome2, maxOcorrencias);

        empresa.adicionarFuncionario(funcionario1);
        empresa.adicionarFuncionario(funcionario2);

        assertEquals(2, empresa.getQuantidadeFuncionario());
        assertEquals(funcionario1, empresa.getFuncionario(funcionario1));
        assertEquals(funcionario2, empresa.getFuncionario(funcionario2));
    }
}
