package br.ufsc.ine.leb.roza.core.modern.clustering;

public enum MergeTieBreakerKind {
	LARGEST_MERGED_CLUSTER {
		@Override
		public MergeTieBreaker createTieBreaker() {
			return new LargestMergedClusterTieBreaker();
		}

		@Override
		public String displayName() {
			return "Largest merged cluster";
		}
	},
	SMALLEST_MERGED_CLUSTER {
		@Override
		public MergeTieBreaker createTieBreaker() {
			return new SmallestMergedClusterTieBreaker();
		}

		@Override
		public String displayName() {
			return "Smallest merged cluster";
		}
	},
	STABLE_TEST_CASE_ORDER {
		@Override
		public MergeTieBreaker createTieBreaker() {
			return new StableTestCaseOrderMergeTieBreaker();
		}

		@Override
		public String displayName() {
			return "Stable test order";
		}
	};

	public abstract MergeTieBreaker createTieBreaker();

	public abstract String displayName();
}
