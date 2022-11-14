package com.winga.demo;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class EmployeeController {

	@PostMapping("/employees")
	Employee all() {
		UUID uuid = UUID.randomUUID();
		String uuidAsString = uuid.toString();
		new BusyThread(uuidAsString, 1, 500).start();
		throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Foo Not Found", new Throwable());
	}
}
