package org.code4everything.springbee;

import com.spring4all.swagger.EnableSwagger2Doc;
import com.zhazhapan.util.FileExecutor;
import com.zhazhapan.util.Utils;
import com.zhazhapan.util.interfaces.SimpleHutoolWatcher;
import org.code4everything.springbee.config.BeeConfigProperty;
import org.code4everything.springbee.util.BeeUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
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
        String configFile = Utils.getCurrentWorkDir() + File.separator + "config.json";
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

    private CorsConfiguration buildConfig() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.setAllowCredentials(true);
        return corsConfiguration;
    }

    /**
     * 跨域过滤器
     */
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", buildConfig());
        return new CorsFilter(source);
    }
}
