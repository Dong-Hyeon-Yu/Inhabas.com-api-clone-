package com.inhabas.api.web;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inhabas.testAnnotataion.WithMockJwtAuthenticationToken;
import com.inhabas.api.domain.contest.dto.DetailContestBoardDto;
import com.inhabas.api.domain.contest.dto.ListContestBoardDto;
import com.inhabas.api.domain.contest.dto.SaveContestBoardDto;
import com.inhabas.api.domain.contest.dto.UpdateContestBoardDto;
import com.inhabas.api.domain.contest.usecase.ContestBoardService;
import com.inhabas.testAnnotataion.NoSecureWebMvcTest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@NoSecureWebMvcTest(ContestBoardController.class)
public class ContestBoardControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ContestBoardService contestBoardService;


    @DisplayName("????????? ????????? ????????? ????????????.")
    @Test
    @WithMockJwtAuthenticationToken
    public void addNewContestBoard() throws Exception {
        //given
        SaveContestBoardDto saveContestBoardDto = new SaveContestBoardDto("title", "contents", "association", "topic", LocalDate.of(2022, 1, 1), LocalDate.of(9022, 3, 26));
        given(contestBoardService.write(any(), any(SaveContestBoardDto.class))).willReturn(1);

        // when
        mvc.perform(post("/contest")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(saveContestBoardDto)))
                .andExpect(status().isCreated())
                .andExpect(content().string("1"));
    }

    @DisplayName("????????? ????????? ????????? ????????????.")
    @Test
    @WithMockJwtAuthenticationToken
    public void updateContestBoard() throws Exception{
        //given
        UpdateContestBoardDto updateContestBoardDto = new UpdateContestBoardDto(1, "????????? ??????", "????????? ??????", "????????? ???????????????", "????????? ????????? ??????", LocalDate.of(2022, 1, 1), LocalDate.of(9022, 3, 26));
        given(contestBoardService.update(any(), any(UpdateContestBoardDto.class))).willReturn(1);

        // when
        mvc.perform(put("/contest")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateContestBoardDto)))
                .andExpect(status().isOk())
                .andExpect(content().string("1"));
    }

    @DisplayName("????????? ????????? ????????? ????????????.")
    @Test
    public void deleteContestBoard() throws Exception{
        //given
        doNothing().when(contestBoardService).delete(any(), anyInt());

        // when
        mvc.perform(delete("/contest/1"))
                .andExpect(status().isNoContent());
    }

    @DisplayName("????????? ????????? ?????? ????????? ????????????.")
    @Test
    public void getContestBoardList() throws Exception {
        PageRequest pageable = PageRequest.of(0,10, Sort.Direction.DESC, "id");

        List<ListContestBoardDto> results = new ArrayList<>();
        results.add(new ListContestBoardDto("title1", "contents1", LocalDate.of(2022,1,1), LocalDate.of(2022, 1, 29)));
        results.add(new ListContestBoardDto("title2", "contents2", LocalDate.of(2022,1,1), LocalDate.of(2022, 1, 29)));
        results.add(new ListContestBoardDto("title3", "contents3", LocalDate.of(2022,1,1), LocalDate.of(2022, 1, 29)));

        Page<ListContestBoardDto> expectedContestBoardDto = new PageImpl<>(results, pageable, results.size());

        given(contestBoardService.getBoardList(any(), any())).willReturn(expectedContestBoardDto);

        // when
        String responseBody = mvc.perform(get("/contests?menu_id=9")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("utf-8")
                        .param("page", "0")
                        .param("size", "10")
                        .param("sort", "DESC")
                        .param("properties", "id"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse().getContentAsString();

        // then
        assertThat(responseBody).isEqualTo(objectMapper.writeValueAsString(expectedContestBoardDto));
    }

    @DisplayName("????????? ????????? ?????? ????????? ????????????.")
    @Test
    public void getContestBoardDetail() throws Exception{
        //given
        DetailContestBoardDto contestBoardDto = new DetailContestBoardDto(1, "mingyeom", "title", "contents", "association","topic",  LocalDate.of(2022,1,1), LocalDate.of(2022, 1, 29), LocalDateTime.now(), null);
        given(contestBoardService.getBoard(anyInt())).willReturn(contestBoardDto);

        // when
        String responseBody = mvc.perform(get("/contest/1"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse().getContentAsString();

        // then
        assertThat(responseBody).isEqualTo(objectMapper.writeValueAsString(contestBoardDto));

    }

    @DisplayName("????????? ????????? ?????? ??? Title??? ????????? ????????? ????????? ?????? ??????")
    @Test
    @WithMockJwtAuthenticationToken
    public void TitleIsTooLongError() throws Exception {
        //given
        SaveContestBoardDto saveContestBoardDto = new SaveContestBoardDto("title".repeat(20)+ ".", "contents", "association", "topic", LocalDate.of(2022, 1, 1), LocalDate.of(2022, 1,26));


        // when
        String errorMessage = Objects.requireNonNull(
                mvc.perform(post("/contest")
                        .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(saveContestBoardDto)))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResolvedException())
                .getMessage();

        // then
        assertThat(errorMessage).isNotBlank();
        assertThat(errorMessage).contains("????????? ?????? 100????????????.");
    }

    @DisplayName("????????? ????????? ?????? ??? Contents??? null??? ?????? ?????? ??????")
    @Test
    @WithMockJwtAuthenticationToken
    public void ContentIsNullError() throws Exception {
        //given
        SaveContestBoardDto saveContestBoardDto = new SaveContestBoardDto("title",  " ", "association", "topic", LocalDate.of(2022, 1, 1), LocalDate.of(2022, 1,26));

        // when
        String errorMessage = Objects.requireNonNull(
                mvc.perform(post("/contest")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(saveContestBoardDto)))
                .andExpect(status().isBadRequest())
                .andReturn()
                .getResolvedException())
                .getMessage();

        // then
        assertThat(errorMessage).isNotBlank();
        assertThat(errorMessage).contains("????????? ???????????????.");
    }

}
