package my.learn.mireaffjspringsecurity.service;


import my.learn.mireaffjspringsecurity.entity.RoleEntity;
import my.learn.mireaffjspringsecurity.entity.UserRole;

public interface RoleService {


    RoleEntity findRoleByName(UserRole role);

}
