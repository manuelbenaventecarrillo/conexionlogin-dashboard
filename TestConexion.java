package yestellefuncion;

import java.sql.Connection;

public class TestConexion {
    public static void main(String[] args) {
        Connection conn = DBConnection.connect();
        if (conn != null) {
            System.out.println("Todo correcto: conexión establecida.");
        } else {
            System.out.println("No se pudo conectar.");
        }
    }
}
