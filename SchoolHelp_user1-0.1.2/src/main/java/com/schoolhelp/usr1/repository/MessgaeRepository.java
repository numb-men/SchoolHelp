package com.schoolhelp.usr1.repository;

import com.schoolhelp.usr1.domain.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessgaeRepository extends JpaRepository<Message,Integer> {
    
}
