package org.example;


import com.google.zxing.BarcodeFormat;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import com.warrenstrange.googleauth.GoogleAuthenticatorQRGenerator;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/code")
public class CodeController {

    private final GoogleAuthenticator gAuth;

    @SneakyThrows
    @GetMapping("/generate/{username}")
    public byte[]  generate(@PathVariable String username, HttpServletResponse response) {

        System.out.println("COdeGenerate Callled with "+ username);

        final GoogleAuthenticatorKey key = gAuth.createCredentials(username);

        //I've decided to generate QRCode on backend site
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        String otpAuthURL = GoogleAuthenticatorQRGenerator.getOtpAuthTotpURL("my-demo", username, key);
        BitMatrix bitMatrix = qrCodeWriter.encode(otpAuthURL, BarcodeFormat.QR_CODE, 200, 200);

        //Simple writing to output-stream
        ServletOutputStream outputStream = response.getOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", outputStream);
        outputStream.close();


        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "PNG", byteArrayOutputStream);
        byte[] pngData = byteArrayOutputStream.toByteArray();

        return pngData;
    }

    @PostMapping("/validate/key")
    public boolean validateKey(@RequestBody ValidateCodeDto body) {
        return gAuth.authorizeUser(body.getUsername(), body.getCode());
    }







}
