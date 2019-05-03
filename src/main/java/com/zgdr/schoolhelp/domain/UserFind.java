package com.zgdr.schoolhelp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author 星夜、痕
 * @version 1.0
 * @since 2019/4/27
 **/
@JsonIgnoreProperties(value = {"phone","password","sex"})

public class UserFind extends User {

}
