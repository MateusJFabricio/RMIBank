package model;

import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import Main.Main;
import model.DAO.Cliente;

public class Model {
	private TaskMain task;
	private Comunicador comm;
	private ThreadPoolExecutor pool;
	
	public Model()
	{
		comm = new Comunicador();
		task = new TaskMain(comm);
		pool = new ThreadPoolExecutor(5, 10, 1, TimeUnit.HOURS, new ArrayBlockingQueue<Runnable>(10));
	}
	
	public void iniciarServer(int porta) {
		comm.setIniciarServer(true);
		comm.setPorta(porta);
		if (!comm.isAlive())
			pool.submit(task);
	}
	
	public void fecharServidor()
	{
		comm.setIniciarServer(false);
	}


	public ArrayList<Cliente> listaClientesConectados() {
		return Main.clientes;
	}

	public String numeroDeClientes() {
		return String.valueOf(Main.mannDB.numeroClientes());
	}

	public String valorInvestido() {
		return String.valueOf(Main.mannDB.valorInvestido());
	}

	public boolean isConectado() {
		return comm.isConectado();
	}

	public String getErro() {
		return comm.getMsgErro();
	}
	
}
