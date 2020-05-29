package com.powerco.telemetry.controller;

class LocationNotFoundException extends RuntimeException {

  LocationNotFoundException(Long id) {
    super("Could not find LOCATION: " + id);
  }
}