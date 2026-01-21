package tests;

import domain.*;
import org.junit.Before;
import org.junit.Test;


import java.util.ArrayList;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class FuncionarioTest {

    Empresa empresa;
    Projeto projeto;
    @Before
    public void setup(){
        empresa = new Empresa("Empresa Spotify");
        projeto = new Projeto("Projeto Primmer");
        empresa.cadastrarProjeto(projeto);
    }
    @Test
    public void pegarOcorrenciasDoFuncionarioQueNaoTemOcorenciaTest(){
        Funcionario funcionario = new Funcionario("Maria");
        empresa.cadastrarFuncionario(funcionario);

        ArrayList<Ocorrencia> resultado = empresa.pegarOcorrenciasDoFuncionarioResponsavelEmAberto(funcionario);

        assertThat(resultado.size(), equalTo(0));
    }

    @Test
    public void pegarOcorrenciasAtribuidasParaOFuncionarioTest(){
        Funcionario funcionario = new Funcionario("Maria");
        empresa.cadastrarFuncionario(funcionario);
        Ocorrencia ocorrencia = new Ocorrencia("titulo", "descrição", EnumTipoOcorencia.BUG, EnumPrioridadeOcorrencia.BAIXA, funcionario);

        String resultadoCadastro = projeto.cadastrarOcorrencia(ocorrencia);


        ArrayList<Ocorrencia> resultado = empresa.pegarOcorrenciasDoFuncionarioResponsavelEmAberto(funcionario);

        assertThat(resultado.size(), equalTo(1));
        assertThat(resultado.get(0).obterFuncionarioResponsavel(), equalTo(funcionario));
        assertThat(resultado.get(0), equalTo(ocorrencia));
        assertThat(resultadoCadastro, equalTo("Cadastro de ocorrencia com sucesso."));
    }

    @Test
    public void limiteDeOcorrenciaPorFuncionarioTest(){
        empresa.cadastrarProjeto(this.projeto);
        Funcionario funcionario = new Funcionario("Maria");
        empresa.cadastrarFuncionario(funcionario);
        ArrayList<String> mensagemDeResultado = criarOcorrencia(10, funcionario);
        ArrayList<Ocorrencia> resultado = empresa.pegarOcorrenciasDoFuncionarioResponsavelEmAberto(funcionario);

        assertThat(mensagemDeResultado.get(9), equalTo("Cadastro de ocorrencia com sucesso."));
        assertThat(resultado.size(), equalTo(10));

        assertThat(resultado.get(9).obterTitulo(), equalTo("titulo 10"));
        assertThat(resultado.get(9).obterDescricao(), equalTo("descrição 10"));
        assertThat(resultado.get(0).obterSituacaoOcorrencia(), equalTo(EnumSituacaoOcorrencia.ABERTO));
    }

    @Test
    public void acimaDoLimiteDeOcorrenciaPorFuncionarioTest(){
        empresa.cadastrarProjeto(this.projeto);
        Funcionario funcionario = new Funcionario("Maria");
        empresa.cadastrarFuncionario(funcionario);
        ArrayList<String> mensagemDeResultado = criarOcorrencia(11, funcionario);
        ArrayList<Ocorrencia> resultado = empresa.pegarOcorrenciasDoFuncionarioResponsavelEmAberto(funcionario);

        assertThat(mensagemDeResultado.get(9), equalTo("Cadastro de ocorrencia com sucesso."));
        assertThat(mensagemDeResultado.get(10), equalTo("Funcionario já é responsavel por 10 ocorrencias."));
        assertThat(resultado.size(), equalTo(10));
    }

    @Test
    public void pegarNomeDoFuncionarioTest(){
        Funcionario funcionario = new Funcionario("Maria");
        assertThat("Maria", hasToString(funcionario.obterNome()));
    }



    public ArrayList<String> criarOcorrencia(int quantidadeOcorrencias, Funcionario funcionario){
        ArrayList<String> mensagemDeResultado = new ArrayList<>();
        for (int i = 1; i<=quantidadeOcorrencias; i++){
            Ocorrencia ocorrencia = new Ocorrencia("titulo " + i, "descrição " + i,  EnumTipoOcorencia.BUG, EnumPrioridadeOcorrencia.BAIXA, funcionario);
            mensagemDeResultado.add(projeto.cadastrarOcorrencia(ocorrencia));
        }
        return mensagemDeResultado;
    }
}
