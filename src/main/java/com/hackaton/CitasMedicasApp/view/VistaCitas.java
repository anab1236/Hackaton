package com.hackaton.CitasMedicasApp.view;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import com.hackaton.CitasMedicasApp.controller.ControladorCita;
import com.hackaton.CitasMedicasApp.controller.Mensaje;
import com.hackaton.CitasMedicasApp.model.Cita;

public class VistaCitas {
    // Atributos
    private ControladorCita controladorCita;
    private List<Cita> listaCitas;

    private JFrame jFrame;
    private JTextField txtId;
    private JTextField txtPaciente;
    private JTextField txtTipoServicio;
    private JTextField txtPrecio;
    private JTextField txtHora;

    private JButton btnAgregar;
    private JButton btnBorrar;
    private JButton btnActualizar;
    private JButton btnInforme;
    private JButton btnOrdenar;

    private JTable citasTable;

    // Constructor
    public VistaCitas(ControladorCita controladorCita) {
        this.controladorCita = controladorCita;

        this.jFrame = new JFrame();
        this.jFrame.setTitle("Ventana de Citas");
        this.jFrame.setSize(700, 600);

        JLabel lblTitle = new JLabel("¡Bienvenido a la APP de Citas");

        btnAgregar = new JButton("Agregar Citas");
        btnBorrar = new JButton("Cancelar Cita");
        btnActualizar = new JButton("Actualizar Cita");
        btnInforme = new JButton("Informes");
        btnOrdenar = new JButton("Ordenar");

        citasTable = new JTable();
        JScrollPane scrollTable = new JScrollPane();

        JPanel pnlCitas = new JPanel();
        JPanel pnlBtnCitas = new JPanel();
        JPanel center1 = new JPanel();        
        JPanel center2 = new JPanel();
        JPanel top = new JPanel();
        JPanel centerTop = new JPanel();
        JPanel center = new JPanel();
        JPanel bottom = new JPanel();

        JLabel lblId = new JLabel("Id");
        JLabel lblPaciente = new JLabel("Paciente");
        JLabel lblTipoServicio = new JLabel("Tipo de Servicio");
        JLabel lblPrecio = new JLabel("Precio");
        JLabel lblHora = new JLabel("Hora");

        txtId = new JTextField();
        txtPaciente = new JTextField();
        txtTipoServicio = new JTextField();
        txtPrecio = new JTextField();
        txtHora = new JTextField();

        lblTitle.setFont(new Font("Impact", 1, 20));

        txtId.setColumns(9);
        txtPaciente.setColumns(9);
        txtTipoServicio.setColumns(9);
        txtPrecio.setColumns(9);
        txtHora.setColumns(9);

        pnlBtnCitas.add(btnAgregar);
        centerTop.setBorder(new TitledBorder("Agregar nueva cita"));

        pnlCitas.add(lblId);
        pnlCitas.add(txtId);
        pnlCitas.add(lblPaciente);
        pnlCitas.add(txtPaciente);
        pnlCitas.add(lblTipoServicio);
        pnlCitas.add(txtTipoServicio);
        pnlCitas.add(lblPrecio);
        pnlCitas.add(txtPrecio);
        pnlCitas.add(lblHora);
        pnlCitas.add(txtHora);

        pnlCitas.setLayout(new GridLayout(5, 2));
        centerTop.add(pnlCitas);
        centerTop.add(pnlBtnCitas);
        centerTop.setLayout(new GridLayout(2, 1));

        center1.add(centerTop);

        refreshTable();
        scrollTable.setViewportView(citasTable);
        center2.add(scrollTable);

        top.add(lblTitle);
        center.add(center1);
        center.add(center2);
        center.setLayout(new GridLayout(2, 1));
        bottom.add(btnBorrar);
        bottom.add(btnActualizar);
        bottom.add(btnInforme);
        bottom.add(btnOrdenar);

        this.jFrame.add(top, BorderLayout.NORTH);
        this.jFrame.add(center, BorderLayout.CENTER);
        this.jFrame.add(bottom,BorderLayout.SOUTH);
        // this.jFrame.add(bottom, BorderLayout.SOUTH);

        this.jFrame.setVisible(true);

        btnAgregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                agregarCita();
            }
        });

        btnActualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarCita();
            }
        });
        
        btnBorrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                borrarCita();
            }
        });

        btnInforme.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               generarInforme();
            }
        });

        btnOrdenar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                generarOrden();
            }
        });
    }
    // Metodos
    // refresca la lista de los productos
    public void refreshTable() {
        this.listaCitas = controladorCita.buscarCita();
        String[] encabezados = { "Id", "Paciente", "Tipo de Servicio", "Precio", "Hora"};
        Object[][] datos = new Object[this.listaCitas.size()][encabezados.length];
        
        for (int i = 0; i < this.listaCitas.size(); i++) {
            Cita cita = this.listaCitas.get(i);
            datos[i][0] = cita.getId();
            datos[i][1] = cita.getPaciente();
            datos[i][2] = cita.getTipoServicio();
            datos[i][3] = cita.getPrecio();
            datos[i][4] = cita.getHora();
        }
        this.citasTable.setModel(new DefaultTableModel(datos, encabezados));
    }
    // Define un objecto Cita a partir de los textFields
    public Cita getInputCita() {
        try {
            Integer id = Integer.parseInt(txtId.getText());
            String paciente = txtPaciente.getText();
            String tipoServicio = txtTipoServicio.getText();
            Double precio = Double.parseDouble(txtPrecio.getText());
            String hora = txtHora.getText();

            return new Cita(id, paciente, tipoServicio, precio, hora);
        } catch (NumberFormatException e) {
            // Handle the exception
            JOptionPane.showMessageDialog(jFrame, "Tiene un fallo de digitacion", "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }
    }
    // Valida los valores de los TextFields
    public boolean validInputs() {
        if(txtId.getText().isEmpty()){
            return false;
        }
        if(txtPaciente.getText().isEmpty()){
            return false;
        }
        if(txtTipoServicio.getText().isEmpty()){
            return false;
        }
        if(txtPrecio.getText().isEmpty()){
            return false;
        }
        if(txtHora.getText().isEmpty()){
            return false;
        }
        return true;
    }
    // Limpia las entradas de la interfaz
    public void clearInputs(){
        txtId.setText("");
        txtPaciente.setText("");
        txtTipoServicio.setText("");
        txtPrecio.setText("");
        txtHora.setText("");
    }
    // Genera el mensaje devuelto por el Controlador
    public void showMesanje(Mensaje mensaje) {
        JOptionPane.showMessageDialog(jFrame,mensaje.getMensaje(),mensaje.getTituloMensaje(),mensaje.getTipoMensaje());
    }
    // Verifica si seleciona un elemento de la tabla, si no salta una alerta
    public int selectRowTable(){
        int row = citasTable.getSelectedRow();
        if(row == -1) {
            JOptionPane.showMessageDialog(jFrame, "Se debe seleccionar un elemento","Advertencia",JOptionPane.WARNING_MESSAGE);
        }
        return row;
    }
    // Ventana emergente que muestra los campos para actualizar
    public Cita showProductPopUp(Cita cita){
        Integer id = cita.getId();
        String paciente = cita.getPaciente();
        String tipoServicio = cita.getTipoServicio();
        Double precio = cita.getPrecio();
        String hora = cita.getHora();

        // Se crea un JPanel para contener los inputs (Se reutiliza el codigo del constructor)
        JPanel pnlCitas = new JPanel();

        JTextField txtPaciente = new JTextField(paciente, 9);
        JTextField txtTipoServicio = new JTextField(tipoServicio, 9);
        JTextField txtPrecio = new JTextField(Double.toString(precio),9);
        JTextField txtHora = new JTextField(hora, 9);
        
        JLabel lblPaciente = new JLabel("Paciente");
        JLabel lblTipoServicio = new JLabel("Tipo de Servicio");
        JLabel lblPrecio = new JLabel("Precio");
        JLabel lblHora = new JLabel("Hora");
        
        pnlCitas.add(lblPaciente);
        pnlCitas.add(txtPaciente);
        pnlCitas.add(lblTipoServicio);
        pnlCitas.add(txtTipoServicio);
        pnlCitas.add(lblPrecio);
        pnlCitas.add(txtPrecio);
        pnlCitas.add(lblHora);
        pnlCitas.add(txtHora);

        pnlCitas.setLayout(new GridLayout(4, 2));

        // Si se selecciona el OK se actualiza los atributos al elemento 
        int result = JOptionPane.showConfirmDialog(null, pnlCitas, "Actualizar Cita", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            try {
                paciente = txtPaciente.getText();
                tipoServicio = txtTipoServicio.getText();
                precio = Double.parseDouble(txtPrecio.getText());
                hora = txtHora.getText();
                return new Cita(id, paciente, tipoServicio, precio, hora);
            } catch (NumberFormatException e) {
                // Handle the exception
                JOptionPane.showMessageDialog(jFrame, "Tiene un fallo de digitacion", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        return null;
    }
    // Agendar una cita
    public void agregarCita(){
        if(!validInputs()){
            JOptionPane.showMessageDialog(jFrame,"Todos los campos son obligatorios !!","Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        Cita newCita = getInputCita();
        if(newCita != null){
            showMesanje(this.controladorCita.agregarCita(newCita));
            clearInputs();
            refreshTable();
        }
    }
    // Actualiza la Cita selecionada en la tabla
    public void actualizarCita(){
        int row = selectRowTable();
        if(row == -1){
            return;
        }
        Integer code =  Integer.parseInt(citasTable.getModel().getValueAt(row, 0).toString());
        Cita cita = showProductPopUp(this.controladorCita.buscarCita(code));
        if(cita != null){
            showMesanje(this.controladorCita.actualizarCita(cita));
            refreshTable();
            
        }
    }
    // Elimina la cita selecionada en la tabla
    public void borrarCita(){
        int row = selectRowTable();
        if(row == -1){
            return;
        }
        Integer code =  Integer.parseInt(citasTable.getModel().getValueAt(row, 0).toString());
        showMesanje(this.controladorCita.eliminarCita(this.controladorCita.buscarCita(code)));
        refreshTable();
        
    }
    // Generar Orden
    public void generarOrden(){
        int row = selectRowTable();
        if(row == -1){
            return;
        }
        Integer code =  Integer.parseInt(citasTable.getModel().getValueAt(row, 0).toString());
        showMesanje(this.controladorCita.orderCitas(this.controladorCita.buscarCita(code)));
    }
    // Generar Informe
    public void generarInforme(){
        showMesanje(this.controladorCita.generarInforme());
    }
}
