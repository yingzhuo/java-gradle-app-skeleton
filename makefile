MAKEFILE_PATH := $(shell dirname $(realpath $(firstword $(MAKEFILE_LIST))))
GRADLEW := $(MAKEFILE_PATH)/gradlew
GRADLE := $(shell which gradle)

usage:
	@echo '======================================================================'
	@echo 'usage (default)            : 显示本菜单'
	@echo 'clean                      : 清理本项目构建产物'
	@echo 'clean-buildsrc             : 清理构建逻辑和项目构建产物'
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
	@echo '----------------------------------------------------------------------'
	@echo 'run-core-app               : 运行核心程序'
	@echo '======================================================================'

clean:
	$(GRADLEW) 'clean'

clean-buildsrc:
	$(GRADLEW) ':buildSrc:clean'

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
	$(GRADLE) 'wrapper'

stop-gradle-daemon:
	$(GRADLEW) --stop

update-license-header:
	$(GRADLEW) licenseFormat

push-to-vcs: update-license-header
	$(GRADLEW) 'pushToVcs'

run-core-app:
	$(GRADLEW) :projects-app:core:bootRun

.PHONY: usage \
	clean clean-buildsrc \
	refresh-dependencies \
	compile build check test \
	build-docker-image remove-docker-image \
	setup-gradle-wrapper update-license-header push-to-vcs \
	run-core-app
