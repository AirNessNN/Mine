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

	JFrame mainWindow = new JFrame("滑稽扫雷");

	JMenuBar menuBar = new JMenuBar();
	JMenu game = new MyMenu().myMenu("游戏");
	JMenuItem start = new MyMenu().myMenuItem("新游戏");
	JMenuItem restart = new MyMenu().myMenuItem("重开游戏");
	JMenuItem autoMine=new MyMenu().myMenuItem("自动扫雷");
	JMenu setting = new MyMenu().myMenu("设置");
	JMenuItem gameSetting = new MyMenu().myMenuItem("游戏设置");
	JMenu about = new MyMenu().myMenu("关于");
	JMenuItem help = new MyMenu().myMenuItem("帮助");
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
	 * 构造函数
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
	 * 主窗口创建方法
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
		// 关闭游戏菜单选项
		restart.setEnabled(false);
		autoMine.setEnabled(false);
	}

	/*
	 * 菜单点击事件集合
	 */
	void menuEvent() {
		start.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
				new GameWindow(gameWidth, gameHeight, mapW, mapH, mineN).setMineWindow();
			}
		});
		
		//自动扫雷动作监听器
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
				// TODO 自动生成的方法存根
				new GameSetting().settingWindow();
			}
		});
		restart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
			}
		});
		help.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
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
		Font font = new Font("微软雅黑", Font.PLAIN, 15);
		JButton jb = new JButton("确定");
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
				// TODO 自动生成的方法存根
				msgbox.dispose();
			}
		});
	}
}

/*
 * 重写菜单栏字体
 */
@SuppressWarnings("serial")
class MyMenu extends JMenu {
	Font font = new Font("微软雅黑 Light", Font.PLAIN, 15);

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
