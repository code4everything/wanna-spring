package org.code4everything.springbee;

import com.spring4all.swagger.EnableSwagger2Doc;
import com.zhazhapan.util.FileExecutor;
import com.zhazhapan.util.interfaces.SimpleHutoolWatcher;
import org.code4everything.boot.base.FileUtils;
import org.code4everything.boot.web.CorsUtils;
import org.code4everything.springbee.config.BeeConfigProperty;
import org.code4everything.springbee.util.BeeUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.CorsFilter;

import java.io.File;

/**
 * @author pantao
 * @since 2018/9/9
 */
@SpringBootApplication
@EnableSwagger2Doc
public class SpringBeeApplication {

    private static BeeConfigProperty beeConfigProperty;

    public static void main(String[] args) {
        SpringApplication.run(SpringBeeApplication.class, args);
        String configFile = FileUtils.currentWorkDir() + File.separator + "config.json";
        FileExecutor.watchFile(configFile, new SimpleHutoolWatcher() {
            @Override
            public void doSomething() {
                SpringBeeApplication.beeConfigProperty = BeeUtils.parseConfigurer(configFile);
            }
        }, true);
    }

    public static BeeConfigProperty getConfigProperty() {
        return beeConfigProperty;
    }

    /**
     * 跨域过滤器
     */
    @Bean
    public CorsFilter corsFilter() {
        return CorsUtils.newCorsFilter();
    }
}
