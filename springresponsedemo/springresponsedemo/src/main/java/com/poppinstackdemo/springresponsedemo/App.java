package com.poppinstackdemo.springresponsedemo;


import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.poppinstackdemo.springresponsedemo"})
public class App 
{
    public static void main( String[] args )
    {
        SpringApplicationBuilder app = new SpringApplicationBuilder(App.class)
                .web(WebApplicationType.SERVLET);
        app.run(args);
    }
}
