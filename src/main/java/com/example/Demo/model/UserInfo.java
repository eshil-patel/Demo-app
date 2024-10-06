package com.example.Demo.model;

import java.time.LocalDateTime;

public record UserInfo(String id,
                       String name,
                       String address,
                       String dob,
                       Gender gender,
                       String state,
                       String country) {
}
