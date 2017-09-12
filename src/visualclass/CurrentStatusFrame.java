package visualclass;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.*;

import java.awt.BorderLayout;

public class CurrentStatusFrame extends JFrame {
	
	JPanel jpanel = null;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTextField textField_6;
	
	private JLabel label = null;
	private JLabel label_1 = null;
	private JRadioButton radioButton;
	private JRadioButton radioButton_1;
	private JRadioButton radioButton_2;
	private JLabel label_2;
	private JCheckBox checkBox;
	private JCheckBox checkBox_1;
	private JCheckBox checkBox_2;
	private JCheckBox chckbxNewCheckBox;
	private JCheckBox checkBox_3;
	private JLabel label_3;
	private JLabel label_4;
	private JLabel label_5;
	private JRadioButton radioButton_3;
	private JRadioButton radioButton_4;
	private JRadioButton radioButton_5;
	private JCheckBox checkBox_4;
	private JCheckBox checkBox_5;
	private JCheckBox checkBox_6;
	private JCheckBox checkBox_7;
	private JCheckBox checkBox_8;
	private JLabel label_6;
	private JLabel label_7;
	private JLabel label_8;
	
	public CurrentStatusFrame() {
		
		this.setVisible(true);
		this.setBounds(350, 180, 500, 400);
		this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
			
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				setVisible(false);
				}	
			});
		
		try {
			javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName());
			javax.swing.SwingUtilities.updateComponentTreeUI(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		jpanel = new JPanel();
		jpanel.setLocation(0, 0);	
		this.add(jpanel);
		jpanel.setLayout(null);
		jpanel.setSize(484, 362);
		
//		jpanel.updateUI();
	}
	
	public void launch(){

		
		label = new JLabel("�о�������");
		label.setBounds(20, 204, 65, 15);
		jpanel.add(label);
		
		label_1 = new JLabel("�ҷ�������");
		label_1.setBounds(20, 21, 65, 15);
		jpanel.add(label_1);
		
		radioButton = new JRadioButton("������");
		radioButton.setBounds(20, 83, 74, 23);
		jpanel.add(radioButton);
		
		radioButton_1 = new JRadioButton("�սᵯ");
		radioButton_1.setBounds(20, 108, 74, 23);
		jpanel.add(radioButton_1);
		
		radioButton_2 = new JRadioButton("��ͨ��");
		radioButton_2.setBounds(20, 133, 74, 23);
		jpanel.add(radioButton_2);
		
		textField = new JTextField();
		textField.setBounds(85, 18, 66, 21);
		jpanel.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(85, 204, 66, 21);
		jpanel.add(textField_1);
		textField_1.setColumns(10);
		
		label_2 = new JLabel("�ӵ�״̬��");
		label_2.setBounds(20, 55, 65, 15);
		jpanel.add(label_2);
		
		checkBox = new JCheckBox("���ѵ�");
		checkBox.setBounds(114, 83, 74, 23);
		jpanel.add(checkBox);
		
		checkBox_1 = new JCheckBox("���ε�");
		checkBox_1.setBounds(114, 108, 74, 23);
		jpanel.add(checkBox_1);
		
		chckbxNewCheckBox = new JCheckBox("׷����");
		chckbxNewCheckBox.setBounds(114, 133, 74, 23);
		jpanel.add(chckbxNewCheckBox);
		
		checkBox_2 = new JCheckBox("���ᵯ");
		checkBox_2.setBounds(214, 83, 74, 23);
		jpanel.add(checkBox_2);
		
		checkBox_3 = new JCheckBox("��ǽ��");
		checkBox_3.setBounds(214, 108, 74, 23);
		jpanel.add(checkBox_3);
		
		label_3 = new JLabel("�ƶ��ٶȣ�");
		label_3.setBounds(160, 21, 70, 15);
		jpanel.add(label_3);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(228, 18, 66, 21);
		jpanel.add(textField_2);
		
		label_4 = new JLabel("�ӵ��ٶȣ�");
		label_4.setBounds(308, 87, 74, 15);
		jpanel.add(label_4);
		
		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setBounds(383, 84, 66, 21);
		jpanel.add(textField_3);
		
		label_5 = new JLabel("�ӵ�������");
		label_5.setBounds(308, 112, 74, 15);
		jpanel.add(label_5);
		
		textField_4 = new JTextField();
		textField_4.setColumns(10);
		textField_4.setBounds(383, 109, 66, 21);
		jpanel.add(textField_4);
		
		radioButton_3 = new JRadioButton("��ͨ��");
		radioButton_3.setBounds(20, 315, 74, 23);
		jpanel.add(radioButton_3);
		
		radioButton_4 = new JRadioButton("�սᵯ");
		radioButton_4.setBounds(20, 290, 74, 23);
		jpanel.add(radioButton_4);
		
		radioButton_5 = new JRadioButton("������");
		radioButton_5.setBounds(20, 265, 74, 23);
		jpanel.add(radioButton_5);
		
		checkBox_4 = new JCheckBox("���ѵ�");
		checkBox_4.setBounds(114, 265, 74, 23);
		jpanel.add(checkBox_4);
		
		checkBox_5 = new JCheckBox("���ε�");
		checkBox_5.setBounds(114, 290, 74, 23);
		jpanel.add(checkBox_5);
		
		checkBox_6 = new JCheckBox("׷����");
		checkBox_6.setBounds(114, 315, 74, 23);
		jpanel.add(checkBox_6);
		
		checkBox_7 = new JCheckBox("��ǽ��");
		checkBox_7.setBounds(214, 290, 74, 23);
		jpanel.add(checkBox_7);
		
		checkBox_8 = new JCheckBox("���ᵯ");
		checkBox_8.setBounds(214, 265, 74, 23);
		jpanel.add(checkBox_8);
		
		label_6 = new JLabel("�ӵ��ٶȣ�");
		label_6.setBounds(308, 294, 74, 15);
		jpanel.add(label_6);
		
		textField_5 = new JTextField();
		textField_5.setColumns(10);
		textField_5.setBounds(383, 291, 66, 21);
		jpanel.add(textField_5);
		
		textField_6 = new JTextField();
		textField_6.setColumns(10);
		textField_6.setBounds(383, 266, 66, 21);
		jpanel.add(textField_6);
		
		label_7 = new JLabel("�ӵ�������");
		label_7.setBounds(308, 269, 74, 15);
		jpanel.add(label_7);
		
		label_8 = new JLabel("�ӵ�״̬");
		label_8.setBounds(20, 239, 65, 15);
		jpanel.add(label_8);
	}
	
	public static void main(String[] args){
		CurrentStatusFrame currentStatusFrame = new CurrentStatusFrame();
		currentStatusFrame.launch();
		
		currentStatusFrame.repaint();
	}
}
