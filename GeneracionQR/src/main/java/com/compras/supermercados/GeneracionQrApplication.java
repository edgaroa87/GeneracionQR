package com.compras.supermercados;

import java.awt.EventQueue;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import com.compras.supermercados.interfaz.QRView;

@SpringBootApplication
public class GeneracionQrApplication
{
	public static void main(String[] args)
	{
		new SpringApplicationBuilder(GeneracionQrApplication.class).headless(false).web(WebApplicationType.NONE).run(args);
		
		EventQueue.invokeLater(new Runnable()
		{
			public void run() {
				try {
					QRView frame = new QRView();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
