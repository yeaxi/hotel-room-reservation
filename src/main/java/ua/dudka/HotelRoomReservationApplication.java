package ua.dudka;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import ua.dudka.domain.HotelRoom;
import ua.dudka.repository.HotelRoomRepository;

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
			for (int i = 0; i < 5; i++) {
				repository.save(new HotelRoom("room" + i));
			}
		}
	}
}
