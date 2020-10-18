package edu.mum.cs.onlinemarketplace.config;

import edu.mum.cs.onlinemarketplace.domain.Product;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.accept.ContentNegotiationManager;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.ContentNegotiatingViewResolver;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;
import org.springframework.web.servlet.view.xml.MarshallingView;

import java.util.ArrayList;
import java.util.List;

@EnableWebMvc
@Configuration
public class WebApplicationContextConfig implements WebMvcConfigurer {
    /**
     * Configure to display image
     */
   @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/img/**").addResourceLocations("/resources/static/images/");

        if(!registry.hasMappingForPattern("/admin/css/**")){
            registry.addResourceHandler("/admin/css/**").addResourceLocations("classpath:/static/admin/css/");
        }
        if(!registry.hasMappingForPattern("/admin/js/**")){
            registry.addResourceHandler("/admin/js/**").addResourceLocations("classpath:/static/admin/js/");
        }




        if (!registry.hasMappingForPattern("/pdf/**")) {
            registry.addResourceHandler("/pdf/**").addResourceLocations(
                    "classpath:/static/pdf/");
        }

        if (!registry.hasMappingForPattern("/files/**")) {
            registry.addResourceHandler("/files/**").addResourceLocations(
                    "classpath:/static/files/");
        }


        if (!registry.hasMappingForPattern("/img/**")) {
//            registry.addResourceHandler("/img/**").addResourceLocations(
//                    "classpath:/static/imgages/");
            registry.addResourceHandler("/img/**").addResourceLocations(
                    "file:.\\src\\main\\resources\\static\\imgages\\");
        }


        if (!registry.hasMappingForPattern("/css/**")) {
            registry.addResourceHandler("/css/**").addResourceLocations(
                    "classpath:/static/css/");
        }

        if (!registry.hasMappingForPattern("/js/**")) {
            registry.addResourceHandler("/js/**").addResourceLocations(
                    "classpath:/static/js/");
        }

        if (!registry.hasMappingForPattern("/fonts/**")) {
            registry.addResourceHandler("/fonts/**").addResourceLocations(
                    "classpath:/static/fonts/");
        }


    }

    /**
     * For image upload
     *
     * @return
     */
    @Bean
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver resolver = new CommonsMultipartResolver();
        resolver.setDefaultEncoding("utf-8");
        resolver.setMaxUploadSize(10240000);
        return resolver;
    }

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasenames("classpath:errorMessages", "classpath:messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }

    @Bean
    public LocalValidatorFactoryBean validator() {
        LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
        bean.setValidationMessageSource(messageSource());
        return bean;
    }

//    @Bean
//    public MappingJackson2JsonView jsonView() {
//        MappingJackson2JsonView jsonView = new MappingJackson2JsonView();
//        jsonView.setPrettyPrint(true);
//        return jsonView;
//    }
//    @Bean
//    public MarshallingView xmlView() {
//        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
//        marshaller.setClassesToBeBound(Product.class);
//        MarshallingView xmlView = new MarshallingView(marshaller);
//        return xmlView;
//    }
//    @Bean
//    public ViewResolver contentNegotiatingViewResolver(ContentNegotiationManager manager) {
//        ContentNegotiatingViewResolver resolver = new ContentNegotiatingViewResolver();
//        resolver.setContentNegotiationManager(manager);
//        List<View> views = new ArrayList<>();
//        views.add(jsonView());
//        views.add(xmlView());
//        resolver.setDefaultViews(views);
//        return resolver;
//    }
}
