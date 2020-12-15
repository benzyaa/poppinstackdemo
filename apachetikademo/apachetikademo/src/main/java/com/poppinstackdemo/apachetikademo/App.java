package com.poppinstackdemo.apachetikademo;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ComponentScan(basePackages = {"com.poppinstackdemo.apachetikademo"})
public class App
{
    public static void main( String[] args )
    {
        SpringApplicationBuilder app = new SpringApplicationBuilder(App.class)
                .web(WebApplicationType.SERVLET);
        app.run(args);
    }
}