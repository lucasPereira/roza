package br.ufsc.ine.leb.roza.clustering.dendrogram;

import java.math.BigDecimal;

import br.ufsc.ine.leb.roza.Cluster;

interface Linkage {

	BigDecimal evaluate(Cluster first, Cluster second);

}
