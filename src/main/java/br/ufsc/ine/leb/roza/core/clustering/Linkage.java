package br.ufsc.ine.leb.roza.core.clustering;

import java.math.BigDecimal;

import br.ufsc.ine.leb.roza.core.Cluster;

public interface Linkage {

	BigDecimal evaluate(Cluster first, Cluster second);

}
