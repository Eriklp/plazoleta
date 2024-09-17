package com.pragma.plazoleta;

import jdk.jfr.Enabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;

@SpringBootTest
@AutoConfigureMockMvc
class PlazoletaApplicationTests {

	@MockBean
	private UserDetailsService userDetailsService;

	@Test
	void contextLoads() {
	}

}
