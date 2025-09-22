package com.ftn.sbnz.service;
import java.util.Arrays;

import org.kie.api.KieServices;
import org.kie.api.builder.KieScanner;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.rule.QueryResults;
import org.kie.api.runtime.rule.QueryResultsRow;
import org.kie.api.runtime.rule.Variable;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;

import com.ftn.sbnz.model.models.Accident;
import com.ftn.sbnz.model.models.Crossroad;
import com.ftn.sbnz.model.models.EmergencyVehicle;
import com.ftn.sbnz.model.models.EventDay;
import com.ftn.sbnz.model.models.IllegalParking;
import com.ftn.sbnz.model.models.PublicTransportDelay;
import com.ftn.sbnz.model.models.TimeOfDay;
import com.ftn.sbnz.model.models.TrafficDensity;
import com.ftn.sbnz.model.models.Weather;

@SpringBootApplication
public class ServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceApplication.class, args);
	}

	@Bean
	public KieContainer kieContainer() {
		KieServices ks = KieServices.Factory.get();
		KieContainer kContainer = ks
				.newKieContainer(ks.newReleaseId("com.ftn.sbnz", "kjar", "0.0.1-SNAPSHOT"));
		KieScanner kScanner = ks.newKieScanner(kContainer);
		kScanner.start(1000);
		return kContainer;
	}

	@Bean
@ConditionalOnProperty(name = "app.run-sample-rules", havingValue = "true", matchIfMissing = false)
public CommandLineRunner testRules(KieContainer kieContainer) {
    return args -> {
        KieSession kieSession = kieContainer.newKieSession("ksession-rules");

        Crossroad r1 = new Crossroad("Raskrsnica 1", Arrays.asList("Raskrsnica 2"));
        Crossroad r2 = new Crossroad("Raskrsnica 2", Arrays.asList("Raskrsnica 3"));
        Crossroad r3 = new Crossroad("Raskrsnica 3", Arrays.asList());
        kieSession.insert(r1);
        kieSession.insert(r2);
        kieSession.insert(r3);

        kieSession.insert(new TrafficDensity("Raskrsnica 1", 85));          // gužva
        kieSession.insert(new Accident("Raskrsnica 1", "high"));           // nesreća
        kieSession.insert(new EmergencyVehicle("Raskrsnica 2", "north"));  // hitno vozilo
        kieSession.insert(new Weather("rain", "high"));                    // loše vreme
        kieSession.insert(new IllegalParking("Raskrsnica 3"));             // parkiranje
        kieSession.insert(new EventDay("concert", 2000));                  // događaj
        kieSession.insert(new PublicTransportDelay("Linija 23", 15));      // autobus
        kieSession.insert(new TimeOfDay(8));                               // rush hour

        kieSession.fireAllRules();

        QueryResults busResults = kieSession.getQueryResults(
            "whyBusDelay",
            "Linija 23",
            "Raskrsnica 1",
            Variable.v
        );

        System.out.println("=== Bus Delay Causes ===");
        for (QueryResultsRow row : busResults) {
            String crossroad = (String) row.get("$cross");
            String cause = (String) row.get("$c");
            System.out.println("Uzrok problema na " + crossroad + " je: " + cause);
        }

        
        QueryResults jamResults = kieSession.getQueryResults(
            "whyTrafficJam",
            "Raskrsnica 1",
            Variable.v
        );

        System.out.println("=== Traffic Jam Causes ===");
        for (QueryResultsRow row : jamResults) {
            String crossroad = (String) row.get("$cross");
            String cause = (String) row.get("$c");
            System.out.println("Uzrok gužve na " + crossroad + " je: " + cause);
        }
        kieSession.dispose();
    };
}


}
