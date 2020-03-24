package com.vnzmi.commons.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class ExistValidator implements javax.validation.ConstraintValidator<Exist, Object> {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    private String table;
    private String field;
    private boolean nullable;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void initialize(Exist constraintAnnotation) {
        this.table = constraintAnnotation.table();
        this.field = constraintAnnotation.value();
        this.nullable = constraintAnnotation.nullable();

    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        return isValueExists(this.table, this.field, value);
    }

    @Transactional
    protected boolean isValueExists(String table, String field, Object var) {
        if (var == null && this.nullable == false) {
            return false;
        }
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT COUNT(1) FROM  ");
        sb.append(table);
        sb.append(" WHERE ");
        sb.append(field);
        sb.append("=?");
        Query query = entityManager.createNativeQuery(sb.toString());
        query.setParameter(1, var);
        List rs = query.getResultList();
        int count = Integer.valueOf(rs.get(0).toString());

        return count>0;
    }
}
