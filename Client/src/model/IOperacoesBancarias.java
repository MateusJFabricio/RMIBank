package model;


import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

interface IOperacoesBancarias extends Remote
{
	public boolean novaConta(String nome, String senha, String conta) throws RemoteException; 
	public ArrayList<String> loginConta(String conta, String senha) throws RemoteException;
	public boolean buscarConta(String nrmoConta) throws RemoteException; //retorna o ID da conta
	public double buscarSaldo(String  nrmoConta) throws RemoteException; //retorna o valor do saldo na conta
	public String extrato(String  nrmoConta) throws RemoteException; //retorna o historico das operacoes bancarias
	public boolean transferir(String  nrmoConta, String  nrmoContaDestino, double valor,String descricao) throws RemoteException; //retorna se foi possivel fazer a operacao
	public void depositar(String  nrmoConta, double valor) throws RemoteException;
	public void sacar(String  nrmoConta, double valor) throws RemoteException; //retorna se foi possivel fazer a operacao
	public void desconectar(String  nrmoConta) throws RemoteException;
	
}