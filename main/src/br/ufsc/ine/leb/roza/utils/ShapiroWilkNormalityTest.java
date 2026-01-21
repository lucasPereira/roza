package br.ufsc.ine.leb.roza.utils;

import java.util.Arrays;
import java.util.List;

public class ShapiroWilkNormalityTest {
	public NormalityTestResult test(List<Integer> sample) {
		return test(sample, 0.05);
	}

	public NormalityTestResult test(List<Integer> sample, double alpha) {
		double[] values = new double[sample.size()];
		for (int i = 0; i < sample.size(); i++) {
			values[i] = sample.get(i);
		}
		return test(values, alpha);
	}

	public NormalityTestResult test(double[] sample) {
		return test(sample, 0.05);
	}

	public NormalityTestResult test(double[] sample, double alpha) {
		if (sample == null) {
			throw new IllegalArgumentException("Sample must not be null");
		}
		if (alpha <= 0 || alpha >= 1) {
			throw new IllegalArgumentException("Alpha must be between 0 and 1");
		}
		int n = sample.length;
		if (n < 3) {
			return new NormalityTestResult(1.0, 1.0, alpha, true);
		}

		double[] x = Arrays.copyOf(sample, n);
		Arrays.sort(x);

		double mean = 0.0;
		for (double v : x) {
			mean += v;
		}
		mean /= n;

		double ss = 0.0;
		for (double v : x) {
			double d = v - mean;
			ss += d * d;
		}
		if (ss == 0.0) {
			return new NormalityTestResult(1.0, 1.0, alpha, true);
		}

		int m = n / 2;
		double[] a = shapiroCoefficients(n);

		double b = 0.0;
		for (int i = 0; i < m; i++) {
			b += a[i] * (x[n - 1 - i] - x[i]);
		}

		double w = (b * b) / ss;
		if (w > 1.0) {
			w = 1.0;
		}
		if (w < 0.0) {
			w = 0.0;
		}

		double pValue = shapiroPValueApprox(w, n);
		boolean isNormal = pValue >= alpha;
		return new NormalityTestResult(w, pValue, alpha, isNormal);
	}

	private double[] shapiroCoefficients(int n) {
		int m = n / 2;
		double[] a = new double[m];

		// Expected values of normal order statistics (Blom approximation)
		double[] expected = new double[m];
		double sumsq = 0.0;
		for (int i = 0; i < m; i++) {
			int k = i + 1;
			double p = (k - 0.375) / (n + 0.25);
			double z = inverseNormalCDF(p);
			expected[i] = z;
			sumsq += z * z;
		}

		double denom = Math.sqrt(sumsq);
		for (int i = 0; i < m; i++) {
			a[i] = expected[i] / denom;
		}

		return a;
	}

	private double shapiroPValueApprox(double w, int n) {
		// Royston approximation (1995). Valid for n in [3, 5000] in typical implementations.
		if (w >= 1.0) {
			return 1.0;
		}
		if (w <= 0.0) {
			return 0.0;
		}

		double y = Math.log(1.0 - w);
		double z;

		if (n <= 11) {
			double gamma = -2.273 + 0.459 * n;
			if (y >= gamma) {
				return 1e-16;
			}
			double y2 = -Math.log(gamma - y);
			double mu = 0.5440 - 0.39978 * n + 0.025054 * n * n - 0.0006714 * n * n * n;
			double sigma = Math.exp(1.3822 - 0.77857 * n + 0.062767 * n * n - 0.0020322 * n * n * n);
			z = (y2 - mu) / sigma;
		} else {
			double lnn = Math.log(n);
			double mu = -1.5861 - 0.31082 * lnn - 0.083751 * lnn * lnn + 0.0038915 * lnn * lnn * lnn;
			double sigma = Math.exp(-0.4803 - 0.082676 * lnn + 0.0030302 * lnn * lnn);
			z = (y - mu) / sigma;
		}

		double p = 1.0 - normalCDF(z);
		if (p < 0.0) {
			p = 0.0;
		}
		if (p > 1.0) {
			p = 1.0;
		}
		return p;
	}

	private double normalCDF(double z) {
		if (z < -8.0) return 0.0;
		if (z > 8.0) return 1.0;
		double sum = 0.0;
		double term = z;
		for (int exponent = 3; exponent < 100; exponent += 2) {
			sum += term;
			term *= z * z / exponent;
		}
		return 0.5 + sum * Math.exp(-z * z / 2) / Math.sqrt(2 * Math.PI);
	}

	// Acklam's inverse normal CDF approximation
	private double inverseNormalCDF(double p) {
		if (p <= 0.0 || p >= 1.0) {
			throw new IllegalArgumentException("p must be in (0, 1)");
		}

		double[] a = { -3.969683028665376e+01, 2.209460984245205e+02, -2.759285104469687e+02,
			1.383577518672690e+02, -3.066479806614716e+01, 2.506628277459239e+00 };
		double[] b = { -5.447609879822406e+01, 1.615858368580409e+02, -1.556989798598866e+02,
			6.680131188771972e+01, -1.328068155288572e+01 };
		double[] c = { -7.784894002430293e-03, -3.223964580411365e-01, -2.400758277161838e+00,
			-2.549732539343734e+00, 4.374664141464968e+00, 2.938163982698783e+00 };
		double[] d = { 7.784695709041462e-03, 3.224671290700398e-01, 2.445134137142996e+00,
			3.754408661907416e+00 };

		double plow = 0.02425;
		double phigh = 1 - plow;
		double q;
		double r;

		if (p < plow) {
			q = Math.sqrt(-2 * Math.log(p));
			return (((((c[0] * q + c[1]) * q + c[2]) * q + c[3]) * q + c[4]) * q + c[5]) /
				((((d[0] * q + d[1]) * q + d[2]) * q + d[3]) * q + 1);
		}

		if (phigh < p) {
			q = Math.sqrt(-2 * Math.log(1 - p));
			return -(((((c[0] * q + c[1]) * q + c[2]) * q + c[3]) * q + c[4]) * q + c[5]) /
				((((d[0] * q + d[1]) * q + d[2]) * q + d[3]) * q + 1);
		}

		q = p - 0.5;
		r = q * q;
		return (((((a[0] * r + a[1]) * r + a[2]) * r + a[3]) * r + a[4]) * r + a[5]) * q /
			(((((b[0] * r + b[1]) * r + b[2]) * r + b[3]) * r + b[4]) * r + 1);
	}

	public static class NormalityTestResult {
		private final double testStatistic;
		private final double pValue;
		private final double significanceLevel;
		private final boolean isNormal;

		NormalityTestResult(double testStatistic, double pValue, double significanceLevel, boolean isNormal) {
			this.testStatistic = testStatistic;
			this.pValue = pValue;
			this.significanceLevel = significanceLevel;
			this.isNormal = isNormal;
		}

		public double getTestStatistic() {
			return testStatistic;
		}

		public double getPValue() {
			return pValue;
		}

		public double getSignificanceLevel() {
			return significanceLevel;
		}

		public boolean isNormal() {
			return isNormal;
		}

		@Override
		public String toString() {
			return String.format("W=%.4f, p-value=%.10f, α=%.2f, normal=%s",
				testStatistic, pValue, significanceLevel, isNormal ? "Yes" : "No");
		}
	}
}
