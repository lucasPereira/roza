import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FuncionarioTest {

    private Funcionario funcionario;

    @Before
    public void setup() {
        this.funcionario = new Funcionario();
    }

    @Test(expected = Exception.class)
    public void deveRespeitarMaximoDeOcorrencias() throws Exception {
        for (int i = 0; i < 11; i++) {
            this.funcionario.addOcorrencia(new Ocorrencia());
        }
    }

    @Test
    public void deveAceitarAceitar10_Ocorrencias() throws Exception {
        for (int i = 0; i < 10; i++) {
            this.funcionario.addOcorrencia(new Ocorrencia());
        }

        assertEquals(10, this.funcionario.getOcorrencias().size());
    }
}