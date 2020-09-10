package com.lambdaschool.apollo.repository;

import com.lambdaschool.apollo.models.Context;
import org.springframework.data.repository.CrudRepository;

public interface ContextRepository extends CrudRepository<Context, Long> {
    Context findContextByDescription(String description);
}
