package com.compras.supermercados.exceptions;

public class QRException extends Exception
{
	private static final long serialVersionUID = 4179212251108259048L;
	
	private final String mensajeError;
	
	public QRException(String mensaje)
	{
		mensajeError=mensaje;
	}

	public String getMensajeError() {
		return mensajeError;
	}
}
