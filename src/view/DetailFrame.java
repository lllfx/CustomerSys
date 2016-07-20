package view;

import java.awt.BorderLayout;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JPanel;
import model.Customer;
import tools.LineChartTool;
import tools.Tools;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ItemListener;
import java.io.File;
import java.awt.event.ItemEvent;

/**
 * “详情”界面
 * 
 * @author Administrator
 *
 */
public class DetailFrame extends JDialog {

	private static final long serialVersionUID = 3262694936494190833L;
	private ArrayList<ArrayList<Customer>> mResult;
	String[] columnNames = { "序号", "第一季度贡献值", "第二季度贡献值", "第三季度贡献值", "第四季度贡献值" }; // 列名
	private DefaultTableModel tableModel; // 表格模型对象
	private ArrayList<Customer> mData;
	private JComboBox<String> comboBox;
	private JTable table;
	private JLabel lblNum;

	private JLabel lblPicture;

	public void setmResult(ArrayList<ArrayList<Customer>> result) {
		this.mResult = new ArrayList<ArrayList<Customer>>();
		for (ArrayList<Customer> list : result) {
			ArrayList<Customer> l = new ArrayList<Customer>();
			for (Customer c : list) {
				l.add(c.getCopy());
			}
			mResult.add(l);
		}
	}

	public void setmData(ArrayList<Customer> data) {
		this.mData = new ArrayList<Customer>();
		for (Customer c : data) {
			this.mData.add(c.getCopy());
		}
	}

	/**
	 * 初始化数据
	 * 记得开启线程防止界面卡死
	 */
	public void initData() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				int size = mResult.size();
				for (int i = 0; i < size; i++) {
					comboBox.addItem("第" + (i + 1) + "类");
				}
				comboBox.setSelectedIndex(0);
				ArrayList<Customer> data = getData(0);
				display(data);
				drawData(data);
			}

		}).start();

	}

	/**
	 * 绘图并显示
	 * @param data
	 */
	protected void drawData(ArrayList<Customer> data) {
		try {
			LineChartTool.draw(data, "季度", "收益值", "收益走势", "data\\info.jpg");
			lblPicture.setIcon(new ImageIcon(ImageIO.read(new File("data\\info.jpg"))));
			// lblPicture.setIcon(icon);
		} catch (Exception e) {
			e.printStackTrace();
			lblPicture.setIcon(null);
		}
	}

	protected void display(ArrayList<Customer> data) {
		int size = data.size();
		String[][] tableVales = new String[size][5];
		Customer customer = null;
		for (int i = 0; i < size; i++) {
			customer = data.get(i);
			double[] value = customer.getQuarterProfit();
			tableVales[i][0] = customer.getIndex() + "";
			tableVales[i][1] = value[0] + "";
			tableVales[i][2] = value[1] + "";
			tableVales[i][3] = value[2] + "";
			tableVales[i][4] = value[3] + "";
		}
		tableModel.setDataVector(tableVales, columnNames);
		lblNum.setText(tableModel.getRowCount() + "行");
	}

	protected ArrayList<Customer> getData(int i) {
		ArrayList<Customer> after = mResult.get(i);
		ArrayList<Customer> before = new ArrayList<Customer>();
		for (Customer aC : after) {
			for (Customer c : mData) {
				if (c.getIndex() == aC.getIndex()) {
					before.add(c);
				}
			}
		}
		return before;
	}

	/**
	 * Create the dialog.
	 */
	public DetailFrame() {
		setSize(800, 600);
		Tools.setCenterLoaction(this);
		getContentPane().setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(5, 5, 769, 53);
		panel.setBorder(BorderFactory.createTitledBorder(""));
		getContentPane().add(panel);
		panel.setLayout(null);

		JLabel lblNewLabel = new JLabel("\u7C7B\u522B\uFF1A");
		lblNewLabel.setBounds(243, 19, 54, 15);
		panel.add(lblNewLabel);

		comboBox = new JComboBox<String>();
		comboBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					final int index = comboBox.getSelectedIndex();
					new Thread(new Runnable() {

						@Override
						public void run() {
							ArrayList<Customer> data = getData(index);
							display(data);
							drawData(data);
						}

					}).start();
				}
			}
		});
		comboBox.setBounds(307, 11, 100, 30);
		panel.add(comboBox);

		JPanel panel_1 = new JPanel();
		panel_1.setBounds(372, 68, 402, 484);
		getContentPane().add(panel_1);
		panel_1.setLayout(new BorderLayout(0, 0));

		lblPicture = new JLabel("");
		panel_1.add(lblPicture, BorderLayout.CENTER);
		lblPicture.setBorder(BorderFactory.createTitledBorder(""));

		JPanel panel_2 = new JPanel();
		panel_2.setBounds(5, 66, 367, 486);
		getContentPane().add(panel_2);
		panel_2.setLayout(new BorderLayout(0, 0));

		JScrollPane scrollPane = new JScrollPane();
		panel_2.add(scrollPane, BorderLayout.CENTER);

		lblNum = new JLabel("");
		panel_2.add(lblNum, BorderLayout.SOUTH);

		table = new JTable();
		scrollPane.setViewportView(table);
		tableModel = new DefaultTableModel(null, columnNames);
		table.setModel(tableModel);
		lblNum.setText(tableModel.getRowCount() + "行");
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // 单选

		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setIconImage(new ImageIcon("data//crm.jpg").getImage());
	}

}
