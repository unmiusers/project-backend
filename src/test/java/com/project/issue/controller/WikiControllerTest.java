package com.project.issue.controller;

import com.project.issue.model.WikiPage;
import com.project.issue.service.WikiService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class WikiControllerTest {

    private MockMvc mockMvc;

    @Mock
    private WikiService wikiService;

    @InjectMocks
    private WikiController wikiController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(wikiController).build();
    }

    @Test
    public void testGetAllWikiPages() throws Exception {
        WikiPage page1 = new WikiPage();
        page1.setId(1L);
        page1.setTitle("Page 1");

        WikiPage page2 = new WikiPage();
        page2.setId(2L);
        page2.setTitle("Page 2");

        when(wikiService.getAllWikiPages()).thenReturn(Arrays.asList(page1, page2));

        mockMvc.perform(get("/api/wiki/pages")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].title", is("Page 1")))
                .andExpect(jsonPath("$[1].title", is("Page 2")));
    }

    @Test
    public void testGetWikiPageById() throws Exception {
        WikiPage page = new WikiPage();
        page.setId(1L);
        page.setTitle("Test Page");

        when(wikiService.getWikiPageById(1L)).thenReturn(Optional.of(page));

        mockMvc.perform(get("/api/wiki/pages/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("Test Page")));
    }

    @Test
    public void testGetWikiPageByIdNotFound() throws Exception {
        when(wikiService.getWikiPageById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/wiki/pages/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreateWikiPage() throws Exception {
        WikiPage page = new WikiPage();
        page.setTitle("New Page");

        when(wikiService.createWikiPage(any(WikiPage.class))).thenReturn(page);

        mockMvc.perform(post("/api/wiki/pages")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"New Page\", \"content\": \"This is the content\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title", is("New Page")));
    }

    @Test
    public void testUpdateWikiPage() throws Exception {
        WikiPage page = new WikiPage();
        page.setId(1L);
        page.setTitle("Updated Page");

        when(wikiService.updateWikiPage(eq(1L), any(WikiPage.class))).thenReturn(Optional.of(page));

        mockMvc.perform(put("/api/wiki/pages/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"Updated Page\", \"content\": \"Updated content\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("Updated Page")));
    }

    @Test
    public void testUpdateWikiPageNotFound() throws Exception {
        when(wikiService.updateWikiPage(eq(1L), any(WikiPage.class))).thenReturn(Optional.empty());

        mockMvc.perform(put("/api/wiki/pages/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\": \"Updated Page\", \"content\": \"Updated content\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testDeleteWikiPage() throws Exception {
        when(wikiService.deleteWikiPage(1L)).thenReturn(true);

        mockMvc.perform(delete("/api/wiki/pages/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeleteWikiPageNotFound() throws Exception {
        when(wikiService.deleteWikiPage(1L)).thenReturn(false);

        mockMvc.perform(delete("/api/wiki/pages/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
