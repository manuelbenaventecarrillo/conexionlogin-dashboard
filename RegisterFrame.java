package yestellefuncion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class RegisterFrame extends JFrame {

    // Campos de texto y contraseña para el formulario
    private JTextField nombreField, apellidosField, emailField, telefonoField;
    private JComboBox<String> prefijoBox;
    private JPasswordField contrasenaField;

    public RegisterFrame() {
        // Configuración de la ventana
        setTitle("Ye - Registro");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // solo cierra esta ventana
        setLocationRelativeTo(null); // centra la ventana
        setLayout(new GridBagLayout());
        getContentPane().setBackground(Color.WHITE); // fondo blanco

        // Configuración de constraints para GridBagLayout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // espacio entre componentes
        gbc.fill = GridBagConstraints.HORIZONTAL; // ocupa todo el ancho

        // Campos del formulario
        JLabel nombreLabel = new JLabel("Nombre:");
        gbc.gridx = 0; gbc.gridy = 0;
        add(nombreLabel, gbc);
        nombreField = new JTextField(20);
        gbc.gridx = 1;
        add(nombreField, gbc);

        JLabel apellidosLabel = new JLabel("Apellidos:");
        gbc.gridx = 0; gbc.gridy = 1;
        add(apellidosLabel, gbc);
        apellidosField = new JTextField(20);
        gbc.gridx = 1;
        add(apellidosField, gbc);

        JLabel emailLabel = new JLabel("Email:");
        gbc.gridx = 0; gbc.gridy = 2;
        add(emailLabel, gbc);
        emailField = new JTextField(20);
        gbc.gridx = 1;
        add(emailField, gbc);

        JLabel prefijoLabel = new JLabel("Prefijo:");
        gbc.gridx = 0; gbc.gridy = 3;
        add(prefijoLabel, gbc);
        prefijoBox = new JComboBox<>(new String[]{"+34", "+33", "+44", "+49"});
        gbc.gridx = 1;
        add(prefijoBox, gbc);

        JLabel telefonoLabel = new JLabel("Teléfono:");
        gbc.gridx = 0; gbc.gridy = 4;
        add(telefonoLabel, gbc);
        telefonoField = new JTextField(20);
        gbc.gridx = 1;
        add(telefonoField, gbc);

        JLabel contrasenaLabel = new JLabel("Contraseña:");
        gbc.gridx = 0; gbc.gridy = 5;
        add(contrasenaLabel, gbc);
        contrasenaField = new JPasswordField(20);
        gbc.gridx = 1;
        add(contrasenaField, gbc);

        // Botón de "Registrarse"
        JButton registrarButton = new JButton("Registrarse");
        gbc.gridx = 0; gbc.gridy = 6; gbc.gridwidth = 2;
        add(registrarButton, gbc);

        // Acción del botón de llamar al metodo registrarUsario()
        registrarButton.addActionListener(e -> registrarUsuario());
    }

    // Metodo para registrar al usuario en la base de datos
    private void registrarUsuario() {
        //obtener valores de los campos
        String nombre = nombreField.getText();
        String apellidos = apellidosField.getText();
        String email = emailField.getText();
        String prefijo = (String) prefijoBox.getSelectedItem();
        String telefono = telefonoField.getText();
        String contrasena = new String(contrasenaField.getPassword());

        //validacion por si faltan los campos obligatorios
        if (nombre.isEmpty() || email.isEmpty() || contrasena.isEmpty()) {
            JOptionPane.showMessageDialog(this, "⚠️ Rellena todos los campos obligatorios.");
            return;
        }

        //conectar con la base de datos e insertar los datos
        try (Connection conn = DBConnection.connect()) {
            String sql = "INSERT INTO usuarios (nombre, apellidos, email, prefijo, telefono, contrasena) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, nombre);
            ps.setString(2, apellidos);
            ps.setString(3, email);
            ps.setString(4, prefijo);
            ps.setString(5, telefono);
            ps.setString(6, contrasena);

            //insertar los datos en la bdd
            int result = ps.executeUpdate();
            if (result > 0) {
                JOptionPane.showMessageDialog(this, " Usuario registrado correctamente.");
                dispose(); // Cierra la ventana de registro
            } else {
                JOptionPane.showMessageDialog(this, " Error al registrar usuario.");
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error de base de datos: " + ex.getMessage());
        }
    }
}
