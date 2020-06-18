package com.compras.supermercados;

import java.awt.EventQueue;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import com.compras.supermercados.interfaz.QRView;

@SpringBootApplication
public class GeneracionQrApplication
{
	public static void main(String[] args)
	{
		ConfigurableApplicationContext applicationContext=new SpringApplicationBuilder(GeneracionQrApplication.class)
																.headless(false)
																.run(args);
		
		EventQueue.invokeLater(() ->{
			QRView qrView=applicationContext.getBean(QRView.class);
			qrView.setVisible(true);
		});
	}
}
