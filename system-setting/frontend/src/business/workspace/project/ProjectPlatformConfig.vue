<template>
  <div>
    <el-form :model="form" ref="form" label-width="100px" size="small" :rules="rules">
      <el-form-item
        :label-width="labelWidth"
        v-for="item in config.formItems"
        :key="item.name"
        :label="item.i18n ? $t(item.label) : item.label"
        :prop="item.name">
        <custom-filed-component :form="form"
                                :data="item"
                                class="custom-filed"
                                prop="defaultValue"
                                @change="handleChange"/>
        <el-button v-if="item.withProjectCheck"
                   :disabled="!form[item.name]"
                   @click="check"
                   type="primary"
                   class="checkButton">
          {{ $t('test_track.issue.check_id_exist') }}
        </el-button>
        <ms-instructions-icon v-if="item.instructionsIcon || item.instructionsTip" effect="light">
          <template>
            <img v-if="item.instructionsIcon"
                 :src="getPlatformImageUrl(config, item)"/>
            <span v-if="item.instructionsTip" v-html="item.instructionsTip">
            </span>
          </template>
        </ms-instructions-icon>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import MsInstructionsIcon from "metersphere-frontend/src/components/MsInstructionsIcon";
import {getCurrentWorkspaceId} from "metersphere-frontend/src/utils/token";
import {
  generatePlatformResourceUrl,
  getPlatformProjectInfo,
  getPlatformProjectOption,
  validateProjectConfig,
} from "@/api/platform-plugin";
import {getPlatformFormRules} from "metersphere-frontend/src/utils/platform";
import CustomFiledComponent from "metersphere-frontend/src/components/template/CustomFiledComponent";

export default {
  name: "ProjectPlatformConfig",
  components: {MsInstructionsIcon, CustomFiledComponent},
  props: {
    labelWidth: String,
    result: {
      type: Object,
      default() {
        return {}
      }
    },
    platformKey: String,
    projectConfig: {
      type: Object,
      default() {
        return {}
      },
    },
    project: {
      type: Object,
      default() {
        return {}
      }
    }
  },
  data() {
    return {
      form: {},
      rules: {},
      config: {}
    }
  },
  watch: {
    platformKey() {
      this.getPlatformProjectInfo();
    },
  },
  mounted() {
    this.getPlatformProjectInfo();
  },
  methods: {
    getPlatformProjectInfo() {
      getPlatformProjectInfo(this.platformKey)
        .then(r => {
          if (r.data) {
            let form = {};
            Object.assign(form, this.projectConfig);
            this.handleProjectConfigCompatible();

            r.data.formItems.forEach(item => {
              if (!item.options) {
                item.options = [];
              }
              // 设置默认值
              if (form[item.name]) {
                this.$set(item, 'defaultValue', form[item.name]);
              }
              // 获取级联选项值
              if (item.cascade && form[item.cascade]) {
                this.getCascadeOptions(item, () => {
                  // 没有选项值会被组件自动清空，获取下拉框选项之后，重新设置默认值
                  if (form[item.name]) {
                    this.$set(item, 'defaultValue', form[item.name]);
                  }
                });
              }
              this.form = form;
            });
            this.config = r.data;
            this.rules = getPlatformFormRules(this.config);
          }
        });
    },
    check() {
      validateProjectConfig(this.config.id, this.form)
        .then(() => {
          this.$success(this.$t("system.check_third_project_success"));
        });
    },
    handleProjectConfigCompatible() {
      this._handleProjectConfigCompatible("jiraKey");
      this._handleProjectConfigCompatible("tapdId");
      this._handleProjectConfigCompatible("azureDevopsId");
      this._handleProjectConfigCompatible("zentaoId");
    },
    _handleProjectConfigCompatible(prop) {
      if (!this.form[prop] && this.project[prop]) {
        // 兼容性处理，如果配置中没有值，项目中有值，是旧数据，则赋值
        this.form[prop] = this.project[prop];
      }
    },
    validate() {
      return new Promise((resolve, reject) => {
        this.$refs['form'].validate((valid) => {
          if (!valid) {
            reject();
          }
          resolve();
        });
      });
    },
    handleChange(name) {
      this.config.formItems.forEach(item => {
        if (item.cascade === name) {
          this.$set(item, 'options', []);
          this.getCascadeOptions(item);
        }
      });
    },
    getCascadeOptions(item, callback) {
      getPlatformProjectOption(this.config.id, {
        platform: this.platformKey,
        optionMethod: item.optionMethod,
        workspaceId: getCurrentWorkspaceId(),
        projectConfig: JSON.stringify(this.form)
      }).then((r) => {
        this.$set(item, 'options', r.data);
        if (callback) {
          callback();
        }
      });
    },
    getPlatformImageUrl(config, item) {
      return generatePlatformResourceUrl(config.id, item.instructionsIcon);
    }
  }
}
</script>

<style scoped>

.custom-filed :deep(.el-select) {
  width: 260px !important;
}

.custom-filed :deep(.el-input, .el-textarea) {
  width: 80% !important;
}

.checkButton {
  margin-left: 5px;
}
</style>
