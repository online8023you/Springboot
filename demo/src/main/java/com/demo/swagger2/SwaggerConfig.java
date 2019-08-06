package com.demo.swagger2;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author Dawn
 * @ClassName com.demo.swagger2.SwaggerConfig
 * @Description
 * @date 2019/7/9 11:08
 */

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    /**
     * 添加摘要信息（Docket）
     * @return
     */
    @Bean
    public Docket creatRestApi(){
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(new ApiInfoBuilder()
                    .title("权限管理系统_接口文档")
                    .description("描述：权限管理模块、角色管理模块、用户管理权限")
                    .termsOfServiceUrl("localhost:8080")
                    .version("1.0")
                    .build())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.demo.controller"))
                .paths(PathSelectors.any())
                .build();

    }
}
