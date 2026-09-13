
package com.hdfclife.desk.web;

import com.hdfclife.desk.model.Claim;
import com.hdfclife.desk.model.Policy;
import com.hdfclife.desk.service.ClaimService;
import com.hdfclife.desk.service.PolicyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api")
@Tag(name = "Policies", description = "HDFC Life policy operations")
public class PolicyController {

    private final PolicyService policyService;
    private final ClaimService claimService;

    public PolicyController(PolicyService policyService,
                            ClaimService claimService) {
        this.policyService = policyService;
        this.claimService = claimService;
    }

    @GetMapping("/policies")
    @Operation(summary = "Get policies")
    @ApiResponse(responseCode = "200", description = "Policies returned")
    public ResponseEntity<List<Policy>> getPolicies(
            @Parameter(description = "Filter by policy status")
            @RequestParam(required = false) String status,

            @Parameter(description = "Filter by policy type")
            @RequestParam(required = false) String type) {

        return ResponseEntity.ok(
                policyService.getPolicies(status, type));
    }

    @GetMapping("/policies/{policyNo}")
    @Operation(summary = "Get one policy")
    @ApiResponse(responseCode = "200", description = "Policy found")
    @ApiResponse(responseCode = "404", description = "Policy not found")
    public ResponseEntity<Policy> getPolicy(
            @PathVariable String policyNo) {

        return ResponseEntity.ok(
                policyService.getPolicy(policyNo));
    }

    @PostMapping("/policies")
    @Operation(summary = "Create a policy")
    @ApiResponse(responseCode = "201", description = "Policy created")
    @ApiResponse(responseCode = "409", description = "Duplicate policy")
    public ResponseEntity<Policy> createPolicy(
            @RequestBody Policy policy) {

        Policy created = policyService.createPolicy(policy);

        URI location = URI.create(
                "/api/policies/" + created.getPolicyNo());

        return ResponseEntity
                .created(location)
                .body(created);
    }

    @PutMapping("/policies/{policyNo}")
    @Operation(summary = "Replace a policy")
    @ApiResponse(responseCode = "200", description = "Policy updated")
    @ApiResponse(responseCode = "404", description = "Policy not found")
    public ResponseEntity<Policy> updatePolicy(
            @PathVariable String policyNo,
            @RequestBody Policy policy) {

        return ResponseEntity.ok(
                policyService.updatePolicy(policyNo, policy));
    }

    @DeleteMapping("/policies/{policyNo}")
    @Operation(summary = "Delete a policy")
    @ApiResponse(responseCode = "204", description = "Policy deleted")
    @ApiResponse(responseCode = "404", description = "Policy not found")
    public ResponseEntity<Void> deletePolicy(
            @PathVariable String policyNo) {

        policyService.deletePolicy(policyNo);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/policies/{policyNo}/claims")
    @Operation(summary = "Get claims for a policy")
    @ApiResponse(responseCode = "200", description = "Claims returned")
    @ApiResponse(responseCode = "404", description = "Policy not found")
    public ResponseEntity<List<Claim>> getPolicyClaims(
            @PathVariable String policyNo) {

        return ResponseEntity.ok(
                claimService.getClaimsForPolicy(policyNo));
    }
}
