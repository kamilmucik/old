package pl.estrix.app;

import com.sun.faces.config.FacesInitializer;
import org.apache.catalina.Context;
import org.primefaces.webapp.filter.FileUploadFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.CustomScopeConfigurer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.embedded.EmbeddedServletContainerFactory;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.boot.context.embedded.ServletContextInitializer;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.boot.context.embedded.tomcat.TomcatContextCustomizer;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.boot.context.web.NonEmbeddedServletContainerFactory;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import pl.estrix.app.authentication.SessionListener;

import javax.faces.application.ProjectStage;
import javax.faces.webapp.FacesServlet;
import javax.servlet.DispatcherType;
import javax.servlet.ServletContainerInitializer;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.HandlesTypes;
import javax.sql.DataSource;
import java.util.*;

//@EntityScan("pl.estrix")
@ComponentScan("pl.estrix")
//@ComponentScan("pl.estrix.app")
@EnableAutoConfiguration
//@EnableJpaRepositories(
//        basePackages = {
//                "pl.estrix.app.backend.user.dao"
//        })
@EnableScheduling
@SpringBootApplication
public class Configuration extends SpringBootServletInitializer {

    private static Logger LOG = LoggerFactory.getLogger(Configuration.class);

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Configuration.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Configuration.class);
    }

    @Autowired
    DataSource dataSource;


    @Bean
    public static CustomScopeConfigurer customScopeConfigurer() {
        CustomScopeConfigurer configurer = new CustomScopeConfigurer();
        configurer.setScopes(Collections.<String, Object>singletonMap(
                FacesViewScope.NAME, new FacesViewScope()));
        return configurer;
    }

    @Bean
    public ServletContextInitializer servletContextCustomizer() {
        return new ServletContextInitializer() {
            @Override
            public void onStartup(ServletContext sc) throws ServletException {

                sc.addListener(new SessionListener());

                sc.setInitParameter(ProjectStage.PROJECT_STAGE_PARAM_NAME, ProjectStage.Development.name());
//
//                // JSF
//                sc.setInitParameter("com.sun.faces.forceLoadConfiguration", Boolean.TRUE.toString());
//                sc.setInitParameter("javax.faces.FACELETS_SKIP_COMMENTS", Boolean.TRUE.toString());
//                sc.setInitParameter("javax.faces.PROJECT_STAGE", "Production");
////                sc.setInitParameter("javax.faces.PROJECT_STAGE", "Development");
//                // PRIMEFACES
//                sc.setInitParameter("primefaces.THEME", "omega");
////                sc.setInitParameter("primefaces.CLIENT_SIDE_VALIDATION", Boolean.TRUE.toString());
//                sc.setInitParameter("primefaces.CLIENT_SIDE_VALIDATION", "true");
                sc.setInitParameter("primefaces.FONT_AWESOME", Boolean.TRUE.toString());
                sc.setInitParameter("primefaces.UPLOADER", "commons");
//
//                sc.setInitParameter("facelets.DEVELOPMENT", "true");

                sc.setInitParameter("com.sun.faces.forceLoadConfiguration", Boolean.TRUE.toString());
                sc.setInitParameter("javax.faces.PARTIAL_STATE_SAVING_METHOD", "true");
                sc.setInitParameter("javax.faces.PROJECT_STAGE", "Development");
                sc.setInitParameter("facelets.DEVELOPMENT", "true");
                sc.setInitParameter("javax.faces.FACELETS_REFRESH_PERIOD", "1");
                sc.setInitParameter("primefaces.CLIENT_SIDE_VALIDATION", "true");
                sc.setInitParameter("javax.faces.DEFAULT_SUFFIX", ".xhtml");
                sc.setInitParameter("primefaces.THEME", "nova-light");

            }
        };
    }

    @Bean
    @ConditionalOnMissingBean(NonEmbeddedServletContainerFactory.class)
    public EmbeddedServletContainerFactory embeddedServletContainerFactory() {
        TomcatEmbeddedServletContainerFactory tomcat = new TomcatEmbeddedServletContainerFactory();

        tomcat.addContextCustomizers(new TomcatContextCustomizer() {
            @Override
            public void customize(Context context) {
                // register FacesInitializer
                context.addServletContainerInitializer(new FacesInitializer(),
                        getServletContainerInitializerHandlesTypes(FacesInitializer.class));

                // add configuration from web.xml
//                context.addWelcomeFile("index.html");
                context.addWelcomeFile("dashboard.xhtml");

                // register additional mime-types that Spring Boot doesn't register
                context.addMimeMapping("eot", "application/vnd.ms-fontobject");
                context.addMimeMapping("ttf", "application/x-font-ttf");
                context.addMimeMapping("woff", "application/x-font-woff");
            }
        });

        return tomcat;
    }

    @SuppressWarnings("rawtypes")
    private Set<Class<?>> getServletContainerInitializerHandlesTypes(Class<? extends ServletContainerInitializer> sciClass) {
        HandlesTypes annotation = sciClass.getAnnotation(HandlesTypes.class);
        if (annotation == null) {
            return Collections.emptySet();
        }

        Class[] classesArray = annotation.value();
        Set<Class<?>> classesSet = new HashSet<Class<?>>(classesArray.length);
        for (Class clazz: classesArray) {
            classesSet.add(clazz);
        }

        return classesSet;
    }

    @Bean
    public static CustomScopeConfigurer customScope() {
        CustomScopeConfigurer configurer = new CustomScopeConfigurer();
        Map<String, Object> scopes = new HashMap<String, Object>();
        scopes.put("view", new JsfViewScope());
        configurer.setScopes(scopes);
        return configurer;
    }

    @Bean
    public ServletRegistrationBean facesServlet() {
        ServletRegistrationBean registration = new ServletRegistrationBean(new FacesServlet(), new String[]{"*.html"});
        registration.setName("Faces Servlet");
        registration.setLoadOnStartup(1);
        return registration;
    }

    @Bean
    public FilterRegistrationBean facesUploadFilter() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean(new FileUploadFilter(), facesServlet());
        registrationBean.setName("PrimeFaces FileUpload Filter");
        registrationBean.addUrlPatterns("/*");
        registrationBean.setDispatcherTypes(DispatcherType.FORWARD, DispatcherType.REQUEST);
        return registrationBean;
    }

}