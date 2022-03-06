package com.example.securityotp.otp;

import de.taimos.totp.TOTP;
import org.apache.commons.codec.binary.Base32;
import org.apache.commons.codec.binary.Hex;
import org.springframework.stereotype.Service;

@Service
public class OtpTokenValidator {
    public static boolean validate(String inputCode, String secretKey) {
        String totpCode = getOtpCode(secretKey);

        return totpCode.equals(inputCode);
    }

    public static String getOtpCode(String secretKey) {
        Base32 base32 = new Base32();
        byte[] bytes = base32.decode(secretKey);
        String hexKey = Hex.encodeHexString(bytes);

        return TOTP.getOTP(hexKey);
    }
}
