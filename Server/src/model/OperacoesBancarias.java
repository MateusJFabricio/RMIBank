package model;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import model.DAO.Cliente;

public class OperacoesBancarias extends UnicastRemoteObject implements IOperacoesBancarias {

	private static final long serialVersionUID = 1L;
	

	protected OperacoesBancarias() throws RemoteException 
	{
		super();
	}
	@Override
	public boolean novaConta(String nome, String senha, String conta) throws RemoteException {
		return Main.Main.mannDB.novaConta(nome, senha, conta);
	}

	@Override
	public ArrayList<String> loginConta(String conta, String senha) throws RemoteException
	{
		ArrayList<String> c = Main.Main.mannDB.loginConta(conta, senha);
		if (!(c == null))
		{
			Cliente cli = new Cliente();
			cli.setNome (retornaConteudo(c, 0)); //Pega o nome
			cli.setConta(retornaConteudo(c, 1)); //Pega a conta
			cli.setSenha(retornaConteudo(c, 2)); //Pega a senha
			cli.setSaldo (Double.parseDouble(retornaConteudo(c, 3))); //Pega o saldo
			
			Main.Main.clientes.add(cli);
		}
		return c;
	}
	
	private String retornaConteudo(ArrayList<String> t, int indice)
	{
		return t.get(indice).substring(t.get(indice).indexOf(":")+1, t.get(indice).length());
	}

	@Override
	public boolean buscarConta(String nrmoConta) throws RemoteException {
		return Main.Main.mannDB.buscarConta(nrmoConta);
	}

	@Override
	public double buscarSaldo(String nrmoConta) throws RemoteException {
		return Main.Main.mannDB.saldoConta(nrmoConta);
	}

	@Override
	public String extrato(String nrmoConta) throws RemoteException {
		return Main.Main.mannDB.extratoConta(nrmoConta);
	}

	@Override
	public boolean transferir(String nrmoConta, String  nrmoContaDestino, double valor, String descricao) throws RemoteException {
		return Main.Main.mannDB.transferir(nrmoConta, nrmoContaDestino, valor, descricao);
	}

	@Override
	public void depositar(String nrmoConta, double valor) throws RemoteException {
		Main.Main.mannDB.depositar(nrmoConta, valor);
	}

	@Override
	public void sacar(String nrmoConta, double valor) throws RemoteException {
		Main.Main.mannDB.sacar(nrmoConta, valor);
	}

	@Override
	public void desconectar(String nrmoConta) throws RemoteException {
		Main.Main.mannDB.desconectar();
		for (Cliente cliente : Main.Main.clientes) {
			if (cliente.getConta().equals(nrmoConta))
			{
				Main.Main.clientes.remove(cliente);
				return;
			}
		}
	}

}
