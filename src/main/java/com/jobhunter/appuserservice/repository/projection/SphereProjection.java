package com.jobhunter.appuserservice.repository.projection;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.UUID;

@JsonInclude(JsonInclude.Include.NON_NULL)
public interface SphereProjection {
    UUID getId();

    String getName();
}