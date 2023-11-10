package panel;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import model.Hill;

public class HillPanel extends JPanel {

	private JFrame frame;
	private JTextField txtKey, txtInput, txtOutput;
	private JTextField keyString;
	private Hill hill = new Hill();
	private JComboBox cbbCharset;

	public boolean isValidHillKey(String key) {
		try {
			int keyLength = key.length();
			if (keyLength == 4) {

				return true;
			}
		} catch (IllegalArgumentException e) {
			return false;
		}
		return false;
	}

	public HillPanel() {

		setLayout(null);
//key
		JLabel lblKey = new JLabel("Key");
		lblKey.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblKey.setBounds(114, 66, 28, 26);
		add(lblKey);

		txtKey = new JTextField();
		txtKey.setBounds(191, 72, 104, 19);
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
				keyString.setText("");

			}
		});

		keyString = new JTextField();
		keyString.setBounds(323, 72, 103, 19);
		add(keyString);
		keyString.setColumns(10);
		keyString.setVisible(false);
		keyString.setEditable(false);
//title
		cbbCharset = new JComboBox();
		cbbCharset.setModel(new DefaultComboBoxModel(new String[] { "Vie", "Eng" }));
		cbbCharset.setBounds(191, 106, 67, 21);
		add(cbbCharset);

		JLabel lbTitle = new JLabel("Hill");
		lbTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lbTitle.setFont(new Font("Tahoma", Font.BOLD, 20));
		lbTitle.setBounds(248, 21, 47, 26);
		add(lbTitle);
		JButton btnCreateKey = new JButton("Tạo key");
		btnCreateKey.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String charset = cbbCharset.getSelectedItem().toString();
				if (charset.equalsIgnoreCase("vie")) {
					keyString.setText(hill.genKeyVie());
					keyString.setVisible(true);
				}
				if (charset.equalsIgnoreCase("Eng")) {
					keyString.setText(hill.genKeyEng());
					keyString.setVisible(true);
				}
				if (keyString.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Vui lòng thử lại!", "Thông báo", JOptionPane.WARNING_MESSAGE);
				}
			}
		});
		btnCreateKey.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnCreateKey.setBounds(323, 106, 103, 22);
		add(btnCreateKey);
//input
		JLabel lblInput = new JLabel("Input");
		lblInput.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblInput.setBounds(114, 138, 47, 20);
		add(lblInput);

		txtInput = new JTextField();
		txtInput.setBounds(191, 138, 241, 19);
		add(txtInput);
		txtInput.setColumns(10);

		JLabel lbHintInput = new JLabel("(Nhập chuỗi hoặc chọn tệp muốn mã hóa - chọn 1)");
		lbHintInput.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lbHintInput.setBounds(191, 158, 279, 13);
		add(lbHintInput);

		JLabel lblOutput = new JLabel("Output");
		lblOutput.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblOutput.setBounds(114, 181, 47, 26);
		add(lblOutput);

		JTextArea txtOutput = new JTextArea();
		txtOutput.setBounds(191, 184, 241, 50);
		add(txtOutput);
		txtOutput.setEditable(false);

		JLabel lblnuMHa = new JLabel("(Nếu mã hóa chuỗi sẽ hiện ra ở đây or đường dẫn file)");
		lblnuMHa.setFont(new Font("Tahoma", Font.PLAIN, 10));
		lblnuMHa.setBounds(185, 244, 262, 13);
		add(lblnuMHa);
		// btn ma hoa
		JButton btnEncrypt = new JButton("Mã hóa");
		btnEncrypt.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnEncrypt.setBounds(191, 267, 86, 25);
		add(btnEncrypt);
		btnEncrypt.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (keyString.getText().equals("") && txtKey.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Vui lòng nhập key or tạo key", "Thông báo",
							JOptionPane.WARNING_MESSAGE);
				}
				if (!keyString.getText().equals("")) {
					String charset = cbbCharset.getSelectedItem().toString();
					if (charset.equalsIgnoreCase("vie")) {
						if (!txtInput.getText().equals("")) {
							String cipher = hill.encryptVie(txtInput.getText(), keyString.getText());
							if (!cipher.equals("")) {
								txtOutput.setText(cipher);
							} else {
								JOptionPane.showMessageDialog(null, "key không hợp lệ!", "Thông báo",
										JOptionPane.WARNING_MESSAGE);
							}
						} else {
							JOptionPane.showMessageDialog(null, "vui lòng nhập văn bản muốn mã hóa", "Thông báo",
									JOptionPane.WARNING_MESSAGE);
						}
					}
					if (charset.equalsIgnoreCase("eng")) {
						if (!txtInput.getText().equals("")) {
							String cipher = hill.encryptEng(txtInput.getText(), keyString.getText());
							if (!cipher.equals("")) {
								txtOutput.setText(cipher);
							} else {
								JOptionPane.showMessageDialog(null, "key không hợp lệ!", "Thông báo",
										JOptionPane.WARNING_MESSAGE);
							}
						} else {
							JOptionPane.showMessageDialog(null, "vui lòng nhập văn bản muốn mã hóa", "Thông báo",
									JOptionPane.WARNING_MESSAGE);
						}
					}
				}
				if (!txtKey.getText().equals("")) {
					if (isValidHillKey(txtKey.getText())) {
						String charset = cbbCharset.getSelectedItem().toString();
						if (charset.equalsIgnoreCase("vie")) {
							if (!txtInput.getText().equals("")) {
								String cipher = hill.encryptVie(txtInput.getText(), txtKey.getText());
								if (!cipher.equals("")) {
									txtOutput.setText(cipher);
								} else {
									JOptionPane.showMessageDialog(null, "key không hợp lệ!", "Thông báo",
											JOptionPane.WARNING_MESSAGE);
								}
							} else {
								JOptionPane.showMessageDialog(null, "vui lòng nhập văn bản muốn mã hóa", "Thông báo",
										JOptionPane.WARNING_MESSAGE);
							}
						}
						if (charset.equalsIgnoreCase("eng")) {
							if (!txtInput.getText().equals("")) {
								String cipher = hill.encryptEng(txtInput.getText(), txtKey.getText());
								if (!cipher.equals("")) {
									txtOutput.setText(cipher);
								} else {
									JOptionPane.showMessageDialog(null, "key không hợp lệ!", "Thông báo",
											JOptionPane.WARNING_MESSAGE);
								}
							} else {
								JOptionPane.showMessageDialog(null, "vui lòng nhập văn bản muốn mã hóa", "Thông báo",
										JOptionPane.WARNING_MESSAGE);
							}
						}
					} else {
						JOptionPane.showMessageDialog(null, "key vui lòng nhập đủ 4 ký tự", "Thông báo",
								JOptionPane.WARNING_MESSAGE);
					}
				} else {
					JOptionPane.showMessageDialog(null, "key không hợp lệ", "Thông báo", JOptionPane.WARNING_MESSAGE);
				}

			}
		});
		// btn giai ma
		JButton btnDecrypt = new JButton("Giải mã");
		btnDecrypt.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnDecrypt.setBounds(342, 267, 86, 25);
		add(btnDecrypt);

		JLabel lblBng = new JLabel("Bảng mã");
		lblBng.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblBng.setBounds(114, 102, 58, 26);
		add(lblBng);

		btnDecrypt.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if (keyString.getText().equals("") && txtKey.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Vui lòng nhập key or tạo key", "Thông báo",
							JOptionPane.WARNING_MESSAGE);
				}
				if (!keyString.getText().equals("")) {
					String charset = cbbCharset.getSelectedItem().toString();
					if (charset.equalsIgnoreCase("vie")) {
						if (!txtInput.getText().equals("")) {
							String cipher = hill.decryptVie(txtInput.getText(), keyString.getText());
							if (!cipher.equals("")) {
								txtOutput.setText(cipher);
							} else {
								JOptionPane.showMessageDialog(null, "key không hợp lệ!", "Thông báo",
										JOptionPane.WARNING_MESSAGE);
							}
						} else {
							JOptionPane.showMessageDialog(null, "vui lòng nhập văn bản muốn mã hóa", "Thông báo",
									JOptionPane.WARNING_MESSAGE);
						}
					}
					if (charset.equalsIgnoreCase("eng")) {
						if (!txtInput.getText().equals("")) {
							String cipher = hill.decryptEng(txtInput.getText(), keyString.getText());
							if (!cipher.equals("")) {
								txtOutput.setText(cipher);
							} else {
								JOptionPane.showMessageDialog(null, "key không hợp lệ!", "Thông báo",
										JOptionPane.WARNING_MESSAGE);
							}
						} else {
							JOptionPane.showMessageDialog(null, "vui lòng nhập văn bản muốn mã hóa", "Thông báo",
									JOptionPane.WARNING_MESSAGE);
						}
					}
				}
				if (!txtKey.getText().equals("")) {
					if (isValidHillKey(txtKey.getText())) {
						String charset = cbbCharset.getSelectedItem().toString();
						if (charset.equalsIgnoreCase("vie")) {
							if (!txtInput.getText().equals("")) {
								String cipher = hill.decryptVie(txtInput.getText(), txtKey.getText());
								if (!cipher.equals("")) {
									txtOutput.setText(cipher);
								} else {
									JOptionPane.showMessageDialog(null, "key không hợp lệ!", "Thông báo",
											JOptionPane.WARNING_MESSAGE);
								}
							} else {
								JOptionPane.showMessageDialog(null, "vui lòng nhập văn bản muốn mã hóa", "Thông báo",
										JOptionPane.WARNING_MESSAGE);
							}
						}
						if (charset.equalsIgnoreCase("eng")) {
							if (!txtInput.getText().equals("")) {
								String cipher = hill.decryptEng(txtInput.getText(), txtKey.getText());
								if (!cipher.equals("")) {
									txtOutput.setText(cipher);
								} else {
									JOptionPane.showMessageDialog(null, "key không hợp lệ!", "Thông báo",
											JOptionPane.WARNING_MESSAGE);
								}
							} else {
								JOptionPane.showMessageDialog(null, "vui lòng nhập văn bản muốn mã hóa", "Thông báo",
										JOptionPane.WARNING_MESSAGE);
							}
						}
					}

				} else {
					JOptionPane.showMessageDialog(null, "vui lòng nhập 4 ký tự", "Thông báo",
							JOptionPane.WARNING_MESSAGE);
				}

			}
		});

	}
}
