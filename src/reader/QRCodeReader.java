package reader;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.google.zxing.BinaryBitmap;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;

public class QRCodeReader {
	
	private static String readQRCode(File file) throws IOException, NotFoundException {
		BufferedImage buffImage = ImageIO.read(file);
		
		LuminanceSource source = new BufferedImageLuminanceSource(buffImage);
		BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

		try {
			Result result = new MultiFormatReader().decode(bitmap);
			return result.getText();
		} catch (NotFoundException e) {
			throw e;
		} catch (Exception e) {
			return "NO DATA";
		}
	}

	public static void decodeQRCodeImage(){
        try {
            JFileChooser fileChooser = new JFileChooser();
			fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("Images", "jpg", "png", "gif", "bmp"));
			fileChooser.setDialogTitle("Selecione o QR Code:");
			
			int chooserReturn = fileChooser.showSaveDialog(null);
			
			if (chooserReturn != JFileChooser.APPROVE_OPTION) {
				throw new Exception();
			}
			
			String qrCodeData = readQRCode(fileChooser.getSelectedFile());
			
        	JOptionPane.showMessageDialog(null, "Conteúdo do QR Code:\n" + qrCodeData, "", JOptionPane.INFORMATION_MESSAGE, null);
        } catch (WriterException | IOException e) {
        	JOptionPane.showMessageDialog(null, "Erro ao ler QR Code: " + e.getMessage(), "", JOptionPane.ERROR_MESSAGE, null);
        } catch (Exception e) {
        	JOptionPane.showMessageDialog(null, "Erro ao selecionar imagem", "", JOptionPane.ERROR_MESSAGE, null);
        }
	}
}
