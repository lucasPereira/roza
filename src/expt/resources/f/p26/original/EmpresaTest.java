import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class EmpresaTest {

    private Empresa empresaEmTeste;

    @Before
    public void setup() throws Exception {
        Funcionario funcionario = new Funcionario();
        this.empresaEmTeste = new Empresa();

        empresaEmTeste.addFuncionario(funcionario);
        empresaEmTeste.setProjetos(createProjetosComOcorrencia(this.empresaEmTeste, funcionario));
    }

    @Test
    public void responsavelPorOcorrencia() throws Exception {
        Funcionario novoFuncionario = new Funcionario();
        Ocorrencia ocorrencia = Ocorrencia.Bug(this.empresaEmTeste.novaChaveOcorrencia(), "resumo", novoFuncionario, Prioridade.MEDIA);
        this.empresaEmTeste.getProjetos().get(0).addOcorrencia(ocorrencia);


    }

    @Test
    public void deveRetornarFuncionariosSemOcorrencia() {
        Funcionario funcionario = new Funcionario();
        this.empresaEmTeste.addFuncionario(funcionario);

        ArrayList<Funcionario> funcionarios = this.empresaEmTeste.funcionariosSemOcorrencia();

        assertEquals(1, funcionarios.size());
    }

    @Test
    public void deveSubstituirProjetosCorretamente() {
        ArrayList<Projeto> projetos = new ArrayList<>();
        this.empresaEmTeste.setProjetos(projetos);

        assertEquals(this.empresaEmTeste.getProjetos(), projetos);
    }

    @Test
    public void deveAdicionarProjetoCorretamente() {
        int sizeAntes = this.empresaEmTeste.getProjetos().size();
        this.empresaEmTeste.addProjeto(new Projeto());
        int sizeDepois = this.empresaEmTeste.getProjetos().size();

        assertEquals(sizeDepois, sizeAntes + 1);
    }

    @Test
    public void retornaProjetosComBugCorretamente() {
        ArrayList<Projeto> projetos = this.empresaEmTeste.projetosByTipoOcorrencia(TipoOcorrencia.BUG);

        assertEquals(projetos.size(), 1);
    }

    private ArrayList<Projeto> createProjetosComOcorrencia(Empresa empresa, Funcionario funcionario) throws Exception {
        ArrayList<Projeto> projetos = new ArrayList<>();

        Projeto projetoComBug = new Projeto();
        projetoComBug.addOcorrencia(
                Ocorrencia.Bug(empresa.novaChaveOcorrencia(), "resumo", funcionario, Prioridade.MEDIA)
        );
        projetos.add(projetoComBug);
        funcionario.setProjetos(projetos);
        return projetos;
    }
}