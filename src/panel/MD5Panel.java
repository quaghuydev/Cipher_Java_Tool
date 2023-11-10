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

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import model.MD5;
public class MD5Panel extends JPanel{

	private JTextField txtInput;
	private JTextField txtHashCode;
	private JTextField txtPath1;
	private JTextField txtPath2;
	private JTextArea txtOutput;
	private JFileChooser fileChooser1, fileChooser2;
	private MD5 md5 = new MD5();

	public MD5Panel() {

		setLayout(null);
		JLabel lblTitle = new JLabel("MD5");
		lblTitle.setFont(new Font("Tahoma", Font.BOLD, 22));
		lblTitle.setBounds(184, 10, 63, 29);
		add(lblTitle);

		JLabel lbInput = new JLabel("Input");
		lbInput.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lbInput.setBounds(27, 55, 45, 13);
		add(lbInput);

		JLabel lbOutput = new JLabel("Output");
		lbOutput.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lbOutput.setBounds(27, 106, 45, 13);
		add(lbOutput);

		txtInput = new JTextField();
		txtInput.setBounds(91, 53, 188, 19);
		add(txtInput);
		txtInput.setColumns(10);
		txtInput.addFocusListener(new FocusListener() {

			@Override
			public void focusLost(FocusEvent e) {

			}

			@Override
			public void focusGained(FocusEvent e) {
				// TODO Auto-generated method stub
				fileChooser1 = null;
				txtPath1.setText("");
				txtPath1.setVisible(false);
				txtOutput.setText(null);

			}
		});

		txtOutput = new JTextArea();
		txtOutput.setBounds(91, 101, 188, 50);
		txtOutput.setEditable(false);
		txtOutput.setLineWrap(true); // Bật kiểm soát dòng
		txtOutput.setWrapStyleWord(true);
		add(txtOutput);

		JLabel lbHint = new JLabel("vui lòng nhập chuỗi or chọn tệp (chỉ chọn 1)");
		lbHint.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lbHint.setBounds(91, 76, 284, 19);
		add(lbHint);

		txtPath1 = new JTextField();
		txtPath1.setBounds(290, 132, 99, 19);
		txtPath1.setEditable(false);
		txtPath1.setVisible(false);
		add(txtPath1);
		txtPath1.setColumns(10);

		txtPath2 = new JTextField();
		txtPath2.setBounds(290, 233, 99, 19);
		txtPath2.setEditable(false);
		txtPath2.setVisible(false);
		add(txtPath2);
		txtPath2.setColumns(10);

		JButton btnFile1 = new JButton("Chọn tệp");
		btnFile1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				txtPath1.setVisible(true);
				txtPath1.setEditable(false);
				txtOutput.setText(null);
				txtInput.setText("");
				fileChooser1 = new JFileChooser();
				fileChooser1.setFileSelectionMode(JFileChooser.FILES_ONLY);
				int returnValue = fileChooser1.showOpenDialog(null);

				if (returnValue == JFileChooser.APPROVE_OPTION) {
					File selectedFile = fileChooser1.getSelectedFile();
					System.out.println("Selected file: " + selectedFile.getAbsolutePath());
					txtPath1.setText(selectedFile.getAbsolutePath());

				}
			}
		});
		btnFile1.setBounds(290, 103, 99, 21);
		add(btnFile1);

		JButton btnHash = new JButton("Hash");
		btnHash.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!txtInput.getText().equals("") && fileChooser1 != null) {
					JOptionPane.showMessageDialog(null, "Vui lòng nhập chuỗi or chọn file (chỉ chọn 1)", "Thông báo",
							JOptionPane.WARNING_MESSAGE);
				}
				if (!txtInput.getText().equals("") && fileChooser1 == null) {
					String hashCode = md5.convertStringToHash(txtInput.getText());
					txtOutput.setText(hashCode);

				}
				if (txtInput.getText().equals("") && fileChooser1 != null) {
					File selectedFile = fileChooser1.getSelectedFile();
					System.out.println("Selected file: " + selectedFile.getAbsolutePath());
					String hashCode = md5.convertFileToHash(selectedFile.getAbsolutePath());
					txtOutput.setText(hashCode);

				}

			}
		});
		btnHash.setBounds(194, 161, 85, 21);
		add(btnHash);

		JButton btnCopy = new JButton("Sao chép ");
		btnCopy.setBounds(89, 161, 95, 21);
		add(btnCopy);
		btnCopy.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String textToCopy = txtOutput.getText();
				StringSelection selection = new StringSelection(textToCopy);
				Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
				clipboard.setContents(selection, null);
				JOptionPane.showMessageDialog(null, "Văn bản đã được sao chép.");

			}
		});

		JLabel lbCheckFile = new JLabel("Check file");
		lbCheckFile.setFont(new Font("Tahoma", Font.BOLD, 12));
		lbCheckFile.setBounds(27, 183, 63, 13);
		add(lbCheckFile);

		JLabel lbHash = new JLabel("Mã hash");
		lbHash.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lbHash.setBounds(27, 210, 53, 13);
		add(lbHash);

		txtHashCode = new JTextField();
		txtHashCode.setBounds(96, 208, 183, 19);
		add(txtHashCode);
		txtHashCode.setColumns(10);

		JButton btnFile2 = new JButton("Chọn tệp");
		btnFile2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				fileChooser2 = new JFileChooser();
				fileChooser2.setFileSelectionMode(JFileChooser.FILES_ONLY);
				int returnValue = fileChooser2.showOpenDialog(null);

				if (returnValue == JFileChooser.APPROVE_OPTION) {
					File selectedFile = fileChooser2.getSelectedFile();
					System.out.println("Selected file: " + selectedFile.getAbsolutePath());
					txtPath2.setText(selectedFile.getAbsolutePath());
					txtPath2.setVisible(true);
					txtPath2.setEditable(false);
				}
			}
		});
		btnFile2.setBounds(290, 207, 99, 21);
		add(btnFile2);

		JButton btnCheck = new JButton("Kiểm tra");
		btnCheck.setBounds(149, 232, 85, 21);
		add(btnCheck);

		btnCheck.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				if (fileChooser2 == null) {
					JOptionPane.showMessageDialog(null, "Vui lòng chọn file để kiểm tra", "Thông báo",
							JOptionPane.WARNING_MESSAGE);

				}
				if (txtHashCode.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Vui lòng nhập mã hash để kiểm tra", "Thông báo",
							JOptionPane.WARNING_MESSAGE);

				}
				if (fileChooser2 != null && !txtHashCode.getText().equals("")) {
					File selectedFile = fileChooser1.getSelectedFile();
					boolean isNotChange = md5.checkFile(selectedFile.getAbsolutePath(), txtHashCode.getText());
					if (isNotChange) {
						JOptionPane.showMessageDialog(null, "Tệp không bị thay đổi", "Thông báo",
								JOptionPane.WARNING_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(null, "Tệp đã bị thay đổi", "Thông báo",
								JOptionPane.WARNING_MESSAGE);
					}
				}
				// TODO Auto-generated method stub

			}
		});

	}
}
