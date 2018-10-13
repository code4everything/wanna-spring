package org.code4everything.springbee;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Console;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import com.zhazhapan.util.DateUtils;
import com.zhazhapan.util.FileExecutor;
import org.code4everything.springbee.constant.BeeConfigConsts;
import org.junit.Test;

import java.io.IOException;
import java.sql.Date;

/**
 * @author pantao
 * @since 2018/9/15
 */
public class CommonTest {

    @Test
    public void rsa() throws IOException {
        String privateKey = FileExecutor.readFile(BeeConfigConsts.PRIVATE_RSA_KEY_PATH);
        String publicKey = FileExecutor.readFile(BeeConfigConsts.PRIVATE_RSA_KEY_PATH.replaceAll("private", "public"));
        String content = "catch one's heart,never be apart";
        RSA rsa = new RSA(privateKey, publicKey);
        System.out.println(rsa.encryptStr("123456", KeyType.PublicKey));
        assert content.equals(rsa.decryptStr(rsa.encryptStr(content, KeyType.PublicKey), KeyType.PrivateKey));
    }

    @Test
    public void date() {
        Date date = new Date(System.currentTimeMillis());
        long start = System.currentTimeMillis();
        String day1 = DateUtils.getDay(date);
        long end = System.currentTimeMillis();
        Console.log("use times: {}", end - start);
        start = System.currentTimeMillis();
        int day2 = DateUtil.dayOfMonth(date);
        end = System.currentTimeMillis();
        Console.log("use times: {}", end - start);
        Console.log("day1: {}, day2: {}", day1, day2);
        assert Integer.parseInt(DateUtils.getDay(date)) == DateUtil.dayOfMonth(date);
    }
}
