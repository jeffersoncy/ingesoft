package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class conectaDB {

    private String user, pass, urlConectar, driver;
    public Connection conection;
    private java.sql.Statement statement;
    
    public conectaDB()
    {
        
    }
    
    public void conectar()
    {
        user = "postgres";
        pass = "root";
        driver = "org.postgresql.Driver";
        urlConectar = "jdbc:postgresql://localhost/Prueba";
        try {
            Class.forName(driver);
            conection = DriverManager.getConnection(urlConectar, user, pass);
            System.out.println("Si se conecta a la DB");
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (SQLException ex)
        {
            ex.printStackTrace();
            System.err.println("No conecto a la db");
        }
    }
    
    public void cerrarConexion(){
        try {
            conection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public ResultSet retornarconsultas(String cadConsulta)
    {
        ResultSet resultado= null;
        try {
            statement = conection.createStatement();
            resultado = statement.executeQuery(cadConsulta);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return resultado;
    }
    
    public void cierraResultado(ResultSet resultado)
    {
        try {
            resultado.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    public boolean transaccion(String cadenaSql)
    {
        try {
            statement = conection.createStatement();
            statement.execute(cadenaSql);
            statement.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }
    
    
    
    //PROBAR SI SE CONECTA
    public static void main(String arsg[])
    {
        conectaDB cc = new conectaDB();
        cc.conectar();
        String consulta = "insert into abogados values(13,'Jefferson','avenida siempre viva',32123123,'Trabajo');";
        String consultaEliminar = "delete from abogados where abogado_dni = 13;";
        String consultaEliminar2 = "delete from abogados where abogado_dni = 14;";
        if(!(cc.transaccion(consultaEliminar)))
            System.out.println("exito");
        else 
            System.out.println("error");
    }
}
