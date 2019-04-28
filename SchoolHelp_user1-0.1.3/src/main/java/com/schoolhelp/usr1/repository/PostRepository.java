package com.schoolhelp.usr1.repository;

import com.schoolhelp.usr1.domain.Post;
import javafx.geometry.Pos;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post,Integer> {

    //通过用户ID查询用户的所有帖子信息
    public List<Post> findByUsrId(Integer usrId);

}
