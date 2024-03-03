package tn.esprit.Controllers;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class QrCodeView {
    @FXML
    private ImageView QrImage;
    @FXML
    public void setTransportData(String transportData) {
        // Generate QR code for the transport data
        Image qrCodeImage = generateQRCodeForTransport(transportData);

        // Set the generated QR code image in the ImageView
        QrImage.setImage(qrCodeImage);
    }

    private Image generateQRCodeForTransport(String transportData) {
        Image qrCodeImage = null;
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            BitMatrix bitMatrix = new MultiFormatWriter().encode(transportData, BarcodeFormat.QR_CODE, 200, 200);
            MatrixToImageWriter.writeToStream(bitMatrix, "PNG", out);
            byte[] qrCodeBytes = out.toByteArray();
            qrCodeImage = new Image(new ByteArrayInputStream(qrCodeBytes));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return qrCodeImage;
    }
}
