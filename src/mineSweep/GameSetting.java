package mineSweep;

import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;

import SwingTool.MesageBox;

@SuppressWarnings("serial")
public class GameSetting extends MainWindow {
	static JFrame settingWindow = new JFrame("游戏设置");
	Font font = new Font("微软雅黑 Light", Font.PLAIN, 15);
	Font font2 = new Font("微软雅黑 Light", Font.PLAIN, 14);
	JLabel lab1 = new JLabel("默认布局：");
	ButtonGroup group1 = new ButtonGroup();
	JRadioButton easyBtn = new JRadioButton("10*10");
	JRadioButton normalBtn = new JRadioButton("16*16");
	JRadioButton hardBtn = new JRadioButton("32*16");
	JLabel lab2 = new JLabel("自定义布局：");
	JTextArea widthTextBox = new JTextArea();
	JTextArea heightTextBox = new JTextArea();
	JTextArea mineNum = new JTextArea();
	JLabel lab3 = new JLabel("*");
	JLabel lab4 = new JLabel("地雷数：");
	JButton saveBtn = new JButton("保存");

	/*
	 * 创建设置窗口
	 */
	public GameSetting() {
		// TODO 自动生成的构造函数存根
		width = 400;
		height = 250;
	}

	public void settingWindow() {
		settingWindow.setLocationRelativeTo(null);
		Container settingCon = settingWindow.getContentPane();
		settingCon.setLayout(null);
		settingWindow.setBounds(550, 450, width, height);
		settingWindow.setResizable(false);
		/*
		 * 设置界面页面布局
		 */

		lab1.setBounds(20, 20, 100, 20);
		lab1.setFont(font);
		easyBtn.setBounds(40, 50, 100, 20);
		normalBtn.setBounds(140, 50, 100, 20);
		hardBtn.setBounds(240, 50, 100, 20);
		lab2.setBounds(20, 80, 100, 20);
		lab2.setFont(font);
		easyBtn.setFont(font2);
		normalBtn.setFont(font2);
		hardBtn.setFont(font2);
		lab3.setFont(font2);
		lab4.setFont(font2);
		widthTextBox.setBounds(40, 110, 50, 20);
		lab3.setBounds(96, 110, 20, 20);
		heightTextBox.setBounds(110, 110, 50, 20);
		lab4.setBounds(170, 110, 60, 20);
		mineNum.setBounds(230, 110, 50, 20);
		saveBtn.setFont(font2);
		saveBtn.setBounds(60, 160, 70, 30);

		group1.add(easyBtn);
		group1.add(normalBtn);
		group1.add(hardBtn);
		easyBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				MainWindow.getFormat(10, 10, 10);
			}
		});
		normalBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				MainWindow.getFormat(16, 16, 40);
			}
		});
		hardBtn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				MainWindow.getFormat(32, 16, 99);
			}
		});
		

		settingCon.add(lab1);
		settingCon.add(lab2);
		settingCon.add(easyBtn);
		settingCon.add(normalBtn);
		settingCon.add(hardBtn);
		settingCon.add(lab3);
		settingCon.add(lab4);
		settingCon.add(mineNum);
		settingCon.add(widthTextBox);
		settingCon.add(heightTextBox);
		settingCon.add(saveBtn);

		settingWindow.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		settingWindow.setVisible(true);

		saveBtn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int width = 0, height = 0, mine = 0;
				if(!widthTextBox.getText().equals("")&&!heightTextBox.getText().equals("")&&!mineNum.getText().equals("")){
					try {
						width = Integer.valueOf(widthTextBox.getText());
						height = Integer.valueOf(heightTextBox.getText());
						mine = Integer.valueOf(mineNum.getText());
					} catch (Exception e1) {
						MesageBox.show(settingWindow, "请填写内容或者选择预设", "提示");
						return;
					}
					if (width > 32 || height > 32 || mine > width * height) {
						MesageBox.show(settingWindow, "格子超出或者地雷数量过大", "提示");
						return;
					}
					MainWindow.getFormat(width, height, mine);
					group1.clearSelection();
					widthTextBox.setText("");
					heightTextBox.setText("");
					mineNum.setText("");
					settingWindow.dispose();
					return;
				}
				if(!easyBtn.isSelected()&&!normalBtn.isSelected()&&!hardBtn.isSelected()){
					MesageBox.show(settingWindow, "请选择游戏难度或自定义", "提示");
					return;
				}
				group1.clearSelection();
				widthTextBox.setText("");
				heightTextBox.setText("");
				mineNum.setText("");
				settingWindow.dispose();
			}
		});

	}
}
