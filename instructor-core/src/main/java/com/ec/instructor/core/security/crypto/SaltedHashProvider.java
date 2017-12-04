package com.ec.instructor.core.security.crypto;

import com.ec.instructor.core.exceptions.SystemException;
import lombok.Getter;
import lombok.val;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author Colin.Z.Chen
 * @description The class of salted hash cryptographic provider for encryption.
 *  apply to customer login password. it generate fixed 44 length.
 * @time 2017/12/3.
 */
public class SaltedHashProvider {
    //region Private Variables
    private final static String SALT_ALGORITHM ="SHA1";
    private final static String HASH_ALGORITHM="SHA-256";
    @Getter
    private  String defaultCharset  = "UTF-8";
    //endregion

    //region constructor
    public SaltedHashProvider(){
        super();
    }

    public SaltedHashProvider(String charset){
        this.defaultCharset = charset;
    }

    //endregion

    //region Public Methods
    public String encrypt(String plainText){
        if(StringUtils.isBlank(plainText) || StringUtils.isBlank(this.defaultCharset)){
            return "";
        }

        byte[] plainTextBytes = null;
        MessageDigest digest;
        try{
            plainTextBytes = plainText.getBytes(this.defaultCharset);
            digest = MessageDigest.getInstance(SALT_ALGORITHM);
        }catch (UnsupportedEncodingException | NoSuchAlgorithmException ex){
            throw new SystemException(ex);
        }

        val saltBytes = digest.digest(plainTextBytes);
        return Base64.encodeBase64String(encrypt(plainTextBytes, saltBytes));
    }

    //endregion

    //region Private Methods
    public byte[] encrypt(byte[] plainTextBytes, byte[] saltBytes) {
        if (plainTextBytes == null || saltBytes == null) {
            return null;
        }

        xorSalt(saltBytes, plainTextBytes);
        byte[] dataAndSalt = ArrayUtils.addAll(plainTextBytes, saltBytes);
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance(HASH_ALGORITHM);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        return digest.digest(dataAndSalt);
    }
    /**
     * Xor-Salt implements low-rent, symmetrical encryption by xor-ing data with
     * a password and salt. The salt is generated from the password itself, and
     * gets reshuffled at periods of irregular length throughout the encryption.
     *
     * @param saltBytes
     *            the salt byte array.
     * @param plainTextBytes
     *            the plain text byte array.
     */
    private void xorSalt(byte[] saltBytes, byte[] plainTextBytes) {
        int index = 0;
        int num = 0;
        while (index < saltBytes.length) {
            saltBytes[index] = (byte) (saltBytes[index] ^ plainTextBytes[num]);
            if (++num >= plainTextBytes.length) {
                num = 0;
            }
            index++;
        }
    }
    //endregion
}
