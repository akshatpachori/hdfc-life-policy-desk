
package com.hdfclife.desk.store;

import com.hdfclife.desk.model.Policy;

import java.util.List;
import java.util.Optional;

public interface PolicyStore {

    List<Policy> findAll();

    Optional<Policy> findByPolicyNo(String policyNo);

    List<Policy> findByStatus(String status);

    List<Policy> findByType(String type);

    void add(Policy policy);

    void update(Policy policy);

    void delete(String policyNo);

    long count();
}
