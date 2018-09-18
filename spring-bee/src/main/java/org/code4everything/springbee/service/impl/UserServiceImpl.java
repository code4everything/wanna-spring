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
import org.springframework.data.mongodb.core.MongoTemplate;
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

    private MongoTemplate mongoTemplate;

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
    public User login(String loginName, String password) {
        User user = userDAO.getByUsernameOrMail(loginName, loginName);
        if (Checker.isNotNull(user)) {
            if (user.getPassword().equals(decryptRsaAndEncryptToMd5(password))) {
                return user;
            }
        }
        return null;
    }

    private String decryptRsaAndEncryptToMd5(String password) {
        RSA rsa = new RSA(privateKey, null);
        return JavaEncrypt.MD5.digestHex(rsa.decryptStr(password, KeyType.PrivateKey));
    }
}
