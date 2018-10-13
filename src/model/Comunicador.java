package model;

public class Comunicador {
	private boolean iniciarServer;
	private boolean alive;
	private int porta;
	private String desconectarCliente;
	private String msgErro = "";
	private boolean conectado;
	
	public boolean isIniciarServer() {
		return iniciarServer;
	}

	public void setIniciarServer(boolean iniciarServer) {
		this.iniciarServer = iniciarServer;
	}

	public boolean isAlive() {
		return alive;
	}

	public void setAlive(boolean alive) {
		this.alive = alive;
	}

	public int getPorta() {
		return porta;
	}

	public void setPorta(int porta) {
		this.porta = porta;
	}

	public String getDesconectarCliente() {
		return desconectarCliente;
	}

	public void setDesconectarCliente(String desconectarCliente) {
		this.desconectarCliente = desconectarCliente;
	}

	public String getMsgErro() {
		String msg = msgErro;
		msgErro = "";
		return msg;
	}

	public void setMsgErro(String msgErro) {
		this.msgErro = msgErro;
	}

	public boolean isConectado() {
		return conectado;
	}

	public void setConectado(boolean conectado) {
		this.conectado = conectado;
	}
}
