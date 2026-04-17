package com.skillForge.payPilot.dto;

import java.time.LocalDateTime;

public record KycHistory(String merchantId, KycStatus oldStatus, KycStatus newStatus, String changeBy, LocalDateTime timestamp) {

}
