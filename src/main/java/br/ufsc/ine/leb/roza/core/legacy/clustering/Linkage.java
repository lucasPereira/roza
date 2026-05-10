package br.ufsc.ine.leb.roza.core.legacy.clustering;

import java.math.BigDecimal;

import br.ufsc.ine.leb.roza.core.legacy.Cluster;

public interface Linkage {

	BigDecimal evaluate(Cluster first, Cluster second);

}
