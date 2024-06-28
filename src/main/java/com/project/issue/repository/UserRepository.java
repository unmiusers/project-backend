package com.project.issue.repository;

import com.project.issue.model.User;
import com.project.issue.model.LoginHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // 自定义查询方法来获取用户的登录历史记录
    @Query("SELECT lh FROM LoginHistory lh WHERE lh.user.id = :userId")
    List<LoginHistory> findLoginHistoryByUserId(@Param("userId") Long userId);

    Optional<User> findByUsername(String username);
}
