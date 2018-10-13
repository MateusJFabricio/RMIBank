package controller;

import java.util.ArrayList;

import model.Model;

public class Controller {
	private Model model;
	
	public Controller()
	{
		model = new Model();
	}
	
	
	public ArrayList<String> novaConexao(String conta, String ip, String porta, String senha) {
		return model.novaConexao(conta, ip, porta, senha);
	}


	public ArrayList<String> criarConta(String conta, String ip, String porta, String senha, String nome) {
		return model.criarConta(conta, ip, porta, senha, nome);
	}


	public void desconectarCliente(String conta) {
		model.desconectarCliente(conta);
	}


	public void depositar(String conta, double valor) {
		model.depositar(conta, valor);
	}


	public void sacar(String conta, double valor) {
		model.sacar(conta, valor);
	}


	public boolean transferencia(String conta, String contaDestino, double valor) {
		return model.transferencia(conta, contaDestino, valor);
	}


	public String extrato(String conta) {
		return model.extrato(conta);
	}


	public String saldo(String conta) {
		return model.saldo(conta);
	}

}
