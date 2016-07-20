package model;

import java.util.ArrayList;
import java.util.List;
import view.MainFrame;

public class Model {

	private static ArrayList<Customer> listCustomer = null;

	private static ArrayList<ArrayList<Customer>> result = null;

	/**
	 * ת�Ƹ���һ
	 */
	private static final double PROB1 = 0.8;

	/**
	 * ת�Ƹ��ʶ�
	 */
	private static final double PROB2 = 0.15;
	/**
	 * ת�Ƹ�����
	 */
	private static final double PROB3 = 0.05;
	/**
	 * ����Ȩ��
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
	 * ��������
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
	 * ��ʼ����
	 * 
	 * @param value
	 * @param mainFrame
	 */
	public static ArrayList<ArrayList<Customer>> startCluster(int value, MainFrame mainFrame) {
		result = null;

		mainFrame.addLog("����Ԥ������һ��������Ȩ��");
		Model.normalizedData(listCustomer);

		Kmeans myKemans = new Kmeans(listCustomer, value, mainFrame);

		mainFrame.addLog("��ʼ����");
		result = myKemans.comput();
		return result;

	}

}
