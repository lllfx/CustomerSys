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
 * 饼状图绘图工具
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
		JFreeChart chart = ChartFactory.createPieChart3D(null, // 主标题的名称
				dataset, // 图标显示的数据集合
				true, // 是否显示子标题
				true, // 是否生成提示的标签
				true); // 是否生成URL链接

		// 处理图形上的乱码
		// 处理主标题的乱码
		//chart.getTitle().setFont(new Font("宋体", Font.BOLD, 18));
		// 处理子标题乱码
		chart.getLegend().setItemFont(new Font("宋体", Font.BOLD, 12));
		// 获取图表区域对象
		PiePlot3D categoryPlot = (PiePlot3D) chart.getPlot();
		// 处理图像上的乱码
		categoryPlot.setLabelFont(new Font("宋体", Font.BOLD, 12));
		// 设置图形的生成格式为（上海 2 （10%））
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
		// 添加数据
		dataset.setValue("张三", 40);
		dataset.setValue("李四", 32);
		dataset.setValue("王五", 28);
		JFreeChart chart = ChartFactory.createPieChart3D("比重统计报表（所属单位）", // 主标题的名称
				dataset, // 图标显示的数据集合
				true, // 是否显示子标题
				true, // 是否生成提示的标签
				true); // 是否生成URL链接
		// 处理图形上的乱码
		// 处理主标题的乱码
		chart.getTitle().setFont(new Font("宋体", Font.BOLD, 18));
		// 处理子标题乱码
		chart.getLegend().setItemFont(new Font("宋体", Font.BOLD, 15));
		// 获取图表区域对象
		PiePlot3D categoryPlot = (PiePlot3D) chart.getPlot();
		// 处理图像上的乱码
		categoryPlot.setLabelFont(new Font("宋体", Font.BOLD, 15));
		// 设置图形的生成格式为（上海 2 （10%））
		String format = "{0} {1} ({2})";
		categoryPlot.setLabelGenerator(new StandardPieSectionLabelGenerator(format));

		// 在D盘目录下生成图片
		File file = new File("chart2.jpg");
		try {
			ChartUtilities.saveChartAsJPEG(file, chart, 800, 600);
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 使用ChartFrame对象显示图像
		ChartFrame frame = new ChartFrame("xyz", chart);
		frame.setVisible(true);
		frame.pack();
	}

}