package com.seabattlespring.springseabattle.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@Configuration
@ComponentScan("com.seabattlespring.springseabattle")
//@EnableWebMvc
public class GameConfig implements WebMvcConfigurer {

    private final ApplicationContext applicationContext;

    @Autowired
    public GameConfig(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Bean
    public ViewResolver resolver() {
        InternalResourceViewResolver resolverBean = new InternalResourceViewResolver();
        resolverBean.setApplicationContext(applicationContext);
        resolverBean.setPrefix("/views/");
        resolverBean.setSuffix(".html");
        return resolverBean;
    }

}
