package com.study.boot.Data.dao;

import com.study.boot.Data.entity.Admin;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.Calendar;

@Service
public class AdminDao {

    public Admin selectPasswordByUsername(final String username) {
        if (username.equals("zl")) {
            return new Admin(1,
                    "zl",
                    "123456",
                    Calendar.getInstance().getTime(),
                    1
            );
        } else if (username.equals("lxl")) {
            return new Admin(2,
                    "lxl",
                    "123456",
                    Calendar.getInstance().getTime(),
                    1
            );
        } else if (username.equals("wyg")) {
            return new Admin(3,
                    "wyg",
                    "123456",
                    Calendar.getInstance().getTime(),
                    1
            );
        } else {
            return null;
        }
    }

    public int addServer(final String from, final String to) throws SQLException {
        if (from == null){
            return 0;
        }
        return 1;
    }
}
