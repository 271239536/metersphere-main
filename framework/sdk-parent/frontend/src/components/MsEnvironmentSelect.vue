<template>
  <span>
    <el-select :disabled="isReadOnly" v-model="environmentId" size="small" class="environment-select" filterable
               :placeholder="$t('api_test.definition.request.run_env')" clearable @clear="clear">
      <el-option v-for="(environment, key) in environments" :key="key"
                 :label="environment.name"
                 :value="environment.id"/>
      <el-button class="environment-button" size="mini" type="primary" @click="openEnvironmentConfig">
        {{ $t('api_test.environment.environment_config') }}
      </el-button>
      <template v-slot:empty>
        <!--这里只做没有可搜索内容时使用，否则如果没有符合搜索条件的，也会显示该项，与上面的btn重复显示 -->
        <div class="empty-environment" v-if="environments.length===0">
          <el-button class="environment-button" size="mini" type="primary" @click="openEnvironmentConfig">
            {{ $t('api_test.environment.environment_config') }}
          </el-button>
        </div>
      </template>
    </el-select>
    <!-- 环境 -->
    <api-environment-config ref="environmentConfig" @close="environmentConfigClose"/>
  </span>
</template>

<script>
import {parseEnvironment} from "../model/EnvironmentModel";
// import {getEnvironmentByProjectId} from "../../../../api/environment";

export default {
  name: "MsEnvironmentSelect",
  components: {
    // ApiEnvironmentConfig: () => import('@/business/environment/components/ApiEnvironmentConfig')
  },
  data() {
    return {
      environments: [],
      environment: undefined,
      isShow: true,
      environmentId: ""
    }
  },
  props: ['projectId', 'isReadOnly', 'useEnvironment'],
  created() {
    this.getEnvironments();
  },
  watch: {
    projectId() {
      this.getEnvironments();
    },
    environment() {
      this.$emit('setEnvironment', this.environment);
    },
    environmentId() {
      this.environmentChange(this.environmentId);
    },
    useEnvironment() {
      this.getEnvironments();
    }
  },
  methods: {
    refreshEnvironment() {
      this.$emit('setEnvironment', this.environment);
    },
    clear() {
      this.$emit('setEnvironment', {});
    },
    getEnvironments() {
      if (this.projectId) {
        getEnvironmentByProjectId(this.projectId).then(response => {
          this.environments = response.data;
          this.environments.forEach(environment => {
            parseEnvironment(environment);
            if (this.useEnvironment && this.useEnvironment === environment.id) {
              this.environmentId = this.useEnvironment;
            }
          });
        });
      } else {
        this.environmentId = undefined;
      }
    },
    openEnvironmentConfig() {
      if (!this.projectId) {
        this.$error(this.$t('api_test.select_project'));
        return;
      }
      this.$refs.environmentConfig.open(this.projectId, this.environmentId);
    },
    environmentChange(value) {
      for (let i in this.environments) {
        if (this.environments[i].id === value) {
          this.environment = this.environments[i];
          break;
        }
      }
    },
    environmentConfigClose() {
      this.getEnvironments();
    },
  }
}
</script>

<style scoped>


.environment-button {
  margin-left: 20px;
  padding: 7px;
}

.ms-api-header-select {
  margin-left: 20px;
  min-width: 100px;
}

</style>
