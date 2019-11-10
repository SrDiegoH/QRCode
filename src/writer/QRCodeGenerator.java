package writer;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.time.Instant;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

public class QRCodeGenerator {
    
    private static byte[] generateQRCodeAsImageBytes(String text, int width, int height) throws WriterException, IOException {
    	BitMatrix bitMatrix = new QRCodeWriter().encode(text, BarcodeFormat.QR_CODE, width, height);

        ByteArrayOutputStream streamer = new ByteArrayOutputStream();
        
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", streamer);
        
        byte[] imageData = streamer.toByteArray();
        
        return imageData;
    }
        
    private static String saveQRCodeOnPath(String text, int width, int height, String filePath) throws WriterException, IOException {
        byte [] dataArray = generateQRCodeAsImageBytes(text, width, height);
        
        ByteArrayInputStream byteStream = new ByteArrayInputStream(dataArray);
        BufferedImage buffImage = ImageIO.read(byteStream);
        
        String fileName = "QRCode-" + String.valueOf(Instant.now().getNano()) + ".png"; 
        
        File file = new File(filePath + "\\" + fileName);
        
        ImageIO.write(buffImage, "png", file);
        
        return file.toString();
    }
    
    public static void generateQRCodeImage() {
        try {
        	String text = (String) JOptionPane.showInputDialog(null, "Digite o texto que deverá conter no QR Code", "", 0, null, null, null);
        	
            JFileChooser fileChooser = new JFileChooser();
			fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			fileChooser.setDialogTitle("Selecione onde quer salvar:");
			
			int chooserReturn = fileChooser.showSaveDialog(null);
			
			if (chooserReturn != JFileChooser.APPROVE_OPTION) {
				throw new Exception();
			}
			
			String imagePath = saveQRCodeOnPath(text, 350, 350, fileChooser.getSelectedFile().getPath());
			
        	JOptionPane.showMessageDialog(null, "QR Code salvo em: " + imagePath, "", JOptionPane.INFORMATION_MESSAGE, null);
        } catch (WriterException | IOException e) {
        	JOptionPane.showMessageDialog(null, "Erro ao gerar QR Code: " + e.getMessage(), "", JOptionPane.ERROR_MESSAGE, null);
        } catch (Exception e) {
        	JOptionPane.showMessageDialog(null, "Erro ao selecionar local", "", JOptionPane.ERROR_MESSAGE, null);
        }
    }
}