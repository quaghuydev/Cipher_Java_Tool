package view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.plaf.basic.BasicTabbedPaneUI;

import model.Hill;
import panel.AesPanel;
import panel.BlowfishPanel;
import panel.CamelliaPanel;
import panel.DesPanel;
import panel.HillPanel;
import panel.MD4Panel;
import panel.MD5Panel;
import panel.RSAPanel;
import panel.SHAPanel;
import panel.VigenerePanel;

public class StartApp {
	public static void main(String[] args) {
		System.setProperty("file.encoding", "UTF-8");
		JFrame frame = new JFrame("Các giải thuật mã hóa");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(700, 500);

		JTabbedPane typeAlgorithmPane = new JTabbedPane(JTabbedPane.LEFT);
		typeAlgorithmPane.setUI(new BasicTabbedPaneUI());
		typeAlgorithmPane.setBorder(null);

		JTabbedPane symmetryPane = new JTabbedPane();
		symmetryPane.addTab("AES", new AesPanel());
		symmetryPane.addTab("DES", new DesPanel());
		symmetryPane.addTab("Blowfish", new BlowfishPanel());
		symmetryPane.addTab("Vigenere", new VigenerePanel());
		symmetryPane.addTab("Hill", new HillPanel());
		symmetryPane.addTab("Camellia (dùng thư viện ngoài)", new CamelliaPanel());
		symmetryPane.setUI(new BasicTabbedPaneUI());
		symmetryPane.setBorder(null);

	

		JTabbedPane hashFunction = new JTabbedPane();
		hashFunction.addTab("MD4", new MD4Panel());
		hashFunction.addTab("MD5", new MD5Panel());
		hashFunction.addTab("SHA", new SHAPanel());
		hashFunction.setUI(new BasicTabbedPaneUI());
		hashFunction.setBorder(null);
		
		typeAlgorithmPane.setBackground(Color.LIGHT_GRAY);
		typeAlgorithmPane.addTab("Đối xứng", symmetryPane);
		typeAlgorithmPane.addTab("Bất đối xứng", new RSAPanel());
		typeAlgorithmPane.addTab("Hàm băm", hashFunction);

		frame.add(typeAlgorithmPane);

		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}
