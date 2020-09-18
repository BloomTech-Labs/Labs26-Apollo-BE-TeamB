package com.lambdaschool.apollo.services;

import com.lambdaschool.apollo.models.Context;

import java.util.List;

public interface ContextService {

    List<Context> findAllContexts();
    Context findContextById(long contextid);
    Context save(Context context);
}
