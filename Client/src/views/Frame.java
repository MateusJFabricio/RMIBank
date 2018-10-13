package views;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import controller.Controller;
import model.Cliente;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Frame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Controller control;
	private JLabel lblConta;
	private JLabel lblSaldo;
	private JButton btnConexao;
	private JButton btnDepositar;
	private JButton btnTransferir;
	private JButton btnExtrato;
	private JButton btnSacar;
	private JButton btnDesconectar;
	private Cliente cliente;
	private Timer timer;
	private ActionListener actMonitor;

	public Frame() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				if (btnDesconectar.isEnabled())
					actDesconectar();
			}
		});
		setResizable(false);
		control = new Controller();
		cliente = new Cliente();
		setTitle("RMI Bank");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel.setBounds(10, 11, 414, 34);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblDescConta = new JLabel("Conta:");
		lblDescConta.setBounds(10, 11, 46, 14);
		panel.add(lblDescConta);
		
		JLabel lblDescSaldo = new JLabel("Saldo:");
		lblDescSaldo.setBounds(216, 11, 46, 14);
		panel.add(lblDescSaldo);
		
		lblConta = new JLabel("Se conecte ao servidor");
		lblConta.setBounds(65, 11, 154, 14);
		panel.add(lblConta);
		
		lblSaldo = new JLabel("Se conecte ao servidor");
		lblSaldo.setBounds(260, 11, 154, 14);
		panel.add(lblSaldo);
		
		btnConexao = new JButton("Conectar");
		btnConexao.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				actConectar();
			}
		});
		btnConexao.setBounds(10, 54, 194, 59);
		contentPane.add(btnConexao);
		
		btnDepositar = new JButton("Depositar");
		btnDepositar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actDepositar();
			}
		});
		btnDepositar.setEnabled(false);
		btnDepositar.setBounds(10, 124, 194, 59);
		contentPane.add(btnDepositar);
		
		btnTransferir = new JButton("Transferir");
		btnTransferir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actTransferir();
			}
		});
		btnTransferir.setEnabled(false);
		btnTransferir.setBounds(10, 192, 194, 59);
		contentPane.add(btnTransferir);
		
		btnExtrato = new JButton("Extrato");
		btnExtrato.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actExtrato();
			}
		});
		btnExtrato.setEnabled(false);
		btnExtrato.setBounds(230, 192, 194, 59);
		contentPane.add(btnExtrato);
		
		btnSacar = new JButton("Sacar");
		btnSacar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actSacar();
			}
		});
		btnSacar.setEnabled(false);
		btnSacar.setBounds(230, 124, 194, 59);
		contentPane.add(btnSacar);
		
		btnDesconectar = new JButton("Desconectar");
		btnDesconectar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				actDesconectar();
			}
		});
		btnDesconectar.setEnabled(false);
		btnDesconectar.setBounds(230, 54, 194, 59);
		contentPane.add(btnDesconectar);
		iniciaMonitorEmergencia();
	}


	protected void actExtrato() {
		ViewExtrato e = new ViewExtrato(control.extrato(cliente.getConta()));
		e.setVisible(true);
	}


	protected void actTransferir() {
		ViewTransferencia t = new ViewTransferencia();
		t.setVisible(true);
		
		if (t.codAcao == 1)
		{
			if (control.transferencia(cliente.getConta(), t.conta, t.valor))
				JOptionPane.showMessageDialog(this, "Transferido com sucesso");
			else
				JOptionPane.showMessageDialog(this, "Nao foi possivel efetuar a transferencia");
		}
				
	}


	protected void actSacar() {
		ViewSacar s = new ViewSacar();
		s.setVisible(true);
		if (s.codAcao == 1)
			control.sacar(cliente.getConta(), s.valor);
	}


	protected void actDepositar() {
		ViewDepositar d = new ViewDepositar();
		d.setVisible(true);
		if (d.codAcao == 1)
			control.depositar(cliente.getConta(), d.valor);
	}


	protected void actDesconectar() {
		control.desconectarCliente(cliente.getConta());
		desabilitarBotoes();
		timer.stop();
		lblConta.setText("Se conecte ao servidor");
		lblSaldo.setText("Se conecte ao servidor");
	}


	protected void actConectar() {
		ArrayList<String> cliente =  new ArrayList<String>();
		ViewConexao con =  new ViewConexao();
		con.setVisible(true);
		switch (con.tipoResposta) {
		case 1:
			cliente = control.novaConexao(con.conta, con.ip, con.porta, con.senha);
			if(!(cliente == null))
			{
				transferirDados(cliente);
				habilitarBotoes();
				timer.start();
			}else
			{
				JOptionPane.showMessageDialog(this, "Não foi possivel se conectar");
			}
			break;
		case 2:
			cliente = control.criarConta(con.conta, con.ip, con.porta, con.senha, con.nome);
			if (!(cliente == null))
			{
				transferirDados(cliente);
				habilitarBotoes();
			}else
			{
				JOptionPane.showMessageDialog(this, "Não foi possivel criar conta");
			}
			break;
		default:
			break;
		}
	}


	private void habilitarBotoes() {
		btnConexao.setEnabled(false);
		btnDepositar.setEnabled(true);
		btnDesconectar.setEnabled(true);
		btnExtrato.setEnabled(true);
		btnSacar.setEnabled(true);
		btnTransferir.setEnabled(true);
	}
	
	private void desabilitarBotoes()
	{
		btnConexao.setEnabled(true);
		btnDepositar.setEnabled(false);
		btnDesconectar.setEnabled(false);
		btnExtrato.setEnabled(false);
		btnSacar.setEnabled(false);
		btnTransferir.setEnabled(false);
	}


	private void transferirDados(ArrayList<String> c) {
		String mod, val;
		for (String string : c) {
			mod = string.substring(0, string.indexOf(":"));
			val = string.substring(string.indexOf(":") + 1, string.length());
			if (mod.equals("NOME"))
			{
				cliente.setNome(val);
			}
			
			if (mod.equals("CONTA"))
			{
				cliente.setConta(val);
			}
			
			if (mod.equals("SALDO"))
			{
				cliente.setSaldo(Double.parseDouble(val));
			}
			
			if (mod.equals("SENHA"))
			{
				cliente.setSenha(val);
			}
		}
		
		lblConta.setText(cliente.getConta());
		lblSaldo.setText(String.valueOf(cliente.getSaldo()));
	}
	
	private void iniciaMonitorEmergencia() {
		actMonitor = new ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				lblSaldo.setText(control.saldo(cliente.getConta()));
		}};
		
		timer = new Timer(1000, actMonitor);
	}
}
