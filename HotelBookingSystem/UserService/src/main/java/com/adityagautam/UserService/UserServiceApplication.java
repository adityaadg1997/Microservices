package com.adityagautam.UserService;

import com.adityagautam.UserService.constants.AppConstants;
import com.adityagautam.UserService.entities.Role;
import com.adityagautam.UserService.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
//import org.springframework.cloud.openfeign.EnableFeignClients;

import java.util.List;

@SpringBootApplication
@EnableFeignClients
public class UserServiceApplication implements CommandLineRunner {
	
	@Autowired
	private RoleRepository roleRepository;

	public static void main(String[] args) {
		SpringApplication.run(UserServiceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		try {
			Role roleAdmin = new Role();
			roleAdmin.setRoleId(AppConstants.ROLE_ADMIN_ID);
			roleAdmin.setRoleName(AppConstants.ROLE_ADMIN_NAME);

			Role roleManager = new Role();
			roleManager.setRoleId(AppConstants.ROLE_MANAGER_ID);
			roleManager.setRoleName(AppConstants.ROLE_MANAGER_NAME);

			Role roleCustomer = new Role();
			roleCustomer.setRoleId(AppConstants.ROLE_CUSTOMER_ID);
			roleCustomer.setRoleName(AppConstants.ROLE_CUSTOMER_NAME);

			List<Role> roleList = List.of(roleAdmin, roleManager, roleCustomer);

			this.roleRepository.saveAll(roleList);
		}catch (Exception e){
			e.printStackTrace();
		}
	}
}
