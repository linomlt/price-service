package io.github.linomlt.priceservice.adapter.in.rest;

import io.github.linomlt.priceservice.application.exception.PriceNotFoundException;
import io.github.linomlt.priceservice.domain.exception.DomainException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.net.URI;
import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(PriceNotFoundException.class)
    public ProblemDetail handlePriceNotFoundException(PriceNotFoundException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        problemDetail.setTitle("Price Not Found");
        problemDetail.setType(URI.create("https://api.priceservice.com/errors/not-found"));
        problemDetail.setProperty("timestamp", Instant.now());
        return problemDetail;
    }

    @ExceptionHandler(DomainException.class)
    public ProblemDetail handleDomainException(DomainException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                ex.getMessage());
        problemDetail.setTitle("Domain Error");
        problemDetail.setType(URI.create("https://api.priceservice.com/errors/domain-error"));
        problemDetail.setProperty("timestamp", Instant.now());
        return problemDetail;
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ProblemDetail handleMissingServletRequestParameterException(
            MissingServletRequestParameterException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                "Required query parameter '" + ex.getParameterName() + "' is missing");
        problemDetail.setTitle("Missing Required Parameter");
        problemDetail.setType(URI.create("https://api.priceservice.com/errors/missing-request-parameter"));
        problemDetail.setProperty("timestamp", Instant.now());
        problemDetail.setProperty("parameter", ex.getParameterName());
        return problemDetail;
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ProblemDetail handleMethodArgumentTypeMismatchException(
            MethodArgumentTypeMismatchException ex) {
        String expectedType = ex.getRequiredType() != null ? ex.getRequiredType().getSimpleName() : "unknown";
        String parameterName = ex.getName();

        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(
                HttpStatus.BAD_REQUEST,
                "Parameter '" + parameterName + "' has an invalid value type");
        problemDetail.setTitle("Invalid Parameter Type");
        problemDetail.setType(URI.create("https://api.priceservice.com/errors/invalid-parameter-type"));
        problemDetail.setProperty("timestamp", Instant.now());
        problemDetail.setProperty("parameter", parameterName);
        problemDetail.setProperty("expectedType", expectedType);
        return problemDetail;
    }

}
