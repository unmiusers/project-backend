package com.project.issue.service;

import com.project.issue.model.Query;
import com.project.issue.repository.QueryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QueryService {

    @Autowired
    private QueryRepository queryRepository;

    public List<Query> getAllQueries() {
        return queryRepository.findAll();
    }

    public Optional<Query> getQueryById(Long id) {
        return queryRepository.findById(id);
    }

    public Query createQuery(Query query) {
        return queryRepository.save(query);
    }

    public Optional<Query> updateQuery(Long id, Query query) {
        return queryRepository.findById(id)
                .map(existingQuery -> {
                    existingQuery.setName(query.getName());
                    existingQuery.setFilters(query.getFilters());
                    return queryRepository.save(existingQuery);
                });
    }

    public boolean deleteQuery(Long id) {
        return queryRepository.findById(id)
                .map(query -> {
                    queryRepository.delete(query);
                    return true;
                }).orElse(false);
    }
}
