package org.code4everything.springbee;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.code4everything.boot.base.FileUtils;
import org.code4everything.boot.base.FileWatcher;
import org.code4everything.boot.config.EnableSurfaceConfiguration;
import org.code4everything.boot.config.EnableSurfaceLog;
import org.code4everything.boot.config.EnableSurfaceMail;
import org.code4everything.boot.config.EnableSurfaceRedis;
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
@EnableSurfaceLog
@EnableSurfaceMail
@EnableSurfaceRedis
@EnableSurfaceConfiguration
public class SpringBeeApplication {

    private static BeeConfigBean beeConfigBean = new BeeConfigBean();

    public static void main(String[] args) {
        // 监听配置文件
        FileUtils.watchFile(FileUtils.currentWorkDir("config.json"), new FileWatcher() {

            @Override
            public void doSomething() {
                BeeUtils.null2Default(beeConfigBean);
            }
        }, beeConfigBean);
        // 启动项目
        SpringApplication.run(SpringBeeApplication.class, args);
    }

    public static BeeConfigBean getBeeConfigBean() {
        return beeConfigBean;
    }
}
