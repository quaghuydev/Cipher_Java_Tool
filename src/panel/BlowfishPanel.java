package panel;

import java.awt.Font;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.File;
import java.util.Base64;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import model.Blowfish;

public class BlowfishPanel extends JPanel {

	private JTextField txtKey, txtInput, txtOutput, txtPathSrc, txtPathDes;
	private JFileChooser fileChooser;
	private JFileChooser fileExport;
	private Blowfish blowfish = new Blowfish();
	private SecretKey key = null;
	private JTextField keyString;
	private JComboBox<String> limitKey;

	public SecretKey convertStringToSecretKey(String key) {

		byte[] encodedKey = Base64.getDecoder().decode(key);
		DESKeySpec keySpec;
		SecretKey secretKey = null;
		try {
			keySpec = new DESKeySpec(encodedKey);
			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("Blowfish");
			secretKey = keyFactory.generateSecret(keySpec);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return secretKey;
	}

	public boolean isLimitBlowfish(int limitKey) {

		if (limitKey >= 32 && limitKey <= 448) {

			return true;
		}

		return false;
	}

	public boolean isKeyBlowfish(String key, int limitKey) {
		try {
			byte[] bytes = key.getBytes();
			int keyLength = bytes.length * 8;
			if (keyLength == limitKey) {

				return true;
			}
		} catch (IllegalArgumentException e) {
			return false;
		}
		return false;
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

	public BlowfishPanel() {
		setLayout(null);
//length key
		JLabel lblNewLabel = new JLabel("Độ dài key:");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel.setBounds(37, 57, 74, 37);
		add(lblNewLabel);
//key
		JLabel lblKey = new JLabel("Key:");
		lblKey.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblKey.setBounds(192, 62, 28, 26);
		add(lblKey);

		txtKey = new JTextField();
		txtKey.setBounds(225, 68, 96, 19);
		add(txtKey);
		txtKey.setColumns(10);
		txtKey.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {

			}

			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub
				keyString.setVisible(false);

			}
		});
		// path
		txtPathSrc = new JTextField();
		txtPathSrc.setBounds(403, 145, 86, 19);
		add(txtPathSrc);
		txtPathSrc.setColumns(10);
		txtPathSrc.setVisible(false);

		txtPathDes = new JTextField();
		txtPathDes.setColumns(10);
		txtPathDes.setBounds(403, 233, 86, 19);
		add(txtPathDes);
		txtPathDes.setVisible(false);

		keyString = new JTextField();
		keyString.setBounds(225, 93, 96, 19);
		add(keyString);
		keyString.setColumns(10);
		keyString.setVisible(false);
		keyString.setEditable(false);
//title
		JLabel lbTitle = new JLabel("Blowfish");
		lbTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lbTitle.setFont(new Font("Tahoma", Font.BOLD, 20));
		lbTitle.setBounds(225, 21, 100, 26);
		add(lbTitle);
		// key
		limitKey = new JComboBox();
		limitKey.setModel(new DefaultComboBoxModel(new String[] { "32", "64", "128", "192", "256", "448" }));
		limitKey.setBounds(114, 68, 57, 19);
		add(limitKey);
		JButton btnCreateKey = new JButton("Tạo key tự động");
		btnCreateKey.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String valueKey = limitKey.getSelectedItem().toString();
				key = blowfish.createKey(Integer.parseInt(valueKey));
				System.out.println(valueKey);
				keyString.setVisible(true);
				keyString.setText(Base64.getEncoder().encodeToString(key.getEncoded()));
				JOptionPane.showMessageDialog(null, "Tạo key thành công", "Thông báo", JOptionPane.INFORMATION_MESSAGE);

			}
		});
		btnCreateKey.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnCreateKey.setBounds(342, 62, 147, 26);
		add(btnCreateKey);
//input
		JLabel lblInput = new JLabel("Input");
		lblInput.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblInput.setBounds(37, 116, 47, 26);
		add(lblInput);

		txtInput = new JTextField();
		txtInput.setBounds(114, 122, 279, 19);
		add(txtInput);
		txtInput.setColumns(10);

		JLabel lbHintInput = new JLabel("(Nhập chuỗi hoặc chọn tệp muốn mã hóa - chọn 1)");
		lbHintInput.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lbHintInput.setBounds(114, 142, 279, 13);
		add(lbHintInput);

		JLabel lblOutput = new JLabel("Output");
		lblOutput.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblOutput.setBounds(37, 173, 47, 26);
		add(lblOutput);

		JTextArea txtOutput = new JTextArea();
		txtOutput.setBounds(114, 176, 279, 50);
		add(txtOutput);
		txtOutput.setEditable(false);

		JLabel lblnuMHa = new JLabel("(Nếu mã hóa chuỗi sẽ hiện ra ở đây or đường dẫn file)");
		lblnuMHa.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblnuMHa.setBounds(108, 236, 285, 13);
		add(lblnuMHa);

		JButton btnSaoChp = new JButton("Sao chép");
		btnSaoChp.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnSaoChp.setBounds(403, 174, 86, 25);
		add(btnSaoChp);
		btnSaoChp.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String textToCopy = txtOutput.getText();
				StringSelection selection = new StringSelection(textToCopy);
				Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
				clipboard.setContents(selection, null);
				JOptionPane.showMessageDialog(null, "Văn bản đã được sao chép.");
			}
		});

		// chon file
		JButton btnChooseFile = new JButton("Chọn tệp");
		btnChooseFile.addActionListener(new ActionListener() {
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
		btnChooseFile.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnChooseFile.setBounds(403, 118, 86, 25);
		add(btnChooseFile);

		JButton btnSave = new JButton("Vị trí lưu");
		btnSave.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnSave.setBounds(403, 201, 86, 25);
		add(btnSave);
		btnSave.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub

				fileExport = new JFileChooser();
				fileExport.setFileSelectionMode(JFileChooser.SAVE_DIALOG);
				int returnValue = fileExport.showOpenDialog(null);

				if (returnValue == JFileChooser.APPROVE_OPTION) {
					System.out.println(fileExport.getSelectedFile());
					File selectedFile = fileExport.getSelectedFile();

					System.out.println("Selected file: " + selectedFile.getAbsolutePath());
					txtPathDes.setText(selectedFile.getAbsolutePath());
					txtPathDes.setEditable(false);
					txtPathDes.setVisible(true);

				}
			}
		});
		// btn ma hoa
		JButton btnEncrypt = new JButton("Mã hóa");
		btnEncrypt.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnEncrypt.setBounds(166, 259, 86, 25);
		add(btnEncrypt);
		btnEncrypt.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (txtKey.getText().equals("") && key == null) {
					JOptionPane.showMessageDialog(null, "Vui lòng nhập key or tạo key", "Thông báo",
							JOptionPane.WARNING_MESSAGE);
				}
				if (!txtKey.getText().equals("")) {
					String keyString = txtKey.getText();
					String valueKey = limitKey.getSelectedItem().toString();
					if (isKeyBlowfish(txtKey.getText(), Integer.parseInt(valueKey))) {
						if (fileChooser == null && txtInput.getText().equals("")) {
							JOptionPane.showMessageDialog(null, "vui lòng chọn file or nhập chuỗi cần mã hóa",
									"Thông báo", JOptionPane.WARNING_MESSAGE);
						}
						if (fileExport == null && fileChooser != null) {
							JOptionPane.showMessageDialog(null, "vui lòng chọn vị trí lưu file", "Thông báo",
									JOptionPane.WARNING_MESSAGE);
						}
						if (fileExport != null && fileChooser != null) {
							try {

								blowfish.encryptFile(fileChooser.getSelectedFile().getAbsolutePath(),
										creatPath("Blowfish_encrypt", fileChooser, fileExport), keyString);
								System.out.println("đã mã hóa");
								fileChooser=null; 
								txtPathSrc.setText("");
								txtPathSrc.setVisible(false);
								JOptionPane.showMessageDialog(null, "Đã mã hóa", "Thông báo",
										JOptionPane.WARNING_MESSAGE);

							} catch (Exception e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}

						if (!txtInput.getText().equals("")) {
							try {
								txtOutput.setText(blowfish.encryptToBase64(txtInput.getText(), keyString));
								System.out.println(blowfish.encryptToBase64(txtInput.getText(), keyString));
							} catch (Exception e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
					} else {
						JOptionPane.showMessageDialog(null, "key không hợp lệ, vui lòng nhập đủ " + valueKey + "bit",
								"Thông báo", JOptionPane.WARNING_MESSAGE);
					}

				}
				if (key != null) {
					if (fileChooser == null && txtInput.getText().equals("")) {
						JOptionPane.showMessageDialog(null, "vui lòng chọn file or nhập chuỗi cần mã hóa", "Thông báo",
								JOptionPane.WARNING_MESSAGE);
					}
					if (fileExport == null && fileChooser != null) {
						JOptionPane.showMessageDialog(null, "vui lòng chọn vị trí lưu file", "Thông báo",
								JOptionPane.WARNING_MESSAGE);
					}
					if (fileExport != null && fileChooser != null) {
						try {

							blowfish.encryptFile(fileChooser.getSelectedFile().getAbsolutePath(),
									creatPath("Blowfish_encrypt", fileChooser, fileExport), key);
							JOptionPane.showMessageDialog(null, "Đã mã hóa", "Thông báo", JOptionPane.WARNING_MESSAGE);
							System.out.println("đã mã hóa");
							fileChooser=null; 
							txtPathSrc.setText("");
							txtPathSrc.setVisible(false);

						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}

					if (!txtInput.getText().equals("")) {
						try {
							txtOutput.setText(blowfish.encryptToBase64(txtInput.getText(), key));
							System.out.println(blowfish.encryptToBase64(txtInput.getText(), key));
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}
			}

		});
		// btn giai ma
		JButton btnDecrypt = new JButton("Giải mã");
		btnDecrypt.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnDecrypt.setBounds(314, 259, 86, 25);
		add(btnDecrypt);

		btnDecrypt.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (txtKey.getText().equals("") && key == null) {
					JOptionPane.showMessageDialog(null, "Vui lòng nhập key or tạo key", "Thông báo",
							JOptionPane.WARNING_MESSAGE);
				}
				if (!txtKey.getText().equals("")) {
					String keyString = txtKey.getText();
					String valueKey = limitKey.getSelectedItem().toString();
					if (isKeyBlowfish(txtKey.getText(), Integer.parseInt(valueKey))) {
						if (fileChooser == null && txtInput.getText().equals("")) {
							JOptionPane.showMessageDialog(null, "vui lòng chọn file or nhập chuỗi cần giải mã",
									"Thông báo", JOptionPane.WARNING_MESSAGE);
						}
						if (fileExport == null && fileChooser != null) {
							JOptionPane.showMessageDialog(null, "vui lòng chọn vị trí lưu file", "Thông báo",
									JOptionPane.WARNING_MESSAGE);
						}
						if (fileExport != null && fileChooser != null) {
							try {

								blowfish.decryptFile(fileChooser.getSelectedFile().getAbsolutePath(),
										creatPath("Blowfish_decrypt", fileChooser, fileExport), keyString);
								System.out.println("đã giải hóa");
								fileChooser=null; 
								txtPathSrc.setText("");
								txtPathSrc.setVisible(false);
								JOptionPane.showMessageDialog(null, "Đã giải mã", "Thông báo",
										JOptionPane.WARNING_MESSAGE);
							} catch (Exception e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}

						if (!txtInput.getText().equals("")) {
							try {
								txtOutput.setText(blowfish.decryptFromBase64(txtInput.getText(), keyString));
								System.out.println(blowfish.decryptFromBase64(txtInput.getText(), keyString));
							} catch (Exception e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
					} else {
						JOptionPane.showMessageDialog(null, "key không hợp lệ, vui lòng nhập đủ" + valueKey + "bit",
								"Thông báo", JOptionPane.WARNING_MESSAGE);
					}

				}
				if (key != null) {
					if (fileChooser == null && txtInput.getText().equals("")) {
						JOptionPane.showMessageDialog(null, "vui lòng chọn file or nhập chuỗi cần mã hóa", "Thông báo",
								JOptionPane.WARNING_MESSAGE);
					}
					if (fileExport == null && fileChooser != null) {
						JOptionPane.showMessageDialog(null, "vui lòng chọn vị trí lưu file", "Thông báo",
								JOptionPane.WARNING_MESSAGE);
					}
					if (fileExport != null && fileChooser != null) {
						try {

							blowfish.decryptFile(fileChooser.getSelectedFile().getAbsolutePath(),
									creatPath("Blowfish_decrypt", fileChooser, fileExport), key);
							System.out.println("đã giai ma");
							fileChooser=null; 
							txtPathSrc.setText("");
							txtPathSrc.setVisible(false);
							JOptionPane.showMessageDialog(null, "Đã giải mã", "Thông báo", JOptionPane.WARNING_MESSAGE);

						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}

					if (!txtInput.getText().equals("")) {
						try {
							txtOutput.setText(blowfish.decryptFromBase64(txtInput.getText(), key));
							System.out.println(blowfish.decryptFromBase64(txtInput.getText(), key));
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
				}

			}
		});

	}
}
