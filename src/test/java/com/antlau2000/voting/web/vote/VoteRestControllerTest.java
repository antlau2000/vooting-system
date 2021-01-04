package com.antlau2000.voting.web.vote;

import com.antlau2000.voting.web.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.antlau2000.voting.RestaurantTestData.RESTAURANT_1_ID;
import static com.antlau2000.voting.TestUtil.userHttpBasic;
import static com.antlau2000.voting.UserTestData.USER;
import static com.antlau2000.voting.web.vote.VoteController.REST_URL;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class VoteRestControllerTest  extends AbstractControllerTest {

    @Test
    void voteSimple() throws Exception {
        perform(MockMvcRequestBuilders.post(REST_URL)
                .param("restaurantId", String.valueOf(RESTAURANT_1_ID))
                .with(userHttpBasic(USER))
                .contentType(MediaType.APPLICATION_JSON)
                .content(""))
                .andExpect(status().isCreated());
    }
}
