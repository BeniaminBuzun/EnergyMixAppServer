package spyrosoft.testproject.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;

@WebMvcTest(ApiController.class)
class EnergyControllerTest {

	@Autowired
	private ApiController energyController;

	@Test
	void placeholder() {
		// tests to be implemented
	}

}
