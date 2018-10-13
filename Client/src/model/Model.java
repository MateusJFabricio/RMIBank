package model;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;

public class Model {
	private IOperacoesBancarias oper;
	
	public ArrayList<String> novaConexao(String conta, String ip, String porta, String senha) {
		try {
			oper = (IOperacoesBancarias)Naming.lookup("//" + ip + ":"+porta+"/RMIBank");
			ArrayList<String> cliente = new ArrayList<String>();
			cliente = oper.loginConta(conta, senha);
			return cliente;
		} catch (MalformedURLException | RemoteException | NotBoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	public ArrayList<String> criarConta(String conta, String ip, String porta, String senha, String nome) {
		try {
			oper = (IOperacoesBancarias)Naming.lookup("//" + ip + ":"+porta+"/RMIBank");
			if (oper.novaConta(nome, senha, conta))
			{
				return oper.loginConta(conta, senha);
			}
			return null;
		} catch (MalformedURLException | RemoteException | NotBoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	public void desconectarCliente(String conta) {
		try {
			oper.desconectar(conta);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void depositar(String conta, double valor) {
		try {
			oper.depositar(conta, valor);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public void sacar(String conta, double valor) {
		try {
			oper.sacar(conta, valor);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}

	public boolean transferencia(String conta, String contaDestino, double valor) {
		try {
			return oper.transferir(conta, contaDestino, valor, "Sou bom");
		} catch (RemoteException e) {
			e.printStackTrace();
			return false;
		}
	}

	public String extrato(String conta) {
		try {
			return oper.extrato(conta);
		} catch (RemoteException e) {
			e.printStackTrace();
			return null;
		}
	}

	public String saldo(String conta) {
		try {
			return String.valueOf(oper.buscarSaldo(conta));
		} catch (RemoteException e) {
			e.printStackTrace();
			return null;
		}
	}

}
