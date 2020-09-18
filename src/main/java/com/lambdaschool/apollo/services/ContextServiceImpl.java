package com.lambdaschool.apollo.services;

import com.lambdaschool.apollo.exceptions.ResourceNotFoundException;
import com.lambdaschool.apollo.models.Context;
import com.lambdaschool.apollo.repository.ContextRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service(value = "contextService")
public class ContextServiceImpl implements ContextService {

    @Autowired
    private ContextRepository contextRepository;

    @Autowired
    private SurveyService surveyService;

    @Override
    public List<Context> findAll() {

        List<Context> contextList = new ArrayList<>();
        contextRepository.findAll().iterator().forEachRemaining(contextList::add);
        return contextList;
    }

    @Override
    public Context findById(long id) {

        return contextRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Context " + id + " Not Found"));
    }

    @Override
    public Context findByDescription(String description) {

        Context context = contextRepository.findContextByDescription(description.toLowerCase());
        if (context == null) {
            throw new ResourceNotFoundException("Context Description: " + description + " Not Found");
        }
        return context;
    }

    @Transactional
    @Override
    public void delete(long id) {

        contextRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Context " + id + " Not Found"));
        contextRepository.deleteById(id);
    }

    @Transactional
    @Override
    public Context save(Context context) {

        Context newContext = new Context();

        if (context.getContextId() != 0) {
            Context oldContext = contextRepository.findById(context.getContextId())
                    .orElseThrow(() -> new ResourceNotFoundException("Context Id " + context.getContextId() + " Not Found"));
            newContext.setContextId(context.getContextId());
        }
        newContext.setContextname(context.getContextname());
        return contextRepository.save(newContext);
    }

    @Transactional
    @Override
    public Context update(Context context, long id) {
        return null;
    }
}
