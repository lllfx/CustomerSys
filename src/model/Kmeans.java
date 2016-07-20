package model;

import java.util.ArrayList;
import java.util.HashSet;

import view.MainFrame;

public class Kmeans {

	/**
	 * 所有数据列表
	 */
	private ArrayList<Customer> customers;

	/**
	 * 分类数
	 */
	private int clusterNum;

	private ArrayList<ArrayList<Customer>> lastResult;

	private ArrayList<ArrayList<Customer>> nowResult;

	private MainFrame mF;

	/**
	 * 初始化列表
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
		// 初始化
		initNowResult();
		// 中心点
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
					 * 计算距离
					 */
					double dist = distance(clusterCenter[j], customer);
					dists[j] = dist;
				}
				int dist_index = computOrder(dists);
				nowResult.get(dist_index).add(customer);
			}

			// 判断是否变化了
			centerchange = isChangeCluster(lastResult, nowResult);
			if (centerchange) {
				// 计算中心
				updateClusterCenter(nowResult, clusterCenter);

				// 更新
				lastResult = nowResult;
				initNowResult();
			}
			count++;
			mF.addLog("正在进行第" + count + "次迭代");
		}
		mF.addLog("聚类完成，总共进行了" + count + "次迭代");
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
	 * 簇分类结果是否变化了
	 *
	 * @param lR 上一下分类结果
	 * @param nR 这一次分类结果
	 * @return
	 */
	private boolean isChangeCluster(ArrayList<ArrayList<Customer>> lR, ArrayList<ArrayList<Customer>> nR) {
		if (lR == null || lR.isEmpty()) {
			return true;
		}
		// 看
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
		// 数量没变

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
	 * 获得 位置
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
		System.out.println("错误");
		return -1;
	}

	/**
	 * 初始化聚类中心 随机选取
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
	 * 初始化列表
	 */
	private void initNowResult() {
		nowResult = new ArrayList<ArrayList<Customer>>(clusterNum);
		for (int i = 0; i < clusterNum; i++) {
			nowResult.add(new ArrayList<Customer>());
		}
	}

	/**
	 * 得到最短距离，并返回最短距离索引
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
	 * 计算距离（相似性） 采用欧几里得算法
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
