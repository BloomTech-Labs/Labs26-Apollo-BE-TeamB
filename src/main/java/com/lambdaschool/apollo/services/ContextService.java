package com.lambdaschool.apollo.services;

import com.lambdaschool.apollo.models.Context;

import java.util.List;

public interface ContextService {

    List<Context> findAll();
    Context findById(long id);
    Context findByDescription(String description);
    Context save(Context context);
}
