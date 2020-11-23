package com.study.boot.Data.server;


import com.study.boot.Data.Util.TokenUtils;
import com.study.boot.Data.dao.AdminDao;
import com.study.boot.Data.entity.Admin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.tools.jstat.Token;

import java.sql.SQLException;

@Service
public class AdminServer {

    private static final Logger log = LoggerFactory.getLogger(AdminServer.class);

    @Autowired
    private AdminDao adminDao;

    public int isLogin(final String username, final String password) throws SQLException {
        Admin admin = adminDao.selectPasswordByUsername(username);
        if (admin == null){
            return 0;
        }
        if (admin.getPassword().equals(password)){
            return 1;
        }else {
            return -1;
        }
    }

   public int addServerMes(String from, String to) throws SQLException {
        if (adminDao.addServer(from, to) == 1){
            return 1;
        }
        return 0;
    }


}
