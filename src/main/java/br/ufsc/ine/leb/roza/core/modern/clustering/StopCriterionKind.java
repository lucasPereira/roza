package br.ufsc.ine.leb.roza.core.modern.clustering;

public enum StopCriterionKind {
	MINIMUM_SIMILARITY {
		@Override
		public StopCriterion create(String value) {
			return new MinimumSimilarityStopCriterion(Double.parseDouble(value));
		}

		@Override
		public String defaultValue() {
			return String.valueOf(MinimumSimilarityStopCriterion.DEFAULT);
		}

		@Override
		public String displayName() {
			return "Minimum similarity";
		}
	},
	MAX_TESTS_PER_CLUSTER {
		@Override
		public StopCriterion create(String value) {
			return new MaxTestsPerClusterStopCriterion(Integer.parseInt(value));
		}

		@Override
		public String defaultValue() {
			return String.valueOf(MaxTestsPerClusterStopCriterion.DEFAULT);
		}

		@Override
		public String displayName() {
			return "Maximum tests per cluster";
		}
	},
	MINIMUM_TESTS_PER_CLUSTER {
		@Override
		public StopCriterion create(String value) {
			return new MinimumTestsPerClusterStopCriterion(Integer.parseInt(value));
		}

		@Override
		public String defaultValue() {
			return String.valueOf(MinimumTestsPerClusterStopCriterion.DEFAULT);
		}

		@Override
		public String displayName() {
			return "Minimum tests per cluster";
		}
	},
	MAX_MERGE_LEVEL {
		@Override
		public StopCriterion create(String value) {
			return new MaxLevelStopCriterion(Integer.parseInt(value));
		}

		@Override
		public String defaultValue() {
			return String.valueOf(MaxLevelStopCriterion.DEFAULT);
		}

		@Override
		public String displayName() {
			return "Maximum merge level";
		}
	},
	TARGET_CLUSTER_COUNT {
		@Override
		public StopCriterion create(String value) {
			return new TargetClusterCountStopCriterion(Integer.parseInt(value));
		}

		@Override
		public String defaultValue() {
			return String.valueOf(TargetClusterCountStopCriterion.DEFAULT);
		}

		@Override
		public String displayName() {
			return "Target cluster count";
		}
	},
	MINIMUM_SHARED_PREFIX {
		@Override
		public StopCriterion create(String value) {
			return new MinimumSharedPrefixStopCriterion(Integer.parseInt(value));
		}

		@Override
		public String defaultValue() {
			return String.valueOf(MinimumSharedPrefixStopCriterion.DEFAULT);
		}

		@Override
		public String displayName() {
			return "Minimum shared prefix";
		}
	};

	public abstract StopCriterion create(String value);

	public abstract String defaultValue();

	public abstract String displayName();
}
