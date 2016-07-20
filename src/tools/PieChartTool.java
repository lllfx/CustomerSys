package tools;

import java.awt.Font;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;
/**
 * ��״ͼ��ͼ����
 * @author Administrator
 *
 */
public class PieChartTool {

	public static void draw(ArrayList<String> categorys, ArrayList<Double> nums, String title, String fileName) {
		DefaultPieDataset dataset = new DefaultPieDataset();
		int size = categorys.size();
		for (int i = 0; i < size; i++) {
			dataset.setValue(categorys.get(i), nums.get(i));
		}
		JFreeChart chart = ChartFactory.createPieChart3D(null, // �����������
				dataset, // ͼ����ʾ�����ݼ���
				true, // �Ƿ���ʾ�ӱ���
				true, // �Ƿ�������ʾ�ı�ǩ
				true); // �Ƿ�����URL����

		// ����ͼ���ϵ�����
		// ���������������
		//chart.getTitle().setFont(new Font("����", Font.BOLD, 18));
		// �����ӱ�������
		chart.getLegend().setItemFont(new Font("����", Font.BOLD, 12));
		// ��ȡͼ���������
		PiePlot3D categoryPlot = (PiePlot3D) chart.getPlot();
		// ����ͼ���ϵ�����
		categoryPlot.setLabelFont(new Font("����", Font.BOLD, 12));
		// ����ͼ�ε����ɸ�ʽΪ���Ϻ� 2 ��10%����
	//	String format = "{0} {1} ({2})";
		//categoryPlot.setLabelGenerator(new StandardPieSectionLabelGenerator(format));

		File file = new File(fileName);
		try {
			ChartUtilities.saveChartAsJPEG(file, chart, 250, 200);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		DefaultPieDataset dataset = new DefaultPieDataset();
		// �������
		dataset.setValue("����", 40);
		dataset.setValue("����", 32);
		dataset.setValue("����", 28);
		JFreeChart chart = ChartFactory.createPieChart3D("����ͳ�Ʊ���������λ��", // �����������
				dataset, // ͼ����ʾ�����ݼ���
				true, // �Ƿ���ʾ�ӱ���
				true, // �Ƿ�������ʾ�ı�ǩ
				true); // �Ƿ�����URL����
		// ����ͼ���ϵ�����
		// ���������������
		chart.getTitle().setFont(new Font("����", Font.BOLD, 18));
		// �����ӱ�������
		chart.getLegend().setItemFont(new Font("����", Font.BOLD, 15));
		// ��ȡͼ���������
		PiePlot3D categoryPlot = (PiePlot3D) chart.getPlot();
		// ����ͼ���ϵ�����
		categoryPlot.setLabelFont(new Font("����", Font.BOLD, 15));
		// ����ͼ�ε����ɸ�ʽΪ���Ϻ� 2 ��10%����
		String format = "{0} {1} ({2})";
		categoryPlot.setLabelGenerator(new StandardPieSectionLabelGenerator(format));

		// ��D��Ŀ¼������ͼƬ
		File file = new File("chart2.jpg");
		try {
			ChartUtilities.saveChartAsJPEG(file, chart, 800, 600);
		} catch (IOException e) {
			e.printStackTrace();
		}
		// ʹ��ChartFrame������ʾͼ��
		ChartFrame frame = new ChartFrame("xyz", chart);
		frame.setVisible(true);
		frame.pack();
	}

}