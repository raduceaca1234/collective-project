package ro.ubb.repository;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.ComponentScan;

@TestConfiguration
@ComponentScan(basePackages = "ro.ubb.repository")
public class RepositoryTestConfiguration {
}
