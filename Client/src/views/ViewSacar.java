package views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class ViewSacar extends JDialog {

	private static final long serialVersionUID = 1L;
	private JTextField txtValor;
	public double valor;
	public int codAcao = 0; 
	
	public ViewSacar() {
		setResizable(false);
		setModal(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setTitle("Sacar");
		setBounds(100, 100, 253, 108);
		getContentPane().setLayout(null);
		{
			JLabel lblValorASer = new JLabel("Valor a ser sacado(R$):");
			lblValorASer.setBounds(10, 11, 120, 14);
			getContentPane().add(lblValorASer);
		}
		{
			txtValor = new JTextField();
			txtValor.setBounds(140, 8, 86, 20);
			getContentPane().add(txtValor);
			txtValor.setColumns(10);
		}
		{
			JButton btnConfirmar = new JButton("Confirmar");
			btnConfirmar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					actConfirmar();
				}
			});
			btnConfirmar.setBounds(10, 45, 89, 23);
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
			btnCancelar.setBounds(140, 45, 89, 23);
			getContentPane().add(btnCancelar);
		}
	}

	protected void actConfirmar() {
		if (txtValor.getText().isEmpty())
		{
			JOptionPane.showMessageDialog(this, "Digite um valor para saque");
			return;
		}
		
		valor = Double.parseDouble(txtValor.getText());
		codAcao = 1;
		dispose();
	}

}
