package ua.dudka;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import ua.dudka.domain.room.HotelRoom;
import ua.dudka.repository.room.HotelRoomRepository;

import static ua.dudka.web.booking.CreateBookingRequestController.Links.CREATE_BOOKING_REQUEST_PAGE_URL;
import static ua.dudka.web.room.HotelRoomController.Links.ROOM_PAGE_URL;

@SpringBootApplication
public class HotelRoomReservationApplication {

	public static void main(String[] args) {
		SpringApplication.run(HotelRoomReservationApplication.class, args);
	}


	@Configuration
	@Profile("dev")
	@RequiredArgsConstructor
	public class DevConfig implements CommandLineRunner {

		private final HotelRoomRepository repository;

		@Override
		public void run(String... strings) throws Exception {
			repository.deleteAll();
			for (int i = 0; i < 5; i++) {
				HotelRoom s = new HotelRoom(i, "room" + i);
				if (i % 2 == 0) {
					s.book();
				}
				repository.save(s);
			}
		}
	}


	@Configuration
	@Profile("security")
	@EnableWebSecurity
	@Controller
	public class SecurityConfig extends WebSecurityConfigurerAdapter {

		@GetMapping("/login")
		public String getLoginPage() {
			return "login";
		}

		@Override
		protected void configure(AuthenticationManagerBuilder auth) throws Exception {
			auth.inMemoryAuthentication()
					.withUser("admin")
					.password("admin")
					.roles("ADMIN");
		}

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http
					.csrf()
					.and()
					.authorizeRequests()
					.antMatchers("/**").permitAll()
					.antMatchers("/admin").hasRole("ADMIN")
					.anyRequest().authenticated()
					.and()
					.formLogin()
					.loginPage("/login")
					.successForwardUrl(ROOM_PAGE_URL)
					.permitAll()
					.and()
					.logout()
					.permitAll();
		}

		@Override
		public void configure(WebSecurity web) throws Exception {
			web
					.ignoring()
					.antMatchers("/resources/**", "/static/**", "/css/**", "/js/**", "/images/**", "/fonts/**");
		}
	}
}