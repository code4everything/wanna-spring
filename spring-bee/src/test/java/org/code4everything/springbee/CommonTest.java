package org.code4everything.springbee;

import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import org.junit.Test;

/**
 * @author pantao
 * @since 2018/9/15
 */
public class CommonTest {

    @Test
    public void rsa() {
        String privateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALbNeorXGVKJtGVL\n" +
                "knJD9RO36S1zkghYY9NWcqzV5pbAIk37reAuVnTZgCAaXopYFmr/9nF4siMBdeH5\n" + "uy9JZTCu7Yd5Lt" +
                "/qyiqISJxZeobLUbE+V1AffiQbbZykSP4NYqXPoPjxv44y1+r5\n" + "GESJFYmqfew5zg7eU4/L11LlAm" +
                "/xAgMBAAECgYBic4fr/qzEpyVg6LKWl8MGO6Z5\n" + "tCSSGgipaYWZUBpAVnojRcj/U/V8OSeoIouINqjcN76inj5SXBcA" + "/V6yuPSlumyD\n" + "+J5nSpyq6OnVXG7bAklW/yGPF07i3hqKEj0K136OPx7XoKxHmhlnYARo4vZIA+0f\n" + "niPvecM42whDeb0rgQJBAN9+uBor3cHSPTmeepK2gx6tOvNfwdt0g5BI6d9XCZEl\n" + "fuduoNx4VVjSeSxJZ5lI" + "+LsVWnO4DC/YP6HGFHQtay8CQQDRY65gYxibmCXl1vBL\n" + "fjdOnNoILYfRoToYgfDgvq7wrpq9Kg5H2K9n0tZYNhSV" + "/BDcdJzVJXZH/hRL8ewI\n" + "Yo7fAkEA0EE4M8rkXJMXUfWGqec/zvoEkHuOHHw5i90lcGOeGo4h7m17n3TRsm6N\n" + "UuMjkgYZH9sJ6gJ5+Ks8v/XfYVJhKQJBALW5W2eP2ZWAULPO/TGn0vxJKPkb+to2\n" + "oSMP8yw6kb2U6" + "/rsewRjzKfA1gIsvWUGJOQP2OUr5CHLI3Zd+cm1L+UCQHlAFX8v\n" + "rYtn9qbU" + "/X4w4Eje0TYMTtaNIG2TMcJSFUJUTbIDPXgyq0I+cw3HkVMoH1R+oUcN\n" + "MnvAM8Id+TYWmAY=";
        String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC2zXqK1xlSibRlS5JyQ/UTt+kt\n" +
                "c5IIWGPTVnKs1eaWwCJN+63gLlZ02YAgGl6KWBZq//ZxeLIjAXXh+bsvSWUwru2H\n" +
                "eS7f6soqiEicWXqGy1GxPldQH34kG22cpEj+DWKlz6D48b+OMtfq+RhEiRWJqn3s\n" + "Oc4O3lOPy9dS5QJv8QIDAQAB";
        String content = "catch one's heart,never be apart";
        RSA rsa = new RSA(privateKey, publicKey);
        assert content.equals(rsa.decryptStr(rsa.encryptStr(content, KeyType.PublicKey), KeyType.PrivateKey));
    }
}
