package com.schoolhelp.usr1.repository;

import com.schoolhelp.usr1.domain.Post;
import javafx.geometry.Pos;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post,Integer> {

    //通过用户id查询
    public List<Post> findByUsrId(Integer usrId);
}
