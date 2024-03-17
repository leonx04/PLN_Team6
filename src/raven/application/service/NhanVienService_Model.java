/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package raven.application.service;

import java.util.List;

/**
 *
 * @author acer
 */
public abstract class NhanVienService_Model<EntityType,KeyType> {
    
    public abstract void insert(EntityType entity);

    public abstract void update(EntityType entity);

    public abstract void delete(EntityType entity);

    public abstract List<EntityType> getAll();

    public abstract EntityType selectById(KeyType id);

    public abstract List<EntityType> selectBySQL(String sql, Object... args);
    
}
