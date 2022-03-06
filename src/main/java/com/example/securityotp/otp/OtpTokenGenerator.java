package com.example.securityotp.otp;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import org.apache.commons.codec.binary.Base32;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.SecureRandom;

@Service
public class OtpTokenGenerator {
    private final String qrImagesPath;
    private final String issuer;

    public OtpTokenGenerator(@Value("${custom.path.qr-images}") String qrImagesPath, @Value("${custom.otp-issuer}") String issuer) {
        this.qrImagesPath = qrImagesPath;
        this.issuer = issuer;
    }

    // 회원의 최초 개인 Secret Key 생성
    // 이 값은 구글 OTP 인증에 사용되기 때문에 회원 테이블에 저장되어야 한다.
    public String generateSecretKey() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] bytes = new byte[20];
        secureRandom.nextBytes(bytes);
        Base32 base32 = new Base32();

        return base32.encodeToString(bytes);
    }

    // 개인 Secret Key, 사용자 Id, 발급자를 파라미터로 받아 구글 OTP 링크 생성
    public static String createQRUrl(String secretKey, String account, String issuer) {
        try {
            return "otpauth://totp/"
                    + URLEncoder.encode(issuer + ":" + account, "UTF-8").replace("+", "%20")
                    + "?secret=" + URLEncoder.encode(secretKey, "UTF-8").replace("+", "%20")
                    + "&issuer=" + URLEncoder.encode(issuer, "UTF-8").replace("+", "%20");
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException(e);
        }
    }

    public String generateQRCode(String secretKey, String username) {
        try {
            String barCode = this.createQRUrl(secretKey, username, issuer);

            this.createQRImage(barCode, qrImagesPath + username + ".png", 200, 200);

            return "/tobi/qrcode/" + username + ".png";
        } catch (WriterException | IOException e) {
            e.printStackTrace();

            return "";
        }
    }

    private void createQRImage(String barCodeData, String filePath, int height, int width)
            throws WriterException, IOException {
        BitMatrix bitMatrix = new MultiFormatWriter().encode(barCodeData, BarcodeFormat.QR_CODE, width, height);

        try (FileOutputStream out = new FileOutputStream(filePath)) {
            MatrixToImageWriter.writeToStream(bitMatrix, "png", out);
        }
    }
}
