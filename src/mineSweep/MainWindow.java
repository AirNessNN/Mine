package mineSweep;

import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class MainWindow extends JFrame {
	int width, height;

	JFrame mainWindow = new JFrame("����ɨ��");

	JMenuBar menuBar = new JMenuBar();
	JMenu game = new MyMenu().myMenu("��Ϸ");
	JMenuItem start = new MyMenu().myMenuItem("����Ϸ");
	JMenuItem restart = new MyMenu().myMenuItem("�ؿ���Ϸ");
	JMenuItem autoMine=new MyMenu().myMenuItem("�Զ�ɨ��");
	JMenu setting = new MyMenu().myMenu("����");
	JMenuItem gameSetting = new MyMenu().myMenuItem("��Ϸ����");
	JMenu about = new MyMenu().myMenu("����");
	JMenuItem help = new MyMenu().myMenuItem("����");
	public static int mapW = 10;
	public static int mapH = 10;
	public static int mineN = 10;
	static int gameWidth = 360;
	static int gameHeight = 455;

	public static void getFormat(int W, int H, int N) {
		mapW = W;
		mapH = H;
		mineN = N;
		gameWidth = 35 * W + 10;
		gameHeight = 35 * H + 5 + 100;
	}

	/*
	 * ���캯��
	 */
	MainWindow() {
		this.width = 420;
		this.height = 250;
	}

	MainWindow(int width, int height) {
		this.width = width;
		this.height = height;
	}

	/*
	 * �����ڴ�������
	 */
	void creatWindow() {
		URL url = MainWindow.class.getResource("home.png");
		Icon icon = new ImageIcon(url);
		Container con = mainWindow.getContentPane();
		JLabel img = new JLabel();
		img.setIcon(icon);

		// con.setLayout(null);
		mainWindow.setBounds(500, 200, width, height);
		mainWindow.setDefaultCloseOperation(EXIT_ON_CLOSE);
		mainWindow.setResizable(false);
		mainWindow.setJMenuBar(menuSet());
		con.add(img);
		menuEvent();
		mainWindow.setVisible(true);
		// �ر���Ϸ�˵�ѡ��
		restart.setEnabled(false);
		autoMine.setEnabled(false);
	}

	/*
	 * �˵�����¼�����
	 */
	void menuEvent() {
		start.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				new GameWindow(gameWidth, gameHeight, mapW, mapH, mineN).setMineWindow();
			}
		});
		
		//�Զ�ɨ�׶���������
		autoMine.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new AutoMine().setVisible(true);
			}
		});
		
		gameSetting.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO �Զ����ɵķ������
				new GameSetting().settingWindow();
			}
		});
		restart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO �Զ����ɵķ������
			}
		});
		help.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO �Զ����ɵķ������
				new HelpWindow().creatWindow();
			}
		});
	}

	public JMenuBar menuSet() {
		menuBar.add(game);
		menuBar.add(setting);
		game.add(start);
		game.add(restart);
		game.add(autoMine);
		setting.add(gameSetting);
		menuBar.add(about);
		about.add(help);
		return menuBar;
	}

	void setMessageBox(JFrame own, String title, String content) {
		JDialog msgbox = new JDialog(own, title);
		JLabel tex = new JLabel(content, SwingConstants.CENTER);
		Font font = new Font("΢���ź�", Font.PLAIN, 15);
		JButton jb = new JButton("ȷ��");
		jb.setBounds(120, 80, 60, 30);
		tex.setBounds(30, 20, 240, 50);
		msgbox.setLayout(null);
		msgbox.add(tex);
		tex.setFont(font);
		msgbox.setLocationRelativeTo(null);
		msgbox.setResizable(false);
		msgbox.setSize(300, 150);
		msgbox.add(jb);
		msgbox.setVisible(true);
		jb.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO �Զ����ɵķ������
				msgbox.dispose();
			}
		});
	}
}

/*
 * ��д�˵�������
 */
@SuppressWarnings("serial")
class MyMenu extends JMenu {
	Font font = new Font("΢���ź� Light", Font.PLAIN, 15);

	JMenu myMenu(String name) {
		JMenu menu = new JMenu(name);
		menu.setFont(font);
		return menu;
	}

	JMenuItem myMenuItem(String name) {
		JMenuItem item = new JMenuItem(name);
		item.setFont(font);
		return item;
	}
}
