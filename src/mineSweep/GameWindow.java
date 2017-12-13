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

	private JLabel[][] mineBtn = new JLabel[32][32];// ���װ�ť
	private JLabel timer = new JLabel();// ʱ�����HUB
	private JLabel mineNumLabel = new JLabel();// �������HUB
	private Thread timeCount;// ��ʱ���߳�
	/**
	 * �Ҽ����
	 */
	public int[][] mineFlag = new int[32][32];// �Ҽ����
	/**
	 * ÿ���������
	 */
	public MineMap[][] mineMap=new MineMap[MainWindow.mapH][MainWindow.mapW];
	/**
	 * ���ױ��
	 */
	private boolean[][] isMineV = new boolean[32][32];// ���ױ��
	/**
	 * ���ʱ��
	 */
	public boolean[][] vis = new boolean[32][32];// ���ʱ��
	private int mineSum = 10;// ���׻���
	private Queue<Point> map = new LinkedList<Point>();// ��������
	
	boolean gameFlag = true;// ��Ϸ�Ƿ����б�־
	int mapWidth;// �����ͼ
	int mapHeight;// �����ͼ
	int mineNumb;// ��������
	int visSum;// �ѷ��ʵĵ�ͼ�飬�����ж��Ƿ�ʤ��
	boolean threadFlag = false;// �̱߳��
	private Point[] point;//�Ҽ��Ź�����δ�����������
	int[] checkFlag = new int[5];//���Ź����ڷ�����
	
	/*
	 * ���캯��
	 */
	public GameWindow(int Width, int Height, int x, int y, int mine) {
		// ���캯��
		width = Width;
		height = Height;
		mapWidth = x;
		mapHeight = y;
		mineNumb = mine;
		mineSum = mine;
		visSum = 0;// ��ʼ����������
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
	 * �����ڴ�������
	 */
	public void setMineWindow() {
		JFrame gameWindow = new JFrame("������Ϸ");// �������ڶ���
		Container con = gameWindow.getContentPane();// ��ȡ����
		con.setLayout(null);// ���ô�������Ϊ���Բ���
		gameWindow.setSize(this.width, this.height);
		gameWindow.setLocationRelativeTo(null);
		gameWindow.setResizable(false);// ���ò��ɸ��Ĵ�С
		gameWindow.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		gameWindow.setJMenuBar(menuSet());// �����˵�bar
		JPanel minePanel;// ������������
		JPanel Hub = new JPanel();// ����ʱ��HUB
		Hub.setLayout(null);

		Font font = new Font("΢���ź� Light", Font.PLAIN, 14);
		minePanel = mineMake();// ���ɵ���GUI
		Hub.add(timer);
		Hub.add(mineNumLabel);
		Hub.setSize(width, 40);
		con.add(Hub);
		timer.setFont(font);
		timer.setText("������鿪ʼ��Ϸ");
		timer.setBounds(30, 10, 200, 20);
		mineNumLabel.setFont(font);
		mineNumLabel.setText("��ǰ����������" + mineSum);
		mineNumLabel.setBounds(200, 10, 150, 20);
		minePanel.setBounds(0, 40, width, height);
		con.add(minePanel);
		gameWindow.setVisible(true);
		// �ر�����Ϸ�˵�ѡ��
		start.setEnabled(false);
		start.setText("������Ϸ��");
		setting.setEnabled(false);
		about.setEnabled(false);
		autoMine.setEnabled(false);
		// �ؿ���Ϸ
		restart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO �Զ����ɵķ������
				gameWindow.dispose();
				// �����߳�
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
		// ��ʼ����Ϸ
		start.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO �Զ����ɵķ������
				gameWindow.dispose();
				// �����߳�
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
	 * 1-8�ǵ�����������9�ǵ��׷���10�Ƿǵ��׷���11����������12�ǲ�ȷ���ķ���
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
	 * �����ȼ���1��2��3 ��ϷMap����
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
	 * ��������в�����鷽��
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
	 * ��ʱ��ʵ��
	 */
	void setHub() {
		timeCount = new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				int n = 0;
				while (true) {
					timer.setText("ʱ�䣺" + String.valueOf(n++) + "��");
					try {
						if (gameFlag)
							Thread.sleep(1000);
						else {
							timeCount.interrupt();
							break;
						}
					} catch (InterruptedException e) {
						// TODO �Զ����ɵ� catch ��
						e.printStackTrace();
					}
				}
			}
		});
		this.threadFlag = true;
		timeCount.start();
	}

	/*
	 * ����ͼ����
	 */
	void setQueue(int x, int y) {
		int kase;
		if (isMineV[y][x]) {
			mineNumLabel.setText("��Ϸ����");
			gameFlag = false;
			// �Ƴ����м�����
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
	 * �ж��Ƿ��ҳ�������
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
			setMessageBox(mainWindow, "��Ϸ��ʾ", "������֮�����Ӯ�ñ���");
			start.setEnabled(true);
			start.setText("��ʼ����Ϸ");
			restart.setEnabled(false);
			gameFlag = false;
			// �Ƴ����еļ�����
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
	 * �Ҽ��ľŹ�������Ч
	 * @param kase ����
	 * @param sum ����
	 * @param flag ��־
	 * @param point ��Ҫ��ѹ������
	 */
	void checkBox(int kase, int sum, int flag, Point[] point) {
		if (kase == sum && checkFlag[3] != 0) {
			for (int i = 0; i < flag; i++) {
				setQueue(point[i].x, point[i].y);
			}
		}
	}

	/*
	 * �����¼������ӿڼ���
	 */
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO �Զ����ɵķ������

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO �Զ����ɵķ������

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO �Զ����ɵķ������

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO �Զ����ɵķ������
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
		// TODO �Զ����ɵķ������
		Point pos = transitionBtnName(((JLabel) (e.getSource())).getName(), mapW, mapH);
		int y, x;
		y = pos.y;
		x = pos.x;
		if (threadFlag == false) {
			setHub();
			setMine(mapW, mapH, mineNumb, y, x);// ���õ�������
		}
		// ���Ķ���
		switch (e.getButton()) {
		case MouseEvent.BUTTON1: {
			// ���
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
			// �Ҽ�
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
				mineNumLabel.setText("ʣ����ף�" + String.valueOf(mineSum));
			} else {
				mineFlag[y][x] = 0;
				mineBtn[y][x].setIcon(iconMake(11));
				mineSum++;
				mineNumLabel.setText("ʣ����ף�" + String.valueOf(mineSum));
			}
			break;
		}
		}
		// ȷ���Ƿ�ʤ��
		isVictory();
	}
	
	/**
	 * ģ�������
	 * @param x x����
	 * @param y y����
	 */
	public void mouseClick(int x,int y,ClickMode mod) {
		// TODO Auto-generated method stub
		// ���Ķ���
		switch (mod) {
		case  LeftButton: {
			// ���
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
			// �Ҽ�
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
				mineNumLabel.setText("ʣ����ף�" + String.valueOf(mineSum));
			} else {
				mineFlag[y][x] = 0;
				mineBtn[y][x].setIcon(iconMake(11));
				mineSum++;
				mineNumLabel.setText("ʣ����ף�" + String.valueOf(mineSum));
			}
			break;
		}
		}
		// ȷ���Ƿ�ʤ��
		isVictory();
	}
	
	/**
	 * �����ťö��
	 * @author AN
	 *
	 */
	enum ClickMode{
		LeftButton,
		RightButton
	}
}

