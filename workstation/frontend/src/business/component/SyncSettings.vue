<template>
  <div>
    <el-row>
      <el-col :span="4">{{ $t('api_test.mock.req_param') + ":" }}</el-col>
      <el-col :span="20" style="color: var(--primary_color)">
        <el-checkbox v-model="fromData.headers">{{ "Header" + '\xa0\xa0' }}</el-checkbox>
        <el-checkbox v-model="fromData.query">{{ $t('api_test.definition.request.query_param') }}</el-checkbox>
        <el-checkbox v-model="fromData.rest">{{ $t('api_test.definition.request.rest_param') }}</el-checkbox>
        <el-checkbox v-model="fromData.body">{{ $t('api_test.request.body') }}</el-checkbox>
      </el-col>
    </el-row>
    <el-row>
      <el-col :span="4">{{ $t('api_test.definition.request.other_config') + ":" }}</el-col>
      <el-col :span="20" style="color: var(--primary_color)">
        <el-checkbox v-model="fromData.delNotSame">{{ $t('workstation.delNotSame') }}</el-checkbox>
      </el-col>
    </el-row>
  </div>
</template>

<script>
export default {
  name: "SyncSettings",
  data() {
    return {
      fromData: {
        protocol: true,
        method: true,
        path: true,
        headers: true,
        query: true,
        rest: true,
        body: true,
        delNotSame: true,
        runError: true,
        unRun: true,
      }
    }
  }
}
</script>

<style scoped>
.el-row {
  margin-bottom: 3px;
}
</style>
