package views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class ViewTransferencia extends JDialog {

	private static final long serialVersionUID = 1L;
	private JTextField txtContaDestino;
	private JTextField txtValor;
	public String conta;
	public double valor;
	public int codAcao = 0;

	public ViewTransferencia() {
		setResizable(false);
		setModal(true);
		setBounds(100, 100, 226, 139);
		getContentPane().setLayout(null);
		{
			JLabel lblContaDestino = new JLabel("Conta Destino:");
			lblContaDestino.setHorizontalAlignment(SwingConstants.RIGHT);
			lblContaDestino.setBounds(10, 11, 89, 14);
			getContentPane().add(lblContaDestino);
		}
		{
			JLabel lblValorr = new JLabel("Valor (R$):");
			lblValorr.setHorizontalAlignment(SwingConstants.RIGHT);
			lblValorr.setBounds(10, 38, 89, 14);
			getContentPane().add(lblValorr);
		}
		{
			txtContaDestino = new JTextField();
			txtContaDestino.setBounds(120, 8, 86, 20);
			getContentPane().add(txtContaDestino);
			txtContaDestino.setColumns(10);
		}
		{
			txtValor = new JTextField();
			txtValor.setColumns(10);
			txtValor.setBounds(120, 35, 86, 20);
			getContentPane().add(txtValor);
		}
		{
			JButton btnConfirmar = new JButton("Confirmar");
			btnConfirmar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					actConfirmar();
				}
			});
			btnConfirmar.setBounds(10, 75, 89, 23);
			getContentPane().add(btnConfirmar);
		}
		{
			JButton btnCancelar = new JButton("Cancelar");
			btnCancelar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					codAcao = 0;
					dispose();
				}
			});
			btnCancelar.setBounds(117, 75, 89, 23);
			getContentPane().add(btnCancelar);
		}
	}

	protected void actConfirmar() {
		if(txtContaDestino.getText().isEmpty())
		{
			JOptionPane.showMessageDialog(this, "Digite a conta destino");
			return;
		}
		
		if (txtValor.getText().isEmpty())
		{
			JOptionPane.showMessageDialog(this, "Digite o valor");
			return;
		}
		
		conta = txtContaDestino.getText();
		valor = Double.parseDouble(txtValor.getText());
		codAcao = 1;
		dispose();
	}

}
