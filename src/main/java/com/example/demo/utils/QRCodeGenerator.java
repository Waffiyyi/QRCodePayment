package com.example.demo.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import jakarta.xml.bind.DatatypeConverter;
import net.glxn.qrgen.QRCode;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

public class QRCodeGenerator {
    public static byte[] generateQRCodeImage(String text, int width, int height) throws IOException {
        ByteArrayOutputStream stream = QRCode.from(text).withSize(width, height).stream();
        return stream.toByteArray();
    }

//    public static String decodeQRCodeImage(BufferedImage image) throws NotFoundException {
//        LuminanceSource source = new BufferedImageLuminanceSource(image);
//        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
//        Result result = new MultiFormatReader().decode(bitmap);
//        return result.getText();
//    }

        public static Map<String, String> extractInfoFromQRCode(String base64QRCode) {
        try {
            byte[] decodedBytes = DatatypeConverter.parseBase64Binary(base64QRCode);
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(decodedBytes);
            BufferedImage bufferedImage = ImageIO.read(byteArrayInputStream);

            Map<DecodeHintType, Object> hints = new HashMap<>();
            hints.put(DecodeHintType.TRY_HARDER, Boolean.TRUE);
            hints.put(DecodeHintType.POSSIBLE_FORMATS, EnumSet.allOf(BarcodeFormat.class));

            BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(new BufferedImageLuminanceSource(bufferedImage)));
            Result result = new MultiFormatReader().decode(binaryBitmap, hints);

            String qrCodeData = result.getText();

            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.readValue(qrCodeData, Map.class);
        } catch (IOException | NotFoundException e) {
            e.printStackTrace();
            return null;
        }
}

}
