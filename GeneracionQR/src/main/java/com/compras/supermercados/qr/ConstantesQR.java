package com.compras.supermercados.qr;

import java.util.Arrays;
import java.util.List;

/**
 * Constantes
 * @author eonofre
 */
public class ConstantesQR
{
	public static final String LLAVE_CIFRADO_WALMART="WLTSb4z7EC4";
	public static final String LLAVE_CIFRADO_SORIANA="SRNsxAzt3Ca";
	public static final String LLAVE_CIFRADO_WALMART_PRODUCCION="WLTpR4z7EC4";
	public static final String LLAVE_CIFRADO_SORIANA_PRODUCCION="SRNP0Azt3Ca";
	public static final String ID_COMERCIO_WALMART="33";
	public static final String ID_COMERCIO_SORIANA="55";
	public static final String COMERCIO_WALMART="WALMART";
	public static final String COMERCIO_SORIANA="SORIANA";
	public static final String ARCHIVO_LOGO="_logo.png";
	public static final String ALGORITMO_HASH="SHA-512";
	public static final String ALGORITMO_SHA="PBKDF2WithHmacSHA256";
	public static final String ALGORITMO_AES="AES";
	public static final String ALGORITMO_PADDING="AES/CBC/PKCS5Padding";
	public static final String EXTENSION_QR="png";
	public static final String ERROR_DIALOGO="Error en la operaci\u00f3n";
	public static final List<String> LISTACOMPAÑIAS=Arrays.asList("Bodega Aurrera", "Sam's Club", "Superama", "Walmart", "Soriana (Hiper, Mega, Super, Mercado, Express)", "City Club", "Super City");
	public static final List<String> AMBIENTES=Arrays.asList("Desarrollo", "Producción");
	public static final String AMBIENTE_DESARROLLO="Desarrollo";
	public static final String MONTO_DECIMALES="%.2f";
	public static final String ESPACIO=" ";
	public static final String CADENA_VACIA="";
	public static final String COMILLA_SIMPLE="'";
	public static final String GUION_BAJO="_";
	public static final String PUNTO_EXTENSION=".png";
	public static final String COMA=",";
	public static final String PUNTO=".";
}
