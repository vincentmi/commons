package com.vnzmi.commons.validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class UniqueValidator implements javax.validation.ConstraintValidator<Unique, Object> {


    Logger logger = LoggerFactory.getLogger(this.getClass());

    private String table;
    private String field;
    private String message;

    @PersistenceContext
    private EntityManager  entityManager;

    @Override
    public void initialize(Unique constraintAnnotation) {
        this.table = constraintAnnotation.table();
        this.field = constraintAnnotation.value();
        this.message =  constraintAnnotation.message();

    }

    @Override
    public boolean isValid(Object var, ConstraintValidatorContext context) {

        return isValueExists(this.table, this.field, var);
    }


    //检查数据是否唯一
    @Transactional
    protected boolean isValueExists(String table, String field, Object var) {
        if (var == null) {
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
        int  count = Integer.valueOf(rs.get(0).toString());

        return count  == 0;
    }

}
