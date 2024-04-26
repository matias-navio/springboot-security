package com.matias.springboot.app.security.springbootsecurity;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.matias.springboot.app.security.springbootsecurity.persistence.entity.Permission;
import com.matias.springboot.app.security.springbootsecurity.persistence.entity.Role;
import com.matias.springboot.app.security.springbootsecurity.persistence.entity.RoleEnum;
import com.matias.springboot.app.security.springbootsecurity.persistence.entity.Users;
import com.matias.springboot.app.security.springbootsecurity.persistence.repository.UserRepository;

@SpringBootApplication
public class SpringbootSecurityApplication {

	@Autowired
	private UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(SpringbootSecurityApplication.class, args);
	}
	
	@Bean
	CommandLineRunner init(){
		return args -> {

			/* PERMISSIONS */
			Permission createPermission = Permission.builder()
					.name("CREATE")
					.build();
			Permission readPermission = Permission.builder()
					.name("READ")
					.build();
			Permission updatePermission = Permission.builder()
					.name("UPDATE")
					.build();

			Permission deletePermission = Permission.builder()
					.name("DELETE")
					.build();

			Permission refectorPermission = Permission.builder()
					.name("REFACTOR")
					.build();

			/* ROLES */

			Role roleAdmin = Role.builder()
					.roleEnum(RoleEnum.ADMIN)
					.permissionList(Set.of(createPermission, readPermission, updatePermission, deletePermission))
					.build();

			Role roleUser = Role.builder()
					.roleEnum(RoleEnum.USER)
					.permissionList(Set.of(readPermission, createPermission))
					.build();

			Role roleInvited = Role.builder()
					.roleEnum(RoleEnum.INVITED)
					.permissionList(Set.of(readPermission))
					.build();

			Role roleDeveloper = Role.builder()
					.roleEnum(RoleEnum.DEVELOPER)
					.permissionList(Set.of(createPermission, readPermission, updatePermission, refectorPermission, deletePermission))
					.build();


			/* USERS */
			Users admin = Users.builder()
					.username("matias")
					.password("12345")
					.isEnabled(true)
					.accountNoExpired(true)
					.accountNoLocked(true)
					.credentialNoExpired(true)
					.roles(Set.of(roleAdmin))
			     	.build();

			Users user = Users.builder()
					.username("juan")
					.password("12345")
					.isEnabled(true)
					.accountNoExpired(true)
					.accountNoLocked(true)
					.credentialNoExpired(true)
					.roles(Set.of(roleUser))
			     	.build();

			Users invited = Users.builder()
					.username("santiago")
					.password("12345")
					.isEnabled(true)
					.accountNoExpired(true)
					.accountNoLocked(true)
					.credentialNoExpired(true)
					.roles(Set.of(roleInvited))
			     	.build();

			Users developer = Users.builder()
					.username("eseba")
					.password("12345")
					.isEnabled(true)
					.accountNoExpired(true)
					.accountNoLocked(true)
					.credentialNoExpired(true)
					.roles(Set.of(roleDeveloper))
			     	.build();

			userRepository.saveAll(List.of(admin, user, invited, developer));
		};
	}
}
