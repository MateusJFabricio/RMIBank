package Main;

import java.util.ArrayList;

import model.DAO.Cliente;
import model.DAO.ManagerDB;
import view.Frame;

public class Main {
	public static ManagerDB mannDB;
	public static ArrayList<Cliente> clientes;
	public static void main(String[] args) {
		try {
			clientes = new ArrayList<Cliente>();
			mannDB = new ManagerDB();
			Frame frame = new Frame();
			frame.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
