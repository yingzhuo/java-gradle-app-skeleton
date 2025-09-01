/*
 * Copyright (c) 2024-2025 Unknown Company.
 * All rights reserved.
 */
package io.github.yingzhuo.showcase.core.controller;

import io.github.yingzhuo.showcase.core.controller.form.LoginForm;
import io.github.yingzhuo.showcase.web.api.ApiResult;
import io.github.yingzhuo.showcase.web.controller.ControllerSupport;
import io.github.yingzhuo.showcase.websecurity.annotation.CurrentRequestDetails;
import io.github.yingzhuo.showcase.websecurity.annotation.CurrentUserDetails;
import io.github.yingzhuo.showcase.websecurity.jwt.JwtCreator;
import io.github.yingzhuo.showcase.websecurity.user.UserPrincipal;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static io.github.yingzhuo.showcase.websecurity.util.JwtCreatorUtils.createToken;
import static io.github.yingzhuo.showcase.websecurity.util.UserDetailsServiceUtils.loadUser;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/security")
public class SecurityController extends ControllerSupport {

	private final JwtCreator jwtCreator;
	private final UserDetailsService userDetailsService;
	private final PasswordEncoder passwordEncoder;

	@PostMapping("/login")
	public Object login(@Validated LoginForm form, BindingResult bindingResult, HttpServletRequest request) {

		super.checkDataBinding(bindingResult);

		var userDetails = loadUser(userDetailsService, passwordEncoder, form.getUsername(), form.getPassword());
		var ude = (UserPrincipal) userDetails;
		if (userDetails != null) {
			var jwt = createToken(jwtCreator, (UserPrincipal) userDetails);

			request.setAttribute("jwt", jwt);

			return ApiResult.builder()
				.dataEntry("userId", ude.getId())
				.dataEntry("username", ude.getUsername())
				.dataEntry("token", jwt)
				.build();
		} else {
			return ApiResult.builder()
				.code("001")
				.errorMessage("用户名或密码错误")
				.build();
		}
	}

	@PostMapping("/show")
	public Object show(@CurrentUserDetails UserPrincipal userDetails, @CurrentRequestDetails Object requestDetails) {
		log.debug("userDetails: {}", userDetails);
		log.debug("requestDetails: {}", requestDetails);

		return ApiResult.builder()
			.dataEntry("userDetails", userDetails)
			.build();
	}

}
