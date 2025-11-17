package yestellefuncion;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

/**
 * Ventana de Inicio de Sesión conectada con MySQL
 * Autor: Manuel B (versión con conexión real)
 */
public class LoginFrame extends JFrame {

    private JTextField emailField;
    private JPasswordField passwordField;

    public LoginFrame() {
        // --- Configuración Básica de la Ventana ---
        setTitle("Ye - Iniciar sesión");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // --- Panel Principal con GridBagLayout (Fondo blanco) ---
        JPanel backgroundPanel = new JPanel(new GridBagLayout());
        backgroundPanel.setBackground(Color.WHITE);
        add(backgroundPanel, BorderLayout.CENTER);

        // --- Título ---
        JLabel titleLabel = new JLabel("Ye", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 48));
        GridBagConstraints gbcTitle = new GridBagConstraints();
        gbcTitle.gridx = 0;
        gbcTitle.gridy = 0;
        gbcTitle.insets = new Insets(50, 0, 80, 0); // Márgenes superior e inferior
        backgroundPanel.add(titleLabel, gbcTitle);

        // --- Panel del Formulario ---
        JPanel loginPanel = new JPanel(new GridBagLayout());
        loginPanel.setBackground(Color.WHITE);
        loginPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 20, 10, 20);

        // Título formulario
        JLabel loginTitle = new JLabel("Iniciar sesión", SwingConstants.CENTER);
        loginTitle.setFont(new Font("Arial", Font.BOLD, 20));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2; // Que ocupe dos columnas
        loginPanel.add(loginTitle, gbc);

        // Campo email
        JLabel emailLabel = new JLabel("Email");
        gbc.gridy = 1;
        loginPanel.add(emailLabel, gbc);
        emailField = new JTextField(20);
        gbc.gridy = 2;
        loginPanel.add(emailField, gbc);

        // Campo contraseña
        JLabel passwordLabel = new JLabel("Contraseña");
        gbc.gridy = 3;
        loginPanel.add(passwordLabel, gbc);
        passwordField = new JPasswordField(20);
        gbc.gridy = 4;
        loginPanel.add(passwordField, gbc);

        // Botón "Iniciar sesion"
        JButton loginButton = new JButton("Iniciar sesión");
        loginButton.setBackground(Color.BLACK);
        loginButton.setForeground(Color.WHITE);
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        loginPanel.add(loginButton, gbc);

        // Botón "Regístrate"
        JButton registerButton = new JButton("Regístrate");
        registerButton.setBackground(Color.WHITE);
        registerButton.setForeground(Color.GRAY);
        registerButton.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
        gbc.gridy = 6;
        loginPanel.add(registerButton, gbc);

        // Añadir formulario al fondo
        GridBagConstraints gbcLoginPanel = new GridBagConstraints();
        gbcLoginPanel.gridx = 0;
        gbcLoginPanel.gridy = 1;
        backgroundPanel.add(loginPanel, gbcLoginPanel);

        // --- Footer con derechos y términos ---
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 10));
        footerPanel.setBackground(Color.WHITE);
        JLabel terms = new JLabel("Política de Privacidad | Términos y Condiciones | © 2025 Yestelle");
        terms.setFont(new Font("Arial", Font.PLAIN, 10));
        footerPanel.add(terms);
        add(footerPanel, BorderLayout.SOUTH);

        // --- Acción botón "Iniciar sesión" ---
        loginButton.addActionListener(e -> verificarCredenciales());

        // --- Acción botón "Registrarse" ---
        registerButton.addActionListener(e -> new RegisterFrame().setVisible(true));
    }
        // Metodo para verificar credenciales ingresadas por el usuario
    private void verificarCredenciales() {
        String email = emailField.getText();
        String contrasena = new String(passwordField.getPassword());

        if (email.isEmpty() || contrasena.isEmpty()) {
            JOptionPane.showMessageDialog(this, "⚠ Introduce tu email y contraseña.");
            return;
        }

        try (Connection conn = DBConnection.connect()) {
            String sql = "SELECT * FROM usuarios WHERE email = ? AND contrasena = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, contrasena);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                String nombre = rs.getString("nombre");
                JOptionPane.showMessageDialog(this, "✅ Bienvenido/a, " + nombre + "✅");
                new DashboardFrame(nombre).setVisible(true);
                dispose(); // Cierra la ventana de login
            } else {
                JOptionPane.showMessageDialog(this, "❌ Credenciales incorrectas. ❌", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "❌ Error al conectar con la base de datos: ❌ \n" + ex.getMessage());
        }
    }
            // Método main para iniciar la ventana de login en pantalla
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
    }
}
