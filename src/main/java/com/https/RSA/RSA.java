package com.https.RSA;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import java.security.Key;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;

/**
 * RSA
 *
 * @author ligaoyong@gogpay.cn
 * @date 2019/12/12 11:18
 */
public class RSA {

    /**
     * 公钥
     */
    public static String pub_key = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDmc5nGkdvCJDlNVHCx+9tSsHelXU7XX3odKaCXoflrRdC8XR+1rHGpZ0HxwagQRaArAwSdnQ0rpGXEYv0gb/JhqqwJmOVa02LlzlnXfkGVjcFUJNLtI3S3DEpOrcanBo6yW0TFKCj/S3rCTJpXJ5x7ncjqPsbCpUPvzknVCJqpaQIDAQAB";

    /**
     * 私钥
     *
     * @param args
     */
    public static String pri_key = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAOZzmcaR28IkOU1U\n" +
            "cLH721Kwd6VdTtdfeh0poJeh+WtF0LxdH7WscalnQfHBqBBFoCsDBJ2dDSukZcRi\n" +
            "/SBv8mGqrAmY5VrTYuXOWdd+QZWNwVQk0u0jdLcMSk6txqcGjrJbRMUoKP9LesJM\n" +
            "mlcnnHudyOo+xsKlQ+/OSdUImqlpAgMBAAECgYBBBkVRnj5f3VC93SGfVu1fu6NG\n" +
            "6bkhVDNknyZNIsr51c08GRqlbG712cjlqjr9vGehO0zGHFZnBhAhBb1etbeyBgKy\n" +
            "7WnOqrBtoDPNw32YU5dsA/KGDXYgs8Qp/GJ6wsPRfZIipLAEW94M5joVd8+hBsA5\n" +
            "q2QzdUHD5VC5Hfm2AQJBAP0UbUDvtp3KvuS0LJLiJJXfbgaMIIbCi6NBtaBfRTJe\n" +
            "xlOVZuxwt4BlPjyJIBoqDpqm1O69J+YASSpX3/PwFzECQQDpHFUS86WWPJy+GjSX\n" +
            "mU+drfZaGiObXU3+XmfCwGHPxt8bvG8HPhupKTgHVP+mp72bEMzjFqIz3iHOzAwG\n" +
            "LJe5AkEA7Ydse1QBa9vMis7oPvvtJpVLqO1/4btaX+HeXCTOx1XYgr5omWW5dNKR\n" +
            "NTPmY2gxyMjodSIn/mEqSomrckJXIQJAKoFeCdyffuuCcS96E4KC/+9ukjNd9sBm\n" +
            "rlEsflhx/V7346jSXfG3PXskmkGndA32BsZjdaz02pxQcdLHBoEvEQJALGRo47R+\n" +
            "UEroQQZoKSw2iv9tbbAK2NmIPyv4XfZpc5RxYti6VVW4KjNOXMrknOBjlDDFQDwA\n" +
            "nKLBr4hZ1Ca+Rg==";

    public static void main(String[] args) throws NoSuchPaddingException, NoSuchAlgorithmException {
        String data = "hello world";
        Cipher cipher = Cipher.getInstance("RSA");
        //cipher.init(Cipher.ENCRYPT_MODE);
    }
}
