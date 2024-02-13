package com.qre.tg.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.qre.tg.dto.base.JsonFieldName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * UserRequest
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
public class UserRequest implements Serializable {

    @JsonProperty(JsonFieldName.USER_NAME)
    private String userName;

    @JsonProperty(JsonFieldName.PHONE_NUMBER)
    private String phoneNumber;

    @JsonProperty(JsonFieldName.EMAIL)
    private String email;

    @JsonProperty(JsonFieldName.PASSWORD)
    private String password;

    @JsonProperty(JsonFieldName.ROLE)
    private String role;
}
