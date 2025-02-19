package com.trading.mfanalyser.test;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

public interface UserRepo  extends CrudRepository<User,String> {

}
