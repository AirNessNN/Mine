package mineSweep;

import java.awt.Container;
import java.awt.Font;
import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class HelpWindow extends MainWindow {
	private static final long serialVersionUID = 1L;

	void creatWindow() {
		@SuppressWarnings("unused")
		Font font = new Font("Î¢ÈíÑÅºÚ Light", Font.PLAIN, 13);
		JFrame helpWindow = new JFrame("°ïÖú");
		Container con = helpWindow.getContentPane();
		URL url = HelpWindow.class.getResource("help.png");
		Icon icon = new ImageIcon(url);
		JLabel jl = new JLabel();
		jl.setIcon(icon);
		con.add(jl);
		helpWindow.setBounds(800, 400, 400, 320);
		helpWindow.setResizable(false);
		helpWindow.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		helpWindow.setVisible(true);
	}
}
