package com.mybatis;

import java.time.LocalDateTime;

/**
 * 测试实体类
 */
public class Entity {

    private Long id;
    private String name;
    private Enum<Type> type;
    private String dependent_index_code_list;
    private LocalDateTime gmt_create;
    private LocalDateTime gmt_modified;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Enum<Type> getType() {
        return type;
    }

    public void setType(Enum<Type> type) {
        this.type = type;
    }

    public String getDependent_index_code_list() {
        return dependent_index_code_list;
    }

    public void setDependent_index_code_list(String dependent_index_code_list) {
        this.dependent_index_code_list = dependent_index_code_list;
    }

    public LocalDateTime getGmt_create() {
        return gmt_create;
    }

    public void setGmt_create(LocalDateTime gmt_create) {
        this.gmt_create = gmt_create;
    }

    public LocalDateTime getGmt_modified() {
        return gmt_modified;
    }

    public void setGmt_modified(LocalDateTime gmt_modified) {
        this.gmt_modified = gmt_modified;
    }
}
