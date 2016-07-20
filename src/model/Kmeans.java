package model;

import java.util.ArrayList;
import java.util.HashSet;

import view.MainFrame;

public class Kmeans {

	/**
	 * ���������б�
	 */
	private ArrayList<Customer> customers;

	/**
	 * ������
	 */
	private int clusterNum;

	private ArrayList<ArrayList<Customer>> lastResult;

	private ArrayList<ArrayList<Customer>> nowResult;

	private MainFrame mF;

	/**
	 * ��ʼ���б�
	 * 
	 * @param list
	 * @param mainFrame
	 * @param k
	 */
	public Kmeans(ArrayList<Customer> list, int cN, MainFrame mainFrame) {
		customers = list;
		clusterNum = cN;
		mF = mainFrame;
	}

	public ArrayList<ArrayList<Customer>> comput() {
		// ��ʼ��
		initNowResult();
		// ���ĵ�
		Customer[] clusterCenter = new Customer[clusterNum];
		initClusterCenter(clusterCenter);

		boolean centerchange = true;
		int count = 0;
		while (centerchange) {

			for (int i = 0; i < customers.size(); i++) {
				Customer customer = customers.get(i);
				double[] dists = new double[clusterNum];
				for (int j = 0; j < clusterNum; j++) {
					/**
					 * �������
					 */
					double dist = distance(clusterCenter[j], customer);
					dists[j] = dist;
				}
				int dist_index = computOrder(dists);
				nowResult.get(dist_index).add(customer);
			}

			// �ж��Ƿ�仯��
			centerchange = isChangeCluster(lastResult, nowResult);
			if (centerchange) {
				// ��������
				updateClusterCenter(nowResult, clusterCenter);

				// ����
				lastResult = nowResult;
				initNowResult();
			}
			count++;
			mF.addLog("���ڽ��е�" + count + "�ε���");
		}
		mF.addLog("������ɣ��ܹ�������" + count + "�ε���");
		return nowResult;
	}

	private void updateClusterCenter(ArrayList<ArrayList<Customer>> list, Customer[] clusterCenter) {
		for (int i = 0; i < clusterNum; i++) {
			clusterCenter[i] = calClusterCenter(list.get(i));
		}

	}

	private Customer calClusterCenter(ArrayList<Customer> arrayList) {
		Customer customer = new Customer();
		int size = Model.QUARTER_WEIGHT.length;
		double[] averageValue = new double[size];
		for (int i = 0; i < size; i++) {
			averageValue[i] = 0.0;
		}
		for (Customer c : arrayList) {
			double[] qP = c.getQuarterProfit();
			for (int i = 0; i < size; i++) {
				averageValue[i] += qP[i];
			}
		}
		for (int i = 0; i < size; i++) {
			averageValue[i] = averageValue[i] / arrayList.size();
		}
		customer.setQuarterProfit(averageValue);
		return customer;
	}

	/**
	 * �ط������Ƿ�仯��
	 *
	 * @param lR ��һ�·�����
	 * @param nR ��һ�η�����
	 * @return
	 */
	private boolean isChangeCluster(ArrayList<ArrayList<Customer>> lR, ArrayList<ArrayList<Customer>> nR) {
		if (lR == null || lR.isEmpty()) {
			return true;
		}
		// ��
		HashSet<Integer> set1 = new HashSet<Integer>();
		for (int i = 0; i < clusterNum; i++) {
			set1.add(lR.get(i).size());
		}
		HashSet<Integer> set2 = new HashSet<Integer>();
		for (int i = 0; i < clusterNum; i++) {
			set2.add(nR.get(i).size());
		}
		if (!set1.equals(set2)) {
			return true;
		}
		// ����û��

		for (int i = 0; i < clusterNum; i++) {
			int lRIndex = -1;
			ArrayList<Customer> nRl = nR.get(i);
			for (Customer c : nRl) {
				int index = getIndex(c, lR);
				if (lRIndex == -1) {
					lRIndex = index;
				} else {
					if (lRIndex != index) {
						return true;
					}
				}
			}
		}
		return false;

	}

	/**
	 * ��� λ��
	 * @param c
	 * @param list
	 * @return
	 */
	private int getIndex(Customer c, ArrayList<ArrayList<Customer>> list) {
		for (int i = 0; i < clusterNum; i++) {
			if (list.get(i).contains(c)) {
				return i;
			}
		}
		System.out.println("����");
		return -1;
	}

	/**
	 * ��ʼ���������� ���ѡȡ
	 * 
	 * @param clusterCenter
	 */
	private void initClusterCenter(Customer[] clusterCenter) {
		HashSet<Integer> set = new HashSet<Integer>();
		for (int i = 0; i < clusterNum; i++) {
			int index = (int) (Math.random() * customers.size());
			while (set.contains(index)) {
				index = (int) (Math.random() * customers.size());
			}
			//
			clusterCenter[i] = customers.get(index);
			set.add(index);
		}

	}

	/**
	 * ��ʼ���б�
	 */
	private void initNowResult() {
		nowResult = new ArrayList<ArrayList<Customer>>(clusterNum);
		for (int i = 0; i < clusterNum; i++) {
			nowResult.add(new ArrayList<Customer>());
		}
	}

	/**
	 * �õ���̾��룬��������̾�������
	 * 
	 * @param dists
	 * @return
	 */
	public int computOrder(double[] dists) {
		int index = 0;
		double min = dists[0];
		for (int i = 1, len = dists.length; i < len; i++) {
			double value = dists[i];
			if (value < min) {
				min = value;
				index = i;
			}
		}
		return index;
	}

	/**
	 * ������루�����ԣ� ����ŷ������㷨
	 * 
	 * @param p0
	 * @param p1
	 * @return
	 */
	public double distance(Customer c1, Customer c2) {
		double dis = 0.0;
		double qP1[] = c1.getQuarterProfit();
		double qP2[] = c2.getQuarterProfit();
		int size = Model.QUARTER_WEIGHT.length;
		for (int i = 0; i < size; i++) {
			dis += Math.pow(qP1[i] - qP2[i], 2);
		}
		return Math.sqrt(dis);

	}

}
