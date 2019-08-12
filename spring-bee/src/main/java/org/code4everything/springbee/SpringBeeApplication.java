package org.code4everything.springbee;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.code4everything.boot.base.FileUtils;
import org.code4everything.boot.config.EnableSurfaceMail;
import org.code4everything.springbee.config.BeeConfigBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author pantao
 * @since 2018/9/9
 */
@SpringBootApplication
@EnableSwagger2Doc
@EnableSurfaceMail
public class SpringBeeApplication {

    private static BeeConfigBean beeConfigBean = new BeeConfigBean();

    public static void main(String[] args) {
        // 监听配置文件
        FileUtils.watchFile(FileUtils.currentWorkDir("config.json"), beeConfigBean);
        // 启动项目
        SpringApplication.run(SpringBeeApplication.class, args);
    }

    public static BeeConfigBean getBeeConfigBean() {
        return beeConfigBean;
    }
}
