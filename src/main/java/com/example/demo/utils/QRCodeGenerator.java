package com.example.demo.utils;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import net.glxn.qrgen.QRCode;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

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
}
