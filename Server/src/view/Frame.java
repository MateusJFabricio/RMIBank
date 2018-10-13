package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.net.UnknownHostException;

import javax.swing.AbstractListModel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import controller.Controller;

public class Frame extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Controller control;
	private JLabel lblValorInvestido;
	private JLabel lblNmroClientes;
	private JLabel lblPorta;
	private JLabel lblMeuIp;
	private JToggleButton btnIniciarServer;
	private JLabel lblServidorOffline;
	private JTextField txtPorta;
	private Timer monitor;
	private ActionListener actMonitor;
	private JList<Object> list;
	
	public Frame() {
		control = new Controller();
		setTitle("RMI Bank - Server");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 440, 292);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel.setBounds(0, 27, 434, 235);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_1.setBounds(0, 0, 217, 235);
		panel.add(panel_1);
		panel_1.setLayout(null);
		
		btnIniciarServer = new JToggleButton("Iniciar Server");
		btnIniciarServer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (btnIniciarServer.isSelected())
					actIniciarServer();
				else
					actFecharServer();
					
			}
		});
		btnIniciarServer.setBounds(10, 164, 197, 60);
		panel_1.add(btnIniciarServer);
		
		lblMeuIp = new JLabel("Meu IP:");
		lblMeuIp.setBounds(10, 22, 197, 14);
		panel_1.add(lblMeuIp);
		
		lblPorta = new JLabel("Porta:");
		lblPorta.setBounds(10, 139, 51, 14);
		panel_1.add(lblPorta);
		
		lblNmroClientes = new JLabel("Nmro Clientes:");
		lblNmroClientes.setBounds(10, 47, 197, 14);
		panel_1.add(lblNmroClientes);
		
		lblValorInvestido = new JLabel("Valor Investido:");
		lblValorInvestido.setBounds(10, 72, 197, 14);
		panel_1.add(lblValorInvestido);
		
		txtPorta = new JTextField();
		txtPorta.addFocusListener(new FocusAdapter() {
			@Override
			public void focusLost(FocusEvent arg0) {
				validarPorta();
			}
		});
		txtPorta.setText("9000");
		txtPorta.setBounds(71, 136, 115, 20);
		panel_1.add(txtPorta);
		txtPorta.setColumns(10);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(227, 30, 197, 170);
		panel.add(scrollPane);
		
		list = new JList<Object>();
		list.setModel(new AbstractListModel() {
			String[] values = new String[] {};
			public int getSize() {
				return values.length;
			}
			public Object getElementAt(int index) {
				return values[index];
			}
		});
		scrollPane.setViewportView(list);
		
		JLabel lblClientesConectados = new JLabel("Clientes conectados");
		lblClientesConectados.setBounds(225, 13, 199, 14);
		panel.add(lblClientesConectados);
		
		JLabel lblRmiBankServer = new JLabel("RMI Bank Server");
		lblRmiBankServer.setHorizontalAlignment(SwingConstants.CENTER);
		lblRmiBankServer.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblRmiBankServer.setBounds(25, 0, 192, 27);
		contentPane.add(lblRmiBankServer);
		
		lblServidorOffline = new JLabel("Servidor Offline");
		lblServidorOffline.setForeground(Color.BLUE);
		lblServidorOffline.setHorizontalAlignment(SwingConstants.CENTER);
		lblServidorOffline.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblServidorOffline.setBounds(218, 0, 192, 27);
		contentPane.add(lblServidorOffline);
		
		inicializarDados();
	}

	private void validarPorta()
	{
		if (!control.validaPorta(txtPorta.getText()))
		{
			JOptionPane.showMessageDialog(this, "Numero de porta invalida");
			txtPorta.setText("1212");
		}
	}
	protected void actFecharServer() {
		btnIniciarServer.setText("Iniciar server");
		lblServidorOffline.setText("Servidor Offline");
		lblServidorOffline.setForeground(Color.BLUE);
		monitor.stop();
		btnIniciarServer.setSelected(false);
	}
	protected void actIniciarServer() {
		btnIniciarServer.setText("Fechar server");
		lblServidorOffline.setText("Iniciando servidor");
		lblServidorOffline.setForeground(Color.RED);
		control.iniciarServer(Integer.parseInt(txtPorta.getText()));
		monitor.start();
	}
	private void inicializarDados() {
		 try {
			lblMeuIp.setText("Meu IP: " + control.getIP());
		} catch (UnknownHostException e) {
			JOptionPane.showMessageDialog(this, "Não foi possivel carregar o IP da maquina. Certifique-se de estar conectado");
		}
		lblNmroClientes.setText("Nmro de Clientes: " + control.getNrmoClientes());
		lblValorInvestido.setText("Valor investido: " + control.getValorInvestido()); 
		list.setListData(control.getListaClientesConectados());
		iniciaMonitor();
	}
	
	private void iniciaMonitor() {
		actMonitor = new ActionListener() {
			public void actionPerformed(java.awt.event.ActionEvent e) {
				atualizarClientes();
		}};
		
		monitor = new Timer(1000, actMonitor);
		
	}
	protected void atualizarClientes() {
		lblNmroClientes.setText("Nmro de Clientes: " + control.getNrmoClientes());
		lblValorInvestido.setText("Valor investido: " + control.getValorInvestido());
		list.setListData(control.getListaClientesConectados());
		if(control.isConectado())
			lblServidorOffline.setText("Servidor online");
		else 
		{
			String msg = control.getErro();
			if(msg.isEmpty())
			{
				if (lblServidorOffline.getText().endsWith("..."))
					lblServidorOffline.setText("Iniciando servidor");
				else
					lblServidorOffline.setText(lblServidorOffline.getText() + "...");
			}else
			{
				JOptionPane.showMessageDialog(this, "Erro:"+msg);
				actFecharServer();
			}
		}
	}
}
