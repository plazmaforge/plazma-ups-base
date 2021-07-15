package com.ohapon.query;
import org.junit.Test;
import static org.junit.Assert.*;

public class QueryGeneratorTest {

    @Test
    public void testGetAll() {
        String query = new QueryGenerator().getAll(Person.class);
        assertEquals("SELECT id, person_name, salary FROM persons", query);
    }

    @Test
    public void testInsert() {
        Person person = new Person();
        person.setId(100);
        person.setName("MyPerson");
        person.setSalary(5000.0);
        String query = new QueryGenerator().insert(person);
        assertEquals("INSERT INTO persons (id, person_name, salary) VALUES (100, 'MyPerson', 5000.0)", query);
    }

    @Test
    public void testInsertWithNullValue() {
        Person person = new Person();
        person.setId(100);
        person.setName(null);
        person.setSalary(5000.0);
        String query = new QueryGenerator().insert(person);
        assertEquals("INSERT INTO persons (id, person_name, salary) VALUES (100, NULL, 5000.0)", query);
    }

    @Test
    public void testUpdate() {
        Person person = new Person();
        person.setId(100);
        person.setName("MyPerson");
        person.setSalary(5000.0);
        String query = new QueryGenerator().update(person);
        assertEquals("UPDATE persons SET person_name = 'MyPerson', salary = 5000.0 WHERE id = 100", query);
    }

    @Test
    public void testUpdateWithNullValue() {
        Person person = new Person();
        person.setId(100);
        person.setName(null);
        person.setSalary(5000.0);
        String query = new QueryGenerator().update(person);
        assertEquals("UPDATE persons SET person_name = NULL, salary = 5000.0 WHERE id = 100", query);
    }

    @Test
    public void testGetById() {
        String query = new QueryGenerator().getById(Person.class,100);
        assertEquals("SELECT id, person_name, salary FROM persons WHERE id = 100", query);
    }

    @Test
    public void testDelete() {
        String query = new QueryGenerator().delete(Person.class,100);
        assertEquals("DELETE FROM persons WHERE id = 100", query);
    }

}
