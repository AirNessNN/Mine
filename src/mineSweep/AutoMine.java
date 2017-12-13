package mineSweep;

import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Point;

import javax.swing.JPanel;
import javax.swing.border.TitledBorder;

import SwingTool.MyButton;
import mineSweep.GameWindow.ClickMode;

import javax.swing.JProgressBar;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.awt.event.ActionEvent;

public class AutoMine extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * �ֶ�
	 */
	private MyButton btnStart = new MyButton("\u5F00\u59CB");
	private MyButton btnStop = new MyButton("\u505C\u6B62");
	private JProgressBar progressBar = new JProgressBar();
	private JLabel labWinRate = new JLabel("null");
	private JLabel labWin = new JLabel("null");
	private JLabel labRund = new JLabel("null");
	// �̱߳�ǣ����ڽ����Զ�ɨ��
	private boolean threadFlag = false;

	// ����
	private int round = 0;
	private int win = 0;
	private double winRate = 0;

	public AutoMine() {
		setResizable(false);
		getContentPane().setLayout(null);
		// �����¼�
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// �������߳�
				new Thread(new Runnable() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						
					}
				}).start();
				try {
					start();
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		btnStart.setBounds(10, 182, 93, 23);
		getContentPane().add(btnStart);
		this.setSize(509, 308);

		JLabel lblNewLabel = new JLabel("\u81EA\u52A8\u626B\u96F7");
		lblNewLabel.setFont(new Font("΢���ź�", Font.PLAIN, 22));
		lblNewLabel.setBounds(10, 10, 253, 48);
		getContentPane().add(lblNewLabel);

		btnStop.setEnabled(false);
		btnStop.setBounds(113, 182, 93, 23);
		getContentPane().add(btnStop);

		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(null, "\u6570\u636E", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel.setBounds(10, 60, 485, 112);
		getContentPane().add(panel);
		panel.setLayout(null);

		JLabel lblNewLabel_1 = new JLabel("\u5C40\u6570\uFF1A");
		lblNewLabel_1.setBounds(10, 27, 54, 15);
		panel.add(lblNewLabel_1);

		JLabel lblNewLabel_2 = new JLabel("\u8D62\u6570\uFF1A");
		lblNewLabel_2.setBounds(10, 52, 54, 15);
		panel.add(lblNewLabel_2);

		JLabel lblNewLabel_3 = new JLabel("\u80DC\u7387\uFF1A");
		lblNewLabel_3.setBounds(10, 77, 54, 15);
		panel.add(lblNewLabel_3);

		labRund.setBounds(45, 27, 54, 15);
		panel.add(labRund);

		labWin.setBounds(45, 52, 54, 15);
		panel.add(labWin);

		labWinRate.setBounds(45, 77, 54, 15);
		panel.add(labWinRate);

		progressBar.setBounds(10, 255, 485, 14);
		getContentPane().add(progressBar);

		JLabel labProgress = new JLabel("\u8FDB\u5EA6");
		labProgress.setBounds(10, 230, 54, 15);
		getContentPane().add(labProgress);
		// TODO Auto-generated constructor stub
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(null);
	}

	/**
	 * �Զ�ɨ���߼�
	 * @throws InterruptedException 
	 */
	private void start() throws InterruptedException {
		threadFlag = true;
		while (threadFlag) {
			this.round++;
			this.labRund.setText(String.valueOf(round));
			this.labWin.setText(String.valueOf(win));
			this.labWinRate.setText(String.valueOf(win / round));
			// ʵ����ɨ������
			GameWindow game = new GameWindow(MainWindow.gameWidth, MainWindow.gameHeight, MainWindow.mapW,
					MainWindow.mapH, MainWindow.mineN);
			game.setMineWindow();

			Thread t=new Thread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					// ��ʼѭ����������̷���
					while (threadFlag) {
						
						
						

						// �������Ž�
						// ������Ž�
						// �ж��Ƿ��Ѿ���Ϸ����
						// break

					}
				}
			});
			t.start(); 
			break;

			// ���һ�֣�Ӯ��+1

			// ��������ѭ��

		}
	}

	/**
	 * ����һ����������
	 */
	private void traverse(){
		
	}
	
	//������������
	
	
	
}
