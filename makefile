MAKEFILE_PATH := $(shell dirname $(realpath $(firstword $(MAKEFILE_LIST))))
GRADLEW := $(MAKEFILE_PATH)/gradlew
GRADLE := $(shell which gradle)

usage:
	@echo '======================================================================'
	@echo 'usage (default)            : 显示本菜单'
	@echo 'clean                      : 清理本项目构建产物'
	@echo 'clean-buildsrc             : 清理构建逻辑和项目构建产物'
	@echo 'purge                      : 大清理'
	@echo 'refresh-dependencies       : 更新依赖'
	@echo 'compile                    : 编译项目'
	@echo 'build                      : 构建项目'
	@echo 'build-docker-image         : 构建docker镜像'
	@echo 'remove-docker-image        : 删除docker镜像'
	@echo 'test                       : 执行单元测试'
	@echo 'check                      : 检查代码风格'
	@echo 'setup-gradle-wrapper       : 设置gradle-wrapper'
	@echo 'stop-gradle-daemon         : 停止gradle-daemon'
	@echo 'update-license-header      : 添加代码的许可证头'
	@echo 'push-to-vcs                : 提交文件'
	@echo 'describe-project           : 描述项目目录'
	@echo 'describe-task              : 描述gradle任务'
	@echo '----------------------------------------------------------------------'
	@echo 'run-core-app               : 运行核心程序'
	@echo '======================================================================'

clean:
	$(GRADLEW) 'clean'

clean-buildsrc:
	$(GRADLEW) ':gradle:clean'

purge: clean clean-buildsrc
	@find $(MAKEFILE_PATH) -type f -name ".DS_Store" -delete
	@find $(MAKEFILE_PATH) -type f -name "*.log" -delete
	@rm -rf $(MAKEFILE_PATH)/.gradle/
	@rm -rf $(MAKEFILE_PATH)/gradle/.gradle/

refresh-dependencies:
	$(GRADLEW) -U

compile:
	$(GRADLEW) 'classes'

build:
	$(GRADLEW) 'build' -x 'test' -x 'check'

build-docker-image:
	$(GRADLEW) 'buildDockerImage' -x 'test' -x 'check'

remove-docker-image:
	$(GRADLEW) 'removeDockerImage'

check:
	$(GRADLEW) 'check'

test:
	$(GRADLEW) 'test'

setup-gradle-wrapper:
	$(GRADLEW) 'wrapper'

stop-gradle-daemon:
	$(GRADLEW) --stop

update-license-header:
	$(GRADLEW) 'licenseFormat'

push-to-vcs: update-license-header
	$(GRADLEW) 'pushToVcs'

describe-project:
	$(GRADLEW) 'projects'

describe-task:
	$(GRADLEW) 'tasks'

run-core-app: build
	$(GRADLEW) ':projects-app:core:bootRun'

.PHONY: usage \
	clean clean-buildsrc purge \
	refresh-dependencies \
	compile build check test \
	build-docker-image remove-docker-image \
	setup-gradle-wrapper update-license-header push-to-vcs \
	describe-project describe-task \
	run-core-app
