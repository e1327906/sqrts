package com.qre.tg.query.api.service.impl;

import com.qre.cmel.ecommsvcs.sdk.dto.MessageDto;
import com.qre.cmel.ecommsvcs.sdk.service.ECommSvcService;
import com.qre.tg.dao.feedback.FeedbackRepository;
import com.qre.tg.entity.feedback.Feedback;
import com.qre.tg.query.api.service.FeedbackService;
import com.qre.tg.query.api.service.strategy.FeedbackStrategy;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class FeedbackServiceImpl implements FeedbackService {

    private final Map<String, FeedbackStrategy> strategies;
    private final FeedbackRepository feedbackRepository;
    private final ECommSvcService eCommSvcService;

    public FeedbackServiceImpl(List<FeedbackStrategy> strategies, FeedbackRepository feedbackRepository, ECommSvcService eCommSvcService) {
        this.strategies = strategies.stream().collect(Collectors.toMap(strategy -> strategy.getClass().getSimpleName().replace("FeedbackStrategy", "").toLowerCase(), strategy -> strategy));
        this.feedbackRepository = feedbackRepository;
        this.eCommSvcService = eCommSvcService;
    }

    @Override
    public boolean processFeedback(Feedback feedback) {
        System.out.println("Feedback submitted by " + feedback.getName() + ": " + feedback.getMessage());
        feedbackRepository.save(feedback);
        FeedbackStrategy strategy = strategies.getOrDefault(feedback.getCategory().toLowerCase(), strategies.get("general"));
        System.out.println(strategy);
        String emailBody = strategy.generateAcknowledgment(feedback);
        CompletableFuture.runAsync(() -> {
            try {
                MessageDto messageDto = MessageDto
                        .builder()
                        .to(feedback.getEmail())
                        .subject(strategy.generateAcknowledgmentSubject())
                        .message(emailBody).build();
                eCommSvcService.send(messageDto);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        return true;
    }
}
