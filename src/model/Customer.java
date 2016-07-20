package model;

import java.util.Arrays;

public class Customer {
	/**
	 * 序号
	 */
	private int index;
	/**
	 * 数据数值属性个数
	 */
	private static final int SIZE = 4;
	/**
	 * 季度利润
	 */
	private double[] quarterProfit = new double[SIZE];
	/**
	 * 
	 */
	private int[] level = new int[SIZE];

	public Customer() {

	}

	/**
	 * 概率
	 * 
	 * @param prob1
	 * @param prob2
	 * @param prob3
	 */
	public Customer(int index, double prob1, double prob2, double prob3) {
		this.index = index;
		// 生成单个数据
		level[0] = generateData();
		quarterProfit[0] = levelValue(level[0], -1, -1);

		// 获得等级
		level[1] = generateData(level[0], prob1, prob2, prob3);
		quarterProfit[1] = levelValue(level[1], level[0], quarterProfit[0]);

		level[2] = generateData(level[1], prob1, prob2, prob3);
		quarterProfit[2] = levelValue(level[2], level[1], quarterProfit[1]);

		level[3] = generateData(level[2], prob1, prob2, prob3);
		quarterProfit[3] = levelValue(level[3], level[2], quarterProfit[2]);

	}

	/**
	 * 生成第一季度的数据 按照20%的高价值看客户 
	 * 20%的中价值客户 
	 * 60%的低价值客户
	 * 原则生成 定义
	 * 1-60高低价值客户 
	 * 60-80位中价值客户
	 * 80-100为高价值客户
	 */
	private int generateData() {
		int level = 0;
		double prob = Math.random();
		if (prob < 0.6) {
			level = 1;
		} else if (prob < 0.8) {
			level = 2;
		} else {
			level = 3;
		}
		return level;
	}

	private double levelValue(int nowLevel, int lastLevel, double lastProfit) {
		if (lastLevel < 1) {
			if (nowLevel == 1) {
				return Math.random() * 50 + 10;
			} else if (nowLevel == 2) {
				return Math.random() * (80 - 60) + 60;
			} else {
				return Math.random() * (100 - 80) + 80;
			}
		} else {
			if (nowLevel == lastLevel && nowLevel == 1) {
				// 1 1
				// 变化不能大于20
				double nowProfit = Math.random() * 50 + 10;
				while ((nowProfit - lastProfit) > 20.0) {
					nowProfit = Math.random() * 50 + 10;
				}
				return nowProfit;

			} else {
				if (nowLevel == 1) {
					if (lastLevel == 2) {
						// 1 2

					} else {
						// 1 3

					}
				} else if (nowLevel == 2) {

				} else {

				}

				if (nowLevel == 2) {
					return Math.random() * (80 - 60) + 60;
				} else {
					return Math.random() * (100 - 80) + 80;
				}
			}

		}

	}


	/**
	 * 一个客户
	 * 
	 * @param quarter1Profit2
	 * @param maintainProbability
	 * @return
	 */
	private int generateData(int lastLevel, double prob1, double prob2, double prob3) {
		int nowLevel = 0;
		double prob = Math.random();
		if (prob < prob1) {
			// 继续保持
			nowLevel = lastLevel;
		} else {

			// 不保持
			// 转为其它两种
			prob = Math.random() * (prob2 + prob3);
			if (lastLevel == 1) {
				if (prob < prob2) {
					nowLevel = 2;
				} else {
					nowLevel = 3;
				}

			} else if (lastLevel == 3) {
				if (prob < prob2) {
					nowLevel = 2;
				} else {
					nowLevel = 1;
				}
			} else {
				prob = Math.random();
				if (prob < 0.5) {
					nowLevel = 1;
				} else {
					nowLevel = 3;
				}
			}
		}
		return nowLevel;
	}

	public int getIndex() {
		return index;
	}

	public double[] getQuarterProfit() {
		return quarterProfit;
	}

	public int[] getLevel() {
		return level;
	}

	public void setLevel(int[] level) {
		this.level = level;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	@Override
	public String toString() {
		return index + "," + quarterProfit[0] + "\t" + quarterProfit[1] + "\t" + quarterProfit[2] + "\t"
				+ quarterProfit[2];

	}

	public void setQuarterProfit(double[] quarterProfit) {
		this.quarterProfit = quarterProfit;

	}

	public Customer getCopy() {
		Customer customer = new Customer();
		int index = this.getIndex();
		int[] level = new int[SIZE];
		double[] qP = new double[SIZE];

		for (int i = 0; i < SIZE; i++) {
			level[i] = this.getLevel()[i];
			qP[i] = this.getQuarterProfit()[i];
		}
		customer.setIndex(index);
		customer.setLevel(level);
		customer.setQuarterProfit(qP);

		return customer;

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + index;
		result = prime * result + Arrays.hashCode(level);
		result = prime * result + Arrays.hashCode(quarterProfit);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Customer other = (Customer) obj;
		if (index != other.index)
			return false;
		if (!Arrays.equals(level, other.level))
			return false;
		if (!Arrays.equals(quarterProfit, other.quarterProfit))
			return false;
		return true;
	}

}
