package Kamil215691.ZPO.LAB12.Zad1;

import java.sql.*;

public class ModelConnector {
    private final static String url = "jdbc:mysql://localhost:3306/zpo";
    private final static String user = "root";

    private Connection connection = null;
    public ModelConnector(){

    }

    public boolean connectToDB(String passwd){
        boolean rV = true;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(url,user,passwd);
        }
        catch(SQLException s){
            rV = false;
            System.out.println(s.getMessage());
        }
        catch (ClassNotFoundException c){
            rV = false;
        }
        return rV;
    }

    public void sortRecords(String colName){
        try(PreparedStatement statement = connection.prepareStatement(String.format("SELECT Name, LastName, Country, Salary FROM zpo.Employees ORDER BY %s;", colName))) {
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                System.out.println(rs.getString(1) +" "+ rs.getString(2) + " " + rs.getString(3) + " " + rs.getInt(4) );
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void getAverageSalary(String country){
        try(PreparedStatement statement = connection.prepareStatement("SELECT AVG(Salary) from zpo.Employees where Country = ?;")){
            statement.setString(1, country);

            ResultSet rs = statement.executeQuery();

            if(!rs.next()){
                System.out.println("Country: " + country + " not found in database!");
            }
            else{
                System.out.println("Average salary in: " + country + " equals: " + rs.getDouble(1));
            }
            rs.close();
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean insertRecord(String name, String lastName, Integer salary, String country){
        boolean rV = true;
        try(PreparedStatement statement = connection.prepareStatement("SELECT COUNT(ID) \"SUM\" FROM zpo.Employees WHERE Name = ? AND LastName = ? AND Country = ? AND Salary = ?;")) {
            statement.setString(1, name);
            statement.setString(2, lastName);
            statement.setString(3, country);
            statement.setString(4, String.valueOf(salary));
            ResultSet rs = statement.executeQuery();

            if(!rs.next() || rs.getInt(1) > 0){
                rV = false;
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
            rV = false;
        }
        if(rV) {
            try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO zpo.Employees (Name,LastName, Country,Salary) VALUES (?,?,?,?);")) {
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, lastName);
                preparedStatement.setString(3, country);
                preparedStatement.setString(4, String.valueOf(salary));
                int val = preparedStatement.executeUpdate();
                System.out.println(val);
            } catch (SQLException e) {
                e.printStackTrace();
                rV = false;
            }
        }

        return rV;
    }

    public void cleanUp(){
        try {
            if(connection != null)
                connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
