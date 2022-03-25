package encryptin;

import org.junit.Assert;
import org.junit.Test;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.text.DecimalFormat;
import java.util.Base64;


/**
 * @author mayanwei
 * @date 2022-03-16.
 */
public class EncryptTest{
    //常见的安全算法
    //加密算法：
    //  1.对称加密(DES,3DES,AES) 2.非对称加密(RSA)
    //  对称加密只需要一个key，而非对称加密需要两个key(public key, private key)。
    //数字签名
    //数字证书


    //Base64 彩虹表破解Hash算法原理，群举法

    /**
     * @throws NoSuchAlgorithmException
     * @throws NoSuchPaddingException
     * @throws InvalidKeyException
     * @throws IllegalBlockSizeException
     * @throws BadPaddingException
     */
    @Test
    public void testDES() throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException{
        KeyGenerator keyGenerator = KeyGenerator.getInstance( "DES" );
        keyGenerator.init( 56 );
        SecretKey secretKey = keyGenerator.generateKey();

        //加密
        Cipher cipher = Cipher.getInstance( "DES" );
        cipher.init( Cipher.ENCRYPT_MODE, secretKey );
        byte[] source = "Hello, I am a engineer".getBytes( StandardCharsets.UTF_8 );
        byte[] encryptSource = cipher.doFinal( source );

        cipher.init( Cipher.DECRYPT_MODE, secretKey );
        byte[] decryptSource = cipher.doFinal( encryptSource );

        Assert.assertArrayEquals( source, decryptSource );
    }


    @Test
    public void testAES() throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException{
        KeyGenerator aes = KeyGenerator.getInstance( "AES" );
        //128, 192, 256
        aes.init( 128 );
        SecretKey secretKey = aes.generateKey();
        byte[] secretKeyByte = secretKey.getEncoded();
        Base64.Encoder encoder = Base64.getEncoder();
        //The Base64 is representation of binary data, based 64 printable char, 2^6=64
        byte[] secretKeyByteBase64 = encoder.encode( secretKeyByte );//easy to print

        String keyStr = new String( secretKeyByteBase64 );
        System.out.println( keyStr );
        System.out.println( keyStr.length() ); //24

        Base64.Decoder decoder = Base64.getDecoder();
        byte[] decodeSecretKeyByte = decoder.decode( secretKeyByteBase64 );
        Assert.assertArrayEquals( secretKeyByte, decodeSecretKeyByte );

        //encrypt
        Cipher aesCipher = Cipher.getInstance( "AES" );
        aesCipher.init( Cipher.ENCRYPT_MODE, new SecretKeySpec( decodeSecretKeyByte, "AES" ) );
        byte[] contentBytes = "Hello AES".getBytes( StandardCharsets.UTF_8 );
        byte[] encryptContentBytes = aesCipher.doFinal( contentBytes );


        //decrypt
        Cipher decryptCipher = Cipher.getInstance( "AES" );
        decryptCipher.init( Cipher.DECRYPT_MODE, new SecretKeySpec( decodeSecretKeyByte, "AES" ) );
        byte[] decryptContentBytes = decryptCipher.doFinal( encryptContentBytes );

        Assert.assertArrayEquals( contentBytes, decryptContentBytes );
    }


    // 非对称加密的基本过程：甲方生产一对密钥并将其中的一把作为公钥向其他人公开，得到公钥的乙方使用该密钥对机密信息进行加密后再发送给甲方
    // 乙方获取到信息后，用另外一个配对的专用密钥（即私钥）对加密后的文件进行解密。
    // RSA 1977 原理：将两个大素数相乘十分容易，但反过来想要对其乘积进行因式分解却极其困难，因此可以将两个乘积公开作为加密密钥。

    @Test
    public void testRSA() throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException{
        //generate keyPair(private key and public key)
        KeyPairGenerator rsaKeyPairGenerator = KeyPairGenerator.getInstance( "RSA" );
        rsaKeyPairGenerator.initialize( 512 );
        KeyPair keyPair = rsaKeyPairGenerator.generateKeyPair();

        PublicKey publickey = keyPair.getPublic();
        byte[] publicKeyBytes = publickey.getEncoded();
        byte[] publickeyBytesBase64 = Base64.getEncoder().encode( publicKeyBytes );
        System.out.println("public key base64 encoded："+ new String(publickeyBytesBase64) );


        PrivateKey privateKey = keyPair.getPrivate();
        byte[] privateKeyBytes = privateKey.getEncoded();
        byte[] privateKeyBytesBase64 = Base64.getEncoder().encode( privateKeyBytes );
        System.out.println( "private key base64 encoded："+new String(privateKeyBytesBase64) );

        //str2PublicKey
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec( Base64.getDecoder().decode( publickeyBytesBase64 ) );
        KeyFactory keyFactory = KeyFactory.getInstance( "RSA" );
        PublicKey publicKey1 = keyFactory.generatePublic( keySpec );

        //str2PrivateKey
        PKCS8EncodedKeySpec keySpec1 = new PKCS8EncodedKeySpec( Base64.getDecoder().decode( privateKeyBytesBase64 ) );
        KeyFactory rsaKeyFactory = KeyFactory.getInstance( "RSA" );
        PrivateKey privateKey1 = rsaKeyFactory.generatePrivate( keySpec1 );

        Assert.assertArrayEquals( publickey.getEncoded(), publicKey1.getEncoded() );
        Assert.assertArrayEquals( privateKey.getEncoded(),privateKey1.getEncoded() );

        //encryption with public key and decryption with private key
        String content = "hello, RSA";
        Cipher rsaCipher = Cipher.getInstance( "RSA" );
        rsaCipher.init( Cipher.ENCRYPT_MODE, publicKey1 );
        byte[] encryptedBytesByRSA = rsaCipher.doFinal( content.getBytes( StandardCharsets.UTF_8 ) );

        Cipher rsaDecodeCipher = Cipher.getInstance( "RSA" );
        rsaDecodeCipher.init( Cipher.DECRYPT_MODE, privateKey1 );
        byte[] decryptedBytesByRSA = rsaDecodeCipher.doFinal( encryptedBytesByRSA );
        Assert.assertArrayEquals( content.getBytes( StandardCharsets.UTF_8 ), decryptedBytesByRSA );

        //使用1024初始化KeyPairGenerator， RSA机密后的密文长度为1024为，即128个字节，此时明文的长度不能超过117个字节
        //超过117个字节需要使用2048的keysize来初始化KeyPairGenerate。
        //RSA的keysize位数越高，其产生的密钥及对加密、解密的速度越慢，这是基于大素数非堆成加密算法的缺陷。
    }

    /**
     * 数字签名
     */
    @Test
    public void testMD5withRSA() throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, SignatureException{
        //sign
        MessageDigest md5Digest = MessageDigest.getInstance( "MD5" );
        String content ="content shouldn't be modified.";
        byte[] contentBytes = content.getBytes( StandardCharsets.UTF_8 );
        byte[] contentDigestBytes = md5Digest.digest( contentBytes );

        KeyPairGenerator rsaKeyPairGenerator = KeyPairGenerator.getInstance( "RSA" );
        KeyPair keyPair = rsaKeyPairGenerator.generateKeyPair();
        PrivateKey privateKey = keyPair.getPrivate();

        Cipher rsaCipher = Cipher.getInstance( "RSA" );
        rsaCipher.init( Cipher.ENCRYPT_MODE, privateKey);

        byte[] digestEncrypted = rsaCipher.doFinal( contentDigestBytes );

        //verify
        String receivedContent = content;
        MessageDigest md5DigestSame = MessageDigest.getInstance( "MD5" );
        byte[] receivedContentDigestBytes = md5DigestSame.digest( receivedContent.getBytes( StandardCharsets.UTF_8 ) );

        byte[] receivedDigestEncryptedBytes = digestEncrypted;
        Cipher rsaEncoderCipher = Cipher.getInstance( "RSA" );
        PublicKey publicKey = keyPair.getPublic();
        rsaCipher.init( Cipher.DECRYPT_MODE, publicKey);
        byte[] digestDecryptedBytes = rsaCipher.doFinal( receivedDigestEncryptedBytes );

        Assert.assertArrayEquals( receivedContentDigestBytes,digestDecryptedBytes );

        //基于Java的Signature API使用
        //step 1 sign
        Signature md5WithRSASignature = Signature.getInstance( "MD5withRSA" );
        md5WithRSASignature.initSign( privateKey );
        md5WithRSASignature.update( content.getBytes() );
        byte[] md5withRSASign = md5WithRSASignature.sign();
        //step2 verify
        Signature md5withRSAVerifySignature = Signature.getInstance( "MD5withRSA" );
        md5withRSAVerifySignature.initVerify( publicKey );
        String contentReceived = content;
        md5withRSAVerifySignature.update( contentReceived.getBytes( StandardCharsets.UTF_8 ) );
        boolean verify = md5withRSAVerifySignature.verify( md5withRSASign );
        Assert.assertTrue( verify );

    }

    /**
     * SHA1withRSA 表示采用 SHA-1 算法生成的数字摘要，并且使用RSA 算法来对摘要进行加密和解密。
     */
    @Test
    public void testSHA1withRSA() throws NoSuchAlgorithmException, InvalidKeyException, SignatureException{
        String content = "Hello , SHA1withRSA";
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance( "RSA" );
        keyPairGenerator.initialize(512);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        PrivateKey privateKey = keyPair.getPrivate();
        PublicKey publicKey = keyPair.getPublic();

        //step 1 sign

        Signature sha1withRSASignature = Signature.getInstance( "SHA1withRSA" );
        sha1withRSASignature.initSign( privateKey );
        sha1withRSASignature.update( content.getBytes( StandardCharsets.UTF_8 ) );
        byte[] signBytes = sha1withRSASignature.sign();

        //step 2 verify
        String contentRecived = content;
        byte[]  signByteReceived = signBytes;


        Signature sha1withRSAVerifySignature = Signature.getInstance( "SHA1withRSA" );
        sha1withRSAVerifySignature.initVerify( publicKey );
        sha1withRSAVerifySignature.update( contentRecived.getBytes( StandardCharsets.UTF_8 ) );
        boolean verifyResult = sha1withRSAVerifySignature.verify( signByteReceived );
        Assert.assertTrue( verifyResult );
    }


    //public static void main(String[] args)  throws Exception {
    //    String content = "study hard and make progress everyday";
    //    System.out.println("content :"+content);
    //
    //    KeyPair keyPair = getKeyPair();
    //    PublicKey publicKey =  keyPair.getPublic();
    //    PrivateKey privateKey = keyPair.getPrivate();
    //
    //    String md5Sign  = getMd5Sign(content,privateKey);
    //    System.out.println("sign with md5 and rsa :"+ md5Sign);
    //    boolean md5Verifty = verifyWhenMd5Sign(content,md5Sign,publicKey);
    //    System.out.println("verify sign with md5 and rsa :"+ md5Verifty);
    //
    //    String sha1Sign  = getSha1Sign(content,privateKey);
    //    System.out.println("sign with sha1 and rsa :"+ sha1Sign);
    //    boolean sha1Verifty = verifyWhenSha1Sign(content,sha1Sign,publicKey);
    //    System.out.println("verify sign with sha1 and rsa :"+ sha1Verifty);
    //
    //}
    //
    ////生成密钥对
    //static KeyPair getKeyPair() throws Exception {
    //    KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
    //    keyGen.initialize(512); //可以理解为：加密后的密文长度，实际原文要小些 越大 加密解密越慢
    //    KeyPair keyPair = keyGen.generateKeyPair();
    //    return keyPair;
    //}
    //
    ////用md5生成内容摘要，再用RSA的私钥加密，进而生成数字签名
    //static String getMd5Sign(String content , PrivateKey privateKey) throws Exception {
    //    byte[] contentBytes = content.getBytes("utf-8");
    //    Signature signature = Signature.getInstance("MD5withRSA");
    //    signature.initSign(privateKey);
    //    signature.update(contentBytes);
    //    byte[] signs = signature.sign();
    //    return Base64.getEncoder().encodeToString( signs );
    //}
    //
    ////对用md5和RSA私钥生成的数字签名进行验证
    //static boolean verifyWhenMd5Sign(String content, String sign, PublicKey publicKey) throws Exception {
    //    byte[] contentBytes = content.getBytes("utf-8");
    //    Signature signature = Signature.getInstance("MD5withRSA");
    //    signature.initVerify(publicKey);
    //    signature.update(contentBytes);
    //    return signature.verify(Base64.getDecoder().decode(sign));
    //}
    //
    ////用sha1生成内容摘要，再用RSA的私钥加密，进而生成数字签名
    //static String getSha1Sign(String content , PrivateKey privateKey) throws Exception {
    //    byte[] contentBytes = content.getBytes("utf-8");
    //    Signature signature = Signature.getInstance("SHA1withRSA");
    //    signature.initSign(privateKey);
    //    signature.update(contentBytes);
    //    byte[] signs = signature.sign();
    //    return Base64.getEncoder().encodeToString( signs );
    //}
    //
    ////对用md5和RSA私钥生成的数字签名进行验证
    //static boolean verifyWhenSha1Sign(String content, String sign, PublicKey publicKey) throws Exception {
    //    byte[] contentBytes = content.getBytes("utf-8");
    //    Signature signature = Signature.getInstance("SHA1withRSA");
    //    signature.initVerify(publicKey);
    //    signature.update(contentBytes);
    //    return signature.verify(Base64.getDecoder().decode(sign));
    //}
}
