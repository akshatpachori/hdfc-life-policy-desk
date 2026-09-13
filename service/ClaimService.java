
package com.hdfclife.desk.service;

import com.hdfclife.desk.exception.ClaimNotFoundException;
import com.hdfclife.desk.exception.InvalidClaimException;
import com.hdfclife.desk.model.Claim;
import com.hdfclife.desk.model.Urgency;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClaimService {

    private final PolicyService policyService;
    private final List<Claim> claims = new ArrayList<>();
    private final int maxClaimAmount;

    public ClaimService(PolicyService policyService,com.hdfclife.desk.config.HdfcProperties properties) {
        this.policyService = policyService;
        this.maxClaimAmount = properties.getMaxClaimAmount();
    }

    public Claim createClaim(String policyNo, int claimAmount, Urgency urgency) {

        policyService.getPolicy(policyNo);

        if (claimAmount <= 0 || claimAmount > maxClaimAmount) {
            throw new InvalidClaimException(
                    "Claim amount must be between 1 and " + maxClaimAmount);
        }

        String claimNo = String.format("CLM-%02d", claims.size() + 1);

        Claim claim = new Claim(
                claimNo,
                policyNo,
                claimAmount,
                urgency,
                "SUBMITTED"
        );

        claims.add(claim);

        return claim;
    }

    public Claim getClaim(String claimNo) {

        return claims.stream()
                .filter(claim -> claim.getClaimNo().equals(claimNo))
                .findFirst()
                .orElseThrow(() ->
                        new ClaimNotFoundException(
                                "Claim not found: " + claimNo));
    }

    public List<Claim> getClaimsForPolicy(String policyNo) {

        policyService.getPolicy(policyNo);

        return claims.stream()
                .filter(claim -> claim.getPolicyNo().equals(policyNo))
                .toList();
    }
}
