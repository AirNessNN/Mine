package mineSweep;

import java.awt.Container;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import javax.swing.Icon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class GameWindow extends MainWindow implements MouseListener {
	private static final long serialVersionUID = 1L;

	private JLabel[][] mineBtn = new JLabel[32][32];// 地雷按钮
	private JLabel timer = new JLabel();// 时间面板HUB
	private JLabel mineNumLabel = new JLabel();// 地雷面板HUB
	private Thread timeCount;// 计时器线程
	/**
	 * 右键标记
	 */
	public int[][] mineFlag = new int[32][32];// 右键标记
	/**
	 * 每格地雷数量
	 */
	public MineMap[][] mineMap=new MineMap[MainWindow.mapH][MainWindow.mapW];
	/**
	 * 地雷标记
	 */
	private boolean[][] isMineV = new boolean[32][32];// 地雷标记
	/**
	 * 访问标记
	 */
	public boolean[][] vis = new boolean[32][32];// 访问标记
	private int mineSum = 10;// 地雷缓存
	private Queue<Point> map = new LinkedList<Point>();// 遍历用数
	
	boolean gameFlag = true;// 游戏是否运行标志
	int mapWidth;// 横向地图
	int mapHeight;// 竖向地图
	int mineNumb;// 地雷数量
	int visSum;// 已访问的地图块，用于判断是否胜利
	boolean threadFlag = false;// 线程标记
	private Point[] point;//右键九宫格内未点击方块坐标
	int[] checkFlag = new int[5];//检查九宫格内方块标记
	
	/*
	 * 构造函数
	 */
	public GameWindow(int Width, int Height, int x, int y, int mine) {
		// 构造函数
		width = Width;
		height = Height;
		mapWidth = x;
		mapHeight = y;
		mineNumb = mine;
		mineSum = mine;
		visSum = 0;// 初始化访问数量
	}

	void setMine(int width, int height, int num, int a, int b) {
		int x, y;
		int kase = 0;
		Random rand = new Random();
		while (kase < num) {
			x = rand.nextInt(width);
			y = rand.nextInt(height);
			if (!isMineV[y][x]) {
				if (a == y && b == x) {
					continue;
				}
				isMineV[y][x] = true;
				kase++;
			}
		}
	}

	/*
	 * 主窗口创建方法
	 */
	public void setMineWindow() {
		JFrame gameWindow = new JFrame("正在游戏");// 创建窗口对象
		Container con = gameWindow.getContentPane();// 获取容器
		con.setLayout(null);// 设置窗口容器为绝对布局
		gameWindow.setSize(this.width, this.height);
		gameWindow.setLocationRelativeTo(null);
		gameWindow.setResizable(false);// 设置不可更改大小
		gameWindow.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		gameWindow.setJMenuBar(menuSet());// 建立菜单bar
		JPanel minePanel;// 创建地雷容器
		JPanel Hub = new JPanel();// 建立时间HUB
		Hub.setLayout(null);

		Font font = new Font("微软雅黑 Light", Font.PLAIN, 14);
		minePanel = mineMake();// 生成地雷GUI
		Hub.add(timer);
		Hub.add(mineNumLabel);
		Hub.setSize(width, 40);
		con.add(Hub);
		timer.setFont(font);
		timer.setText("点击方块开始游戏");
		timer.setBounds(30, 10, 200, 20);
		mineNumLabel.setFont(font);
		mineNumLabel.setText("当前地雷数量：" + mineSum);
		mineNumLabel.setBounds(200, 10, 150, 20);
		minePanel.setBounds(0, 40, width, height);
		con.add(minePanel);
		gameWindow.setVisible(true);
		// 关闭新游戏菜单选项
		start.setEnabled(false);
		start.setText("正在游戏中");
		setting.setEnabled(false);
		about.setEnabled(false);
		autoMine.setEnabled(false);
		// 重开游戏
		restart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO 自动生成的方法存根
				gameWindow.dispose();
				// 休眠线程
				try {
					Thread.sleep(300);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				GameWindow.this.threadFlag = false;
				new GameWindow(width, height, mapW, mapH, mineNumb).setMineWindow();
			}
		});
		// 开始新游戏
		start.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO 自动生成的方法存根
				gameWindow.dispose();
				// 休眠线程
				try {
					Thread.sleep(300);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				GameWindow.this.threadFlag = false;
				new GameWindow(width, height, mapW, mapH, mineNumb).setMineWindow();
			}
		});
	}

	/*
	 * 1-8是地雷数量方格，9是地雷方格，10是非地雷方格，11是正常方格，12是不确定的方格
	 */
	Icon iconMake(int style) {
		switch (style) {
		case 1: {
			return new IconMaker("oneMine").getIconPath();
		}
		case 2: {
			return new IconMaker("twoMine").getIconPath();
		}
		case 3: {
			return new IconMaker("threeMine").getIconPath();
		}
		case 4: {
			return new IconMaker("fourMine").getIconPath();
		}
		case 5: {
			return new IconMaker("fiveMine").getIconPath();
		}
		case 6: {
			return new IconMaker("sixMine").getIconPath();
		}
		case 7: {
			return new IconMaker("sevenMine").getIconPath();
		}
		case 8: {
			return new IconMaker("eightMine").getIconPath();
		}
		case 9: {
			return new IconMaker("isMine").getIconPath();
		}
		case 10: {
			return new IconMaker("noMine").getIconPath();
		}
		case 11: {
			return new IconMaker("normal_box").getIconPath();
		}
		case 12: {
			return new IconMaker("unknow").getIconPath();
		}
		case 13: {
			return new IconMaker("check").getIconPath();
		}
		case 14: {
			return new IconMaker("bingo").getIconPath();
		}
		}
		return null;
	}

	/*
	 * 三个等级：1、2、3 游戏Map绘制
	 */
	JPanel mineMake() {
		JPanel jp = new JPanel(null);
		int w = 5, h = 5, kase = 1;
		String ss;
		for (int i = 0; i < mapH; i++) {
			for (int j = 0; j < mapW; j++) {
				mineBtn[i][j] = new JLabel();
				jp.add(mineBtn[i][j]);
				ss = String.valueOf(kase++);
				mineBtn[i][j].setName(ss);
				mineBtn[i][j].setBounds(w, h, 30, 30);
				w += 35;
				mineBtn[i][j].setIcon(iconMake(11));
				mineBtn[i][j].addMouseListener(this);
			}
			w = 5;
			h += 35;
		}
		return jp;
	}

	/*
	 * 被点击序列拆分数组方法
	 */
	Point transitionBtnName(String name, int width, int height) {
		int x = 0, y = 0, value = 0;
		Point pos = new Point();
		value = Integer.parseInt(name) - 1;
		y = value / width;
		x = value % width;
		pos.x = x;
		pos.y = y;
		return pos;
	}

	/*
	 * 计时器实现
	 */
	void setHub() {
		timeCount = new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				int n = 0;
				while (true) {
					timer.setText("时间：" + String.valueOf(n++) + "秒");
					try {
						if (gameFlag)
							Thread.sleep(1000);
						else {
							timeCount.interrupt();
							break;
						}
					} catch (InterruptedException e) {
						// TODO 自动生成的 catch 块
						e.printStackTrace();
					}
				}
			}
		});
		this.threadFlag = true;
		timeCount.start();
	}

	/*
	 * 地雷图遍历
	 */
	void setQueue(int x, int y) {
		int kase;
		if (isMineV[y][x]) {
			mineNumLabel.setText("游戏结束");
			gameFlag = false;
			// 移除所有监听器
			for (int i = 0; i < mapH; i++) {
				for (int j = 0; j < mapW; j++) {
					mineBtn[i][j].removeMouseListener(this);
					if (isMineV[i][j]) {
						if(mineFlag[i][j]==1)
							mineBtn[i][j].setIcon(iconMake(14));
						else
							mineBtn[i][j].setIcon(iconMake(9));
					}
				}
			}
			return;
		}
		map.offer(new Point(x, y));
		vis[y][x] = true;
		while (map.peek() != null) {
			kase = 0;
			Point tmp = map.poll();
			int h, w;
			h = tmp.y;
			w = tmp.x;
			for (int i = -1; i < 2; i++) {
				for (int j = -1; j < 2; j++) {
					if (i == 0 && j == 0)
						continue;
					if (h + i < 0 || w + j < 0 || h + i > mapH - 1 || w + j > mapW - 1)
						continue;
					if (isMineV[h + i][w + j] == true) {
						kase++;
					}
				}
			}
			if (kase == 0) {
				mineBtn[h][w].setIcon(iconMake(10));
				visSum += 1;
				System.out.println(visSum + "[" + h + "," + w + "]");

				for (int i = -1; i < 2; i++) {
					for (int j = -1; j < 2; j++) {
						if (h + i < 0 || w + j < 0 || h + i > mapH - 1 || w + j > mapW - 1)
							continue;
						if (i == 0 && j == 0)
							continue;
						if (vis[h + i][w + j])
							continue;
						map.offer(new Point(w + j, h + i));
						vis[h + i][w + j] = true;
					}
				}
			} else {
				visSum += 1;
				System.out.println(visSum + "[" + h + "," + w + "]");
				mineMap[h][w]=new MineMap(kase);
				mineBtn[h][w].setIcon(iconMake(kase));
				vis[h][w] = true;
			}
		}
	}
	/**
	 * 判断是否找出所有雷
	 * @return
	 */
	boolean isVictory() {
		int kase = 0;
		for (int i = 0; i < mapH; i++) {
			for (int j = 0; j < mapW; j++) {
				if (vis[i][j]) {
					kase++;
				}
			}
		}
		if (mapW * mapH - kase == mineNumb) {
			setMessageBox(mainWindow, "游戏提示", "滑天下之大稽，赢得比赛");
			start.setEnabled(true);
			start.setText("开始新游戏");
			restart.setEnabled(false);
			gameFlag = false;
			// 移除所有的监听器
			for (int i = 0; i < mapH; i++) {
				for (int j = 0; j < mapW; j++) {
					mineBtn[i][j].removeMouseListener(this);
					if (isMineV[i][j]) {
						mineBtn[i][j].setIcon(iconMake(14));
					}
				}
			}
			return true;
		}
		return false;
	}
	
	/**
	 * 右键的九宫格检查特效
	 * @param kase 次数
	 * @param sum 总数
	 * @param flag 标志
	 * @param point 需要按压的坐标
	 */
	void checkBox(int kase, int sum, int flag, Point[] point) {
		if (kase == sum && checkFlag[3] != 0) {
			for (int i = 0; i < flag; i++) {
				setQueue(point[i].x, point[i].y);
			}
		}
	}

	/*
	 * 地雷事件监听接口集合
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO 自动生成的方法存根

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO 自动生成的方法存根

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO 自动生成的方法存根

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO 自动生成的方法存根
		Point pos = transitionBtnName(((JLabel) (e.getSource())).getName(), mapW, mapH);
		int x, y;
		point = new Point[8];
		checkFlag[0] = checkFlag[1] = checkFlag[2] = checkFlag[3] = checkFlag[4] = 0;
		x = pos.x;
		y = pos.y;
		int n = e.getButton();
		if (n == MouseEvent.BUTTON3) {
			if (!vis[y][x]) {
				checkFlag[4] = 1;
				return;
			}
			if (!vis[y][x]) {
				checkFlag[4] = 1;
			}
			for (int i = -1; i < 2; i++) {
				for (int j = -1; j < 2; j++) {
					if (i == 0 && j == 0)
						continue;
					if (y + i < 0 || x + j < 0 || y + i > mapH - 1 || x + j > mapW - 1)
						continue;
					if (vis[y + i][x + j]) {
						checkFlag[3]++;
						continue;
					}
					if (isMineV[y + i][x + j]) {
						checkFlag[0]++;
					}
					if (mineFlag[y + i][x + j] == 1) {
						checkFlag[1]++;
						continue;
					}
					mineBtn[y + i][x + j].setIcon(iconMake(13));
					point[checkFlag[2]++] = new Point(x + j, y + i);
				}
			}
			if (checkFlag[4] != 1) {
				checkBox(checkFlag[0], checkFlag[1], checkFlag[2], point);
			}
			isVictory();
		}

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO 自动生成的方法存根
		Point pos = transitionBtnName(((JLabel) (e.getSource())).getName(), mapW, mapH);
		int y, x;
		y = pos.y;
		x = pos.x;
		if (threadFlag == false) {
			setHub();
			setMine(mapW, mapH, mineNumb, y, x);// 设置地雷数组
		}
		// 鼠标的动作
		switch (e.getButton()) {
		case MouseEvent.BUTTON1: {
			// 左键
			if (mineFlag[y][x] == 1) {
				return;
			}
			if (vis[y][x]) {
				return;
			}
			setQueue(x, y);
			break;
		}
		case MouseEvent.BUTTON3: {
			// 右键
			if (checkFlag[0] != checkFlag[1] || (checkFlag[3] == 0 || checkFlag[4] == 1)) {
				for (int i = 0; i < checkFlag[2]; i++) {
					mineBtn[point[i].y][point[i].x].setIcon(iconMake(11));
				}
			}
			if (vis[y][x]) {
				return;
			}
			if (mineFlag[y][x] == 0) {
				mineFlag[y][x] = 1;
				mineBtn[y][x].setIcon(iconMake(12));
				mineSum--;
				mineNumLabel.setText("剩余地雷：" + String.valueOf(mineSum));
			} else {
				mineFlag[y][x] = 0;
				mineBtn[y][x].setIcon(iconMake(11));
				mineSum++;
				mineNumLabel.setText("剩余地雷：" + String.valueOf(mineSum));
			}
			break;
		}
		}
		// 确定是否胜利
		isVictory();
	}
	
	/**
	 * 模拟鼠标点击
	 * @param x x坐标
	 * @param y y坐标
	 */
	public void mouseClick(int x,int y,ClickMode mod) {
		// TODO Auto-generated method stub
		// 鼠标的动作
		switch (mod) {
		case  LeftButton: {
			// 左键
			if (mineFlag[y][x] == 1) {
				return;
			}
			if (vis[y][x]) {
				return;
			}
			setQueue(x, y);
			break;
		}
		case RightButton: {
			// 右键
			if (checkFlag[0] != checkFlag[1] || (checkFlag[3] == 0 || checkFlag[4] == 1)) {
				for (int i = 0; i < checkFlag[2]; i++) {
					mineBtn[point[i].y][point[i].x].setIcon(iconMake(11));
				}
			}
			if (vis[y][x]) {
				return;
			}
			if (mineFlag[y][x] == 0) {
				mineFlag[y][x] = 1;
				mineBtn[y][x].setIcon(iconMake(12));
				mineSum--;
				mineNumLabel.setText("剩余地雷：" + String.valueOf(mineSum));
			} else {
				mineFlag[y][x] = 0;
				mineBtn[y][x].setIcon(iconMake(11));
				mineSum++;
				mineNumLabel.setText("剩余地雷：" + String.valueOf(mineSum));
			}
			break;
		}
		}
		// 确定是否胜利
		isVictory();
	}
	
	/**
	 * 点击按钮枚举
	 * @author AN
	 *
	 */
	enum ClickMode{
		LeftButton,
		RightButton
	}
}

