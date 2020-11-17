package ro.ubb.service;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.ComponentScan;

@TestConfiguration
@ComponentScan(basePackages = {"ro.ubb.service", "ro.ubb.validator"})
public class ServiceTestConfiguration {

}
