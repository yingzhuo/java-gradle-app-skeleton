/*
 * Copyright (c) 2024-2025 Unknown Company.
 * All rights reserved.
 */
package io.github.yingzhuo.showcase.web.api;

import lombok.*;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ApiResult implements Serializable {

	@NonNull
	private String code = "200";

	@NonNull
	private Map<String, Object> data = new HashMap<>();

	@Nullable
	private String errorMessage;

	public static Builder builder() {
		return new Builder();
	}

	// @formatter:off
	@NoArgsConstructor(access = AccessLevel.PACKAGE)
	public static class Builder {
		private final Map<String, Object> data = new HashMap<>();

		@NonNull
		private String code = "000";

		@Nullable
		private String errorMessage;

		public Builder code(String code) {
			this.code = code;
			return this;
		}

		public Builder dataEntry(String key, Object value) {
			this.data.put(key, value);
			return this;
		}

		public Builder errorMessage(String errorMessage) {
			this.errorMessage = errorMessage;
			return this;
		}

		public ApiResult build() {
			var result = new ApiResult();
			result.code = this.code;
			result.data = this.data;
			result.errorMessage = this.errorMessage;
			return result;
		}
	}
	// @formatter:on

}
