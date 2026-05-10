package tests;

import domain.Empresa;
import domain.Funcionario;
import domain.Projeto;
import org.junit.Before;
import org.junit.Test;


import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


public class EmpresaTest {


    Empresa empresa;
    @Before
    public void setup (){
        empresa = new Empresa("Empresa Spotify");
    }

    @Test
    public void buscarProjetoTest(){
        empresa.cadastrarProjeto(new Projeto("Projeto Primmer"));
        Projeto projeto = empresa.buscarProjeto("Projeto Primmer");

        assertThat(projeto.obterNome(), equalTo("Projeto Primmer"));
    }
    @Test
    public void cadastrarProjetoTest(){
        empresa.cadastrarProjeto(new Projeto("Projeto Primmer"));

        assertThat(empresa.getListaDeProjetos().get(0).obterNome(), equalTo("Projeto Primmer"));
        assertThat(empresa.getListaDeProjetos().size(), equalTo(1));
    }

    @Test
    public void cadastrarFuncionarioTest(){
        empresa.cadastrarFuncionario(new Funcionario("João"));

        assertThat(empresa.getListaDeFuncionarios().get(0).obterNome(), equalTo("João"));
    }

    @Test
    public void pegarNomeDaEmpresaTest(){
        String resultado = empresa.obterNomeDaEmpresa();

        assertThat("Empresa Spotify", equalTo(resultado));
    }

    @Test
    public void empresaComVariosFuncionariosTest(){
        empresa.cadastrarFuncionario(new Funcionario("Ana"));
        empresa.cadastrarFuncionario(new Funcionario("Maria"));

        assertThat(2, equalTo(empresa.getListaDeFuncionarios().size()));
        assertThat(pegarNomeDosFuncionarios(), hasItems("Ana", "Maria"));

    }

    @Test
    public void empresaSemFuncionarioTest(){
        assertThat(empresa.getListaDeFuncionarios().size(), equalTo(0));

    }

    @Test
    public void empresaComVariosProjetosTest(){
        empresa.cadastrarProjeto(new Projeto("Projeto Primmer"));
        empresa.cadastrarProjeto(new Projeto("Projeto Plus"));

        assertThat(2, equalTo(empresa.getListaDeProjetos().size()));
        assertThat(pegarNomeDosProjetos(), hasItems("Projeto Primmer", "Projeto Plus"));
    }

    @Test
    public void empresaSemProjetoESemFuncionarioTest(){

        assertThat(empresa.getListaDeProjetos().size(), equalTo(0));
        assertThat(empresa.getListaDeFuncionarios().size(), equalTo(0));
    }

    @Test
    public void verificarAQuantidadeDeFuncionarioAposInserirProjetoTest(){
        empresa.cadastrarFuncionario(new Funcionario("Maria"));
        empresa.cadastrarProjeto(new Projeto("Projeto Primmer"));

        assertThat(empresa.getListaDeFuncionarios().size(), equalTo(1));
        assertThat(empresa.getListaDeFuncionarios().get(0).obterNome(), equalTo("Maria"));
        assertThat(empresa.getListaDeProjetos().get(0).obterNome(), equalTo("Projeto Primmer"));
        assertThat(empresa.getListaDeProjetos().size(), equalTo(1));
    }

    @Test
    public void pegarNomeFuncionarioTest(){
        empresa.cadastrarFuncionario(new Funcionario("João"));
        assertThat(empresa.getListaDeFuncionarios().get(0).obterNome(), hasToString("João"));
    }

    @Test
    public void pegarNomeProjetoTest(){
        empresa.cadastrarProjeto(new Projeto("Projeto Primmer"));
        assertThat(empresa.getListaDeProjetos().get(0).obterNome(), hasToString("Projeto Primmer"));
    }


    public  ArrayList<String> pegarNomeDosFuncionarios(){
        ArrayList<String> nomesDosFuncionarios = new ArrayList<>();

        for (int i=0; i<empresa.getListaDeFuncionarios().size(); i++ ){
            nomesDosFuncionarios.add(empresa.getListaDeFuncionarios().get(i).obterNome());
        }
        return nomesDosFuncionarios;
    }

    public  ArrayList<String> pegarNomeDosProjetos() {
        ArrayList<String> nomesDosProjetos = new ArrayList<>();

        for (int i = 0; i < empresa.getListaDeProjetos().size(); i++) {
            nomesDosProjetos.add(empresa.getListaDeProjetos().get(i).obterNome());
        }

        return nomesDosProjetos;
    }
}
