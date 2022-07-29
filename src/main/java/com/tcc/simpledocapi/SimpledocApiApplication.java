package com.tcc.simpledocapi;

import com.tcc.simpledocapi.entity.Role;
import com.tcc.simpledocapi.entity.User;
import com.tcc.simpledocapi.enums.AuthorizationProvider;
import com.tcc.simpledocapi.service.role.RoleService;
import com.tcc.simpledocapi.service.user.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

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

	@Bean
	CommandLineRunner run(UserService userService, RoleService roleService) {
		return args -> {
			roleService.save(new Role(null, "ROLE_ADMIN"));
			roleService.save(new Role(null, "ROLE_USER"));

			userService.saveUser(new
					User(null,
					"admin@mailinator.com",
					"123123",
					"Administrator",
					"User",
					"https://cdn-icons-png.flaticon.com/512/149/149071.png",
					LocalDate.now(),
					"Angola",
					"+244945104652",
					AuthorizationProvider.LOCAL ,
					new ArrayList<>(),
					new ArrayList<>(),
					new ArrayList<>() ,
					new ArrayList<>()));

			userService.saveUser(new
					User(null,
					"dumilde.matos@mailinator.com",
					"123123",
					"Dumilde",
					"Matos",
					"avatar",
					LocalDate.now(),
					"Angola",
					"+244945104652",
					AuthorizationProvider.LOCAL ,
					new ArrayList<>(),
					new ArrayList<>(),
					new ArrayList<>() ,
					new ArrayList<>()));

			userService.saveUser(new
					User(null,
					"anibal.antonio@mailinator.com",
					"123123",
					"Anibal",
					"Antonio",
					"https://avatars.githubusercontent.com/u/4990261?v=4",
					LocalDate.now(),
					"Angola",
					"+244945104652",
					AuthorizationProvider.LOCAL ,
					new ArrayList<>(),
					new ArrayList<>(),
					new ArrayList<>(),
					new ArrayList<>()));
			userService.saveUser(new
					User(null, "helio.fragao@mailinator.com", "123123", "Helio", "Fragão", "avatar", LocalDate.now(),				"Angola",
					"+244945104652", AuthorizationProvider.LOCAL , new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>()));

			userService.addRoleToUser("admin@mailinator.com", "ROLE_ADMIN");
			userService.addRoleToUser("dumilde.matos@mailinator.com", "ROLE_USER");
			userService.addRoleToUser("anibal.antonio@mailinator.com", "ROLE_USER");
			userService.addRoleToUser("helio.fragao@mailinator.com", "ROLE_USER");

		};
	}
}
