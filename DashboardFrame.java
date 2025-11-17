package yestellefuncion;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/*
Autor: Manuel B
 */

public class DashboardFrame extends JFrame {

    private DefaultTableModel usuariosModel;
    private DefaultTableModel prendasModel;

    // Configuración ventana del dashboard
    public DashboardFrame(String nombre) {
        setTitle("Dashboard Yestelle");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Colores de Yestelle
        Color fondo = Color.WHITE;
        Color textoPrincipal = Color.BLACK;
        Color grisLinea = new Color(200, 200, 200);
        Color grisTexto = new Color(110, 110, 110);
        Color acento = new Color(0, 150, 200);
        Color acento2 = new Color(0, 180, 120);
        getContentPane().setBackground(fondo);

        // Título y barra superior
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(fondo);
        topBar.setBorder(new EmptyBorder(12, 18, 12, 18));
        JLabel title = new JLabel("Panel de Control — Yestelle");
        title.setFont(new Font("Segoe UI", Font.BOLD, 26));
        title.setForeground(textoPrincipal);
        topBar.add(title, BorderLayout.WEST);
        add(topBar, BorderLayout.NORTH);

        // Panel principal con GridBagLayout (responsive)
        JPanel main = new JPanel(new GridBagLayout());
        main.setBackground(fondo);
        GridBagConstraints gbcMain = new GridBagConstraints();
        gbcMain.insets = new Insets(12, 12, 12, 12);
        gbcMain.fill = GridBagConstraints.BOTH;

        // ---------- TABLA USUARIOS (la columna 0) ----------
        JPanel usuariosPanel = createUsuariosPanel(acento, textoPrincipal, grisLinea, grisTexto);
        gbcMain.gridx = 0;
        gbcMain.gridy = 0;
        gbcMain.weightx = 0.32;      // ancho relativo
        gbcMain.weighty = 0.5;
        main.add(usuariosPanel, gbcMain);

        // ---------- PANEL de GRAFICOS (columna 1) - 3 gráficos apilados ----------
        JPanel graficosStack = new JPanel(new GridBagLayout());
        graficosStack.setBackground(fondo);
        graficosStack.setBorder(BorderFactory.createLineBorder(grisLinea));
        GridBagConstraints gbcG = new GridBagConstraints();
        gbcG.insets = new Insets(8, 8, 8, 8);
        gbcG.fill = GridBagConstraints.BOTH;

        ChartBarPanel bar = new ChartBarPanel(acento, textoPrincipal);
        ChartLinePanel line = new ChartLinePanel(acento2, textoPrincipal);
        ChartPiePanel pie = new ChartPiePanel(new Color[]{acento, acento2, new Color(230, 120, 90)}, textoPrincipal);

        gbcG.gridx = 0; gbcG.gridy = 0; gbcG.weightx = 1; gbcG.weighty = 0.33;
        graficosStack.add(wrapWithTitle(bar, "Ventas del Mes por Tipo de Producto", textoPrincipal, grisLinea), gbcG);
        gbcG.gridy = 1; gbcG.weighty = 0.33;
        graficosStack.add(wrapWithTitle(line, "Evolución de Ganancias (últimos 6 meses)", textoPrincipal, grisLinea), gbcG);
        gbcG.gridy = 2; gbcG.weighty = 0.34;
        graficosStack.add(wrapWithTitle(pie, "Distribución del Inventario por Categoría", textoPrincipal, grisLinea), gbcG);

        gbcMain.gridx = 1;
        gbcMain.gridy = 0;
        gbcMain.weightx = 0.36;
        gbcMain.weighty = 0.5;
        main.add(graficosStack, gbcMain);

        // ---------- TABLA PRENDAS (columna 2) ----------
        JPanel prendasPanel = createPrendasPanel(acento2, textoPrincipal, grisLinea, grisTexto);
        gbcMain.gridx = 2;
        gbcMain.gridy = 0;
        gbcMain.weightx = 0.32;
        gbcMain.weighty = 0.5;
        main.add(prendasPanel, gbcMain);

        // ---------- KPI debajo de Usuarios (ocupa columna 0) ----------
        JPanel kpiPanel = createKpiPanel(textoPrincipal, grisTexto, acento, acento2);
        gbcMain.gridx = 0;
        gbcMain.gridy = 1;
        gbcMain.gridwidth = 1;
        gbcMain.weightx = 0.32;
        gbcMain.weighty = 0.25;
        main.add(kpiPanel, gbcMain);

        // --- Resumen rápido (centro abajo) ---
        JPanel resumenPanel = createResumenPanel(textoPrincipal, grisTexto, grisLinea);
        gbcMain.gridx = 1;
        gbcMain.gridy = 1;
        gbcMain.gridwidth = 1;
        gbcMain.weightx = 0.36;
        gbcMain.weighty = 0.25;
        main.add(resumenPanel, gbcMain);

        // --- Acciones, exportar y filtros (abajo a la derecha) ---
        JPanel accionesPanel = createAccionesPanel(textoPrincipal, grisTexto, grisLinea);
        gbcMain.gridx = 2;
        gbcMain.gridy = 1;
        gbcMain.gridwidth = 1;
        gbcMain.weightx = 0.32;
        gbcMain.weighty = 0.25;
        main.add(accionesPanel, gbcMain);

        // Scroll principal para escalado
        JScrollPane scroll = new JScrollPane(main);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        add(scroll, BorderLayout.CENTER);

        // Al inicio, centra la ventana y muestra
        setVisible(true);
    }

    // ---------- PANEL USUARIOS ----------
    private JPanel createUsuariosPanel(Color acento, Color textoPrincipal, Color grisLinea, Color grisTexto) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(grisLinea),
                new EmptyBorder(8, 8, 8, 8)
        ));

        // Título del panel
        JLabel title = new JLabel("Usuarios", SwingConstants.LEFT);
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setForeground(textoPrincipal);
        panel.add(title, BorderLayout.NORTH);

        //datos de la tabla de usuarios
        String[] cols = {"ID", "Nombre", "Rol", "Email"};
        Object[][] rows = {
                {"USR001", "Ana García", "Administradora", "ana@yestelle.com"},
                {"USR002", "Manuel Paredes", "Empleado", "manuel@yestelle.com"},
                {"USR003", "Marta Lillo", "Gerente", "marta@yestelle.com"},
                {"USR004", "Lucía Fernández", "Empleado", "lucia@yestelle.com"},
                {"USR005", "Carlos López", "Gerente", "carlos@yestelle.com"},
                {"USR006", "Sofía Ramos", "Empleado", "sofia@yestelle.com"},
                {"USR007", "Diego Torres", "Empleado", "diego@yestelle.com"},
                {"USR008", "Valeria Ruiz", "Administradora", "valeria@yestelle.com"},
                {"USR009", "Pablo Díaz", "Empleado", "pablo@yestelle.com"}
            };
        // Modelo de tabla editable
        usuariosModel = new DefaultTableModel(rows, cols);
        JTable tabla = new JTable(usuariosModel);
        styleTable(tabla, textoPrincipal, grisLinea, grisTexto);

        // Agregamos la tabla al panel dentro de un JScrollPane
        panel.add(new JScrollPane(tabla), BorderLayout.CENTER);

        // Panel inferior con botones
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 8));
        bottom.setBackground(Color.WHITE);

        // Botón para añadir usuario
        JButton addUser = new JButton("Añadir Usuario");
        addUser.setBackground(acento);
        addUser.setForeground(Color.WHITE);
        addUser.setFocusPainted(false);
        addUser.addActionListener(e -> onAddUser()); // Llama al formulario de agregar usuario

        // Botón para eliminar usuario seleccionado
        JButton removeUser = new JButton("Eliminar Seleccionado");
        removeUser.setBackground(Color.LIGHT_GRAY);
        removeUser.setForeground(Color.BLACK);
        removeUser.setFocusPainted(false);
        removeUser.addActionListener(e -> {
            JTable t = getTableFromModel(usuariosModel); // obtenemos la tabla real
            int sel = t.getSelectedRow();
            if (sel >= 0) usuariosModel.removeRow(sel); // eliminar fila
            else JOptionPane.showMessageDialog(this, "Selecciona una fila para eliminar.", "Info", JOptionPane.INFORMATION_MESSAGE);
        });

        bottom.add(addUser);
        bottom.add(removeUser);

        panel.add(bottom, BorderLayout.SOUTH);
        return panel;
    }

    // Formulario para añadir usuario
    private void onAddUser() {
        JPanel form = new JPanel(new GridLayout(4, 2, 6, 6));
        form.add(new JLabel("ID:"));
        JTextField idF = new JTextField();
        form.add(idF);
        form.add(new JLabel("Nombre:"));
        JTextField nombreF = new JTextField();
        form.add(nombreF);
        form.add(new JLabel("Rol:"));
        JTextField rolF = new JTextField();
        form.add(rolF);
        form.add(new JLabel("Email:"));
        JTextField emailF = new JTextField();
        form.add(emailF);

        int opt = JOptionPane.showConfirmDialog(this, form, "Añadir Usuario", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (opt == JOptionPane.OK_OPTION) {
            String id = idF.getText().trim();
            String nombre = nombreF.getText().trim();
            String rol = rolF.getText().trim();
            String email = emailF.getText().trim();
             // Validar que los campos obligatorios están completados
            if (!id.isEmpty() && !nombre.isEmpty()) {
                usuariosModel.addRow(new Object[]{id, nombre, rol, email});
            } else {
                JOptionPane.showMessageDialog(this, "ID y Nombre son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // ---------- PANEL PRENDAS ----------
    private JPanel createPrendasPanel(Color acento, Color textoPrincipal, Color grisLinea, Color grisTexto) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(grisLinea),
                new EmptyBorder(8, 8, 8, 8)
        ));

        // Título del panel
        JLabel title = new JLabel("Inventario de Prendas", SwingConstants.LEFT);
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setForeground(textoPrincipal);
        panel.add(title, BorderLayout.NORTH);

        //contenido de la tabla Usuarios
        String[] cols = {"Código", "Nombre", "XS", "S", "M", "L", "XL", "Color"};
        Object[][] rows = {
            {"SUD001", "Sudadera Oversize", 15, 20, 18, 12, 9, "Negro"},
            {"CAM002", "Camiseta Básica", 30, 40, 35, 28, 20, "Blanco"},
            {"PNT003", "Pantalón Jogger", 10, 12, 14, 8, 6, "Beige"},
            {"VST004", "Vestido Largo", 8, 5, 7, 4, 3, "Rojo"},
            {"BLU005", "Blusa Casual", 12, 14, 11, 13, 10, "Azul"},
            {"CHA006", "Chaqueta Vaquera", 7, 8, 6, 7, 5, "Denim"},
            {"FAL007", "Falda Midi", 9, 10, 8, 9, 7, "Negro"},
            {"CAM008", "Camiseta Premium", 15, 18, 14, 16, 12, "Gris"},
            {"SUD009", "Sudadera Capucha", 10, 12, 11, 9, 8, "Verde"},
            {"PNT010", "Pantalón Skinny", 11, 13, 12, 10, 9, "Negro"},
            {"BLU011", "Blusa Elegante", 6, 7, 5, 6, 4, "Blanco"},
            {"CHA012", "Chaqueta Cuero", 5, 6, 5, 4, 3, "Marrón"}
        };
        // Modelo de tabla editable
        prendasModel = new DefaultTableModel(rows, cols);
        JTable tabla = new JTable(prendasModel);
        styleTable(tabla, textoPrincipal, grisLinea, grisTexto);

        //agregamos la tabla al panel dentro de un JScriollPane
        panel.add(new JScrollPane(tabla), BorderLayout.CENTER);

        // Panel inferior con botones
        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 8));
        bottom.setBackground(Color.WHITE);

        // Botón para añadir prenda
        JButton addPrenda = new JButton("Añadir Prenda");
        addPrenda.setBackground(acento);
        addPrenda.setForeground(Color.WHITE);
        addPrenda.setFocusPainted(false);
        addPrenda.addActionListener(e -> onAddPrenda());

        // Botón eliminar prenda seleccionada
        JButton removePrenda = new JButton("Eliminar Seleccionado");
        removePrenda.setBackground(Color.LIGHT_GRAY);
        removePrenda.setForeground(Color.BLACK);
        removePrenda.setFocusPainted(false);
        removePrenda.addActionListener(e -> {
            JTable t = getTableFromModel(prendasModel);
            int sel = t.getSelectedRow();
            if (sel >= 0) prendasModel.removeRow(sel);
            else JOptionPane.showMessageDialog(this, "Selecciona una fila para eliminar.", "Info", JOptionPane.INFORMATION_MESSAGE);
        });

        bottom.add(removePrenda);
        bottom.add(addPrenda);

        panel.add(bottom, BorderLayout.SOUTH);
        return panel;
    }

    // Formulario para añadir prenda
    private void onAddPrenda() {
        JPanel form = new JPanel(new GridLayout(5, 2, 6, 6));
        form.add(new JLabel("Código:"));
        JTextField idF = new JTextField();
        form.add(idF);
        form.add(new JLabel("Nombre:"));
        JTextField nombreF = new JTextField();
        form.add(nombreF);
        form.add(new JLabel("Stock total (ej. sum de tallas):"));
        JTextField stockF = new JTextField();
        form.add(stockF);
        form.add(new JLabel("Color:"));
        JTextField colorF = new JTextField();
        form.add(colorF);

        int opt = JOptionPane.showConfirmDialog(this, form, "Añadir Prenda", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (opt == JOptionPane.OK_OPTION) {
            String id = idF.getText().trim();
            String nombre = nombreF.getText().trim();
            String stock = stockF.getText().trim();
            String color = colorF.getText().trim();
            // Validar que los campos obligatorios están completados
            if (!id.isEmpty() && !nombre.isEmpty()) {
                prendasModel.addRow(new Object[]{id, nombre, stock, color});
            } else {
                JOptionPane.showMessageDialog(this, "Código y Nombre son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // ---------- ESTILO TABLA ----------
    private void styleTable(JTable t, Color textoPrincipal, Color border, Color grisTexto) {
        t.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        t.setRowHeight(26);
        t.setShowGrid(true);
        t.setGridColor(border);
        t.setSelectionBackground(new Color(220, 235, 245));
        t.setSelectionForeground(Color.BLACK);
        t.setFillsViewportHeight(true);

        // Header de la tabla
        t.getTableHeader().setBackground(new Color(245, 245, 245));
        t.getTableHeader().setForeground(Color.BLACK);
        t.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        ((DefaultTableCellRenderer) t.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(SwingConstants.LEFT);

        // Celdas alineadas a la izquierda y color del texto
        DefaultTableCellRenderer left = new DefaultTableCellRenderer();
        left.setHorizontalAlignment(SwingConstants.LEFT);
        left.setForeground(Color.BLACK);
        t.setDefaultRenderer(Object.class, left);
    }

    // Helpers para obtener tabla desde modelo
    private JTable getTableFromModel(DefaultTableModel model) {
        for (Window w : Window.getWindows()) {
            JTable found = findTableInContainer(w, model);
            if (found != null) return found;
        }
        return new JTable(model);
    }

    private JTable findTableInContainer(Container c, DefaultTableModel model) {
        for (Component comp : c.getComponents()) {
            if (comp instanceof JTable) {
                JTable jt = (JTable) comp;
                if (jt.getModel() == model) return jt;
            } else if (comp instanceof Container) {
                JTable res = findTableInContainer((Container) comp, model);
                if (res != null) return res;
            }
        }
        return null;
    }

    // ---------- PANEL KPI (Indicadores importantes de rendimiento de la empresa) ----------
    private JPanel createKpiPanel(Color textoPrincipal, Color grisTexto, Color acento, Color acento2) {
        JPanel kpi = new JPanel(new GridLayout(1, 3, 10, 10)); // 3 KPIs en una fila, con espacio entre ellos
        kpi.setBackground(Color.WHITE);
        kpi.setBorder(new EmptyBorder(6, 0, 0, 0));

        // Cada KPI se representa con una "tarjeta" que muestra título y datos
        kpi.add(createKpiCard("Beneficios Totales", "185.200 €", acento, textoPrincipal, grisTexto));
        kpi.add(createKpiCard("Beneficios Mensuales", "12.800 €", acento2, textoPrincipal, grisTexto));
        kpi.add(createKpiCard("Satisfacción Clientes", "93 %", new Color(120, 120, 120), textoPrincipal, grisTexto));

        return kpi;
    }

    // Crear una tarjeta individual de KPI
    private JPanel createKpiCard(String title, String value, Color colorValue, Color textoPrincipal, Color grisTexto) {
        JPanel c = new JPanel(new BorderLayout());
        c.setBackground(Color.WHITE);
        c.setBorder(BorderFactory.createLineBorder(new Color(230, 230, 230)));

        // Título del KPI
        JLabel t = new JLabel(title, SwingConstants.LEFT);
        t.setFont(new Font("Segoe UI", Font.BOLD, 14));
        t.setForeground(Color.BLACK);
        t.setBorder(new EmptyBorder(8, 8, 0, 8));

        // Valor del KPI
        JLabel v = new JLabel(value, SwingConstants.LEFT);
        v.setFont(new Font("Segoe UI", Font.BOLD, 22));
        v.setForeground(colorValue);
        v.setBorder(new EmptyBorder(6, 8, 8, 8));

        c.add(t, BorderLayout.NORTH);
        c.add(v, BorderLayout.CENTER);
        return c;
    }

    // -- Panel resumen rapido (centro abajo) --
    private JPanel createResumenPanel(Color textoPrincipal, Color grisTexto, Color grisLinea) {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(Color.WHITE);
        p.setBorder(BorderFactory.createLineBorder(grisLinea));

        // Título del panel de resumen rapido
        JLabel t = new JLabel("Resumen Rápido", SwingConstants.LEFT);
        t.setFont(new Font("Segoe UI", Font.BOLD, 16));
        t.setBorder(new EmptyBorder(8, 8, 8, 8));
        p.add(t, BorderLayout.NORTH);

        // Área de texto con la información general
        JTextArea area = new JTextArea();
        area.setEditable(false);
        area.setBackground(Color.WHITE);
        area.setForeground(grisTexto);
        area.setText(
                "• Promoción en curso: 25% descuento en camisetas.\n" +
                "• Stock bajo: Sudaderas (SUD001) — 9 unidades en total.\n" +
                "• Objetivo mensual: 15.000 € (actual: 8.210 €).\n" +
                "• Clientes satisfechos (último mes): 93%.\n"
        );
        area.setBorder(new EmptyBorder(10, 10, 10, 10));
        p.add(area, BorderLayout.CENTER);
        return p;
    }

    // -- PANEL ACCIONES (zona inferior izquierda) --
    // Ninguno funciona son solo visuales e introductorios para implementacion en el futuro
    private JPanel createAccionesPanel(Color textoPrincipal, Color grisTexto, Color grisLinea) {
        JPanel p = new JPanel(new GridLayout(3, 1, 8, 8));
        p.setBackground(Color.WHITE);
        p.setBorder(BorderFactory.createLineBorder(grisLinea));
        p.setBorder(new EmptyBorder(8, 8, 8, 8));

        // Botón de exportar CSV (demo)
        JButton exportBtn = new JButton("Exportar CSV (Tablas)");
        exportBtn.setBackground(new Color(240, 240, 240));
        exportBtn.setFocusPainted(false);
        exportBtn.addActionListener(e -> JOptionPane.showMessageDialog(this, "Función de exportar (demo)."));

        // Botón de filtros avanzados 
        JButton filtroBtn = new JButton("Filtros avanzados");
        filtroBtn.setBackground(new Color(240, 240, 240));
        filtroBtn.setFocusPainted(false);

        // Botón de ayuda/manual
        JButton ayudaBtn = new JButton("Ayuda / Manual");
        ayudaBtn.setBackground(new Color(240, 240, 240));
        ayudaBtn.setFocusPainted(false);

        // Agregar botones al panel
        p.add(exportBtn);
        p.add(filtroBtn);
        p.add(ayudaBtn);
        return p;
    }

    // WRAPPER CON TÍTULO (para reutilizar componentes)
    private JPanel wrapWithTitle(JComponent comp, String title, Color titleColor, Color borderColor) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        JLabel t = new JLabel(title, SwingConstants.LEFT);
        t.setFont(new Font("Segoe UI", Font.BOLD, 14));
        t.setForeground(titleColor);
        panel.add(t, BorderLayout.NORTH);
        panel.add(comp, BorderLayout.CENTER);
        panel.setBorder(BorderFactory.createLineBorder(borderColor));
        return panel;
    }

    //GRAFICOS DIBUJADOS (sin librerías externas)

    // Barras: ventas del mes por tipo de producto (datos ficticios, coloreados)
    private static class ChartBarPanel extends JPanel {
        private final Color color;
        private final Color textColor;
        private final String[] tipos = {"Camisetas", "Sudaderas", "Pantalones", "Zapatos", "Accesorios"};
        private final int[] datos = {4200, 7800, 3200, 5100, 1900};

        ChartBarPanel(Color color, Color textColor) {
            this.color = color;
            this.textColor = textColor;
            setBackground(Color.WHITE);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            int w = getWidth();
            int h = getHeight();
            int padding = 36;
            // Título del gráfico
            g.setColor(textColor);
            g.setFont(new Font("Segoe UI", Font.BOLD, 12));
            g.drawString("Ventas por tipo (EUR)", 10, 18);

            int max = 1;
            for (int v : datos) if (v > max) max = v;

            int barAreaWidth = w - padding * 2;
            int barWidth = Math.max(20, barAreaWidth / datos.length - 12);
            int x = padding;
            for (int i = 0; i < datos.length; i++) {
                int val = datos[i];
                int barH = (int) ((h - 80) * (val / (double) max));
                int y = h - 30 - barH;
                // color variado por tipo
                Color c = interpolate(color, new Color(60, 60, 60), (float) i / (datos.length - 1));
                g.setColor(c);
                g.fillRoundRect(x, y, barWidth, barH, 8, 8);
                // Etiquetas tipo y valor
                g.setColor(new Color(150, 150, 150));
                g.drawString(tipos[i], x, h - 12);
                g.setColor(Color.DARK_GRAY);
                g.drawString(String.valueOf(val) + "€", x, y - 6);
                x += barWidth + 12;
            }
        }

        // mezcla simple para crear gradientes de color
        private static Color interpolate(Color a, Color b, float t) {
            t = Math.max(0f, Math.min(1f, t));
            int r = (int) (a.getRed() * (1 - t) + b.getRed() * t);
            int gr = (int) (a.getGreen() * (1 - t) + b.getGreen() * t);
            int bl = (int) (a.getBlue() * (1 - t) + b.getBlue() * t);
            return new Color(r, gr, bl);
        }
    }

    // Gráfico "Evolución de ganancias últimos 6 meses" en el centro 
    private static class ChartLinePanel extends JPanel {
        private final Color color;
        private final Color textColor;
        private final double[] datos = {6500, 7200, 8100, 7700, 9200, 10500};

        ChartLinePanel(Color color, Color textColor) {
            this.color = color;
            this.textColor = textColor;
            setBackground(Color.WHITE);
        }

        @Override
        protected void paintComponent(Graphics gg) {
            super.paintComponent(gg);
            Graphics2D g = (Graphics2D) gg;
            int w = getWidth();
            int h = getHeight();
            g.setColor(textColor);
            g.setFont(new Font("Segoe UI", Font.BOLD, 12));
            g.drawString("Ganancias (últimos 6 meses)", 10, 18);

            int paddingX = 40;
            int paddingY = 40;
            double max = 1;
            for (double v : datos) if (v > max) max = v;

            int plotW = w - paddingX * 2;
            int plotH = h - paddingY * 2;
            g.setColor(new Color(230, 230, 230));
            for (int i = 0; i <= 4; i++) {
                int y = paddingY + (i * plotH / 4);
                g.drawLine(paddingX, y, paddingX + plotW, y);
            }

            // // Dibujar línea de datos
            g.setColor(color);
            g.setStroke(new BasicStroke(2.5f));
            int n = datos.length;
            int prevX = -1, prevY = -1;
            for (int i = 0; i < n; i++) {
                int x = paddingX + (int) ((plotW) * (i / (double) (n - 1)));
                int y = paddingY + plotH - (int) ((datos[i] / max) * plotH);
                g.fillOval(x - 4, y - 4, 8, 8);
                if (prevX >= 0) {
                    g.drawLine(prevX, prevY, x, y);
                }
                prevX = x;
                prevY = y;
                g.setColor(new Color(90, 90, 90));
                g.setFont(new Font("Segoe UI", Font.PLAIN, 11));
                g.drawString((i + 1) + "m", x - 8, h - 8);
                g.setColor(color);
            }
        }
    }

    // Gráfico circular "Distribución de inventario por categoría"
    private static class ChartPiePanel extends JPanel {
        private final Color[] palette;
        private final Color textColor;
        private final int[] datos = {45, 25, 20, 10};
        private final String[] labels = {"Vestidos", "Camisetas", "Pantalones", "Accesorios"};

        ChartPiePanel(Color[] palette, Color textColor) {
            this.palette = palette;
            this.textColor = textColor;
            setBackground(Color.WHITE);
        }

        @Override
        protected void paintComponent(Graphics g0) {
            super.paintComponent(g0);
            Graphics2D g = (Graphics2D) g0;
            int w = getWidth();
            int h = getHeight();
            // Título
            g.setColor(textColor);
            g.setFont(new Font("Segoe UI", Font.BOLD, 12));
            g.drawString("Inventario por categoría", 10, 18);

            int size = Math.min(w, h) - 80;
            int x = 20;
            int y = 30;
            int cx = x + size / 2;
            int cy = y + size / 2;
            int start = 0;
            int total = 0;
            for (int v : datos) total += v;

            
            for (int i = 0; i < datos.length; i++) {
                int angle = (int) Math.round(datos[i] / (double) total * 360);
                g.setColor(palette[i % palette.length]);
                g.fillArc(cx - size / 2, cy - size / 2, size, size, start, angle);
                start += angle;
            }

            // Leyenda
            int lx = cx + size / 2 + 10;
            int ly = cy - size / 2;
            for (int i = 0; i < labels.length; i++) {
                g.setColor(palette[i % palette.length]);
                g.fillRect(lx, ly + i * 22, 14, 14);
                g.setColor(Color.DARK_GRAY);
                g.setFont(new Font("Segoe UI", Font.PLAIN, 12));
                g.drawString(labels[i] + " (" + datos[i] + "%)", lx + 20, ly + 12 + i * 22);
            }
        }
    }
}
