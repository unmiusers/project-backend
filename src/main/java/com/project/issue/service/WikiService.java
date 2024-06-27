package com.project.issue.service;

import com.project.issue.model.WikiPage;
import com.project.issue.repository.WikiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WikiService {

    @Autowired
    private WikiRepository wikiRepository;

    public List<WikiPage> getAllWikiPages() {
        return wikiRepository.findAll();
    }

    public Optional<WikiPage> getWikiPageById(Long id) {
        return wikiRepository.findById(id);
    }

    public WikiPage createWikiPage(WikiPage wikiPage) {
        return wikiRepository.save(wikiPage);
    }

    public Optional<WikiPage> updateWikiPage(Long id, WikiPage wikiPage) {
        return wikiRepository.findById(id)
                .map(existingPage -> {
                    existingPage.setTitle(wikiPage.getTitle());
                    existingPage.setContent(wikiPage.getContent());
                    return wikiRepository.save(existingPage);
                });
    }

    public boolean deleteWikiPage(Long id) {
        return wikiRepository.findById(id)
                .map(page -> {
                    wikiRepository.delete(page);
                    return true;
                }).orElse(false);
    }
}
