import org.gradle.api.Project

import java.time.LocalDateTime

/**
 * 构建逻辑的共享函数
 */
class SharedFunctions {

	static String getTimestamp(String formatPattern = 'yyyyMMddHHmmss') {
		return LocalDateTime.now().format(formatPattern)
	}

	static List<String> getLeafProjectNames(Project rootProject) {
		return rootProject.allprojects
			.findAll {
				it.subprojects.isEmpty()
			}
			.collect {
				it.displayName
					.replace("project '", '')
					.replace("'", '')
			}
	}

}
