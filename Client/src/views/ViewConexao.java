package views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class ViewConexao extends JDialog {
	private static final long serialVersionUID = 1L;
	private JTextField txtConta;
	private JPasswordField password;
	private JButton btnConectar;
	private JLabel lblIP;
	private JTextField txtIP;
	private JLabel lblNome_1;
	private JTextField txtNome;
	private JButton btnNovaConta;
	public int tipoResposta = 0; //1 - Conectar | 2 - nova conta | 0 - cancelado
	public String senha;
	public String nome;
	public String ip;
	public String conta;
	public String porta;
	private JTextField txtPorta;

	public ViewConexao() {
		setResizable(false);
		setModal(true);
		setTitle("Acessar conta");
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 351, 188);
		getContentPane().setLayout(null);
		{
			JLabel lblNome = new JLabel("Conta:");
			lblNome.setBounds(10, 42, 46, 14);
			getContentPane().add(lblNome);
		}
		{
			JLabel lblSenha = new JLabel("Senha:");
			lblSenha.setBounds(10, 77, 46, 14);
			getContentPane().add(lblSenha);
		}
		{
			txtConta = new JTextField();
			txtConta.setBounds(56, 39, 86, 20);
			getContentPane().add(txtConta);
			txtConta.setColumns(10);
		}
		{
			password = new JPasswordField(20);
			password.setBounds(56, 74, 86, 20);
			getContentPane().add(password);
			password.setColumns(10);
		}
		{
			btnConectar = new JButton("Conectar");
			btnConectar.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					actConectar();
				}
			});
			btnConectar.setBounds(182, 10, 129, 41);
			getContentPane().add(btnConectar);
		}
		{
			lblIP = new JLabel("IP:");
			lblIP.setBounds(10, 109, 46, 14);
			getContentPane().add(lblIP);
		}
		{
			txtIP = new JTextField();
			txtIP.setColumns(10);
			txtIP.setBounds(56, 106, 86, 20);
			getContentPane().add(txtIP);
		}
		{
			lblNome_1 = new JLabel("Nome:");
			lblNome_1.setBounds(10, 13, 46, 14);
			getContentPane().add(lblNome_1);
		}
		{
			txtNome = new JTextField();
			txtNome.setColumns(10);
			txtNome.setBounds(56, 10, 86, 20);
			getContentPane().add(txtNome);
		}
		{
			btnNovaConta = new JButton("Nova conta");
			btnNovaConta.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					actNovaConta();
				}
			});
			btnNovaConta.setBounds(182, 110, 129, 41);
			getContentPane().add(btnNovaConta);
		}
		
		JLabel lblPorta = new JLabel("Porta:");
		lblPorta.setBounds(10, 137, 46, 14);
		getContentPane().add(lblPorta);
		
		txtPorta = new JTextField();
		txtPorta.setText("9000");
		txtPorta.setColumns(10);
		txtPorta.setBounds(56, 134, 86, 20);
		getContentPane().add(txtPorta);
	}

	@SuppressWarnings("deprecation")
	protected void actNovaConta() {
		if (txtConta.getText().isEmpty())
		{
			JOptionPane.showMessageDialog(this, "Digite a conta");
			return;
		}
		
		if (txtIP.getText().isEmpty())
		{
			JOptionPane.showMessageDialog(this, "Digite o IP do servidor");
			return;
		}
		
		if (txtPorta.getText().isEmpty())
		{
			JOptionPane.showMessageDialog(this, "Digite a porta do servidor");
			return;
		}
		
		if (password.getPassword().toString().isEmpty())
		{
			JOptionPane.showMessageDialog(this, "Digite a senha");
			return;
		}
		
		if (txtNome.getText().isEmpty())
		{
			JOptionPane.showMessageDialog(this, "Digite um nome");
			return;
		}
		
		nome = txtNome.getText();
		ip = txtIP.getText();
		conta = txtConta.getText();
		senha = password.getText();
		porta = txtPorta.getText();
		
		tipoResposta = 2;
		this.dispose();
	}

	@SuppressWarnings("deprecation")
	protected void actConectar() {
		
		if (txtConta.getText().isEmpty())
		{
			JOptionPane.showMessageDialog(this, "Digite a conta");
			return;
		}
		
		if (txtIP.getText().isEmpty())
		{
			JOptionPane.showMessageDialog(this, "Digite o IP do servidor");
			return;
		}
		
		if (txtPorta.getText().isEmpty())
		{
			JOptionPane.showMessageDialog(this, "Digite a porta do servidor");
			return;
		}
		
		if (password.getPassword().toString().isEmpty())
		{
			JOptionPane.showMessageDialog(this, "Digite a senha");
			return;
		}
		
		nome = txtNome.getText();
		ip = txtIP.getText();
		conta = txtConta.getText();
		senha = password.getText();
		porta = txtPorta.getText();
		
		tipoResposta = 1;
		this.dispose();
		
	}
}
