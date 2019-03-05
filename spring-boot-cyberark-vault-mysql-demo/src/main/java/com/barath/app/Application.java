package com.barath.app;

import java.util.List;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.barath.app.Application.User;
import com.barath.cyberark.vault.configuration.EnableVaultAutoConfiguration;
import com.zaxxer.hikari.HikariDataSource;


@SpringBootApplication
//@EnableVaultAutoConfiguration
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
	protected static class UserService{
		
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
	
	
	@Entity
	@Table
	protected static class User{
		
		@Id
		@Column
		private Long userId;
		
		@Column
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

interface UserRepository extends JpaRepository<User, Long> {
	
}

//@Configuration
class DataSourceConfiguration{
	
		@Value("${mysql-password}")
		private String userName;
		
		@Value("${mysql-username}")
		private String password;
	
		@Bean
		@ConfigurationProperties(prefix = "spring.datasource.hikari")
		public HikariDataSource dataSource(DataSourceProperties properties) {
			properties.setUsername(userName);
			properties.setPassword(password);
			HikariDataSource dataSource = createDataSource(properties,
					HikariDataSource.class);
			if (StringUtils.hasText(properties.getName())) {
				dataSource.setPoolName(properties.getName());
			}
			return dataSource;
		}
		
		@SuppressWarnings("unchecked")
		protected static <T> T createDataSource(DataSourceProperties properties,
				Class<? extends DataSource> type) {
			return (T) properties.initializeDataSourceBuilder().type(type).build();
		}


	
}

