package model;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.DriverManager;
import com.mysql.jdbc.Driver;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import dataUnit.Footballer;
import view.View;

public class DatabaseHandler {
	Connection dbConnection;
	private String dbUser = "root";
	private String dbPass = "LEO170701"; 


	public Connection getDbConnection()
			throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {

		String connectionString = "jdbc:mysql://127.0.0.1:3306/footballers?useSSL=true";
		Class.forName("com.mysql.cj.jdbc.Driver").getDeclaredConstructor().newInstance();
		dbConnection = DriverManager.getConnection(connectionString, dbUser, dbPass);
		return dbConnection;
	}

	public void addFootballer(Footballer footballer)
			throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
			NoSuchMethodException, SecurityException, ClassNotFoundException {

		try (Connection connection = getDbConnection()) {

			String insertInformation = "INSERT " + Const.FOOTBALLER_TABLE + "(" + Const.FOOTBALLER_NAME + ", "
					+ Const.FOOTBALLER_DATE + ", " + Const.FOOTBALLER_TEAM + ", " + Const.FOOTBALLER_HOMETOWN + ", "
					+ Const.FOOTBALLER_CATEGORY + ", " + Const.FOOTBALLER_POSITION + ")" + "VALUE(?,?,?,?,?,?)";
			PreparedStatement statement = connection.prepareStatement(insertInformation);
			statement.setString(1, footballer.getName());
			java.sql.Date sDate = new java.sql.Date(footballer.getDate().getTime());
			statement.setDate(2, sDate);
			statement.setString(3, footballer.getTeam());
			statement.setString(4, footballer.getTown());
			statement.setString(5, footballer.getCategory());
			statement.setString(6, footballer.getPosition());

			statement.executeUpdate();
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void deleteFromTable(String deleteFromTableString) throws ClassNotFoundException, NoSuchMethodException,
			IllegalAccessException, InvocationTargetException, InstantiationException, IOException {

		try (Connection connection = getDbConnection()) {
			PreparedStatement statement = connection.prepareStatement(deleteFromTableString);
			statement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void saveInSQL(List<Footballer> list) throws ClassNotFoundException, NoSuchMethodException,
			IllegalAccessException, InvocationTargetException, InstantiationException, IOException {

		try (Connection connection = getDbConnection()) {
			String clearTable = "TRUNCATE TABLE " + Const.FOOTBALLER_TABLE;
			PreparedStatement statement = connection.prepareStatement(clearTable);
			statement.executeUpdate();
			for (Footballer footballer : list) {
				addFootballer(footballer);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public void userForLoad(View view, Model model) throws ClassNotFoundException, NoSuchMethodException,
			IllegalAccessException, InvocationTargetException, InstantiationException, IOException, SQLException {

		try (Connection connection = getDbConnection()) {

			String selectTable = "SELECT * FROM " + Const.FOOTBALLER_TABLE + ";";
			PreparedStatement statement = connection.prepareStatement(selectTable);

			ResultSet resultSet = statement.executeQuery(selectTable);
			List<Footballer> footballers = new ArrayList<Footballer>();

			while (resultSet.next()) {
				Footballer footballer = new Footballer(resultSet.getString(1), resultSet.getDate(2),
						resultSet.getString(3), resultSet.getString(4), resultSet.getString(5), resultSet.getString(6));
				footballers.add(footballer);

			}
			view.fillTheRows(model.extractBlock(view.getPageLabel(), view.getCurElemsLabel(), view.getAllElemsLabel()));
			statement.executeQuery();
			model.setFootballers(footballers);
			view.fillTheRows(model.extractBlock(view.getPageLabel(), view.getCurElemsLabel(), view.getAllElemsLabel()));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
