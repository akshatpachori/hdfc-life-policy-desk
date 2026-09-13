
package com.hdfclife.desk.config;

import com.hdfclife.desk.model.Policy;
import com.hdfclife.desk.store.PolicyStore;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class DataSeeder implements CommandLineRunner {

    private final PolicyStore policyStore;
    private final HdfcProperties properties;
    private final Environment environment;

    public DataSeeder(PolicyStore policyStore,
                      HdfcProperties properties,
                      Environment environment) {
        this.policyStore = policyStore;
        this.properties = properties;
        this.environment = environment;
    }

    @Override
    public void run(String... args) {

        if (policyStore.count() == 0) {

            policyStore.add(new Policy(
                    "HDFC-LIFE-1001",
                    "Anita Sharma",
                    "TERM",
                    18500,
                    "Active"
            ));

            policyStore.add(new Policy(
                    "HDFC-LIFE-1002",
                    "Rahul Mehta",
                    "ULIP",
                    42000,
                    "Active"
            ));

            policyStore.add(new Policy(
                    "HDFC-LIFE-1003",
                    "Priya Nair",
                    "ENDOWMENT",
                    27000,
                    "Lapsed"
            ));

            policyStore.add(new Policy(
                    "HDFC-LIFE-1004",
                    "Vikram Singh",
                    "TERM",
                    15200,
                    "Active"
            ));

            policyStore.add(new Policy(
                    "HDFC-LIFE-1005",
                    "Sneha Patel",
                    "ULIP",
                    36000,
                    "Active"
            ));

            policyStore.add(new Policy(
                    "HDFC-LIFE-1006",
                    "Anita Sharma",
                    "ENDOWMENT",
                    22000,
                    "Pending"
            ));
        }

        String activeProfile = environment.getActiveProfiles().length > 0
                ? environment.getActiveProfiles()[0]
                : "default";

        long activeCount = policyStore.findByStatus("Active").size();

        long termCount = policyStore.findByType("TERM").size();

        Set<String> customers = new HashSet<>();

        for (Policy policy : policyStore.findAll()) {
            customers.add(policy.getCustomer());
        }

        System.out.println("Active profile → " + activeProfile);
        System.out.println("Company name from HdfcProperties → "
                + properties.getCompanyName());
        System.out.println("Max claim amount → "
                + properties.getMaxClaimAmount());
        System.out.println("Seeded policy count → "
                + policyStore.count());
        System.out.println("Lookup HDFC-LIFE-1004 customer → "
                + policyStore.findByPolicyNo("HDFC-LIFE-1004")
                        .map(Policy::getCustomer)
                        .orElse("Not found"));
        System.out.println("Active policy count via PolicyService → "
                + activeCount);
        System.out.println("TERM policy count via PolicyService → "
                + termCount);
        System.out.println("Unique customer count → "
                + customers.size());
        System.out.println("Simple class name of the injected PolicyStore → "
                + policyStore.getClass().getSimpleName());
    }
}

