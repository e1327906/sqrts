package com.qre.tg.dto.base;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * APIResponse
 *
 * @author Zaw
 * @since 1.0
 * <p>
 * <pre>
 * Revision History:
 * Version  Date            Author          Changes
 * ------------------------------------------------------------------------------------------------------------------------
 * 1.0      13/2/2024     Zaw           Initial Coding
 *
 * </pre>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class APIResponse {
    @JsonProperty("ResponseCode")
    private String responseCode;

    @JsonProperty("ResponseMsg")
    private String responseMsg;

    @JsonProperty("ResponseData")
    private Object responseData;
}

