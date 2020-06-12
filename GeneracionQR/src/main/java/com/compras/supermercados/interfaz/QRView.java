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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import com.compras.supermercados.exceptions.QRException;
import com.compras.supermercados.qr.ConstantesQR;
import com.compras.supermercados.qr.GenerarQR;

public class QRView extends JFrame
{
	private static final long serialVersionUID = 628637494855902878L;
	
	private JButton botonGenerarQR;
	
	private JTextField monto;
	private JTextField subsidiaria;
	private JTextField tienda;
	private JTextField idCajero;
	private JTextField cajero;
	private JTextField idCaja;
	private JTextField archivo;

	/**
	 * Create the frame.
	 */
	public QRView() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setBounds(0, 0, 550, 320);
		setLocationRelativeTo(null);
		setTitle("QR Comercios Walmart");
		getContentPane().setLayout(null);
		etiquetas();
		cargarTextFields();
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
		JLabel labelMonto = new JLabel("Monto");
		labelMonto.setBounds(75, 30, 45, 15);
		getContentPane().add(labelMonto);
		
		JLabel idSubsidiaria = new JLabel("Id Subsidiaria");
		idSubsidiaria.setBounds(33, 60, 100, 15);
		getContentPane().add(idSubsidiaria);
		
		JLabel idTienda = new JLabel("Id Tienda");
		idTienda.setBounds(58, 90, 70, 15);
		getContentPane().add(idTienda);
		
		JLabel idCajero = new JLabel("Id Cajero");
		idCajero.setBounds(60, 120, 70, 15);
		getContentPane().add(idCajero);
		
		JLabel labelCajero = new JLabel("Nombre cajero");
		labelCajero.setBounds(26, 150, 120, 15);
		getContentPane().add(labelCajero);
		
		JLabel idCaja = new JLabel("Id Caja");
		idCaja.setBounds(72, 180, 55, 15);
		getContentPane().add(idCaja);
		
		JLabel ruta = new JLabel("Archivo creado");
		ruta.setBounds(24, 210, 110, 15);
		getContentPane().add(ruta);
	}
	
	/**
	 * Cargar text fields
	 */
	private void cargarTextFields()
	{
		monto = new JTextField();
		monto.setBounds(144, 28, 114, 19);
		getContentPane().add(monto);
		monto.setColumns(10);
		
		subsidiaria = new JTextField();
		subsidiaria.setBounds(144, 58, 114, 19);
		getContentPane().add(subsidiaria);
		subsidiaria.setColumns(10);
		
		tienda = new JTextField();
		tienda.setBounds(144, 88, 114, 19);
		getContentPane().add(tienda);
		tienda.setColumns(10);
		
		idCajero = new JTextField();
		idCajero.setBounds(144, 118, 114, 19);
		getContentPane().add(idCajero);
		idCajero.setColumns(10);
		
		cajero = new JTextField();
		cajero.setBounds(144, 148, 114, 19);
		getContentPane().add(cajero);
		cajero.setColumns(10);
		
		idCaja = new JTextField();
		idCaja.setBounds(144, 178, 114, 19);
		getContentPane().add(idCaja);
		idCaja.setColumns(10);
		
		archivo = new JTextField();
		archivo.setBounds(144, 208, 390, 19);
		getContentPane().add(archivo);
		archivo.setColumns(10);
		archivo.setEditable(false);
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
				if(!(GenerarQR.esNumero(String.valueOf(c)) || c==ke.VK_PERIOD || c==ke.VK_DELETE || c==ke.VK_BACK_SPACE))
					ke.consume();
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
				if(!(GenerarQR.esNumero(String.valueOf(c)) || c==ke.VK_DELETE || c==ke.VK_BACK_SPACE))
					ke.consume();
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
				if(!(GenerarQR.esNumero(String.valueOf(c)) || c==ke.VK_DELETE || c==ke.VK_BACK_SPACE))
					ke.consume();
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
				if(!(GenerarQR.esNumero(String.valueOf(c)) || c==ke.VK_DELETE || c==ke.VK_BACK_SPACE))
					ke.consume();
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
				if(!(GenerarQR.soloTexto(String.valueOf(c)) || c==ke.VK_DELETE || c==ke.VK_BACK_SPACE || c==ke.VK_SPACE))
					ke.consume();
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
				if(!(GenerarQR.esNumero(String.valueOf(c)) || c==ke.VK_DELETE || c==ke.VK_BACK_SPACE))
					ke.consume();
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
			public void mouseClicked(MouseEvent e)
			{
				try {
					validarInformacionQR();
					GenerarQR.esMontoValido(monto.getText());
					String numeroTransaccion=GenerarQR.generarCodigoQR(monto.getText(), subsidiaria.getText(), tienda.getText(), idCajero.getText(), cajero.getText(), idCaja.getText());
					cargarQR(numeroTransaccion);
				}catch(QRException qre) {
					JOptionPane.showMessageDialog(null, qre.getMensajeError(), ConstantesQR.ERROR_DIALOGO, JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		botonGenerarQR.setBounds(60, 250, 160, 25);
		getContentPane().add(botonGenerarQR);
	}
	
	/**
	 * Metodo para validar que la información para generar el QR este informada
	 * @throws QRException 
	 */
	private void validarInformacionQR() throws QRException
	{
		if(GenerarQR.esNuloVacio(monto.getText()) || GenerarQR.esNuloVacio(subsidiaria.getText()) || GenerarQR.esNuloVacio(tienda.getText()) || GenerarQR.esNuloVacio(idCajero.getText()) || GenerarQR.esNuloVacio(cajero.getText()) || GenerarQR.esNuloVacio(idCaja.getText()))
			throw new QRException("Complete la informaci\u00f3n para la generaci\u00f3n del c\u00f3digo QR.");
	}
	
	/**
	 * Metodo para cargar la imagen al label
	 */
	@SuppressWarnings("deprecation")
	private void cargarQR(String numeroTransaccion)
	{
		String nombreArchivoQR=numeroTransaccion.concat(ConstantesQR.WALMART);
		
		try {
			Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
			BufferedImage img = ImageIO.read(new File(nombreArchivoQR).toURL());
			ImageIcon icon = new ImageIcon(new ImageIcon(img).getImage().getScaledInstance(screenSize.width/3, screenSize.height/2, Image.SCALE_DEFAULT));
			JLabel imagen = new JLabel(icon);
			File codigoQR = new File(nombreArchivoQR);
			archivo.setText(codigoQR.getAbsolutePath());
			JOptionPane.showMessageDialog(null, imagen);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}