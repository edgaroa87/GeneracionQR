package com.compras.supermercados.qr;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.Iterator;
import java.util.Random;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.imageio.ImageIO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.compras.supermercados.exceptions.QRException;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

public class GenerarQR
{
	private static final Logger LOG=LoggerFactory.getLogger(GenerarQR.class);
	
	/**
	 * Metodo para crear al codigo QR
	 * @param monto
	 * @param idSubsidiaria
	 * @param idTienda
	 * @param idCajero
	 * @param nombreCajero
	 * @param idCaja
	 * @return String
	 * @throws QRException
	 */
	public static String generarCodigoQR(String monto, String idSubsidiaria, String idTienda, String idCajero, String nombreCajero, String idCaja) throws QRException
	{
		StringBuilder infoCodigoQR=new StringBuilder();
		
		LOG.info("Generando codigo QR");
		
		String referenciaUnica=generarReferencia(16);
		String numeroTransaccion=generarReferencia(5);
		String hash=crearHash(referenciaUnica, monto, idSubsidiaria, numeroTransaccion);
		String referenciaUnicaCifrada=cifraReferenciaUnica(referenciaUnica, numeroTransaccion);
		
		descifraReferenciaUnica(referenciaUnicaCifrada, numeroTransaccion);
		
		infoCodigoQR.append(referenciaUnicaCifrada).append("|");
		infoCodigoQR.append(monto).append("|");
		infoCodigoQR.append(ConstantesQR.ID_COMERCIO).append("|");
		infoCodigoQR.append(idSubsidiaria).append("|");
		infoCodigoQR.append(idTienda).append("|");
		infoCodigoQR.append(idCajero).append("|");
		infoCodigoQR.append(nombreCajero).append("|");
		infoCodigoQR.append(idCaja).append("|");
		infoCodigoQR.append(numeroTransaccion).append("|");
		infoCodigoQR.append(hash);
		
		LOG.info("Informacion Codigo QR:       {}", String.valueOf(infoCodigoQR));
		
		crearArchivoQR(String.valueOf(infoCodigoQR), numeroTransaccion);
		
		return numeroTransaccion;
	}
	
	/**
	 * Metodo para validar si el valor ingresado es un numero
	 * @param informacion
	 * @return boolean
	 */
	public static boolean esNumero(String informacion)
	{
		return Pattern.matches("\\d*", informacion);
	}
	
	/**
	 * Metodo para validar solo texto
	 * @param texto
	 * @return boolean
	 */
	public static boolean soloTexto(String texto)
	{
		return Pattern.matches("[a-zA-z]", texto);
	}
	
	/**
	 * Metodo para validar que el campo no sea nulo o vacio
	 * @return boolean
	 */
	public static boolean esNuloVacio(String informacion)
	{
		if(informacion==null || informacion.trim().isEmpty())
			return true;
		
		return false;
	}
	
	/**
	 * Metodo para validar si el monto es correcto
	 * @param informacion
	 * @return boolean
	 * @throws QRException
	 */
	public static boolean esMontoValido(String informacion) throws QRException
	{
		try {
	        Double.parseDouble(informacion);
	        return true;
	    } catch (NumberFormatException e) {
	    	throw new QRException("El monto no contiene el formato correcto.");
	    }
	}
	
	/**
	 * Metodo para generar referencias unicas
	 * @param posiciones
	 * @return String
	 */
	@SuppressWarnings("rawtypes")
	private static String generarReferencia(int posiciones)
	{
		StringBuilder aleatorio=new StringBuilder();
		Random random = new Random();
		IntStream intStream = random.ints(posiciones, 0, 10);
		Iterator iterator = intStream.iterator();
		
		while (iterator.hasNext()) {
			aleatorio.append(iterator.next());
		}
		
		return aleatorio.toString();
	}
	
	/**
	 * Metodo para obtener el hash
	 * @param referenciaUnica
	 * @param monto
	 * @param idSucursal
	 * @param numeroTransaccion
	 * @return String
	 * @throws QRException 
	 */
	private static String crearHash(String referenciaUnica, String monto, String idSucursal, String numeroTransaccion) throws QRException
	{
		StringBuilder hash=new StringBuilder();
		
		hash.append(referenciaUnica);
		hash.append(monto);
		hash.append(idSucursal);
		hash.append(numeroTransaccion);
		try {
			MessageDigest digest = MessageDigest.getInstance(ConstantesQR.ALGORITMO_HASH);
			byte[] hashByte = digest.digest(String.valueOf(hash).getBytes(StandardCharsets.UTF_8));
			return Base64.getEncoder().encodeToString(hashByte);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			throw new QRException("No fue posible obtener el valos Hash.");
		}
	}
	
	/**
	 * Metodo para cifrar la referencia unica
	 * @param referenciaUnica
	 * @param numeroTransaccion
	 * @return String
	 * @throws QRException
	 */
	private static String cifraReferenciaUnica(String referenciaUnica, String numeroTransaccion) throws QRException
	{
		String nuevaLlave=ConstantesQR.LLAVE_CIFRADO.concat(numeroTransaccion);
		
		try {
			LOG.info("Referencia unica a cifrar:   {}", referenciaUnica);
			SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(ConstantesQR.ALGORITMO_SHA);
	        KeySpec keySpec = new PBEKeySpec(nuevaLlave.toCharArray(), nuevaLlave.getBytes(), 65536, 256);
	        SecretKey secretKey = secretKeyFactory.generateSecret(keySpec);
	        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getEncoded(), ConstantesQR.ALGORITMO_AES);
	         
	        Cipher cipher = Cipher.getInstance(ConstantesQR.ALGORITMO_PADDING);
	        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, new IvParameterSpec(nuevaLlave.getBytes()));
	        
	        return java.util.Base64.getEncoder().encodeToString(cipher.doFinal(referenciaUnica.getBytes(StandardCharsets.UTF_8)));
		} catch (NoSuchAlgorithmException | InvalidKeySpecException | NoSuchPaddingException | InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
			e.printStackTrace();
			throw new QRException("Error al cifrar la referencia unica.");
		}
	}
	
	/**
	 * Metodo para descifrar la referencia unica
	 * @param textoCifrado
	 * @param numeroTransaccion

	 */
	private static void descifraReferenciaUnica(String textoCifrado, String numeroTransaccion)
	{
		String nuevaLlave=ConstantesQR.LLAVE_CIFRADO.concat(numeroTransaccion);
		
		try {
			LOG.info("Antes de descifrar:          {}", textoCifrado);
	        SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(ConstantesQR.ALGORITMO_SHA);
	        KeySpec keySpen = new PBEKeySpec(nuevaLlave.toCharArray(), nuevaLlave.getBytes(), 65536, 256);
	        SecretKey secretKey = secretKeyFactory.generateSecret(keySpen);
	        SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getEncoded(), ConstantesQR.ALGORITMO_AES);
	         
	        Cipher cipher = Cipher.getInstance(ConstantesQR.ALGORITMO_PADDING);
	        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, new IvParameterSpec(nuevaLlave.getBytes()));
	        
	        LOG.info("Referencia unica descifrada: "+new String(cipher.doFinal(java.util.Base64.getDecoder().decode(textoCifrado)), StandardCharsets.UTF_8));
		} catch (NoSuchAlgorithmException | InvalidKeySpecException | NoSuchPaddingException | InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Metodo para crear el codigo QR
	 * @param infoCodigoQR
	 * @param numeroTransaccion
	 * @throws QRException 
	 */
	@SuppressWarnings("deprecation")
	private static void crearArchivoQR(String infoCodigoQR, String numeroTransaccion) throws QRException
	{
		try {
			File codigoQR = new File(numeroTransaccion.concat(ConstantesQR.WALMART));
			File logo=new File(ConstantesQR.WALMART_LOGO);
			QRCodeWriter writer = new QRCodeWriter();
			
			BitMatrix matrix = writer.encode(infoCodigoQR, BarcodeFormat.QR_CODE, 1200, 1200);
			MatrixToImageConfig config = new MatrixToImageConfig(MatrixToImageConfig.BLACK, MatrixToImageConfig.WHITE);

		    BufferedImage qrImage = MatrixToImageWriter.toBufferedImage(matrix, config);
		    
		    BufferedImage logoImage = ImageIO.read(logo.toURL());
		    
		    int deltaHeight = qrImage.getHeight() - logoImage.getHeight();
		    int deltaWidth = qrImage.getWidth() - logoImage.getWidth();
		    
		    BufferedImage combined = new BufferedImage(qrImage.getHeight(), qrImage.getWidth(), BufferedImage.TYPE_INT_ARGB);
		    Graphics2D g = (Graphics2D) combined.getGraphics();
		    
		    g.drawImage(qrImage, 0, 0, null);
		    g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
		    
		    g.drawImage(logoImage, (int) Math.round(deltaWidth / 2), (int) Math.round(deltaHeight / 2), null);

			ImageIO.write(combined, ConstantesQR.EXTENSION_QR, codigoQR);
			LOG.info("QR guardado en:              {}", codigoQR.getAbsolutePath());
		}catch(WriterException e) {
			e.printStackTrace();
			throw new QRException("Error al crear el archivo para el codigo QR.");
		}catch(IOException e) {
			e.printStackTrace();
			throw new QRException("Error al crear el archivo para el codigo QR.");
		}
	}
}
