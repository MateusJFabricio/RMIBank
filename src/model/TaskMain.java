package model;

import java.rmi.AccessException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.Registry;

public class TaskMain implements Runnable {
	private Comunicador comm;
	private Registry r;
	public TaskMain(Comunicador comm) {
		this.comm = comm;
	}

	@Override
	public void run() {
		comm.setConectado(false);
		try
		{
			r = java.rmi.registry.LocateRegistry.createRegistry(comm.getPorta());
			r.rebind("RMIBank", new OperacoesBancarias()); 
			comm.setConectado(true);		
		} catch (RemoteException e) {
			comm.setMsgErro(e.getMessage());
			comm.setConectado(false);
		}
	}


}
