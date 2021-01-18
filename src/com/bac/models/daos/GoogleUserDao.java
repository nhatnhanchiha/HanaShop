package com.bac.models.daos;

import com.bac.models.entities.GoogleUser;

import java.sql.SQLException;

public interface GoogleUserDao extends Dao {
    GoogleUser getByGmail(String gmail) throws SQLException;
    GoogleUser addUser(GoogleUser googleUser) throws SQLException;
}