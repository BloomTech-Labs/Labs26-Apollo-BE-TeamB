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
    private ContextRepository contextrepos;

    @Override
    public List<Context> findAllContexts(){
        List<Context> allcontexts = new ArrayList<>();

        contextrepos.findAll().iterator()
                .forEachRemaining(allcontexts::add);
        return allcontexts;
    }

    @Override
    public Context findContextById(long contextid)
            throws ResourceNotFoundException
    {
        return contextrepos.findById(contextid)
                .orElseThrow(() -> new
                        ResourceNotFoundException
                        ("Context id " + contextid + " Not Found!"));
    }

    @Transactional
    @Override
    public Context save(Context context){
        Context newContext = new Context();
        newContext.setContextname(context.getContextname());
        newContext.setContextquestions(context.getContextquestions());
        return contextrepos.save(newContext);
    }

}
