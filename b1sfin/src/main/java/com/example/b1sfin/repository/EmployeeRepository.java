package com.example.b1sfin.repository;

import com.example.b1sfin.model.Agency;
import com.example.b1sfin.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,String > {
    List<Employee> getEmployeesByRetired(boolean retired);
    List<Employee> getEmployeesByAgency(Agency agency);

    @Query("SELECT e FROM Employee e WHERE " +
            "(:name IS NULL OR e.name LIKE %:name%) AND " +
            "(:phone IS NULL OR e.phone LIKE %:phone%) AND " +
            "(:email IS NULL OR e.email LIKE %:email%) AND " +
            "(:address IS NULL OR e.address LIKE %:address%) AND " +
            "(:beginAt IS NULL OR e.beginAt = :beginAt) AND " +
            "(:retired IS NULL OR e.retired = :retired) AND " +
            "(:agencyId IS NULL OR e.agency.id = :agencyId) AND " +
            "(:roleId IS NULL OR e.role.id = :roleId)")
    List<Employee> findEmployeesByCriteria(
            @Param("name") String name,
            @Param("phone") String phone,
            @Param("email") String email,
            @Param("address") String address,
            @Param("beginAt") Date beginAt,
            @Param("retired") boolean retired,
            @Param("agencyId") String agencyId,
            @Param("roleId") String roleId
    );
}
