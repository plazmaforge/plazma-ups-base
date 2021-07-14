package com.ohapon.query;
import org.junit.Test;
import static org.junit.Assert.*;

public class QueryGeneratorTest {

    @Test
    public void testGetAll() {
        String query = new QueryGenerator().getAll(Person.class);
        assertEquals("SELECT id, person_name, salary FROM persons;", query);
    }
}
