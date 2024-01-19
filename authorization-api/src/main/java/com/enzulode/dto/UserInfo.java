package com.enzulode.dto;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/** This class contains user-related information. */
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {

    private UUID id;
    private String firstName;
    private String lastName;
    private String middleName;
    private String profileImg;
    private String email;
    private Collection<String> authorities;

    /**
     * This method constructs user info instance from the parameter map.
     *
     * @param map parameter map
     * @return user info instance
     */
    @SuppressWarnings("unchecked")
    public static UserInfo fromMap(Map<String, Object> map) {
        //        @formatter:off
        if (!(map.containsKey("id")
            && map.containsKey("firstName")
            && map.containsKey("lastName")
            && map.containsKey("middleName")
            && map.containsKey("profileImg")
            && map.containsKey("email")
            && map.containsKey("authorities")
        )) return null;

        return UserInfo.builder()
            .id(UUID.fromString((String) map.get("id")))
            .firstName((String) map.get("firstName"))
            .lastName((String) map.get("lastName"))
            .middleName((String) map.get("middleName"))
            .profileImg((String) map.get("profileImg"))
            .email((String) map.get("email"))
            .authorities((List<String>) map.get("authorities"))
            .build();
//        @formatter:on
    }
}
