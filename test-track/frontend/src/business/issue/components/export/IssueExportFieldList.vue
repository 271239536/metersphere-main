<template>
  <div>
    <el-row v-for="rowIndex in fieldRowCount" :key="rowIndex">
    <span v-for="(item, index) in fields"
          :key="item.id">
      <el-tooltip :content="item.name" :disabled="item.name.length < 6">
        <el-col :span="6" v-if="Math.floor(index / colCountEachRow) === rowIndex - 1">
          <el-checkbox
            v-model="item.enable"
            :disabled="item.disabled"
            @change="change">
            {{ item.name }}
          </el-checkbox>
        </el-col>
      </el-tooltip>
    </span>
    </el-row>
  </div>
</template>

<script>
export default {
  name: "IssueExportFieldList",
  props: ['fields'],
  data() {
    return {
      colCountEachRow: 4
    }
  },
  computed: {
    fieldRowCount() {
      if (!this.fields) {
        return 0;
      }
      return Math.ceil(this.fields.length / this.colCountEachRow);
    }
  },
  methods: {
    change(value) {
      this.$emit('enableChange', value);
    }
  }
}
</script>

<style scoped>
.el-row {
  margin-bottom: 10px;
}

:deep(.el-checkbox .el-checkbox__label) {
  overflow: hidden;
  white-space: nowrap;
  text-overflow: ellipsis;
  max-width: 100px;
  position: relative;
  top: 5px;
}
</style>
