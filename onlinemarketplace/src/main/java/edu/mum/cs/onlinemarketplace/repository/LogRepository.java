package edu.mum.cs.onlinemarketplace.repository;


import edu.mum.cs.onlinemarketplace.domain.Cart;
import edu.mum.cs.onlinemarketplace.domain.Log;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LogRepository extends JpaRepository<Log, Long> {
}
