package com.kj.interviewround.Repository;

import com.kj.interviewround.Entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {

}
