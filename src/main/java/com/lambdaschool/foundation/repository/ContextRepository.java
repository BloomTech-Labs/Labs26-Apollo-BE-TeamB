package com.lambdaschool.foundation.repository;

import com.lambdaschool.foundation.models.Context;
import org.springframework.data.repository.CrudRepository;

public interface ContextRepository extends CrudRepository<Context, Long> {
    Context findContextByDescription(String description);
}
