package com.jiaoya.line.dao;

import com.jiaoya.common.dao.BaseDaoImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jiaoya.line.dao.entity.Person;

@Service("testDao")
//@Transactional 可以在服务层调用事务
public class TestDAOImpl extends BaseDaoImpl<Person,Long> implements TestDAO {



}
