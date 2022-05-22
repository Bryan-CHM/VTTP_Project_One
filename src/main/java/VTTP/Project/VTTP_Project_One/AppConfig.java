package VTTP.Project.VTTP_Project_One;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import VTTP.Project.VTTP_Project_One.filters.AuthenticationFilter;

@Configuration
public class AppConfig {

    @Bean
    public FilterRegistrationBean<AuthenticationFilter> registerFilters() {
        // Create an instance of authentication filter
        AuthenticationFilter authFilter = new AuthenticationFilter();

        // Create an instance of registration filter
        FilterRegistrationBean<AuthenticationFilter> regFilter = new FilterRegistrationBean<>();
        regFilter.setFilter(authFilter);
        regFilter.addUrlPatterns("/protected/*");
        
        return regFilter;
    }
    
}
