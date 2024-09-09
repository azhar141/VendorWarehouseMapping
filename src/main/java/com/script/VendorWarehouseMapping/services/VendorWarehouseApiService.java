package com.script.VendorWarehouseMapping.services;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.Map;

@Log4j2
@Service
public class VendorWarehouseApiService {
    private final WebClient webClient;

    @Value("${url.remove-duplicate-vendor}")
    private String removeDuplicateVendorUrl;

//    @Value("${app.security.token}")
//    private String token;

    public VendorWarehouseApiService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    public String removeDuplicateVendor(Long manufacturerId, Long warehouseId) throws Exception {
        try {
            log.info("removeDuplicateVendor manufacturerId = {}, warehouseId = {}", manufacturerId, warehouseId);

            Map<String, Object> requestBody = Map.of(
                    "manufacturerIds", manufacturerId,
                    "warehouseId", warehouseId
            );
    var token = "eyJhbGciOiJIUzM4NCIsInR5cCI6IkpXVCJ9.eyJqdGkiOiI5ZTZiNjYzOC0wZmUxLTQ2OWEtOGJlNC02NzkzMDIyYjFiZTEiLCJpZCI6OTcsIm1vYmlsZSI6Ijk5OTg4ODc3NjYiLCJyb2xlX2lkIjoxLCJyb2xlIjoiQURNSU4iLCJwZXJtaXNzaW9ucyI6WyJhYnNvbHV0ZV9zYWxlcyIsImFjdGl2YXRlX3RhZyIsImFwcHJvdmVfYXR0ZW5kYW5jZSIsImF0dGVuZGFuY2UiLCJhdWRpdF9tYWNoaW5lIiwiYmFubmVyX2NyZWF0ZSIsImJ1bGtfcHVyY2hhc2UiLCJidXlfYXRfdnAiLCJjb2hvcnRfY3JlYXRlIiwiY29ob3J0X21hcF9tYWNoaW5lIiwiY29ob3J0X21hcF91c2VyIiwiY29ob3J0X3ZpZXciLCJjb3Jwb3JhdGU6YWxsIiwiY3JlYXRlX2JhbmsiLCJjcmVhdGVfYmFzZSIsImNyZWF0ZV9icmFuZCIsImNyZWF0ZV9jb3Vwb24iLCJjcmVhdGVfZnJhbmNoaXNlZSIsImNyZWF0ZV9wbGF0Zm9ybV9jaGFyZ2UiLCJjcmVhdGVfcHJvZHVjdHMiLCJjcmVhdGVfdmFyaWFudCIsImNyZWF0ZV92ZW5kb3IiLCJlX2RhYWxjaGluaSIsImZ1ZWxfcmF0ZSIsImZ1ZWxfcmF0ZV9yZWFkIiwiaGFyZHdhcmVfaXNzdWUiLCJoZWFsdGhfYWxlcnQiLCJraXR0aW5nX3JlZmlsbGluZyIsIm1hcF9icF91c2VyX2ludGVybmFsIiwibWFwX21hY2hpbmVzIiwibWFwX3BsYXRmb3JtX2NoYXJnZSIsIm1hcF91c2VyIiwibWFwX3ZlbmRvciIsIm1lYWxzX21lbnUiLCJtb2JpbGl0eV9jaGVja2luIiwibW9iaWxpdHlfcmVmaWxsIiwibm90aWZpY2F0aW9uX2VuYWJsZWQiLCJvcmRlcnNfYWxsb3dlZCIsIm9yZGVyX2FwcHJvdmUiLCJwYXJ0bmVyX3JlY2hhcmdlIiwicHJvZHVjdF9hbGxvd2VkIiwicXVpY2tfdW5ibG9jayIsInJhaXNlX3RpY2tldCIsInJlY2NlIiwicmVmaWxsX2xvc3Nfb3ZlcnJpZGUiLCJyZWZ1bmRfcmV2b2tlIiwic2VsZl9hc3Nlc3NtZW50Iiwic2xvdF9vcGVyYXRpb24iLCJzbG90X3JlcGFpciIsInNsb3RfcmVwb3J0Iiwic2xvdF9yZXBvcnRfaW52ZW50b3J5Iiwic2xvdF91dGlsaXphdGlvbiIsInNwYXJlX3BhcnRzIiwic3RvY2tpbmciLCJzdXBwb3J0X25vdGlmaWNhdGlvbiIsInRhZzphbGwiLCJ0cm91Ymxlc2hvb3RfZ3VpZGUiLCJ1bmJsb2NrX2FsbF9zbG90cyIsInVzZXJfYWxsb3dlZCIsInZhcmlhbnRfY29uZmlnIiwidmVuZG9yX2FsbG93ZWQiLCJ2aWV3X2JhbmsiLCJ2aWV3X2Jhc2UiLCJ2aWV3X2JwX3VzZXIiLCJ2aWV3X2JyYW5kcyIsInZpZXdfY291cG9uIiwidmlld19mcmFuY2hpc2VlIiwidmlld19wbGF0Zm9ybV9jaGFyZ2UiLCJ2aWV3X3Byb2R1Y3RzIiwidmlld19wcm9maWxlIiwidmlld190YWciLCJ2aWV3X3VzZXJzIiwidmlld192YXJpYW50cyIsInZpZXdfdmVuZG9yIiwidm1fYWxsb3dlZCIsInZtX2NyZWF0ZSIsInZtX2luc3RhbGxhdGlvbiIsInZtX3BhcmFtZXRlcnMiLCJ2bV9zYWxlcyIsInZtX3NldHRpbmdzIiwidm1fdXBkYXRlIiwidm1fdmlldyIsIndhbGxldF9oaXN0b3J5Il0sImlhdCI6MTcyNTc5NzU3MiwiZXhwIjoxNzI1ODgzOTcyfQ.Dkp91_SgkrhmZjRkIpy5jaiP6SVYZ1wBIpgHHvlbVWDH3wADc_MSfVsZiOuCDW9p";
            String resolvedUrl = removeDuplicateVendorUrl
                    .replace("{warehouseId}", warehouseId.toString())
                    .replace("{vendorId}", manufacturerId.toString());

            System.out.println("Response from partner app service:  " +  resolvedUrl);
//            Map<Long, Map<String, Long>> response = webClient
          String response = webClient
                    .method(HttpMethod.POST)
                    .uri(resolvedUrl)
                    .headers(httpHeaders -> httpHeaders.setBearerAuth(token))
                    .bodyValue(requestBody)
                    .retrieve()
//                    .bodyToMono(new ParameterizedTypeReference<Map<Long, Map<String, Long>>>() {})
                    .bodyToMono(String.class)
                    .block();

            log.info("Response from removeDuplicateVendor: {}", response);
            return response;

        } catch (WebClientResponseException e) {
            log.warn("removeDuplicateVendor, Response error: {}", e.getResponseBodyAsString());
            log.error("Unable to process removeDuplicateVendor request", e);
            throw new Exception(e.getMessage(), e);
        }
    }
}