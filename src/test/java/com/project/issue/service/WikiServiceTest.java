package com.project.issue.service;

import com.project.issue.model.WikiPage;
import com.project.issue.repository.WikiRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

public class WikiServiceTest {

    @InjectMocks
    private WikiService wikiService;

    @Mock
    private WikiRepository wikiRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetAllWikiPages() {
        WikiPage page1 = new WikiPage();
        page1.setId(1L);
        page1.setTitle("Page 1");

        WikiPage page2 = new WikiPage();
        page2.setId(2L);
        page2.setTitle("Page 2");

        when(wikiRepository.findAll()).thenReturn(Arrays.asList(page1, page2));

        List<WikiPage> pages = wikiService.getAllWikiPages();
        assertEquals(2, pages.size());
        verify(wikiRepository, times(1)).findAll();
    }

    @Test
    public void testGetWikiPageById() {
        WikiPage page = new WikiPage();
        page.setId(1L);
        page.setTitle("Test Page");

        when(wikiRepository.findById(1L)).thenReturn(Optional.of(page));

        Optional<WikiPage> foundPage = wikiService.getWikiPageById(1L);
        assertTrue(foundPage.isPresent());
        assertEquals("Test Page", foundPage.get().getTitle());
    }

    @Test
    public void testCreateWikiPage() {
        WikiPage page = new WikiPage();
        page.setTitle("New Page");

        when(wikiRepository.save(any(WikiPage.class))).thenReturn(page);

        WikiPage createdPage = wikiService.createWikiPage(page);
        assertEquals("New Page", createdPage.getTitle());
        verify(wikiRepository, times(1)).save(page);
    }

    @Test
    public void testUpdateWikiPage() {
        WikiPage existingPage = new WikiPage();
        existingPage.setId(1L);
        existingPage.setTitle("Existing Page");

        WikiPage updatedPage = new WikiPage();
        updatedPage.setTitle("Updated Page");
        updatedPage.setContent("Updated Content");

        when(wikiRepository.findById(1L)).thenReturn(Optional.of(existingPage));
        when(wikiRepository.save(any(WikiPage.class))).thenReturn(existingPage);

        Optional<WikiPage> result = wikiService.updateWikiPage(1L, updatedPage);
        assertTrue(result.isPresent());
        assertEquals("Updated Page", result.get().getTitle());
        assertEquals("Updated Content", result.get().getContent());
        verify(wikiRepository, times(1)).findById(1L);
        verify(wikiRepository, times(1)).save(existingPage);
    }

    @Test
    public void testDeleteWikiPage() {
        WikiPage page = new WikiPage();
        page.setId(1L);

        when(wikiRepository.findById(1L)).thenReturn(Optional.of(page));

        boolean isDeleted = wikiService.deleteWikiPage(1L);
        assertTrue(isDeleted);
        verify(wikiRepository, times(1)).findById(1L);
        verify(wikiRepository, times(1)).delete(page);
    }

    @Test
    public void testDeleteWikiPageNotFound() {
        when(wikiRepository.findById(1L)).thenReturn(Optional.empty());

        boolean isDeleted = wikiService.deleteWikiPage(1L);
        assertFalse(isDeleted);
        verify(wikiRepository, times(1)).findById(1L);
        verify(wikiRepository, times(0)).delete(any(WikiPage.class));
    }
}
