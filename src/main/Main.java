package main;

import javax.swing.JOptionPane;

import reader.QRCodeReader;
import writer.QRCodeGenerator;

public class Main {
	public static void main(String[] args) {
		over:
    	do {
    		try {
	    		String userEntry = (String) JOptionPane.showInputDialog(null, "Escolha uma opção:\n1- Gerar um QR Code\n2- Ler QR Code\n3- Sair", "", JOptionPane.QUESTION_MESSAGE, null, null, null);
	    		switch (userEntry) {
					case "1":
						QRCodeGenerator.generateQRCodeImage();
						break;
					case "2":
						QRCodeReader.decodeQRCodeImage();
						break;
					case "3":
						break over;
					default:
						throw new Exception();
				}
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Opção inválida!", "", JOptionPane.ERROR_MESSAGE, null);
			}
    	} while(true);
	}
}
