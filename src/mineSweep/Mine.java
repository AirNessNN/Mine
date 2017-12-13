package mineSweep;
import java.net.URL;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;


public class Mine {

	public static void main(String[] args) {
		
		
		String lookAndFeel = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
		try {
			UIManager.setLookAndFeel(lookAndFeel);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		new MainWindow().creatWindow();
	}
}


class IconMaker {
	private URL url;
	private Icon icon;
	private String path;
	/**
	 * 
	 * @param path 在类目录中的文件名.png格式萨达
	 */
	public IconMaker(String path) {
		// TODO 自动生成的构造函数存根
				this.path=path+".png";
				url=IconMaker.class.getResource(this.path);
				this.icon=new ImageIcon(url);
	}
	public Icon getIconPath(){
		return icon;
	}
}
