package org.maiccol.compraventa.controllers.services;

import jakarta.servlet.http.HttpServletRequest;

import java.util.Optional;

public interface LoginService {
    Optional<String> getUserName(HttpServletRequest request);
}
