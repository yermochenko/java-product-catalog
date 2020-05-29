package org.itstep.web.action;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.itstep.logic.LogicException;

public interface Action {
	Result exec(HttpServletRequest req, HttpServletResponse resp) throws LogicException;

	public static class Result {
		private final String url;
		private final ResultType type;
		private List<Pair> parameters = new ArrayList<>();

		public Result(String url) {
			this(url, ResultType.REDIRECT);
		}

		public Result(String url, ResultType type) {
			this.url = url;
			this.type = type;
		}

		public Result add(String name, String value) {
			parameters.add(new Pair(name, value));
			return this;
		}

		public String getUrl() {
			return url;
		}

		public ResultType getType() {
			return type;
		}

		public String getParameters() {
			return parameters.stream().map((parameter) -> parameter.toString()).collect(Collectors.joining("&"));
		}

		private static class Pair {
			private final String name;
			private final String value;

			public Pair(String name, String value) {
				this.name = name;
				this.value = value;
			}

			@Override
			public String toString() {
				String encodedValue = null;
				try {
					encodedValue = URLEncoder.encode(value, "UTF-8");
				} catch(UnsupportedEncodingException e) {
					encodedValue = value;
				}
				return String.join("=", name, encodedValue);
			}
		}
	}

	public static enum ResultType {
		FORWARD,
		REDIRECT
	}
}
