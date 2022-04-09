package br.ufsc.ine.leb.roza.clustering;

import java.math.BigDecimal;

public interface Linkage {

	BigDecimal evaluate(Cluster first, Cluster second);

}
