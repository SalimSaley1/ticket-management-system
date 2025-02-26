package com.hahn.ticketsystem;

import com.hahn.ticketsystem.entities.Ticket;
import com.hahn.ticketsystem.enums.Category;
import com.hahn.ticketsystem.enums.Priority;
import com.hahn.ticketsystem.repositories.TicketRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class TicketsystemApplication {

	public static void main(String[] args) {

		SpringApplication.run(TicketsystemApplication.class, args);

		/* Récupérer le repository depuis le contexte
		TicketRepository ticketRepository = ctxt.getBean(TicketRepository.class);

		// Créer plusieurs tickets
		Ticket ticket1 = new Ticket();
		ticket1.setTitle("11111111");
		ticket1.setDescription("Bug critique sur la page d'accueil");
		ticket1.setPriority(Priority.HIGH);
		ticket1.setCategory(Category.SOFTWARE);

		Ticket ticket2 = new Ticket();
		ticket2.setTitle("22222222");
		ticket2.setDescription("Mise à jour documentation");
		ticket2.setPriority(Priority.LOW);
		ticket2.setCategory(Category.NETWORK);

		// Sauvegarder les tickets
		ticketRepository.save(ticket1);
		ticketRepository.save(ticket2);


		// Fermer le contexte à la fin
		ctxt.close();

		 */
	}


}
