package com.qre.tg.query.api.service.strategy;

import com.qre.tg.entity.feedback.Feedback;

public interface FeedbackStrategy {

    String generateAcknowledgmentSubject();
    String generateAcknowledgment(Feedback feedback);

}
