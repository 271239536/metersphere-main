<template>
  <div>
    <el-card>
      <el-form :model="request" label-width="auto" ref="request">
        <el-form-item :label="$t('api_test.request.name')" prop="name">
          <el-input v-model="request.name" maxlength="200" show-word-limit size="small" />
        </el-form-item>
        <el-form-item :label="$t('api_test.definition.api_type')" prop="protocol">
          <el-radio v-model="request.protocol" label="HTTP">HTTP</el-radio>
          <el-radio v-model="request.protocol" label="DUBBO">DUBBO</el-radio>
          <el-radio v-model="request.protocol" label="SQL">SQL</el-radio>
          <el-radio v-model="request.protocol" label="TCP">TCP</el-radio>
        </el-form-item>
      </el-form>
      <!--不同协议请求-->
      <ms-debug-http-page
        :scenario="true"
        :current-api="request"
        @saveAs="editApi"
        :currentProtocol="request.protocol"
        v-if="request.protocol === 'HTTP'" />
      <ms-debug-jdbc-page
        :scenario="true"
        :currentProtocol="request.protocol"
        @saveAs="editApi"
        v-if="request.protocol === 'SQL'" />
      <ms-debug-tcp-page
        :scenario="true"
        :currentProtocol="request.protocol"
        @saveAs="editApi"
        v-if="request.protocol === 'TCP'" />
      <ms-debug-dubbo-page
        :scenario="true"
        :currentProtocol="request.protocol"
        @saveAs="editApi"
        v-if="request.protocol === 'DUBBO'" />
    </el-card>
  </div>
</template>

<script>
import MsDebugHttpPage from '../../definition/components/debug/DebugHttpPage';
import MsDebugJdbcPage from '../../definition/components/debug/DebugJdbcPage';
import MsDebugTcpPage from '../../definition/components/debug/DebugTcpPage';
import MsDebugDubboPage from '../../definition/components/debug/DebugDubboPage';
import { getCurrentProjectID } from 'metersphere-frontend/src/utils/token';
import { getUUID } from 'metersphere-frontend/src/utils';

export default {
  name: 'ApiCustomize',
  props: {
    node: {},
    request: {},
  },
  components: {
    MsDebugHttpPage,
    MsDebugJdbcPage,
    MsDebugTcpPage,
    MsDebugDubboPage,
  },
  data() {
    return {
      loading: false,
    };
  },
  methods: {
    remove() {
      this.$emit('remove', this.request, this.node);
    },
    active(item) {
      item.active = !item.active;
      this.reload();
    },
    editApi(row) {
      let name = this.request.name;
      Object.assign(this.request, row.request);
      this.request.name = name;
      if (this.request.protocol === 'HTTP') {
        if (row.url) {
          // 自定义请求根据是否勾选判断是否需要引用环境
          this.request.url = row.url;
          this.request.path = row.url;
        } else {
          this.request.url = row.path;
          this.request.path = row.path;
        }
        this.request.method = row.method;
      }
      this.request.resourceId = getUUID();
      this.request.projectId = getCurrentProjectID();
      let obj = {};
      Object.assign(obj, this.request);
      this.$emit('addCustomizeApi', obj);
    },
    reload() {
      this.loading = true;
      this.$nextTick(() => {
        this.loading = false;
      });
    },
  },
};
</script>

<style scoped></style>
