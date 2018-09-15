package org.code4everything.springbee.service.impl;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import com.zhazhapan.util.Checker;
import com.zhazhapan.util.encryption.JavaEncrypt;
import org.code4everything.springbee.dao.UserDAO;
import org.code4everything.springbee.domain.User;
import org.code4everything.springbee.model.RegisterDTO;
import org.code4everything.springbee.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

/**
 * @author pantao
 * @since 2018/9/15
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;

    private final String privateKey;

    private final RedisTemplate<String, String> stringRedisTemplate;

    @Autowired
    public UserServiceImpl(UserDAO userDAO, RedisTemplate<String, String> stringRedisTemplate, String privateKey) {
        this.userDAO = userDAO;
        this.stringRedisTemplate = stringRedisTemplate;
        this.privateKey = privateKey;
    }

    @Override
    public void register(RegisterDTO registerDTO) {
        User user = new User();
        user.setUsername(registerDTO.getUsername());
        user.setMail(registerDTO.getEmail());
        user.setPassword(decryptRsaAndEncryptToMd5(registerDTO.getPassword()));
        user.setCreateTime(new Timestamp(System.currentTimeMillis()));
        user.setId(RandomUtil.simpleUUID());
        user.setStatus("7");
        userDAO.save(user);
    }

    @Override
    public boolean existsEmail(String email) {
        return userDAO.countByMail(email) > 0;
    }

    @Override
    public boolean isVcodeValidated(String email, String vcode) {
        String key = "vcode:" + email;
        return Checker.checkNull(vcode).equals(stringRedisTemplate.opsForValue().get(key));
    }

    private String decryptRsaAndEncryptToMd5(String password) {
        RSA rsa = new RSA(privateKey, null);
        return JavaEncrypt.MD5.digestHex(rsa.decryptStr(password, KeyType.PrivateKey));
    }
}
