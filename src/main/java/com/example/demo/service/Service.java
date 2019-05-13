package com.example.demo.service;

import com.example.demo.Neo4jSessionFactory;
import org.neo4j.ogm.session.Session;

interface Service<T> {

    int DEPTH_LIST = 0;
    int DEPTH_ENTITY = 1;
    Session session = Neo4jSessionFactory.getInstance().getNeo4jSession();

    default Iterable<T> findAll() {
        return session.loadAll(getEntityType(), DEPTH_LIST);
    }

    default T find(Long id) {
        return session.load(getEntityType(), id, DEPTH_ENTITY);
    }

    default void delete(Long id) {
        session.delete(session.load(getEntityType(), id));
    }

    default void createOrUpdate(T entity) {
        session.save(entity, DEPTH_ENTITY);
    }

    Class<T> getEntityType();
}