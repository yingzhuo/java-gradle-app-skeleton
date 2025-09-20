/*
 * Copyright (c) 2024-2025 Unknown Company.
 * All rights reserved.
 */
package io.github.yingzhuo.showcase.core.controller.form;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.BindingResult;

import java.io.Serializable;

/**
 * @see io.github.yingzhuo.showcase.core.controller.SecurityController#login(LoginForm, BindingResult, HttpServletRequest)
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LoginForm implements Serializable {

	@Length(min = 5, max = 18, message = "用户名长度必须在{min}和{max}之间")
	@NotEmpty(message = "用户名不可为空")
	private String username;

	@NotEmpty(message = "密码不可为空")
	private String password;

}
