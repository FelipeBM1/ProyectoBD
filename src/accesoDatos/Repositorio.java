package accesoDatos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import negocio.Constantes;

public class Repositorio {

    private Connection conex;
    private PreparedStatement ps;
    private ResultSet rs;

    public void iniciarConexion() {
        try {
            conex = DriverManager.getConnection(
                    Constantes.THINCONN,
                    Constantes.USERNAME,
                    Constantes.PASSWORD);

        } catch (SQLException ex) {
            System.out.println("Error de conexion:" + ex.toString());
        }
    }

    public void commit() {
        try {
            conex.commit();
        } catch (SQLException ex) {
            System.out.println("Error al hacer commit" + ex.toString());
        }
    }

    public boolean login(String usuario, String contraseña) {
        String SQL = "select usuario,contraseña from usuario";
        iniciarConexion();
        try {
            ps = conex.prepareStatement(SQL);
            rs = ps.executeQuery();

            while (rs.next()) {

                if (usuario.equals(rs.getString("usuario"))
                        && contraseña.equals(rs.getString("contraseña"))) {
                    return true;
                }
            }
            return false;
        } catch (SQLException ex) {
            System.out.println("Error en login");
        }

        return false;
    }
}
