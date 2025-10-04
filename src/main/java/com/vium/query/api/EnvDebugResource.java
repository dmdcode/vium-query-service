package com.vium.query.api;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/env-debug")
public class EnvDebugResource {
  @GET @Produces(MediaType.APPLICATION_JSON)
  public String env() {
    String url = System.getenv("DATABASE_URL");
    String user = System.getenv("DB_USER");
    String pass = System.getenv("DB_PASS");
    String passMask = pass == null ? null : "*".repeat(Math.min(pass.length(), 8));
    boolean hasPrefer = url != null && url.contains("preferQueryMode=simple");
    return String.format("{\"DATABASE_URL\":\"%s\",\"hasPreferQueryMode\":%s,\"DB_USER\":\"%s\",\"DB_PASS_mask\":\"%s\"}",
        url, hasPrefer, user, passMask);
  }
}
