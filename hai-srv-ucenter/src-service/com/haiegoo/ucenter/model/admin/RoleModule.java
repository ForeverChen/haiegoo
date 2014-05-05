package com.haiegoo.ucenter.model.admin;

import java.io.Serializable;

public class RoleModule implements Serializable {

	private static final long serialVersionUID = -514087846786714697L;

	/**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column role_module.module_id
     *
     * @ibatorgenerated Thu Nov 03 18:04:39 CST 2011
     */
    private Integer moduleId;

    /**
     * This field was generated by Apache iBATIS ibator.
     * This field corresponds to the database column role_module.role_id
     *
     * @ibatorgenerated Thu Nov 03 18:04:39 CST 2011
     */
    private Integer roleId;

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column role_module.module_id
     *
     * @return the value of role_module.module_id
     *
     * @ibatorgenerated Thu Nov 03 18:04:39 CST 2011
     */
    public Integer getModuleId() {
        return moduleId;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column role_module.module_id
     *
     * @param moduleId the value for role_module.module_id
     *
     * @ibatorgenerated Thu Nov 03 18:04:39 CST 2011
     */
    public void setModuleId(Integer moduleId) {
        this.moduleId = moduleId;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method returns the value of the database column role_module.role_id
     *
     * @return the value of role_module.role_id
     *
     * @ibatorgenerated Thu Nov 03 18:04:39 CST 2011
     */
    public Integer getRoleId() {
        return roleId;
    }

    /**
     * This method was generated by Apache iBATIS ibator.
     * This method sets the value of the database column role_module.role_id
     *
     * @param roleId the value for role_module.role_id
     *
     * @ibatorgenerated Thu Nov 03 18:04:39 CST 2011
     */
    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }
}