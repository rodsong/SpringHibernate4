package com.jiaoya.line.dao;

import com.jiaoya.common.dao.BaseDao;
import org.springframework.transaction.annotation.Transactional;
import com.jiaoya.line.dao.entity.Person;

public interface TestDAO extends BaseDao<Person,Long> {
}
