package com.example.b1sfin.service;

import com.example.b1sfin.model.Agency;
import com.example.b1sfin.model.Employee;
import com.example.b1sfin.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.b1sfin.lib.FinalString.EMPLOYEE_KEY_PREFIX;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final RedisTemplate<String,Object> redisTemplate;
    // connect with MySQL
    public List<Employee> findAll(){
        return employeeRepository.findAll();
    }
    public List<Employee> getByRetired(boolean retired){
        return employeeRepository.getEmployeesByRetired(retired);
    }
    public List<Employee> getByAgency(Agency agency){
        return employeeRepository.getEmployeesByAgency(agency);
    }
    public boolean save(Employee employee){
        try{
            if(employeeRepository.existsById(employee.getId())){
                return false;
            }else{
                employeeRepository.save(employee);
                return true;
            }

        }catch (Exception e){
            return false;
        }
    }
    public boolean delete(Employee employee){
        try{
            if(employeeRepository.existsById(employee.getId())){
                employeeRepository.delete(employee);
                return true;
            }else {
                return false;
            }
        }catch (Exception e){
            return false;
        }
    }
    public boolean update(Employee employee){
        try{
            if(employeeRepository.existsById(employee.getId())){
                employeeRepository.save(employee);
                return true;
            }else{
                return false;
            }
        }catch (Exception e){
            return false;
        }
    }
    public List<Employee> findByCriteria(Employee employee) {
        return employeeRepository.findEmployeesByCriteria(
                employee.getName(),
                employee.getPhone(),
                employee.getEmail(),
                employee.getAddress(),
                employee.getBeginAt(),
                employee.isRetired(),
                employee.getAgency() != null ? employee.getAgency().getId() : null,
                employee.getRole() != null ? employee.getRole().getId() : null
        );
    }

    // connect with redis
    public boolean saveRedis(Employee employee){
        try{
            String keyRedis = EMPLOYEE_KEY_PREFIX+employee.getId();
            System.out.println("Key:"+keyRedis);
            redisTemplate.opsForValue().set(keyRedis,employee);
            return true;

        }catch (Exception e){
            return false;
        }
    }
    public boolean updateRedis(Employee employee){
        try{
            String redisEmployee = EMPLOYEE_KEY_PREFIX+employee.getId();
            if(redisTemplate.opsForValue().get(redisEmployee)!=null){
                redisTemplate.opsForValue().set(EMPLOYEE_KEY_PREFIX+employee.getId(),employee);
                return true;
            }else {
                return false;
            }
        }catch (Exception e){
            return false;
        }
    }

    public boolean deleteRedis(String id){
        try{
            String redisEmployee = EMPLOYEE_KEY_PREFIX+id;
            if(redisTemplate.opsForValue().get(redisEmployee)!=null){
                redisTemplate.delete(redisEmployee);
                return true;
            }else {
                return false;
            }
        }catch (Exception e){
            return false;
        }
    }
    public Employee getRedis(String id){
        try{
            String redisEmployee = EMPLOYEE_KEY_PREFIX+id;
            return (Employee) redisTemplate.opsForValue().get(redisEmployee);
        }catch (Exception e){
            return null;
        }
    }

}
