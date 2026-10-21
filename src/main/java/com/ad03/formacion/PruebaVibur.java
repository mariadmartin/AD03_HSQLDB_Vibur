package com.ad03.formacion;

import org.vibur.dbcp.ViburDBCPDataSource;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PruebaVibur {

	public static void main(String[] args) {

		// ### 5.- Ejercicio ###

		// Configuracion e inicio explicito del pool de conexiones
		ViburDBCPDataSource ds = new ViburDBCPDataSource();
		ds.setJdbcUrl("jdbc:hsqldb:file:data/comprobacion;shutdown=true");
		ds.setUsername("SA");
		ds.setPassword("");
		ds.setPoolInitialSize(5);
		ds.setPoolMaxSize(10);
		ds.start();

		try (Connection con = ds.getConnection()) {

			System.out.println("Conexion valida: " + con.isValid(2));

			try (Statement st = con.createStatement()) {

				st.execute("DROP TABLE IF EXISTS comprobacion");
				st.execute("CREATE TABLE comprobacion (id INT PRIMARY KEY, mensaje VARCHAR(100))");
				st.execute("INSERT INTO comprobacion (id, mensaje) VALUES (1, 'Conexion de prueba con Vibur DBCP')");

				try (ResultSet rs = st.executeQuery("SELECT id, mensaje FROM comprobacion")) {

					while (rs.next()) {
						int id = rs.getInt("id");
						String mensaje = rs.getString("mensaje");
						System.out.println("id=" + id + "  mensaje=" + mensaje);
					}
				}

			}

		} catch (SQLException e) {
			System.out.println("Error SQL (" + e.getSQLState() + "): " + e.getMessage());
		} finally {
			ds.terminate();
		}

	}

}
