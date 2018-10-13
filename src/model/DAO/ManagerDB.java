package model.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.JOptionPane;

public class ManagerDB {
	private Connection conexaoBD;
	
	public void getStatement() {
		String URL = "jdbc:sqlite:RMIBank.db";
	
		conexaoBD = null;
        try {
        	conexaoBD = DriverManager.getConnection(URL);
        	conexaoBD.setAutoCommit(true);
        } catch (SQLException e) {
        	 JOptionPane.showMessageDialog(null,"Nao foi possivel estabelecer conexao com o banco de dados. A aplicacao sera encerrada. Motivo: " + e.getMessage());
        	 System.exit(0);
        }
	}
	
	public ManagerDB()
	{
		getStatement();
	}
	
	public boolean novaConta(String nome, String senha, String conta)
	{
		try {
			if (conexaoBD.isClosed())
				getStatement();
			conexaoBD.createStatement().executeUpdate("INSERT INTO CLIENTE(CONTA, NOME, SENHA) VALUES( '" + conta + "','"+ nome +"','"+ senha+"')");
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}

	public ArrayList<String> loginConta(String conta, String senha)
	{
		ResultSet r;
		ArrayList<String> cliente;
		
		try {
			if (conexaoBD.isClosed())
				getStatement();
			r = conexaoBD.createStatement().executeQuery("SELECT * FROM CLIENTE WHERE CONTA = '"+ conta +"' AND SENHA = '"+ senha + "'");
			if (r.next())
			{
				cliente = new ArrayList<String>();
				cliente.add("NOME:" + r.getString("NOME")); //NOME
				cliente.add("CONTA:" + r.getString("CONTA")); //CONTA
				cliente.add("SENHA:" + r.getString("SENHA")); //SENHA
				cliente.add("SALDO:" + String.valueOf(r.getDouble("SALDO"))); //SALDO
				return cliente;
			}			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
	public boolean buscarConta(String nrmoConta) {	
		ResultSet r;
		
		try {
			if (conexaoBD.isClosed())
				getStatement();
			r = conexaoBD.createStatement().executeQuery("SELECT COUNT(*) AS N FROM CLIENTE WHERE CONTA = '"+ nrmoConta +"'");
			return r.getInt("N") > 0;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	public double saldoConta(String nrmoConta) {
		ResultSet r;
		
		try {
			
			if (conexaoBD.isClosed())
				getStatement();
			r = conexaoBD.createStatement().executeQuery("SELECT SALDO FROM CLIENTE WHERE CONTA = '"+ nrmoConta +"'");
			return r.getDouble("SALDO");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return 0.0;
	}

	public String extratoConta(String nrmoConta) {
		ResultSet r;
		String resultado = "";
		
		try {
			if (conexaoBD.isClosed())
				getStatement();
			r = conexaoBD.createStatement().executeQuery("SELECT OPERACAO, DATAHORA FROM HISTORICO WHERE CONTA = '"+ nrmoConta +"'");
			
			while(r.next())
			{
				resultado = resultado + r.getString("OPERACAO") + ", na data: "+ r.getString("DATAHORA") + ";\n";
			}
			
			return resultado;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return resultado;
	}

	public boolean transferir(String nrmoConta,String nrmoContaDestino, double valor, String descricao) {
		ResultSet r;
		double saldoCliente;
		double saldoClienteDestino;
		
		try {
			if (conexaoBD.isClosed())
				getStatement();
			r = conexaoBD.createStatement().executeQuery("SELECT SALDO FROM CLIENTE WHERE CONTA = '"+ nrmoConta +"'");
			saldoCliente = r.getDouble("SALDO");
			r = conexaoBD.createStatement().executeQuery("SELECT SALDO FROM CLIENTE WHERE CONTA = '"+ nrmoContaDestino +"'");
			saldoClienteDestino = r.getDouble("SALDO");
			
			conexaoBD.createStatement().executeUpdate("UPDATE CLIENTE SET SALDO = " + String.valueOf(saldoCliente - valor)  + " WHERE CONTA = '"+ nrmoConta +"'");
			conexaoBD.createStatement().executeUpdate("UPDATE CLIENTE SET SALDO = " + String.valueOf(saldoClienteDestino + valor)  + " WHERE CONTA = '"+ nrmoContaDestino +"'");
			conexaoBD.createStatement().executeUpdate("INSERT INTO HISTORICO(CONTA, OPERACAO, DATAHORA) VALUES( '" + nrmoConta + "',' Transferencia de : R$ " + String.valueOf(valor) + " para a conta: "+ nrmoContaDestino +"','"+ dataHoraAtual() +"')");
			conexaoBD.createStatement().executeUpdate("INSERT INTO HISTORICO(CONTA, OPERACAO, DATAHORA) VALUES( '" + nrmoContaDestino + "',' Depositado o valor: R$ " + String.valueOf(valor) + " pela conta: "+ nrmoConta +"','"+ dataHoraAtual() +"')");
			
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	private String dataHoraAtual()
	{
		return Calendar.getInstance().get(Calendar.DATE) + "/" + Calendar.getInstance().get(Calendar.MONTH) + "/" + Calendar.getInstance().get(Calendar.YEAR) + " " + Calendar.getInstance().get(Calendar.HOUR) + ":" + Calendar.getInstance().get(Calendar.MINUTE);
	}

	public void depositar(String nrmoConta, double valor) {
		ResultSet r;
		double saldoCliente;
		
		try {
			if (conexaoBD.isClosed())
				getStatement();
			r = conexaoBD.createStatement().executeQuery("SELECT SALDO FROM CLIENTE WHERE CONTA = '"+ nrmoConta +"'");
			saldoCliente = r.getDouble("SALDO");
			
			conexaoBD.createStatement().executeUpdate("UPDATE CLIENTE SET SALDO = " + String.valueOf(saldoCliente + valor)  + " WHERE CONTA = '"+ nrmoConta +"'");
			conexaoBD.createStatement().executeUpdate("INSERT INTO HISTORICO(CONTA, OPERACAO, DATAHORA) VALUES( '" + nrmoConta + "',' Depositado o valor: R$ " + String.valueOf(valor) +"','"+ dataHoraAtual() +"')");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	public void sacar(String nrmoConta, double valor) {
		ResultSet r;
		double saldoCliente;
		
		try {
			if (conexaoBD.isClosed())
				getStatement();
			r = conexaoBD.createStatement().executeQuery("SELECT SALDO FROM CLIENTE WHERE CONTA = '"+ nrmoConta +"'");
			saldoCliente = r.getDouble("SALDO");
			
			conexaoBD.createStatement().executeUpdate("UPDATE CLIENTE SET SALDO = " + String.valueOf(saldoCliente - valor)  + " WHERE CONTA = '"+ nrmoConta +"'");
			conexaoBD.createStatement().executeUpdate("INSERT INTO HISTORICO(CONTA, OPERACAO, DATAHORA) VALUES( '" + nrmoConta + "',' Sacado o valor: R$ " + String.valueOf(valor) +"','"+ dataHoraAtual() +"')");
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void desconectar()
	{
		try
        {
			conexaoBD.close();
        }
        catch(SQLException fecha)
        {
       	 JOptionPane.showMessageDialog(null, "Houve um problema com o banco de dados. Problema: " +  fecha.getMessage());
       	 System.exit(0);
        }
	}
	
	@Override
	protected void finalize() throws Throwable {
		desconectar();
		super.finalize();
	}

	public int numeroClientes() {
		ResultSet r;
		int nmro = 0;
		try {
			if (conexaoBD.isClosed())
				getStatement();
			r = conexaoBD.createStatement().executeQuery("SELECT * FROM CLIENTE ");
			
			while(r.next())
			{
				nmro++;
			}
			
			return nmro;
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}

	public double valorInvestido() {
		ResultSet r;
		double nmro = 0;
		try {
			if (conexaoBD.isClosed())
				getStatement();
			r = conexaoBD.createStatement().executeQuery("SELECT SALDO FROM CLIENTE ");
			
			while(r.next())
			{
				nmro += r.getDouble("SALDO");
			}
			
			return nmro;
		} catch (SQLException e) {
			e.printStackTrace();
			return 0;
		}
	}
	
}
