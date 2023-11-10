package panel;

import java.awt.Font;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import model.RSA;

public class RSAPanel extends JPanel {

	private JFrame frame;
	private JTextField txtInput, txtPathSrc;
	private JComboBox cbbLimitKey;
	private JTextArea txtOutput, txtPublicKey, txtPrivateKey;
	private JButton btnCreateKey, btnDecrypt, btnEncrypt, btnSave, btnFile;
	private JFileChooser fileChooser, fileExport;
	private RSA rsa = new RSA();
	private JButton btnCopy;
	private JTextField textField;
	private JTextField txtPathDes;

	public boolean isPublicKey(String publicKeyString) {
		try {
			byte[] publicKeyBytes = publicKeyString.getBytes();
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
			PublicKey publicKey = keyFactory.generatePublic(publicKeySpec);
			// Nếu chuyển đổi thành công, trả về true
			return true;
		} catch (Exception e) {
			// Nếu xảy ra lỗi, trả về false
			return false;
		}
	}

	public boolean isPrivateKey(String privateKeyString) {
		try {
			byte[] privateKeyBytes = privateKeyString.getBytes();
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(privateKeyBytes);
			PrivateKey privateKey = keyFactory.generatePrivate(publicKeySpec);
			// Nếu chuyển đổi thành công, trả về true
			return true;
		} catch (Exception e) {
			// Nếu xảy ra lỗi, trả về false
			return false;
		}
	}

	public String creatPath(String name, JFileChooser fileChoose, JFileChooser fileExport) {
		int index = 0;
		String srcFilePath = fileChoose.getSelectedFile().getAbsolutePath();
		int indexSrc = srcFilePath.lastIndexOf(".");
		String ext = srcFilePath.substring(indexSrc);

		String desFile = fileExport.getSelectedFile().getAbsolutePath() + "/" + name + ext;
		File file = new File(desFile);
		while (file.exists()) {
			index += 1;
			desFile = fileExport.getSelectedFile().getAbsolutePath() + "/" + name + "(" + index + ")" + ext;
			file = new File(desFile);
		}
		return desFile;
	}

	public RSAPanel() {

		JLabel lblTile = new JLabel("RSA");
		lblTile.setBounds(215, 31, 47, 25);
		lblTile.setFont(new Font("Tahoma", Font.BOLD, 20));

		txtPublicKey = new JTextArea();
		txtPublicKey.setBounds(87, 139, 331, 46);
		txtPublicKey.setLineWrap(true); // Bật kiểm soát dòng
		txtPublicKey.setWrapStyleWord(true);

		txtPrivateKey = new JTextArea();
		txtPrivateKey.setBounds(87, 216, 331, 46);
		txtPrivateKey.setLineWrap(true); // Bật kiểm soát dòng
		txtPrivateKey.setWrapStyleWord(true);

		JLabel lblPublicKey = new JLabel("Public key");
		lblPublicKey.setBounds(87, 117, 68, 12);
		lblPublicKey.setFont(new Font("Tahoma", Font.BOLD, 12));

		JLabel lblPrivateKey = new JLabel("Private key");
		lblPrivateKey.setBounds(87, 195, 90, 12);
		lblPrivateKey.setFont(new Font("Tahoma", Font.BOLD, 12));

		JLabel lblKey = new JLabel("\u0110\u1ED9 d\u00E0i key");
		lblKey.setBounds(87, 74, 68, 15);
		lblKey.setFont(new Font("Tahoma", Font.BOLD, 12));

		cbbLimitKey = new JComboBox();
		cbbLimitKey.setBounds(190, 74, 90, 21);
		cbbLimitKey.setFont(new Font("Tahoma", Font.PLAIN, 12));
		cbbLimitKey.setModel(new DefaultComboBoxModel(new String[] { "512", "1024", "2048", "3072", "4096" }));

		btnCreateKey = new JButton("T\u1EA1o key");
		btnCreateKey.setBounds(319, 75, 101, 21);
		btnCreateKey.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				rsa.setLimitkey(Integer.parseInt(cbbLimitKey.getSelectedItem().toString()));
				rsa.genKey();
				txtPrivateKey.setText(rsa.exportPrivateKey(rsa.getPrivateKey()));
				txtPublicKey.setText(rsa.exportPublicKey(rsa.getPublicKey()));

			}
		});

		btnFile = new JButton("Ch\u1ECDn t\u1EC7p");
		btnFile.setBounds(317, 271, 101, 21);
		btnFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fileChooser = new JFileChooser();
				fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
				int returnValue = fileChooser.showOpenDialog(null);

				if (returnValue == JFileChooser.APPROVE_OPTION) {
					File selectedFile = fileChooser.getSelectedFile();
					System.out.println("Selected file: " + selectedFile.getAbsolutePath());
					txtPathSrc.setText(selectedFile.getAbsolutePath());
					txtPathSrc.setVisible(true);
					txtPathSrc.setEditable(false);
				}
			}
		});

		btnSave = new JButton("L\u01B0u file");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fileExport = new JFileChooser();
				fileExport.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				int returnValue = fileExport.showOpenDialog(null);

				if (returnValue == JFileChooser.APPROVE_OPTION) {
					File selectedFile = fileExport.getSelectedFile();
					System.out.println("Selected file: " + selectedFile.getAbsolutePath());
					txtPathDes.setText(selectedFile.getAbsolutePath());
					txtPathDes.setVisible(true);
					txtPathDes.setEditable(false);
				}
			}
		});
		btnSave.setBounds(317, 348, 101, 21);

		JLabel lblInput = new JLabel("Input");
		lblInput.setBounds(87, 274, 47, 12);
		lblInput.setFont(new Font("Tahoma", Font.BOLD, 12));

		txtInput = new JTextField();
		txtInput.setBounds(144, 272, 163, 19);
		txtInput.setColumns(10);

		JLabel lblOutput = new JLabel("Output");
		lblOutput.setBounds(87, 327, 47, 12);
		lblOutput.setFont(new Font("Tahoma", Font.BOLD, 12));

		txtOutput = new JTextArea();
		txtOutput.setBounds(144, 311, 163, 82);
		txtOutput.setLineWrap(true); // Bật kiểm soát dòng
		txtOutput.setWrapStyleWord(true);

		btnCopy = new JButton("Sao chép");
		btnCopy.setBounds(317, 320, 101, 25);
		btnCopy.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String textToCopy = txtOutput.getText();
				StringSelection selection = new StringSelection(textToCopy);
				Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
				clipboard.setContents(selection, null);
				JOptionPane.showMessageDialog(null, "Văn bản đã được sao chép.");

			}
		});

		txtPathSrc = new JTextField();
		txtPathSrc.setColumns(10);
		txtPathSrc.setBounds(317, 296, 101, 19);
		txtPathSrc.setEditable(false);
		txtPathSrc.setVisible(false);

		txtPathDes = new JTextField();
		txtPathDes.setBounds(317, 378, 101, 19);
		txtPathDes.setColumns(10);
		txtPathDes.setEditable(false);
		txtPathDes.setVisible(false);

		btnEncrypt = new JButton("M\u00E3 h\u00F3a");
		btnEncrypt.setBounds(87, 419, 101, 21);
		btnEncrypt.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String txtString = txtPublicKey.getText();
				// TODO Auto-generated method stub
				if (txtPublicKey.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Vui lòng tạo key or nhập public key", "Thông báo",
							JOptionPane.INFORMATION_MESSAGE);
				}
				if (!txtPublicKey.getText().equals("")) {
//					if (isPublicKey(txtString) == true) {
					try {
						String output = rsa.encrypt(txtInput.getText());
						txtOutput.setText(output);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
//					} else {
//						JOptionPane.showMessageDialog(null, "public key không hợp lệ", "Thông báo",
//								JOptionPane.INFORMATION_MESSAGE);
//					}

				}
				if (fileChooser != null && fileExport != null) {
					try {
						rsa.encryptFile(fileChooser.getSelectedFile().getAbsolutePath(),
								creatPath("RSA_encrypt", fileChooser, fileExport));
						JOptionPane.showMessageDialog(null, "Đã mã hóa thành công", "Thông báo",
								JOptionPane.INFORMATION_MESSAGE);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				if (fileChooser != null && fileExport == null) {
					JOptionPane.showMessageDialog(null, "Vui lòng chọn vị trí lưu tệp", "Thông báo",
							JOptionPane.INFORMATION_MESSAGE);
				}

			}
		});

		btnDecrypt = new JButton("Gi\u1EA3i m\u00E3");
		btnDecrypt.setBounds(317, 419, 101, 21);
		btnDecrypt.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String txtString = txtPrivateKey.getText();
				// TODO Auto-generated method stub
				// TODO Auto-generated method stub
				if (txtPrivateKey.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Vui lòng tạo key or nhập private key", "Thông báo",
							JOptionPane.INFORMATION_MESSAGE);
				}
				if (!txtPrivateKey.getText().equals("") && !txtInput.getText().equals("")) {
//					if (isPrivateKey(txtString) == true) {
					try {
						String output = rsa.decrypt(txtInput.getText());
						txtOutput.setText(output);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
//					} else {
//						JOptionPane.showMessageDialog(null, "private key không hợp lệ", "Thông báo",
//								JOptionPane.INFORMATION_MESSAGE);
//					}

				}

				if (fileChooser != null && fileExport != null) {
					try {
						rsa.decryptFile(fileChooser.getSelectedFile().getAbsolutePath(),
								creatPath("RSA_decrypt", fileChooser, fileExport));
						JOptionPane.showMessageDialog(null, "Đã giải mã thành công", "Thông báo",
								JOptionPane.INFORMATION_MESSAGE);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				if (fileChooser != null && fileExport == null) {
					JOptionPane.showMessageDialog(null, "Vui lòng chọn vị trí lưu tệp", "Thông báo",
							JOptionPane.INFORMATION_MESSAGE);
				}

			}
		});

		setLayout(null);
		add(lblTile);
		add(lblInput);
		add(txtInput);
		add(lblOutput);
		add(txtOutput);
		add(btnEncrypt);
		add(btnDecrypt);
		add(lblKey);
		add(cbbLimitKey);
		add(txtPublicKey);
		add(lblPublicKey);
		add(txtPrivateKey);
		add(lblPrivateKey);
		add(btnFile);
		add(btnSave);
		add(btnCreateKey);
		add(btnCopy);
		add(txtPathSrc);
		add(txtPathDes);

	}
}
