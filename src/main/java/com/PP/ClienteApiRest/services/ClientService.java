package com.PP.ClienteApiRest.services;

import com.PP.ClienteApiRest.exceptions.ClientNotFoundException;
import com.PP.ClienteApiRest.exceptions.IdNotValidException;
import com.PP.ClienteApiRest.models.ClientModel;
import com.PP.ClienteApiRest.repositories.ClientRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;

@Slf4j
@Service
public class ClientService {

	@Autowired
	private ClientRepository clientRepository;

	public ClientModel create (ClientModel newClient) {

		return this.clientRepository.save(newClient);
	}


	public ClientModel update (ClientModel newClient, Long id) throws Exception {

		log.info("Id Ingresado: " + id);
		if(id<=0){
			throw new IdNotValidException("el id ingresado no es válido");
		}

		Optional<ClientModel> clientOp = this.clientRepository.findById(id);

		if(clientOp.isEmpty()){
			log.info("El cliente que intenta modificar no se encuentra en la base de datos: " + newClient);
			throw new ClientNotFoundException("El cliente que intenta modificar no se encuentra en la base de datos");
		}else{
			log.info("Cliente encontrado");
			ClientModel clientBd = clientOp.get();

			clientBd.setName(newClient.getName());
			clientBd.setLastName(newClient.getLastName());
			clientBd.setBirthDate(newClient.getBirthDate());
			log.info("Cliente actualizado :" + clientBd);

			return this.clientRepository.save(clientBd);
		}
	}

	public ClientModel findById (Long id) throws Exception {

		log.info("Id Ingresado: " + id);
		if(id<=0){
			throw new IdNotValidException("el id ingresado no es válido");
		}

		Optional<ClientModel> clientOp = this.clientRepository.findById(id);

		if(clientOp.isEmpty()){
			log.info("El cliente buscado no se encuentra en la base de datos:" + id);
			throw new ClientNotFoundException("El cliente solicitado no existe");
		}else{

			return clientOp.get();
		}

	}

	public List<ClientModel> findAll(){
		return this.clientRepository.findAll();
	}

	public LinkedHashMap<String,String> getAge(long id) throws Exception{

		log.info("Id Ingresado: " + id);
		if(id<=0){
			throw new IdNotValidException("el id ingresado no es válido");
		}

		Optional<ClientModel> clientOp = this.clientRepository.findById(id);

		if(clientOp.isEmpty()){
			log.info("El cliente buscado no se encuentra en la base de datos:" + id);
			throw new ClientNotFoundException("El cliente solicitado no existe");
		}else {

			int age;
			age = Period.between(clientOp.get().getBirthDate(), LocalDate.now()).getYears();

			LinkedHashMap<String, String> map = new LinkedHashMap<>();
			map.put("Nombre", clientOp.get().getName());
			map.put("Apellido", clientOp.get().getLastName());
			map.put("Años", String.valueOf(age));

			return map;
		}
	}
}
