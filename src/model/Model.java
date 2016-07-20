package model;

import java.util.ArrayList;
import java.util.List;
import view.MainFrame;

public class Model {

	private static ArrayList<Customer> listCustomer = null;

	private static ArrayList<ArrayList<Customer>> result = null;

	/**
	 * 转移概率一
	 */
	private static final double PROB1 = 0.8;

	/**
	 * 转移概率二
	 */
	private static final double PROB2 = 0.15;
	/**
	 * 转移概率三
	 */
	private static final double PROB3 = 0.05;
	/**
	 * 季度权重
	 */
	public static final double[] QUARTER_WEIGHT = { 0.8, 0.8, 0.8, 1 };

	private static ArrayList<Customer> normalizedData(List<Customer> listCustomer) {
		ArrayList<Customer> aData = new ArrayList<Customer>();
		int size = QUARTER_WEIGHT.length;
		double[] max = new double[size];
		double[] min = new double[size];
		for (int i = 0; i < size; i++) {
			max[i] = Double.MIN_VALUE;
			min[i] = Double.MAX_VALUE;
		}

		for (Customer customer : listCustomer) {
			double[] qP = customer.getQuarterProfit();
			for (int i = 0; i < size; i++) {
				double value = qP[i];
				if (value > max[i]) {
					max[i] = value;
				}
				if (value < min[i]) {
					min[i] = value;
				}
			}
		}
		for (Customer customer : listCustomer) {
			Customer aCustomer = customer.getCopy();
			double[] qP = aCustomer.getQuarterProfit();
			for (int i = 0; i < size; i++) {
				qP[i] = (qP[i] - min[i]) / (max[i] - min[i]) * QUARTER_WEIGHT[i];
			}
			aData.add(aCustomer);
		}

		return aData;

	}

	/**
	 * 生成数据
	 */
	public static void generateData() {

	}

	public static void generateData(int value) {
		listCustomer = new ArrayList<Customer>();
		Customer customer = null;
		for (int i = 0; i < value; i++) {
			customer = new Customer((i + 1), Model.PROB1, Model.PROB2, Model.PROB3);
			listCustomer.add(customer);
		}
	}

	public static ArrayList<Customer> getData() {
		return listCustomer;
	}

	public static ArrayList<ArrayList<Customer>> getResult() {
		return result;
	}

	/**
	 * 开始聚类
	 * 
	 * @param value
	 * @param mainFrame
	 */
	public static ArrayList<ArrayList<Customer>> startCluster(int value, MainFrame mainFrame) {
		result = null;

		mainFrame.addLog("数据预处理：归一化后设置权重");
		Model.normalizedData(listCustomer);

		Kmeans myKemans = new Kmeans(listCustomer, value, mainFrame);

		mainFrame.addLog("开始聚类");
		result = myKemans.comput();
		return result;

	}

}
