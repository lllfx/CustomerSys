package view;

import java.awt.BorderLayout;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import model.Customer;
import model.Model;
import tools.PieChartTool;
import tools.Tools;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.FlowLayout;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;

public class MainFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8720432252745326845L;
	private JPanel contentPane;
	private JTable table;
	private JTextField textField;
	private JTextField textField_1;
	String[] columnNames = { "���", "��һ���ȹ���ֵ", "�ڶ����ȹ���ֵ", "�������ȹ���ֵ", "���ļ��ȹ���ֵ" }; // ����
	private DefaultTableModel tableModel; // ���ģ�Ͷ���
	private JLabel lblDataNum;
	private JTextArea textArea;
	private JLabel lblPicture;

	

	/**
	 * Create the frame.
	 */
	public MainFrame() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize( 800, 600);
		Tools.setCenterLoaction(this);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(5, 5, 514, 565);
		contentPane.add(panel);
		panel.setLayout(new BorderLayout(0, 0));
		panel.setBorder(BorderFactory.createTitledBorder("����ģ������"));
		JScrollPane scrollPane = new JScrollPane();
		panel.add(scrollPane, BorderLayout.CENTER);
		lblDataNum = new JLabel("\u603B\u8BA1\uFF1A");
		panel.add(lblDataNum, BorderLayout.SOUTH);

		table = new JTable();
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // ��ѡ
		tableModel = new DefaultTableModel(null, columnNames);
		table.setModel(tableModel);
		lblDataNum.setText("�ܼ�:" + tableModel.getRowCount() + "��");
		scrollPane.setViewportView(table);

		JPanel panel_1 = new JPanel();
		panel.add(panel_1, BorderLayout.NORTH);
		panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JLabel lblNewLabel_1 = new JLabel("\u6570\u636E\u7684\u6570\u91CF\uFF08>=50&&<=1000\uFF09");
		panel_1.add(lblNewLabel_1);

		textField = new JTextField();
		textField.setDocument(new NumberLenghtLimitedDmt(4));
		panel_1.add(textField);
		textField.setColumns(10);

		JButton btnNewButton = new JButton("\u751F\u6210\u6570\u636E");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String tFValue = textField.getText();
				if (tFValue == null || tFValue.equals("")) {
					JOptionPane.showMessageDialog(MainFrame.this, "��������Ч��������", "����", JOptionPane.ERROR_MESSAGE);
					return;
				}
				final int value = Integer.parseInt(tFValue);
				if (value < 50 || value > 1000) {
					JOptionPane.showMessageDialog(MainFrame.this, value + "������Ч��Χ��!", "����", JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				lblPicture.setIcon(null);
				
				addLog("��������" + value + "��");
				new Thread(new Runnable() {

					@Override
					public void run() {
						addLog("�ɹ�����" + value + "������");
						Model.generateData(value);
						// ��ʾ����
						ArrayList<Customer> cList = Model.getData();
						int size = cList.size();
						String[][] tableVales = new String[size][5];
						Customer customer = null;
						for (int i = 0; i < size; i++) {
							customer = cList.get(i);
							double[] value = customer.getQuarterProfit();
							tableVales[i][0] = customer.getIndex() + "";
							tableVales[i][1] = value[0] + "";
							tableVales[i][2] = value[1] + "";
							tableVales[i][3] = value[2] + "";
							tableVales[i][4] = value[3] + "";
						}
						tableModel.setDataVector(tableVales, columnNames);
						lblDataNum.setText("�ܼ�:" + tableModel.getRowCount() + "��");
					}
				}).start();

			}
		});
		panel_1.add(btnNewButton);

		JPanel panel_2 = new JPanel();
		panel_2.setBounds(518, 10, 270, 560);
		panel_2.setBorder(BorderFactory.createTitledBorder("�����㷨"));
		contentPane.add(panel_2);
		panel_2.setLayout(null);

		JLabel lblNewLabel_2 = new JLabel("\u7C7B\u522B\u6570\u91CF\uFF1A");
		lblNewLabel_2.setBounds(27, 20, 83, 29);
		panel_2.add(lblNewLabel_2);

		textField_1 = new JTextField();
		textField_1.setBounds(120, 20, 100, 30);
		textField_1.setDocument(new NumberLenghtLimitedDmt(4));
		panel_2.add(textField_1);
		textField_1.setColumns(10);

		JButton btnNewButton_1 = new JButton("\u805A\u7C7B");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ArrayList<Customer> cList = Model.getData();
				if (cList == null || cList.isEmpty()) {
					JOptionPane.showMessageDialog(MainFrame.this, "��������ģ�����ݣ�", "����", JOptionPane.ERROR_MESSAGE);
					return;
				}
				int rowNum = cList.size();
				String tFValue = textField_1.getText();
				if (tFValue == null || tFValue.equals("")) {
					JOptionPane.showMessageDialog(MainFrame.this, "��������Ч��������", "����", JOptionPane.ERROR_MESSAGE);
					return;
				}
				final int value = Integer.parseInt(tFValue);
				if (value < 2 || value >= rowNum) {
					JOptionPane.showMessageDialog(MainFrame.this, value + "��������Ӧ��(>=2&&<" + rowNum + ")", "����",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				addLog("�����������" + value + ",��ʼ�������");
				new Thread(new Runnable() {

					@Override
					public void run() {
						ArrayList<ArrayList<Customer>> result = Model.startCluster(value, MainFrame.this);
						// ��ʾͼƬ
						ArrayList<String> categorys = new ArrayList<String>();
						ArrayList<Double> nums = new ArrayList<Double>();
						for (int i = 0; i < result.size(); i++) {
							ArrayList<Customer> temp = result.get(i);
							categorys.add("��" + (i + 1) + "�����");
							nums.add((double) temp.size());
						}
						addLog("����������ݷֲ�ͼ");
						// lblPicture.setIcon(null);
						try {
							PieChartTool.draw(categorys, nums, "����ֲ�", "data\\cluster.jpg");
							lblPicture.setIcon(new ImageIcon(ImageIO.read(new File("data\\cluster.jpg"))));
							// lblPicture.setIcon(icon);
						} catch (Exception e) {
							e.printStackTrace();
							lblPicture.setIcon(null);
						}
					}
				}).start();

			}
		});
		btnNewButton_1.setBounds(27, 60, 100, 30);
		panel_2.add(btnNewButton_1);

		textArea = new JTextArea();
		JScrollPane scroll = new JScrollPane(textArea);
		scroll.setBounds(10, 303, 246, 239);
		panel_2.add(scroll);

		JButton btnNewButton_2 = new JButton("\u805A\u7C7B\u8BE6\u60C5");
		btnNewButton_2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ArrayList<ArrayList<Customer>> result = Model.getResult();
				if (result == null) {
					JOptionPane.showMessageDialog(MainFrame.this, "���Ƚ��о������", "����", JOptionPane.ERROR_MESSAGE);
					return;
				}
				DetailFrame dialog = new DetailFrame();
				dialog.setmData(Model.getData());
				dialog.setmResult(result);
				dialog.initData();
				dialog.setVisible(true);

			}
		});
		btnNewButton_2.setBounds(140, 60, 100, 30);
		panel_2.add(btnNewButton_2);

		lblPicture = new JLabel("");
		lblPicture.setBounds(10, 100, 250, 200);
		panel_2.add(lblPicture);
		
		setIconImage(new ImageIcon("data//crm.jpg").getImage());
	}

	public void addLog(String str) {
		textArea.append(str + "\n");
		textArea.setCaretPosition(textArea.getDocument().getLength());
	}
}
