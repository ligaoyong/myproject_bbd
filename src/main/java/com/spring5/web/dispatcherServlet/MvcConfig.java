package com.spring5.web.dispatcherServlet;

import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.validation.Validator;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.theme.ThemeChangeInterceptor;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * WebMvc配置
 * 实现WebMvcConfigurer即可 (接口以及有了默认空实现，所以不用WebMvcConfigurerAdapter了
 */
@Configuration
@EnableWebMvc
public class MvcConfig implements WebMvcConfigurer {

    /**
     * 配置类型转换器
     *       默认情况下，会安装数字和日期类型的格式化程序，包括对@NumberFormat和@DateTimeFormat注释的支持。
     *       如果类路径中存在Joda-Time，还将安装对Joda-Time格式化库的完全支持
     */
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addFormatter(null);
    }

    /**
     * Validation：验证器
     *      默认情况下，如果类路径中存在Bean验证(例如Hibernate验证器)，
     *      那么LocalValidatorFactoryBean将注册为一个全局验证器，以便与@Valid一起使用，
     *      并在控制器方法参数上进行验证。
     */
    @Override
    public Validator getValidator() {
        //添加自定义的验证器
        //有可以使用WebDataBinder添加本地验证器
        return null;
    }

    /**
     * Interceptors：拦截器
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LocaleChangeInterceptor());
        registry.addInterceptor(new ThemeChangeInterceptor()).addPathPatterns("/**").excludePathPatterns("/admin/**");
        //registry.addInterceptor(new SecurityInterceptor()).addPathPatterns("/secure/*");
    }

    /**
     * Content Types
     *      您可以配置Spring MVC如何从请求中确定所请求的媒体类型(例如，Accept header、URL路径扩展、查询参数等)。
     *      默认情况下，首先检查URL路径扩展—将json、xml、rss和atom注册为已知的扩展(取决于类路径依赖关系)。然后检查Accept标头。
     *      在Java配置中，您可以自定义请求的内容类型解析
     */
    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.mediaType("json", MediaType.APPLICATION_JSON);
        configurer.mediaType("xml", MediaType.APPLICATION_XML);
    }

    /**
     * Message Converters：消息转换器
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder()
                .indentOutput(true)
                .dateFormat(new SimpleDateFormat("yyyy-MM-dd"))
                .modulesToInstall(new ParameterNamesModule());
        converters.add(new MappingJackson2HttpMessageConverter(builder.build()));
        converters.add(new MappingJackson2XmlHttpMessageConverter(builder.createXmlMapper(true).build()));
    }

    /**
     * View Controllers：视图控制
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        //url路径--->视图名称
        registry.addViewController("index").setViewName("index");
    }

    /**
     * View Resolvers：视图解析器
     */
    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        registry.enableContentNegotiation(new MappingJackson2JsonView());
        registry.jsp();
    }

    /**
     *  静态资源
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/resources/**")
                .addResourceLocations("/public", "classpath:/static/")
                .setCachePeriod(31556926);//缓存有效期
    }

    /**
     * 默认Servlet
     */
    @Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    /**
     * 路径匹配
     */
    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {

    }

    /**
     * 跨域映射
     *     也可以使用@CrossOrigin注解
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        //允许http://xxxx:xxxx域访问api/xxxx(响应头Access-Control-Allow-Origin=http://xxxx:xxxx)
        registry.addMapping("api/xxxx").allowedOrigins("http://xxxx:xxxx");
    }
}
