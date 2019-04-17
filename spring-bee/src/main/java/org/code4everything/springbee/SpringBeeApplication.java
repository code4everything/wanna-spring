package org.code4everything.springbee;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.code4everything.boot.annotation.EnableSurfaceAutoLog;
import org.code4everything.boot.annotation.EnableSurfaceConfiguration;
import org.code4everything.boot.annotation.EnableSurfaceMailSender;
import org.code4everything.boot.annotation.EnableSurfaceRedisTemplate;
import org.code4everything.boot.base.FileUtils;
import org.code4everything.boot.base.FileWatcher;
import org.code4everything.boot.config.BootConfig;
import org.code4everything.springbee.config.BeeConfigBean;
import org.code4everything.springbee.util.BeeUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author pantao
 * @since 2018/9/9
 */
@SpringBootApplication
@EnableSwagger2Doc
@EnableSurfaceConfiguration
@EnableSurfaceAutoLog
@EnableSurfaceMailSender
@EnableSurfaceRedisTemplate
public class SpringBeeApplication {

    private static BeeConfigBean beeConfigBean;

    public static void main(String[] args) {
        // 配置文件路径
        String configFile = FileUtils.currentWorkDir("config.json");
        // 监听配置文件
        FileUtils.watchFile(configFile, new FileWatcher() {

            @Override
            public void doSomething() {
                SpringBeeApplication.beeConfigBean = BeeUtils.parseConfigurer(configFile);
                BootConfig.setConfigBean(SpringBeeApplication.beeConfigBean);
            }
        }, true);
        // 启动项目
        SpringApplication.run(SpringBeeApplication.class, args);
    }

    public static BeeConfigBean getBeeConfigBean() {
        return beeConfigBean;
    }
}
