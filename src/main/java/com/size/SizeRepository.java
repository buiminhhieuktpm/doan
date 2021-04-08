package com.size;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
@Repository
public interface SizeRepository extends JpaRepository<Size, Integer>{
	@Query(value="SELECT * FROM size WHERE productid= ?1",nativeQuery = true)
	List<Size> findSizeByProductId(int id);
	@Query(value="SELECT * FROM size WHERE productid= ?1 AND size=?2",nativeQuery = true)
	Optional<Size> findSizeByName(int pid,String name);
	@Query(value = "SELECT * FROM size WHERE productid=?1 AND amount>0",nativeQuery = true)
	List<Size> findSizeByProductAndAmount(int pid);
}
