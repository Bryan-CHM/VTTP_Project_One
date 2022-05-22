package VTTP.Project.VTTP_Project_One.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import VTTP.Project.VTTP_Project_One.models.User;

@Repository
public class LoginRepository {

    private static final String SQL_VERIFY_USER = "select * from user where username = ? and password = sha1(?);";
    private static final String SQL_CHECK_USER_EXISTS = "select * from user where username = ?;";
    private static final String SQL_CREATE_USER = "insert into user(username, password) values(?, sha1(?));";
    public static final String SQL_DELETE_USER = "delete from user where username = ?;";

    @Autowired
    private JdbcTemplate template;

    public Integer checkIfUserExists(User user){
        final SqlRowSet result = template.queryForRowSet(
            SQL_CHECK_USER_EXISTS, user.getUsername());
    
        if(result.next()){
                return result.getInt("user_id");
        }
        return -1;   
    }

    public Boolean verifyUser(User user){
        final SqlRowSet result = template.queryForRowSet(
            SQL_VERIFY_USER, user.getUsername(),user.getPassword());

        if(result.next()){
                return true;
        }
        return false;   
    }

    public Boolean createUser(User user) throws DataIntegrityViolationException{
        int count = template.update(SQL_CREATE_USER, user.getUsername(), user.getPassword());
        return 1 == count; 
    }   

}
