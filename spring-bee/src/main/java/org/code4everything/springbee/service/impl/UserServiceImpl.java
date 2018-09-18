package org.code4everything.springbee.service.impl;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import com.zhazhapan.util.Checker;
import com.zhazhapan.util.NetUtils;
import com.zhazhapan.util.encryption.JavaEncrypt;
import org.code4everything.springbee.dao.UserDAO;
import org.code4everything.springbee.domain.User;
import org.code4everything.springbee.model.RegisterDTO;
import org.code4everything.springbee.model.UserInfoDTO;
import org.code4everything.springbee.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.concurrent.TimeUnit;

/**
 * @author pantao
 * @since 2018/9/15
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserDAO userDAO;

    private final String privateKey;

    private final RedisTemplate<String, String> stringRedisTemplate;

    private final RedisTemplate<String, User> userRedisTemplate;

    private MongoTemplate mongoTemplate;

    @Autowired
    public UserServiceImpl(UserDAO userDAO, RedisTemplate<String, String> stringRedisTemplate, String privateKey,
                           RedisTemplate<String, User> userRedisTemplate) {
        this.userDAO = userDAO;
        this.stringRedisTemplate = stringRedisTemplate;
        this.privateKey = privateKey;
        this.userRedisTemplate = userRedisTemplate;
    }

    @Override
    public boolean updateInfo(String token, UserInfoDTO userInfoDTO) {
        User user = userRedisTemplate.opsForValue().get(token);
        if (Checker.isNotNull(user)) {
            user.setNickname(userInfoDTO.getNickname());
            user.setBio(userInfoDTO.getBio());
            user.setGender(userInfoDTO.getGender());
            mongoTemplate.save(user);
            return true;
        }
        return false;
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
    public String login(String loginName, String password) {
        User user = userDAO.getByUsernameOrMail(loginName, loginName);
        if (Checker.isNotNull(user)) {
            if (user.getPassword().equals(decryptRsaAndEncryptToMd5(password))) {
                String token = NetUtils.generateToken();
                userRedisTemplate.opsForValue().set(token, user, 30, TimeUnit.MINUTES);
                return token;
            }
        }
        return null;
    }

    private String decryptRsaAndEncryptToMd5(String password) {
        RSA rsa = new RSA(privateKey, null);
        return JavaEncrypt.MD5.digestHex(rsa.decryptStr(password, KeyType.PrivateKey));
    }
}
