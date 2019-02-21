package com.barath.app;

import java.util.List;
import java.util.Optional;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.barath.cyberark.vault.configuration.EnableVaultAutoConfiguration;


@SpringBootApplication
@EnableVaultAutoConfiguration
@RestController
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
	
	private final UserService userService;
	
	
	public Application(UserService userService) {
		super();
		this.userService = userService;
	}
	
	@GetMapping("/user/{userId}")
	public Optional<User> findUser(@PathVariable Long userId){
		return this.userService.getUser(userId);
	}
	
	@GetMapping("/users")
	public List<User> findUsers(){
		return this.userService.getUsers();
	}


	@Service
	private class UserService{
		
		private final UserRepository userRepository;

		public UserService(UserRepository userRepository) {
			super();
			this.userRepository = userRepository;
		}
		
		public User saveUser(User user) {
			
			return this.userRepository.save(user);
		}
		
		public Optional<User> getUser(Long userId) {
			return this.userRepository.findById(userId);
		}
		
		public List<User> getUsers(){
			return this.userRepository.findAll();
		}
		
		
		
		
	}
	
	private interface UserRepository extends JpaRepository<User, Long> {
		
	}
	
	
	private class User{
		
		private Long userId;
		
		private String userName;

		public Long getUserId() {
			return userId;
		}

		public void setUserId(Long userId) {
			this.userId = userId;
		}

		public String getUserName() {
			return userName;
		}

		public void setUserName(String userName) {
			this.userName = userName;
		}
		
		

		public User() {
			super();
			
		}

		public User(Long userId, String userName) {
			super();
			this.userId = userId;
			this.userName = userName;
		}

		@Override
		public String toString() {
			return "User [userId=" + userId + ", userName=" + userName + "]";
		}
		
		
	}

}
