
package com.hdfclife.desk.service;

import com.hdfclife.desk.exception.DuplicatePolicyException;
import com.hdfclife.desk.exception.PolicyNotFoundException;
import com.hdfclife.desk.model.Policy;
import com.hdfclife.desk.store.PolicyStore;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class PolicyService {

    private final PolicyStore policyStore;

    public PolicyService(PolicyStore policyStore) {
        this.policyStore = policyStore;
    }

    public List<Policy> getPolicies(String status, String type) {

        if (status == null && type == null) {
            return policyStore.findAll();
        }

        if (status != null && type != null) {
            return policyStore.findAll()
                    .stream()
                    .filter(policy -> policy.getStatus().equals(status))
                    .filter(policy -> policy.getType().equals(type))
                    .toList();
        }

        if (status != null) {
            return policyStore.findByStatus(status);
        }

        return policyStore.findByType(type);
    }

    public Policy getPolicy(String policyNo) {
        return policyStore.findByPolicyNo(policyNo)
                .orElseThrow(() ->
                        new PolicyNotFoundException(
                                "Policy not found: " + policyNo));
    }

    public Policy createPolicy(Policy policy) {

        if (policyStore.findByPolicyNo(policy.getPolicyNo()).isPresent()) {
            throw new DuplicatePolicyException(
                    "Policy already exists: " + policy.getPolicyNo());
        }

        policyStore.add(policy);
        return policy;
    }

    public Policy updatePolicy(String policyNo, Policy policy) {

        getPolicy(policyNo);

        policy.setPolicyNo(policyNo);
        policyStore.update(policy);

        return policy;
    }

    public void deletePolicy(String policyNo) {

        getPolicy(policyNo);

        policyStore.delete(policyNo);
    }

    public long count() {
        return policyStore.count();
    }

    public long activePolicyCount() {
        return policyStore.findByStatus("Active").size();
    }

    public long termPolicyCount() {
        return policyStore.findByType("TERM").size();
    }

    public long uniqueCustomerCount() {

        Set<String> customers = new HashSet<>();

        for (Policy policy : policyStore.findAll()) {
            customers.add(policy.getCustomer());
        }

        return customers.size();
    }
}

