MAKEFILE_PATH := $(shell dirname $(realpath $(firstword $(MAKEFILE_LIST))))
GRADLEW := $(MAKEFILE_PATH)/gradlew
GRADLE := $(shell which gradle)

.PHONY: \
usage clean clean-buildsrc purge update-dependencies compile build check test build-docker-image remove-docker-image update-gradle-wrapper update-license-header push-to-vcs

usage:
	@echo '======================================================================'
	@echo 'usage (default)            : 显示本菜单'
	@echo 'clean                      : 清理本项目构建产物'
	@echo 'clean-buildsrc             : 清理构建逻辑和项目构建产物'
	@echo 'purge                      : 大清理'
	@echo 'update-dependencies        : 更新依赖'
	@echo 'compile                    : 编译项目'
	@echo 'build                      : 构建项目'
	@echo 'build-docker-image         : 构建docker镜像'
	@echo 'remove-docker-image        : 删除docker镜像'
	@echo 'test                       : 执行单元测试'
	@echo 'check                      : 检查代码风格'
	@echo 'update-gradle-wrapper      : 设置gradle-wrapper'
	@echo 'stop-gradle-daemon         : 停止gradle-daemon'
	@echo 'update-license-header      : 添加代码的许可证头'
	@echo 'push-to-vcs                : 提交文件'
	@echo '======================================================================'

clean:
	@$(GRADLEW) 'clean' -q

clean-buildsrc:
	@$(GRADLEW) ':gradle:clean' -q

purge: clean clean-buildsrc
	@find $(MAKEFILE_PATH) -type f -name ".DS_Store" -delete
	@find $(MAKEFILE_PATH) -type f -name "*.log" -delete
	@rm -rf $(MAKEFILE_PATH)/.gradle/
	@rm -rf $(MAKEFILE_PATH)/gradle/.gradle/

update-dependencies:
	@$(GRADLEW) -U

compile:
	@$(GRADLEW) 'classes'

build:
	@$(GRADLEW) 'build' -x 'test' -x 'check'

build-docker-image:
	@$(GRADLEW) 'buildDockerImage' -x 'test' -x 'check'

remove-docker-image:
	@$(GRADLEW) 'removeDockerImage'

check:
	@$(GRADLEW) 'check'

test:
	@$(GRADLEW) 'test'

update-gradle-wrapper:
	@$(GRADLEW) 'wrapper' -q

stop-gradle-daemon:
	@$(GRADLEW) --stop -q

update-license-header:
	@$(GRADLEW) 'licenseFormat' -q

push-to-vcs: update-license-header
	@$(GRADLEW) 'pushToVcs'
