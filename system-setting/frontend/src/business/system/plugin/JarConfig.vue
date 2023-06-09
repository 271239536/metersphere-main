<template>
  <el-form
    v-loading="loading"
    label-width="105px"
    :model="currentConfig"
    :rules="rules"
    ref="form">
    <el-row>
      <el-col :span="11">
        <el-form-item label="使用场景">
          <el-select size="small" v-model="currentConfig.scenario" clearable>
            <el-option v-for="item in scenarioOptions" :key="item.id" :label="item.text" :value="item.value"/>
          </el-select>
        </el-form-item>
      </el-col>

      <el-col :span="1">
        <el-divider direction="vertical"/>
      </el-col>

      <el-col :span="12">
        <el-upload
          class="jar-upload"
          drag
          action="#"
          :http-request="upload"
          :limit="1"
          :beforeUpload="uploadValidate"
          :on-remove="handleRemove"
          :on-exceed="handleExceed"
          :file-list="fileList"
          ref="fileUpload">
          <i class="el-icon-upload"></i>
          <div class="el-upload__text" v-html="$t('load_test.upload_tips')"></div>
          <div class="el-upload__tip" slot="tip">{{ $t('api_test.jar_config.upload_tip') }}</div>
        </el-upload>
      </el-col>

      <div class="buttons">
        <el-button type="primary" size="small" @click="save()">{{ $t('commons.confirm') }}</el-button>
      </div>

    </el-row>
  </el-form>
</template>

<script>
import {addPlugin} from "../../../api/plugin";

export default {
  name: "JarConfig",
  data() {
    return {
      visible: false,
      loading: false,
      currentConfig: {
        name: '',
        description: '',
        fileName: '',
        scenario: 'api'
      },
      scenarioOptions: [
        {text: '接口测试', value: 'api'},
        {text: '平台对接', value: "platform"},
      ],
      rules: {
        name: [
          {required: true, message: this.$t('commons.input_name'), trigger: 'blur'},
          {max: 200, message: this.$t('commons.input_limit', [1, 200]), trigger: 'blur'}
        ],
        execEntry: [
          {required: true, message: this.$t('plugin.script_entry'), trigger: 'blur'},
          {max: 300, message: this.$t('commons.input_limit', [1, 300]), trigger: 'blur'}
        ],
      },
      fileList: []
    }
  },
  props: {
    readOnly: {
      type: Boolean,
      default: false
    },
    config: {
      type: Object,
      default() {
        return {};
      }
    },
    callback: {
      type: Function
    },
  },
  watch: {
    config() {
      this.currentConfig = {
        name: '',
        description: '',
        fileName: ''
      };
      if (this.config.fileName) {
        this.fileList = [{name: this.config.fileName}];
      } else {
        this.fileList = [];
      }
      Object.assign(this.currentConfig, this.config);
    }
  },
  mounted() {
    Object.assign(this.currentConfig, this.config);
  },
  methods: {
    upload(file) {
      this.fileList.push(file.file)
    },
    handleExceed(files, fileList) {
      this.$warning(this.$t('test_track.case.import.upload_limit_count'));
    },
    handleRemove(file, fileList) {
      this.fileList = [];
    },
    uploadValidate(file, fileList) {
      let suffix = file.name.substring(file.name.lastIndexOf('.') + 1);
      if (suffix !== 'jar') {
        this.$warning(this.$t('api_test.api_import.suffixFormatErr'));
        return false;
      }
      return true;
    },
    save() {
      this.$refs['form'].validate((valid) => {
        if (!valid) {
          this.fileList = [];
          return;
        }
        if (this.fileList <= 0) {
          this.$warning(this.$t('commons.please_upload'));
          return;
        }
        this.loading = addPlugin(this.currentConfig.scenario, this.fileList[0]).then(() => {
          this.$success(this.$t('organization.integration.successful_operation'));
          this.$emit("close");
          this.fileList = [];
        });
      });
    },
    clear() {
      this.$refs['form'].clearValidate();
      this.fileList = [];
    }
  }
}
</script>

<style scoped>

.el-divider {
  height: 200px;
}

.jar-upload {
  text-align: center;
  margin: auto 0;
}

.jar-upload :deep(.el-upload) {
  width: 100%;
  max-width: 350px;
}

.jar-upload :deep(.el-upload-dragger) {
  width: 100%;
}

.el-form {
  border: solid #E1E1E1 1px;
  margin: 10px 0;
  padding: 20px 10px;
  border-radius: 3px;
}

.buttons {
  margin-top: 20px;
  margin-bottom: -10px;
  float: right;
}

</style>
