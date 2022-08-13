package com.tcc.simpledocapi;

import com.tcc.simpledocapi.entity.Role;
import com.tcc.simpledocapi.entity.User;
import com.tcc.simpledocapi.enums.AuthorizationProvider;
import com.tcc.simpledocapi.enums.Avatar;
import com.tcc.simpledocapi.service.role.RoleService;
import com.tcc.simpledocapi.service.user.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.time.LocalDate;
import java.util.ArrayList;

@SpringBootApplication
public class SimpledocApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SimpledocApiApplication.class, args);
	}

	@Bean
	PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}

	// @Bean
	/* CommandLineRunner run(UserService userService, RoleService roleService) {
		return args -> {
			roleService.save(new Role(null, "ROLE_ADMIN"));
			roleService.save(new Role(null, "ROLE_USER"));

			userService.saveUser(new
					User(null,
					"admin@mailinator.com",
					"123123",
					"Administrator",
					"User",
					Avatar.getBase(),
					LocalDate.now(),
					"Angola",
					"+244945104652",
					AuthorizationProvider.LOCAL ,
					new ArrayList<>(),
					new ArrayList<>(),
					new ArrayList<>() ,
					new ArrayList<>()), "ROLE_ADMIN");

			userService.saveUser(new
					User(null,
					"dumilde.matos@mailinator.com",
					"123123",
					"Dumilde",
					"Matos",
					Avatar.getBase(),
					LocalDate.now(),
					"Angola",
					"+244945104652",
					AuthorizationProvider.LOCAL ,
					new ArrayList<>(),
					new ArrayList<>(),
					new ArrayList<>() ,
					new ArrayList<>()), "ROLE_USER");

			userService.saveUser(new
					User(null,
					"anibal.antonio@mailinator.com",
					"123123",
					"Anibal",
					"Antonio",
					Avatar.getBase(),
					LocalDate.now(),
					"Angola",
					"+244945104652",
					AuthorizationProvider.LOCAL ,
					new ArrayList<>(),
					new ArrayList<>(),
					new ArrayList<>(),
					new ArrayList<>()), "ROLE_USER");
			userService.saveUser(new
					User(null, "helio.fragao@mailinator.com", "123123", "Helio", "Fragão", Avatar.getBase(), LocalDate.now(),				"Angola",
					"+244945104652", AuthorizationProvider.LOCAL , new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>()), "ROLE_USER");



		};*/
	//}
}
