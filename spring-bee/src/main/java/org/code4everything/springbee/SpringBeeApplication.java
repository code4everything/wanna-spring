package org.code4everything.springbee;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.code4everything.boot.base.FileUtils;
import org.code4everything.boot.config.BootConfig;
import org.code4everything.boot.constant.IntegerConsts;
import org.code4everything.boot.interfaces.FileWatcher;
import org.code4everything.boot.web.mvc.DefaultWebInterceptor;
import org.code4everything.springbee.config.BeeConfigBean;
import org.code4everything.springbee.util.BeeUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;

/**
 * @author pantao
 * @since 2018/9/9
 */
@SpringBootApplication
@EnableSwagger2Doc
public class SpringBeeApplication {

    private static BeeConfigBean beeConfigBean;

    public static void main(String[] args) {
        // 配置基本信息
        BootConfig.setDebug(false);
        BootConfig.setMaxUploadFileSize(IntegerConsts.FileSize.MB);
        // 启动项目
        SpringApplication.run(SpringBeeApplication.class, args);
        // 配置文件路径
        String configFile = FileUtils.currentWorkDir() + File.separator + "config.json";
        // 监听配置文件
        FileUtils.watchFile(configFile, new FileWatcher() {
            @Override
            public void doSomething() {
                SpringBeeApplication.beeConfigBean = BeeUtils.parseConfigurer(configFile);
                DefaultWebInterceptor.setConfigBean(SpringBeeApplication.beeConfigBean);
            }
        }, true);
    }

    public static BeeConfigBean getBeeConfigBean() {
        return beeConfigBean;
    }
}
