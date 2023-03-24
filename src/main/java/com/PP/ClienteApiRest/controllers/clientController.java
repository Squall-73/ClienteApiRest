package com.PP.ClienteApiRest.controllers;


import com.PP.ClienteApiRest.models.ClientModel;
import com.PP.ClienteApiRest.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.LinkedHashMap;
import java.util.List;

@RestController
@RequestMapping(path = "api/client")
public class clientController {

	@Autowired
	private ClientService clientService;

	@PostMapping(path = "/")
	public ResponseEntity<ClientModel> create(@RequestBody ClientModel product) {
		return new ResponseEntity<>(this.clientService.create(product), HttpStatus.OK);
	}

	@PutMapping(path = "/{id}")
	public ResponseEntity<ClientModel> update(@RequestBody ClientModel product, @PathVariable Long id) throws Exception {
		return new ResponseEntity<>(this.clientService.update(product,id), HttpStatus.OK);
	}

	@GetMapping(path = "/{id}")
	public ResponseEntity<ClientModel> findById(@PathVariable Long id) throws Exception {

		return new ResponseEntity<>(this.clientService.findById(id), HttpStatus.OK);
	}

	@GetMapping(path = "/")
	public ResponseEntity<List<ClientModel>> findAll(){
		return new ResponseEntity<>(this.clientService.findAll(), HttpStatus.OK);
	}

	@GetMapping(path = "/edad/{id}")
	public ResponseEntity<LinkedHashMap<String,String>> getAge(@PathVariable Long id) throws Exception {
		return new ResponseEntity<>(this.clientService.getAge(id), HttpStatus.OK);
	}

}
