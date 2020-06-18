package com.compras.supermercados.interfaz;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.compras.supermercados.exceptions.QRException;
import com.compras.supermercados.qr.ConstantesQR;
import com.compras.supermercados.qr.GenerarQR;

@Component
public class QRView extends JFrame
{
	private static final Logger LOG=LoggerFactory.getLogger(QRView.class);
	
	private static final long serialVersionUID = 628637494855902878L;
	
	@Autowired
	private GenerarQR generarQR;
	
	private JButton botonGenerarQR;
	
	private JTextField monto;
	private JTextField subsidiaria;
	private JTextField tienda;
	private JTextField idCajero;
	private JTextField cajero;
	private JTextField idCaja;
	private JTextField archivo;
	
	@SuppressWarnings("rawtypes")
	private JComboBox companiasLogos;

	/**
	 * Create the frame.
	 */
	public QRView() {
		LOG.info("Cargando Frame...");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setBounds(0, 0, 550, 345);
		setLocationRelativeTo(null);
		setTitle("QR Comercios Walmart");
		getContentPane().setLayout(null);
		etiquetas();
		cargarTextFields();
		cargarListenerCombo();
		cargarListenersMonto();
		cargarListenersSubsidiaria();
		cargarListenersTienda();
		cargarListenersIdCajero();
		cargarListenersCajero();
		cargarListenersIdCaja();
		botonGenerarQR();
	}
	
	/**
	 * Metodo para crear las etiquetas
	 */
	private void etiquetas()
	{
		LOG.info("Cargando labels...");
		
		JLabel logoSucursal = new JLabel("Sucursal logo");
		logoSucursal.setBounds(27, 30, 100, 15);
		getContentPane().add(logoSucursal);
		
		JLabel labelMonto = new JLabel("Monto");
		labelMonto.setBounds(78, 60, 45, 15);
		getContentPane().add(labelMonto);
		
		JLabel idSubsidiaria = new JLabel("Id Subsidiaria");
		idSubsidiaria.setBounds(26, 90, 100, 15);
		getContentPane().add(idSubsidiaria);
		
		JLabel idTienda = new JLabel("Id Tienda");
		idTienda.setBounds(58, 120, 70, 15);
		getContentPane().add(idTienda);
		
		JLabel idCajero = new JLabel("Id Cajero");
		idCajero.setBounds(60, 150, 70, 15);
		getContentPane().add(idCajero);
		
		JLabel labelCajero = new JLabel("Nombre cajero");
		labelCajero.setBounds(19, 180, 110, 15);
		getContentPane().add(labelCajero);
		
		JLabel idCaja = new JLabel("Id Caja");
		idCaja.setBounds(72, 210, 55, 15);
		getContentPane().add(idCaja);
		
		JLabel ruta = new JLabel("Archivo creado");
		ruta.setBounds(18, 240, 110, 15);
		getContentPane().add(ruta);
	}
	
	/**
	 * Cargar text fields
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void cargarTextFields()
	{
		LOG.info("Cargando text fileds...");
		
		companiasLogos = new JComboBox(ConstantesQR.LISTACOMPAÑIAS.toArray());
		companiasLogos.setBounds(144, 24, 165, 23);
		getContentPane().add(companiasLogos);
		
		monto = new JTextField();
		monto.setBounds(144, 58, 114, 19);
		getContentPane().add(monto);
		monto.setColumns(10);
		
		subsidiaria = new JTextField();
		subsidiaria.setBounds(144, 88, 114, 19);
		getContentPane().add(subsidiaria);
		subsidiaria.setColumns(10);
		
		tienda = new JTextField();
		tienda.setBounds(144, 118, 114, 19);
		getContentPane().add(tienda);
		tienda.setColumns(10);
		
		idCajero = new JTextField();
		idCajero.setBounds(144, 148, 114, 19);
		getContentPane().add(idCajero);
		idCajero.setColumns(10);
		
		cajero = new JTextField();
		cajero.setBounds(144, 178, 250, 19);
		getContentPane().add(cajero);
		cajero.setColumns(10);
		
		idCaja = new JTextField();
		idCaja.setBounds(144, 208, 114, 19);
		getContentPane().add(idCaja);
		idCaja.setColumns(10);
		
		archivo = new JTextField();
		archivo.setBounds(144, 238, 390, 19);
		getContentPane().add(archivo);
		archivo.setColumns(10);
		archivo.setEditable(false);
	}
	
	/**
	 * Metodo para cargar el listener del combo
	 */
	private void cargarListenerCombo()
	{
		companiasLogos.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
			    if (e.getKeyCode()==KeyEvent.VK_ENTER){
			    	generarQR();
			    }
			}
		});
	}
	
	/**
	 * Carga el listener para el campo de monto
	 */
	private void cargarListenersMonto()
	{
		monto.addKeyListener(new KeyAdapter() {
			@SuppressWarnings("static-access")
			@Override
			public void keyTyped(KeyEvent ke) {
				if(monto.getText().length()==10)
					ke.consume();
				char c=ke.getKeyChar();
				if(!(generarQR.esNumero(String.valueOf(c)) || c==ke.VK_PERIOD || c==ke.VK_DELETE || c==ke.VK_BACK_SPACE))
					ke.consume();
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
			    if (e.getKeyCode()==KeyEvent.VK_ENTER){
			    	generarQR();
			    }
			}
		});
	}
	
	/**
	 * Carga el listener para el campo de subsidiaria
	 */
	private void cargarListenersSubsidiaria()
	{
		subsidiaria.addKeyListener(new KeyAdapter() {
			@SuppressWarnings("static-access")
			@Override
			public void keyTyped(KeyEvent ke) {
				if(subsidiaria.getText().length()==5)
					ke.consume();
				char c=ke.getKeyChar();
				if(!(generarQR.esNumero(String.valueOf(c)) || c==ke.VK_DELETE || c==ke.VK_BACK_SPACE))
					ke.consume();
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
			    if (e.getKeyCode()==KeyEvent.VK_ENTER){
			    	generarQR();
			    }
			}
		});
	}
	
	/**
	 * Carga el listener para el campo de id tienda
	 */
	private void cargarListenersTienda()
	{
		tienda.addKeyListener(new KeyAdapter() {
			@SuppressWarnings("static-access")
			@Override
			public void keyTyped(KeyEvent ke) {
				if(tienda.getText().length()==5)
					ke.consume();
				char c=ke.getKeyChar();
				if(!(generarQR.esNumero(String.valueOf(c)) || c==ke.VK_DELETE || c==ke.VK_BACK_SPACE))
					ke.consume();
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
			    if (e.getKeyCode()==KeyEvent.VK_ENTER){
			    	generarQR();
			    }
			}
		});
	}
	
	/**
	 * Carga el listener para el campo id cajero
	 */
	private void cargarListenersIdCajero()
	{
		idCajero.addKeyListener(new KeyAdapter() {
			@SuppressWarnings("static-access")
			@Override
			public void keyTyped(KeyEvent ke) {
				if(idCajero.getText().length()==5)
					ke.consume();
				char c=ke.getKeyChar();
				if(!(generarQR.esNumero(String.valueOf(c)) || c==ke.VK_DELETE || c==ke.VK_BACK_SPACE))
					ke.consume();
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
			    if (e.getKeyCode()==KeyEvent.VK_ENTER){
			    	generarQR();
			    }
			}
		});
	}
	
	/**
	 * Carga el listener para el campo cajero
	 */
	private void cargarListenersCajero()
	{
		cajero.addKeyListener(new KeyAdapter() {
			@SuppressWarnings("static-access")
			@Override
			public void keyTyped(KeyEvent ke) {
				if(cajero.getText().length()==40)
					ke.consume();
				char c=ke.getKeyChar();
				if(!(generarQR.soloTexto(String.valueOf(c)) || c==ke.VK_DELETE || c==ke.VK_BACK_SPACE || c==ke.VK_SPACE))
					ke.consume();
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
			    if (e.getKeyCode()==KeyEvent.VK_ENTER){
			    	generarQR();
			    }
			}
		});
	}
	
	/**
	 * Carga el listener para el campo id caja
	 */
	private void cargarListenersIdCaja()
	{
		idCaja.addKeyListener(new KeyAdapter() {
			@SuppressWarnings("static-access")
			@Override
			public void keyTyped(KeyEvent ke) {
				if(idCaja.getText().length()==5)
					ke.consume();
				char c=ke.getKeyChar();
				if(!(generarQR.esNumero(String.valueOf(c)) || c==ke.VK_DELETE || c==ke.VK_BACK_SPACE))
					ke.consume();
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
			    if (e.getKeyCode()==KeyEvent.VK_ENTER){
			    	generarQR();
			    }
			}
		});
	}
	
	/**
	 * Metodo para crear el boton para generar el QR
	 */
	private void botonGenerarQR()
	{
		botonGenerarQR = new JButton("Generar QR");
		botonGenerarQR.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e){
				generarQR();
			}
		});
		botonGenerarQR.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
			    if (e.getKeyCode()==KeyEvent.VK_ENTER){
			    	generarQR();
			    }
			}
		});
		botonGenerarQR.setBounds(144, 275, 160, 25);
		getContentPane().add(botonGenerarQR);
	}
	
	/**
	 * Metodo para generar el codigo QR
	 */
	private void generarQR()
	{
		try {
			LOG.info("Validando informacion para QR...");
			validarInformacionQR();
			generarQR.esMontoValido(monto.getText());
			String nuevoMonto=String.format(ConstantesQR.MONTO_DECIMALES, Double.valueOf(monto.getText())).replace(ConstantesQR.COMA, ConstantesQR.PUNTO);
			String archivoLogo=String.valueOf(companiasLogos.getSelectedItem()).replace(ConstantesQR.ESPACIO, ConstantesQR.CADENA_VACIA).replace(ConstantesQR.COMILLA_SIMPLE, ConstantesQR.CADENA_VACIA).toLowerCase().concat(ConstantesQR.ARCHIVO_LOGO);
			String archivoQR=String.valueOf(companiasLogos.getSelectedItem()).replace(ConstantesQR.ESPACIO, ConstantesQR.CADENA_VACIA).replace(ConstantesQR.COMILLA_SIMPLE, ConstantesQR.CADENA_VACIA).toLowerCase().concat(ConstantesQR.PUNTO_EXTENSION);
			String numeroTransaccion=generarQR.generarCodigoQR(archivoQR, archivoLogo, nuevoMonto, subsidiaria.getText(), tienda.getText(), idCajero.getText(), cajero.getText().toUpperCase(), idCaja.getText());
			cargarQR(numeroTransaccion, archivoQR);
		}catch(QRException qre) {
			JOptionPane.showMessageDialog(null, qre.getMensajeError(), ConstantesQR.ERROR_DIALOGO, JOptionPane.ERROR_MESSAGE);
		}
	}
	
	/**
	 * Metodo para validar que la información para generar el QR este informada
	 * @throws QRException 
	 */
	private void validarInformacionQR() throws QRException
	{
		if(generarQR.esNuloVacio(monto.getText()) || generarQR.esNuloVacio(subsidiaria.getText()) || generarQR.esNuloVacio(tienda.getText()) || generarQR.esNuloVacio(idCajero.getText()) || generarQR.esNuloVacio(cajero.getText()) || generarQR.esNuloVacio(idCaja.getText()))
			throw new QRException("Complete la informaci\u00f3n para la generaci\u00f3n del c\u00f3digo QR.");
	}
	
	/**
	 * Metodo para mostrar el QR
	 * @param numeroTransaccion
	 * @param archivoQR
	 */
	@SuppressWarnings({ "deprecation", "unused" })
	private void cargarQR(String numeroTransaccion, String archivoQR)
	{
		String nombreArchivoQR=numeroTransaccion.concat(ConstantesQR.GUION_BAJO).concat(archivoQR);
		
		try {
			LOG.info("Cargando QR generado...");
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			BufferedImage img = ImageIO.read(new File(nombreArchivoQR).toURL());
			ImageIcon icon = new ImageIcon(new ImageIcon(img).getImage().getScaledInstance(450, 450, Image.SCALE_DEFAULT));
			JLabel imagen = new JLabel(icon);
			File codigoQR = new File(nombreArchivoQR);
			archivo.setText(codigoQR.getAbsolutePath());
			JOptionPane.showMessageDialog(null, imagen);
		} catch (IOException e) {
			LOG.error(generarQR.obtenerTrazaError(e));
		}
	}
}
