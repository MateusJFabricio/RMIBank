package views;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTextPane;

public class ViewExtrato extends JDialog {

	public ViewExtrato(String extrato) {
		setTitle("Extrato");
		setResizable(false);
		setModal(true);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(10, 21, 424, 240);
		getContentPane().add(panel);
		panel.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		panel.add(scrollPane, BorderLayout.CENTER);
		
		JTextPane txtpnAsd = new JTextPane();
		txtpnAsd.setText(extrato);
		scrollPane.setViewportView(txtpnAsd);
		
		JLabel lblExtratoDeConta = new JLabel("Extrato de conta bancaria");
		lblExtratoDeConta.setHorizontalAlignment(SwingConstants.CENTER);
		lblExtratoDeConta.setBounds(10, 0, 424, 21);
		getContentPane().add(lblExtratoDeConta);
		
	}


}
