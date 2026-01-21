
public class ClasseAuxiliar {

	public static Funcionario preencheOcorrencias(int quant) throws ExceptionInvalidKey, ExceptionTipoNull, ExceptionPrioridadeNull {
		Funcionario func = new Funcionario("Joao");
		for(int i = 0; i < quant; i++) {
			Ocorrencia oc = new Ocorrencia(i, "resumo", Tipo.BUG, Prioridade.BAIXA);
			func.adicionaOcorrencia(oc);
		}
		return func;
	}

}
