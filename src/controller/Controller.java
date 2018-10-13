package controller;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import model.Model;
import model.DAO.Cliente;

public class Controller {
	private Model model;
	
	public Controller()
	{
		model = new Model();
	}
	
	public void iniciarServer(int porta)
	{
		model.iniciarServer(porta);
	}
	
	public String getIP() throws UnknownHostException {
		return InetAddress.getLocalHost().getHostAddress();
	}

	public String getNrmoClientes() {
		return model.numeroDeClientes();
	}

	public String getValorInvestido() {
		
		return model.valorInvestido();
	}

	public Object[] getListaClientesConectados() {
		ArrayList<Cliente> clientes = model.listaClientesConectados();
		
		if (clientes.size() == 0)
			return new String[] {"Sem clientes conectados"};
		String[] array = new String[clientes.size()];
		
		for (int i = 0; i < array.length; i++) {
			array[i] = "Nome: " + clientes.get(i).getNome() + " | Conta: " + clientes.get(i).getConta(); 
		}
		
		return array; 
		
	}

	public boolean validaPorta(String string) {
		try {
			Integer.parseInt(string);
		} catch (Exception e) {
			return false;
		}
		
		return true;
	}
	
	public boolean isConectado() {
		return model.isConectado();
	}

	public String getErro() {
		return model.getErro();
	}

}
