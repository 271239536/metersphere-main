<template>
  <el-dropdown @command="handleCommand" class="scenario-ext-btn">
    <el-link type="primary" :underline="false">
      <el-icon class="el-icon-more"></el-icon>
    </el-link>
    <el-dropdown-menu slot="dropdown">
      <el-dropdown-item command="ref">{{ $t('api_test.automation.view_ref') }}</el-dropdown-item>
      <el-dropdown-item command="history" v-modules="['history']">
        {{ $t('commons.execute_history') }}
      </el-dropdown-item>
      <el-dropdown-item
        command="create_performance"
        v-modules="['performance']"
        v-permission="['PROJECT_API_DEFINITION:READ+CREATE_PERFORMANCE']">
        {{ $t('api_test.create_performance_test') }}
      </el-dropdown-item>
    </el-dropdown-menu>
  </el-dropdown>
</template>

<script>
import { hasPermission } from 'metersphere-frontend/src/utils/permission';

export default {
  name: 'MsApiCaseTableExtendBtns',
  components: {},
  props: {
    row: Object,
  },
  data() {
    return {
      planVisible: false,
    };
  },

  methods: {
    hasPermission,
    handleCommand(cmd) {
      if (this.row.id) {
        switch (cmd) {
          case 'ref':
            this.$emit('showCaseRef', this.row);
            break;
          case 'create_performance':
            if (!hasPermission('PROJECT_PERFORMANCE_TEST:READ+CREATE')) {
              this.$warning(this.$t('api_test.create_performance_test_tips'));
              break;
            }
            this.$emit('showEnvironment', this.row);
            break;
          case 'history':
            this.$emit('showHistory', this.row);
            break;
        }
      } else {
        this.$warning(this.$t('api_test.automation.save_case_info'));
      }
    },
    createPerformance(row, environment) {
      this.$emit('createPerformance', row, environment);
    },
  },
};
</script>

<style scoped>
.scenario-ext-btn {
  margin-left: 10px;
}
</style>
