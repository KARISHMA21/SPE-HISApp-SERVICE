package com.hisa.backend.service.serviceinterface;

import com.hisa.backend.bean.model.OneDayConsent;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface OnedayConsentService {
    ResponseEntity saveOnedayConsent(List<OneDayConsent> consents);

}
