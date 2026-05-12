package br.ufsc.ine.leb.roza.core.modern.clustering;

public enum LinkageMethod {
	SINGLE {
		@Override
		public ClusterLinkage createLinkage() {
			return new SingleLinkage();
		}

		@Override
		public String displayName() {
			return "Single";
		}
	},
	COMPLETE {
		@Override
		public ClusterLinkage createLinkage() {
			return new CompleteLinkage();
		}

		@Override
		public String displayName() {
			return "Complete";
		}
	},
	AVERAGE {
		@Override
		public ClusterLinkage createLinkage() {
			return new AverageLinkage();
		}

		@Override
		public String displayName() {
			return "Average";
		}
	};

	public abstract ClusterLinkage createLinkage();

	public abstract String displayName();
}
