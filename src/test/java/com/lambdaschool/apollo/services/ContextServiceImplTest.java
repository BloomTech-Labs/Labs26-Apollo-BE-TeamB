package com.lambdaschool.apollo.services;

import com.lambdaschool.apollo.ApolloApplication;
import com.lambdaschool.apollo.exceptions.ResourceNotFoundException;
import com.lambdaschool.apollo.models.Context;
import com.lambdaschool.apollo.models.Survey;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApolloApplication.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ContextServiceImplTest {

    @Autowired
    private ContextService contextService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void a_findAll() {
        assertEquals(5, contextService.findAll().size());
    }

    @Test
    public void b_findById() {
        assertEquals("Product Leadership", contextService.findById(24).getDescription());
    }

    @Test
    public void c_findByDescription() {
        assertEquals(24, contextService.findByDescription("Product Leadership").getContextId());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void ca_findByDescriptionNotFound() {
        assertEquals(ResourceNotFoundException.class, contextService.findByDescription("No").getContextId());
    }

    @Test
    public void d_save() {
        Context context = new Context("New Context", new Survey());
        contextService.save(context);

        assertEquals(6, contextService.findAll().size());
    }
}